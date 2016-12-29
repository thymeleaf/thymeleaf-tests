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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.result.view.RequestContext;
import org.springframework.web.reactive.result.view.RequestDataValueProcessor;
import org.springframework.web.server.ServerWebExchange;
import org.thymeleaf.context.ILazyContextVariable;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.spring5.context.reactive.ISpringWebReactiveContext;
import org.thymeleaf.spring5.context.reactive.ReactiveLazyContextVariable;
import org.thymeleaf.spring5.context.reactive.SpringWebReactiveThymeleafRequestContext;
import org.thymeleaf.spring5.naming.SpringContextVariableNames;
import org.thymeleaf.spring5.reactive.context.TestingSpringWebReactiveContext;
import org.thymeleaf.spring5.reactive.exchange.TestingServerWebExchange;
import org.thymeleaf.spring5.reactive.messagesource.TestingMessageSource;
import org.thymeleaf.util.ClassLoaderUtils;

public final class ReactiveTestUtils {


    public static final String TEMPLATE_PATH_BASE = "spring5/reactive/";




    public static String bufferAsString(final DataBuffer dataBuffer, final Charset charset) {
        try {
            return IOUtils.toString(dataBuffer.asInputStream(), charset.name());
        } catch (final IOException e) {
            throw new TemplateProcessingException("Error converting databuffer to string", e);
        }
    }




    public static String readExpectedResults(final String templateName, final Charset charset) {
        final String path = TEMPLATE_PATH_BASE + templateName + "-result.html";
        try {
            final InputStream templateIS = ClassLoaderUtils.loadResourceAsStream(path);
            return IOUtils.toString(templateIS, charset.name());
        } catch (final IOException e) {
            throw new TemplateProcessingException("Could not read '" + path + "'", e);
        }
    }




    public static String readExpectedNormalizedResults(final String templateName, final Charset charset) {
        return normalizeResult(readExpectedResults(templateName, charset));
    }




    public static String normalizeResult(final String result) {
        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < result.length(); i++) {
            final char c = result.charAt(i);
            if (!Character.isWhitespace(c)) {
                strBuilder.append(c);
            }
        }
        return strBuilder.toString();
    }



    public static ISpringWebReactiveContext buildReactiveContext(final Map<String,Object> model) {
        return buildReactiveContext(model, null);
    }

    public static ISpringWebReactiveContext buildReactiveContext(
            final Map<String,Object> model, final RequestDataValueProcessor requestDataValueProcessor) {

        final ServerWebExchange exchange =
                new TestingServerWebExchange("reactive07", Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());

        final TestingMessageSource testingMessageSource = new TestingMessageSource();

        final RequestContext requestContext = new RequestContext(exchange, model, testingMessageSource, requestDataValueProcessor);

        final SpringWebReactiveThymeleafRequestContext thymeleafRequestContext =
                new SpringWebReactiveThymeleafRequestContext(requestContext, exchange);

        model.put(SpringContextVariableNames.SPRING_REQUEST_CONTEXT, requestContext);
        model.put(SpringContextVariableNames.THYMELEAF_REQUEST_CONTEXT, thymeleafRequestContext);


        final TestingSpringWebReactiveContext context = new TestingSpringWebReactiveContext(exchange);
        context.setVariables(model);

        return context;

    }





    private ReactiveTestUtils() {
        super();
    }



    static class InstrumentedReactiveLazyContextVariable implements ILazyContextVariable<Object> {

        private final ReactiveLazyContextVariable lazyContextVariable;
        private boolean initialized = false;

        InstrumentedReactiveLazyContextVariable(final Object target) {
            super();
            this.lazyContextVariable = new ReactiveLazyContextVariable(target);
        }


        boolean isInitialized() {
            return this.initialized;
        }

        @Override
        public Object getValue() {
            this.initialized = true;
            return this.lazyContextVariable.getValue();
        }

    }

    
}
