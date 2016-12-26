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
package org.thymeleaf.spring5.reactive.datadriven;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringWebReactiveTemplateEngine;
import org.thymeleaf.spring5.reactive.DataBufferTestUtils;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import reactor.core.publisher.Flux;

public final class ReactiveDataDriven01Test {


    private static final String TEMPLATE_PATH_BASE = "spring5/reactive/datadriven/";

    private static SpringWebReactiveTemplateEngine templateEngine;
    private static DataBufferFactory bufferFactory;
    private static Charset charset;


    @BeforeClass
    public static void initTemplateEngine() {

        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(TEMPLATE_PATH_BASE);
        templateResolver.setSuffix(".html");

        templateEngine = new SpringWebReactiveTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        bufferFactory = new DefaultDataBufferFactory();

        charset = Charset.forName("UTF-8");

    }



    @Test
    public void testDataDriven01() throws Exception {

        final Context ctx1 = new Context();

        final Publisher<DataBuffer> stream =
                templateEngine.processStream("datadriven01", null, ctx1, bufferFactory, charset);

        final List<String> strs = Flux.from(stream).map((buffer) -> DataBufferTestUtils.asString(buffer, charset)).collectList().block();

        System.out.println(strs);

    }

    
}
