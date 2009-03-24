/*
 * Copyright 2002-2009 the original author or authors.
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
package org.springframework.integration.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.integration.core.Message;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * 
 * @author Iwein Fuld
 *
 */
@SuppressWarnings("unchecked")
public class DefaultInboundRequestMapperTests {

	private static final String SIMPLE_STRING = "just ascii";

	private static final String COMPLEX_STRING = "A\u00ea\u00f1\u00fcC";

	private DefaultInboundRequestMapper mapper = new DefaultInboundRequestMapper();

	@Test
	public void simpleUtf8TextMapping() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContentType("text");
		request.setCharacterEncoding("utf-8");
		byte[] bytes = SIMPLE_STRING.getBytes("utf-8");
		request.setContent(bytes);
		Message<String> message = (Message<String>) mapper.toMessage(request);
		assertThat(message.getPayload(), is(SIMPLE_STRING));
	}

	@Test
	public void complexUtf8TextMapping() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContentType("text");
		// don't forget to specify the character encoding on the request or you
		// will end up with unpredictable results!
		request.setCharacterEncoding("utf-8");
		byte[] bytes = COMPLEX_STRING.getBytes("utf-8");
		request.setContent(bytes);
		Message<String> message = (Message<String>) mapper.toMessage(request);
		assertThat(message.getPayload(), is(COMPLEX_STRING));
	}
}
