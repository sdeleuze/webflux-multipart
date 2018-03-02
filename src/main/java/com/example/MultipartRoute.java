/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

//@Configuration
public class MultipartRoute {

	@Value("#{systemProperties['user.dir']}")
	private String dir;

	@Bean
	RouterFunction<ServerResponse> multipartRouter() {
		return RouterFunctions.route(RequestPredicates.POST("/upload"), request ->
				request.body(BodyExtractors.toMultipartData()).flatMap(parts -> {
					FilePart filePart = (FilePart) parts.getFirst("files");
					return filePart.transferTo(new File(dir + "/" + filePart.filename()))
						.then(ServerResponse.ok().body(parts.getFirst("submit-name").content(), DataBuffer.class));
				}
		)).and(RouterFunctions.resources("/**", new ClassPathResource("static/")));
	}
}
