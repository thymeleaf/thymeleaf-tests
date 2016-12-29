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
package org.thymeleaf.spring5.reactive.exchange;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

public final class TestingServerWebExchange implements ServerWebExchange {

    private final TestingServerHttpRequest request;
    private final TestingServerHttpResponse response;
    private final TestingWebSession session;
    private final Map<String,Object> attributes;


    public TestingServerWebExchange(
            final String path, final Map<String,List<String>> queryParams,
            final Map<String,Object> requestAttributes, final Map<String,Object> sessionAttributes) {
        super();
        this.request = new TestingServerHttpRequest(path, queryParams);
        this.response = new TestingServerHttpResponse();
        this.session = new TestingWebSession(sessionAttributes);
        this.attributes = requestAttributes;
    }



    @Override
    public ServerHttpRequest getRequest() {
        return this.request;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return this.response;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public <T> Optional<T> getAttribute(final String s) {
        return Optional.ofNullable((T)this.attributes.get(s));
    }

    @Override
    public Mono<WebSession> getSession() {
        return Mono.just(this.session);
    }

    @Override
    public <T extends Principal> Mono<T> getPrincipal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<MultiValueMap<String, String>> getFormData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<MultiValueMap<String, String>> getRequestParams() {
        return Mono.just(this.request.getQueryParams());
    }

    @Override
    public boolean isNotModified() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkNotModified(final Instant instant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkNotModified(final String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkNotModified(final String s, final Instant instant) {
        throw new UnsupportedOperationException();
    }

}
