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
package org.thymeleaf.templateengine.springintegration.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.testing.templateengine.util.JakartaServletMockUtils;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import static org.junit.Assert.assertThrows;
import static org.thymeleaf.spring6.util.SpringRequestUtils.checkViewNameNotInRequest;


public final class SpringRequestUtilsTest {

    private static final ServletContext SERVLET_CONTEXT = JakartaServletMockUtils.buildServletContext().build();
    private static final HttpServletResponse HTTP_SERVLET_RESPONSE = JakartaServletMockUtils.buildResponse().build();


    @Test
    public void testCheckViewNameNotInRequest() {

        checkViewNameNotInRequest("lala", mockRequest("alala"));
        checkViewNameNotInRequest("lala :: le", mockRequest("a/elala::le//"));
        checkViewNameNotInRequest("lala :: le", mockRequest("a/elala :: le//"));
        checkViewNameNotInRequest("${lala} :: le", mockRequest("a/elala::le//"));
        checkViewNameNotInRequest("${lala} :: le", mockRequest("a/elala :: le//"));
        assertThrows(TemplateProcessingException.class, () -> checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e${lala}::le//")));
        assertThrows(TemplateProcessingException.class, () -> checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e${lala} :: le//")));
        assertThrows(TemplateProcessingException.class, () -> checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e${x} :: le//")));
        assertThrows(TemplateProcessingException.class, () -> checkViewNameNotInRequest("${lala} :: le (a=23)", mockRequest("a/e${lala} :: le//")));
        checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e","p0","${lala} :: le//"));
        assertThrows(TemplateProcessingException.class, () -> checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e","p0","${lala} :: le")));
        checkViewNameNotInRequest("${lala} :: le", mockRequest("a/e","p0","${lala}_le//"));
        checkViewNameNotInRequest("${lala}::le", mockRequest("a/e","p0","${lili}::le"));

    }


    private IWebRequest mockRequest(final String path, final String... params) {

        final Map<String,String[]> paramsMap = new HashMap<>();
        if (params != null && params.length > 0 && (params.length % 2 == 0)) {
            for (int i = 0; i < params.length; i+=2) {
                paramsMap.put(params[i], new String[] {params[i+1]});
            }
        }

        final HttpServletRequest request =
                JakartaServletMockUtils.buildRequest(SERVLET_CONTEXT, path).parameterMap(paramsMap).build();

        final IWebExchange webExchange =
                JakartaServletWebApplication.buildApplication(SERVLET_CONTEXT)
                        .buildExchange(request, HTTP_SERVLET_RESPONSE);

        return webExchange.getRequest();

    }


}
