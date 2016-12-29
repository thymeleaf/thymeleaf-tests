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

import java.util.function.Function;
import java.util.function.Supplier;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public final class TestingServerHttpResponse implements ServerHttpResponse {

    private final HttpHeaders headers;
    private final DataBufferFactory bufferFactory;
    private final MultiValueMap<String, ResponseCookie> cookies;


    public TestingServerHttpResponse() {
        super();
        this.headers = new HttpHeaders();
        this.bufferFactory = new DefaultDataBufferFactory();
        this.cookies = new LinkedMultiValueMap<>();
    }


    @Override
    public boolean setStatusCode(final HttpStatus httpStatus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.OK;
    }

    @Override
    public MultiValueMap<String, ResponseCookie> getCookies() {
        return this.cookies;
    }

    @Override
    public String encodeUrl(final String s) {
        return "[" + s + "]";
    }

    @Override
    public void registerUrlEncoder(final Function<String, String> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataBufferFactory bufferFactory() {
        return this.bufferFactory;
    }

    @Override
    public void beforeCommit(final Supplier<? extends Mono<Void>> supplier) {
        // Nothing to do
    }

    @Override
    public Mono<Void> writeWith(final Publisher<? extends DataBuffer> publisher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Void> writeAndFlushWith(final Publisher<? extends Publisher<? extends DataBuffer>> publisher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Void> setComplete() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

}
