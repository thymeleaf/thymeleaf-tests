/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.spring5.reactive;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.thymeleaf.context.IContext;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import reactor.core.publisher.Flux;

public abstract class AbstractSpring5ReactiveTest {

    private static SpringWebFluxTemplateEngine templateEngine;
    private static DataBufferFactory bufferFactory;
    private static Charset charset;

    // This array will contain the chunk sizes we will consider interesting for our tests
    private static int[] testResponseChunkSizes;



    static {

        testResponseChunkSizes = new int[115];
        testResponseChunkSizes[0] = Integer.MAX_VALUE; // Unlimited
        testResponseChunkSizes[1] = 65536;
        testResponseChunkSizes[2] = 32768;
        testResponseChunkSizes[3] = 16384;
        testResponseChunkSizes[4] = 8192;
        testResponseChunkSizes[5] = 4096;
        testResponseChunkSizes[6] = 3072;
        testResponseChunkSizes[7] = 2048;
        testResponseChunkSizes[8] = 1024;
        testResponseChunkSizes[9] = 513;
        testResponseChunkSizes[10] = 512;
        testResponseChunkSizes[11] = 511;
        testResponseChunkSizes[12] = 500;
        testResponseChunkSizes[13] = 256;
        testResponseChunkSizes[14] = 128;
        for (int i = 1; i <= 100; i++) {
            testResponseChunkSizes[115 - i] = i;
        }

    }




    @BeforeClass
    public static void initTemplateEngine() {

        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(ReactiveTestUtils.TEMPLATE_PATH_BASE);
        templateResolver.setSuffix(".html");

        templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        bufferFactory = new DefaultDataBufferFactory();

        charset = Charset.forName("UTF-8");

    }





    protected static void testTemplate(
            final String template, final Set<String> markupSelectors, final IContext context,
            final String result) throws Exception {
        for (final int templateResponseChunkSize : testResponseChunkSizes) {
            testTemplate(template, markupSelectors, context, result, templateResponseChunkSize);
        }
    }

    private static void testTemplate(
            final String template, final Set<String> markupSelectors, final IContext context,
            final String result, final int responseMaxChunkSizeBytes) throws Exception {

        final String dataDriverVariableName = detectDataDriver(context);
        final boolean isDataDriven = dataDriverVariableName != null;

        List<DataBuffer> resultBuffers = null;
        try {
            final Publisher<DataBuffer> resultStream =
                    templateEngine.processStream(template, markupSelectors, context, bufferFactory, charset, responseMaxChunkSizeBytes);

            resultBuffers = Flux.from(resultStream).collectList().block();
        } catch (final Exception e) {
            throw new TemplateProcessingException(
                    "Error happened while executing reactive test for template " + template + " with markup " +
                    "selectors " + markupSelectors + ", context with variables " + context.getVariableNames() + " and " +
                    "response chunk size of " + responseMaxChunkSizeBytes + " bytes.", e);
        }

        if (responseMaxChunkSizeBytes != Integer.MAX_VALUE) {
            for (final DataBuffer resultBuffer : resultBuffers) {
                Assert.assertTrue("Buffer returned by stream is of size larger than " + responseMaxChunkSizeBytes, resultBuffer.readableByteCount() <= responseMaxChunkSizeBytes);
            }
        } else {
            if (!isDataDriven) {
                final int bufferCount = resultBuffers.size();
                Assert.assertTrue("No limit set on buffer size, and non-data-driven: there should only be one result buffer instead of " + bufferCount, bufferCount == 1);
            }
        }

        final String resultStr =
                resultBuffers
                        .stream()
                        .map((buffer) -> ReactiveTestUtils.bufferAsString(buffer, charset))
                        .map(ReactiveTestUtils::normalizeResult) // Note we NORMALIZE before joining it all
                        .collect(Collectors.joining());

        final String expected =
                ReactiveTestUtils.readExpectedNormalizedResults(result, charset);

        Assert.assertEquals(expected, resultStr);

    }



    private static String detectDataDriver(final IContext context) {

        final Set<String> contextVariableNames = context.getVariableNames();
        for (final String contextVariableName : contextVariableNames) {
            final Object contextVariableValue = context.getVariable(contextVariableName);
            if (contextVariableValue instanceof IReactiveDataDriverContextVariable) {
                return contextVariableName;
            }
        }
        return null;

    }

    
}
