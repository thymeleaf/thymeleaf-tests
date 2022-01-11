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
package org.thymeleaf.spring5.reactive.context;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.spring6.context.webflux.ISpringWebFluxContext;
import reactor.core.publisher.Mono;

public final class TestingSpringWebFluxContext extends AbstractContext implements ISpringWebFluxContext {

    private final ServerWebExchange exchange;


    public TestingSpringWebFluxContext(final ServerWebExchange exchange) {
        super();
        this.exchange = exchange;
    }


    @Override
    public ServerHttpRequest getRequest() {
        return this.exchange.getRequest();
    }

    @Override
    public ServerHttpResponse getResponse() {
        return this.exchange.getResponse();
    }

    @Override
    public Mono<WebSession> getSession() {
        return this.exchange.getSession();
    }

    @Override
    public ServerWebExchange getExchange() {
        return this.exchange;
    }
}
