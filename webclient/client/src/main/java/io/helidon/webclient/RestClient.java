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

import io.helidon.common.http.ContextualRegistry;
import io.helidon.webclient.spi.ClientService;

/**
 * TODO javadoc.
 */
public interface RestClient extends AutoCloseable {
    /**
     * Create a new rest client.
     *
     * @return
     */
    static RestClient create() {
        return builder().build();
    }

    /**
     * Fluent API builder for client.
     *
     * @return
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Create a request builder for a put method.
     *
     * @param url
     * @return
     */
    ClientRequestBuilder put(String url);

    /**
     * Create a request builder for a put method propagating context from server (or manually created one).
     *
     * @param context
     * @param url
     * @return
     */
    ClientRequestBuilder put(ContextualRegistry context, String url);

    ClientRequestBuilder get(String url);

    ClientRequestBuilder request(String url);

    ClientRequestBuilder get(ContextualRegistry context, String url);

    ClientRequestBuilder request(ContextualRegistry context, String url);

    final class Builder implements io.helidon.common.Builder<RestClient> {
        private Builder() {
        }

        @Override
        public RestClient build() {
            return new RestClientImpl(this);
        }

        public Builder register(ClientService service) {
            return this;
        }

        public Builder proxy(Proxy proxy) {
            return this;
        }

        public Builder register(ClientContentHandler<?> contentHandler) {
            return null;
        }
    }
}
