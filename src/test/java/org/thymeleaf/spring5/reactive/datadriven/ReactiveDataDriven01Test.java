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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.spring5.SpringWebReactiveTemplateEngine;
import org.thymeleaf.spring5.reactive.ReactiveTestUtils;
import org.thymeleaf.spring5.reactive.data.Album;
import org.thymeleaf.spring5.reactive.data.AlbumRepository;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.util.ClassLoaderUtils;
import reactor.core.publisher.Flux;

public final class ReactiveDataDriven01Test {

    private static SpringWebReactiveTemplateEngine templateEngine;
    private static DataBufferFactory bufferFactory;
    private static Charset charset;


    @BeforeClass
    public static void initTemplateEngine() {

        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(ReactiveTestUtils.TEMPLATE_PATH_BASE);
        templateResolver.setSuffix(".html");

        templateEngine = new SpringWebReactiveTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        bufferFactory = new DefaultDataBufferFactory();

        charset = Charset.forName("UTF-8");

    }




    @Test
    public void testDataDriven01_01() throws Exception {

        final Context ctx1 = new Context();

        final Publisher<DataBuffer> resultStream =
                templateEngine.processStream("datadriven/datadriven01", null, ctx1, bufferFactory, charset);

        final List<String> resultStrs =
                Flux.from(resultStream).map((buffer) -> ReactiveTestUtils.bufferAsString(buffer, charset)).collectList().block();

        final List<String> normalizedResultStrs = ReactiveTestUtils.normalizeResults(resultStrs);
        Assert.assertEquals(1, normalizedResultStrs.size());

        final String expected =
                ReactiveTestUtils.readExpectedNormalizedResults("datadriven/datadriven01-01", charset);

        Assert.assertEquals(expected, normalizedResultStrs.get(0));

    }


    @Test
    public void testDataDriven01_02() throws Exception {

        final List<Album> albums = AlbumRepository.findAllAlbums();

        final Context ctx1 = new Context();
        ctx1.setVariable("albums", albums);

        final Publisher<DataBuffer> resultStream =
                templateEngine.processStream("datadriven/datadriven01", null, ctx1, bufferFactory, charset);

        final List<String> resultStrs =
                Flux.from(resultStream).map((buffer) -> ReactiveTestUtils.bufferAsString(buffer, charset)).collectList().block();

        final List<String> normalizedResultStrs = ReactiveTestUtils.normalizeResults(resultStrs);
        Assert.assertEquals(1, normalizedResultStrs.size());

        final String expected =
                ReactiveTestUtils.readExpectedNormalizedResults("datadriven/datadriven01-02", charset);

        Assert.assertEquals(expected, normalizedResultStrs.get(0));

    }

    
}
