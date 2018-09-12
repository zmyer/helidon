/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package io.helidon.webserver;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.helidon.common.http.MediaType;

/**
 * A server extension of the {@link io.helidon.common.http.StringContentReader}.
 * Used for backward compatibility when the main body was extracted to common.
 */
class StringContentReader extends io.helidon.common.http.StringContentReader {

    /** The default charset to use in case that no charset or no mime-type is defined in the content type header. */
    static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Constructs the reader based on the charset information located in the given request.
     *
     * @param request the request to obtain the charset information from
     */
    StringContentReader(Request request) {
        super(requestContentCharset(request));
    }

    /**
     * Obtain the charset from the request.
     *
     * @param request the request to extract the charset from
     * @return the charset or {@link #DEFAULT_CHARSET} if none found
     */
    static Charset requestContentCharset(ServerRequest request) {
        return request.headers()
                      .contentType()
                .flatMap(MediaType::getCharset)
                .map(Charset::forName)
                      .orElse(DEFAULT_CHARSET);
    }
}
