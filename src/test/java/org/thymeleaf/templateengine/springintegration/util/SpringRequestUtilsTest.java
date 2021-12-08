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

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.thymeleaf.engine.TestMockServletUtil;
import org.thymeleaf.exceptions.TemplateProcessingException;

import static org.junit.Assert.*;
import static org.thymeleaf.spring4.util.SpringRequestUtils.*;


public final class SpringRequestUtilsTest {



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


    private HttpServletRequest mockRequest(final String path, final String... params) {

        final Map<String,String[]> paramsMap = new HashMap<>();
        if (params != null && params.length > 0 && (params.length % 2 == 0)) {
            for (int i = 0; i < params.length; i+=2) {
                paramsMap.put(params[i], new String[] {params[i+1]});
            }
        }

        final HttpServletRequest request =
                TestMockServletUtil.createHttpServletRequest(path, null, null, paramsMap, null);

        return request;

    }


}
