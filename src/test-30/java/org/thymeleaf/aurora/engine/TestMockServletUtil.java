/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
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
package org.thymeleaf.aurora.engine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public final class TestMockServletUtil {





    public static HttpServletRequest createHttpServletRequest(
            final String path,
            final HttpSession session, final Map<String,Object> attributes,
            final Map<String,Object[]> parameters, final Locale locale) {

        final String mimeType = "text/html";
        final String method = "GET";
        final String contextName = "/tests";
        final String protocol = "HTTP/1.1";
        final String scheme = "http";
        final int port = 80;
        final String serverName = "thymeleaf";
        final String servletPath = "/" + path;
        final String requestURI = contextName + servletPath;
        final String requestURL = scheme + "://" + serverName + requestURI;
        final String queryString = buildQueryString(parameters);

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getContentType()).thenReturn(mimeType);
        Mockito.when(request.getMethod()).thenReturn(method);
        Mockito.when(request.getProtocol()).thenReturn(protocol);
        Mockito.when(request.getScheme()).thenReturn(scheme);
        Mockito.when(request.getServerName()).thenReturn(serverName);
        Mockito.when(Integer.valueOf(request.getServerPort())).thenReturn(Integer.valueOf(port));
        Mockito.when(request.getContextPath()).thenReturn(contextName);
        Mockito.when(request.getServletPath()).thenReturn(servletPath);
        Mockito.when(request.getRequestURI()).thenReturn(requestURI);
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(requestURL));
        Mockito.when(request.getQueryString()).thenReturn(queryString);
        Mockito.when(request.getLocale()).thenReturn(locale);
        Mockito.when(request.getLocales()).thenReturn(new ObjectEnumeration<Locale>(Arrays.asList(new Locale[]{locale})));

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(Matchers.anyBoolean())).thenReturn(session);

        Mockito.when(request.getAttributeNames()).thenAnswer(new GetVariableNamesAnswer(attributes));
        Mockito.when(request.getAttribute(Matchers.anyString())).thenAnswer(new GetAttributeAnswer(attributes));
        Mockito.doAnswer(new SetAttributeAnswer(attributes)).when(request).setAttribute(Matchers.anyString(), Matchers.anyObject());
        Mockito.doAnswer(new RemoveAttributeAnswer(attributes)).when(request).removeAttribute(Matchers.anyString());

        Mockito.when(request.getParameterNames()).thenAnswer(new GetVariableNamesAnswer(parameters));
        Mockito.when(request.getParameterValues(Matchers.anyString())).thenAnswer(new GetParameterValuesAnswer(parameters));
        Mockito.when(request.getParameterMap()).thenAnswer(new GetParameterMapAnswer(parameters));
        Mockito.when(request.getParameter(Matchers.anyString())).thenAnswer(new GetParameterAnswer(parameters));


        return request;

    }



    public static HttpSession createHttpSession(final ServletContext context, final Map<String,Object> attributes) {

        final HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(session.getServletContext()).thenReturn(context);

        Mockito.when(session.getAttributeNames()).thenAnswer(new GetVariableNamesAnswer(attributes));
        Mockito.when(session.getAttribute(Matchers.anyString())).thenAnswer(new GetAttributeAnswer(attributes));
        Mockito.doAnswer(new SetAttributeAnswer(attributes)).when(session).setAttribute(Matchers.anyString(), Matchers.anyObject());
        Mockito.doAnswer(new RemoveAttributeAnswer(attributes)).when(session).removeAttribute(Matchers.anyString());

        return session;

    }



    private static final HttpServletResponse createHttpServletResponse() {
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.encodeURL(Matchers.anyString())).thenAnswer(new EncodeUrlAnswer());
        return response;
    }



    public static ServletContext createServletContext(final Map<String,Object> attributes) {

        final ServletContext servletContext = Mockito.mock(ServletContext.class);

        Mockito.when(servletContext.getAttributeNames()).thenAnswer(new GetVariableNamesAnswer(attributes));
        Mockito.when(servletContext.getAttribute(Matchers.anyString())).thenAnswer(new GetAttributeAnswer(attributes));
        Mockito.doAnswer(new SetAttributeAnswer(attributes)).when(servletContext).setAttribute(Matchers.anyString(), Matchers.anyObject());
        Mockito.doAnswer(new RemoveAttributeAnswer(attributes)).when(servletContext).removeAttribute(Matchers.anyString());

        Mockito.when(servletContext.getInitParameterNames()).thenReturn(new ObjectEnumeration<String>(null));
        Mockito.when(servletContext.getInitParameter(Matchers.anyString())).thenReturn(null);

        return servletContext;
    }



    private static String buildQueryString(final Map<String,Object[]> parameters) {

        if (parameters == null || parameters.size() == 0) {
            return null;
        }

        final StringBuilder strBuilder = new StringBuilder();
        for (final Map.Entry<String,Object[]> parameterEntry : parameters.entrySet()) {

            final String parameterName = parameterEntry.getKey();
            final Object[] parameterValues = parameterEntry.getValue();

            if (parameterValues == null || parameterValues.length == 0) {
                if (strBuilder.length() > 0) {
                    strBuilder.append('&');
                }
                strBuilder.append(parameterName);
                continue;
            }

            for (final Object parameterValue : parameterValues) {
                if (strBuilder.length() > 0) {
                    strBuilder.append('&');
                }
                strBuilder.append(parameterName);
                if (parameterValue != null) {
                    strBuilder.append("=");
                    try {
                        strBuilder.append(URLEncoder.encode(parameterValue.toString(), "UTF-8"));
                    } catch (final UnsupportedEncodingException e) {
                        // Should never happen, UTF-8 just exists.
                        throw new RuntimeException(e);
                    }
                }
            }

        }

        return strBuilder.toString();

    }







    private static class ObjectEnumeration<T> implements Enumeration<T> {

        private final Iterator<T> iterator;

        @SuppressWarnings("unchecked")
        public ObjectEnumeration(final Collection<T> values) {
            super();
            if (values != null) {
                this.iterator = values.iterator();
            } else {
                this.iterator = ((List<T>) Collections.emptyList()).iterator();
            }
        }

        public boolean hasMoreElements() {
            return this.iterator.hasNext();
        }

        public T nextElement() {
            return this.iterator.next();
        }

    }




    private static class GetVariableNamesAnswer implements Answer<Enumeration<?>> {

        private final Map<String,?> values;

        public GetVariableNamesAnswer(final Map<String,?> values) {
            super();
            this.values = values;
        }

        public Enumeration<?> answer(final InvocationOnMock invocation) throws Throwable {
            return new ObjectEnumeration<String>(this.values.keySet());
        }

    }



    private static class GetAttributeAnswer implements Answer<Object> {

        private final Map<String,Object> values;

        public GetAttributeAnswer(final Map<String,Object> values) {
            super();
            this.values = values;
        }

        public Object answer(final InvocationOnMock invocation) throws Throwable {
            final String attributeName = (String) invocation.getArguments()[0];
            return this.values.get(attributeName);
        }

    }



    private static class SetAttributeAnswer implements Answer<Object> {

        private final Map<String,Object> values;

        public SetAttributeAnswer(final Map<String,Object> values) {
            super();
            this.values = values;
        }

        public Object answer(final InvocationOnMock invocation) throws Throwable {
            final String attributeName = (String) invocation.getArguments()[0];
            final Object attributeValue = invocation.getArguments()[1];
            this.values.put(attributeName, attributeValue);
            return null;
        }

    }



    private static class RemoveAttributeAnswer implements Answer<Object> {

        private final Map<String,Object> values;

        public RemoveAttributeAnswer(final Map<String,Object> values) {
            super();
            this.values = values;
        }

        public Object answer(final InvocationOnMock invocation) throws Throwable {
            final String attributeName = (String) invocation.getArguments()[0];
            this.values.remove(attributeName);
            return null;
        }

    }



    private static class GetParameterValuesAnswer implements Answer<String[]> {

        private final Map<String,Object[]> values;

        public GetParameterValuesAnswer(final Map<String,Object[]> values) {
            super();
            this.values = values;
        }

        public String[] answer(final InvocationOnMock invocation) throws Throwable {
            final String parameterName = (String) invocation.getArguments()[0];
            final Object[] parameterValues = this.values.get(parameterName);
            if (parameterValues == null) {
                return null;
            }
            final String[] parameterValuesArray = new String[parameterValues.length];
            for (int i = 0; i < parameterValuesArray.length; i++) {
                final Object value = parameterValues[i];
                parameterValuesArray[i] = (value == null? null : value.toString());
            }
            return parameterValuesArray;
        }

    }



    private static class GetParameterAnswer implements Answer<String> {

        private final Map<String,Object[]> values;

        public GetParameterAnswer(final Map<String,Object[]> values) {
            super();
            this.values = values;
        }

        public String answer(final InvocationOnMock invocation) throws Throwable {
            final String parameterName = (String) invocation.getArguments()[0];
            final Object[] parameterValues = this.values.get(parameterName);
            if (parameterValues == null) {
                return null;
            }
            final Object value = parameterValues[0];
            return (value == null? null : value.toString());
        }

    }



    private static class GetParameterMapAnswer implements Answer<Map<String,String[]>> {

        private final Map<String,Object[]> values;

        public GetParameterMapAnswer(final Map<String,Object[]> values) {
            super();
            this.values = values;
        }

        public Map<String,String[]> answer(final InvocationOnMock invocation) throws Throwable {
            final Map<String,String[]> parameterMap = new HashMap<String, String[]>();
            for (final Map.Entry<String,Object[]> valueEntry : this.values.entrySet()) {
                final String parameterName = valueEntry.getKey();
                final Object[] parameterValues = valueEntry.getValue();
                if (parameterValues == null) {
                    parameterMap.put(parameterName, null);
                    continue;
                }
                final String[] parameterValuesArray = new String[parameterValues.length];
                for (int i = 0; i < parameterValuesArray.length; i++) {
                    final Object value = parameterValues[i];
                    parameterValuesArray[i] = (value == null? null : value.toString());
                }
                parameterMap.put(parameterName, parameterValuesArray);
            }
            return parameterMap;
        }

    }



    private static class EncodeUrlAnswer implements Answer<String> {

        public EncodeUrlAnswer() {
            super();
        }

        public String answer(final InvocationOnMock invocation) throws Throwable {
            return (String) invocation.getArguments()[0];
        }

    }



}
