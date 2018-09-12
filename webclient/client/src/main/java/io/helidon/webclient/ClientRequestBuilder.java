/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.helidon.webclient;

import java.util.concurrent.CompletionStage;

import io.helidon.common.http.DataChunk;
import io.helidon.common.http.Headers;
import io.helidon.common.http.HttpRequest;
import io.helidon.common.http.Parameters;
import io.helidon.common.reactive.Flow;

/**
 * Fluent API builder that is used by {@link RestClient} to create an outgoing request.
 */
public interface ClientRequestBuilder {
    /**
     * Add a property to be used by a {@link io.helidon.webclient.spi.ClientService}
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    ClientRequestBuilder property(String propertyName, Object propertyValue);

    /**
     * Add a request header.
     *
     * @param header
     * @param values
     * @return
     * @see #headers()
     */
    ClientRequestBuilder header(String header, String... values);

    /**
     * Get a (mutable) instance of outgoing headers
     *
     * @return
     */
    ClientRequestHeaders headers();

    /**
     * Configure request method (will override existing method).
     *
     * @param method
     * @return
     */
    ClientRequestBuilder method(String method);

    /**
     * Add a query parameter.
     *
     * @param name
     * @param values
     * @return
     */
    ClientRequestBuilder queryParam(String name, String... values);

    /**
     * Override client proxy configuration
     *
     * @param proxy
     * @return
     */
    ClientRequestBuilder proxy(Proxy proxy);

    //TODO
    ClientRequest build();

    /**
     * Configure headers. Copy all headers from supplied {@link Headers} instance.
     *
     * @param headers to copy
     * @return updated builder instance
     */
    ClientRequestBuilder headers(Headers headers);

    /**
     * Configure query parameters.
     * Copy all query parameters from supplied {@link Parameters} instance.
     *
     * @param queryParams to copy
     * @return udpated builder instance
     */
    ClientRequestBuilder queryParams(Parameters queryParams);

    /**
     * Register an entity handler.
     *
     * @param handler
     * @return
     */
    ClientRequestBuilder register(ClientContentHandler<?> handler);

    interface ClientRequest extends HttpRequest {
        ClientRequestHeaders headers();
        /**
         * Build the request and send it to the server.
         *
         * @return a completion stage that is completed when we receive response from the server (entity may not be yet fully
         * received)
         */
        CompletionStage<ClientResponse> send();

        /**
         * Build the request and send it with an entity to the server.
         *
         * @param entity entity of a type supported by one of configured {@link ClientContentHandler}
         * @param <E>
         * @return a completion stage that is completed when we receive response from the server (entity may not be yet fully
         * received)
         */
        <E> CompletionStage<ClientResponse> send(E entity);

        /**
         * Build the request and send it with an entity to the server.
         *
         * @param content publisher of entity {@link DataChunk DataChunks}
         * @return a completion stage that is completed when we receive response from the server (entity may not be yet fully
         * received)
         */
        CompletionStage<ClientResponse> send(Flow.Publisher<DataChunk> content);

    }
}
