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
package org.thymeleaf.inline;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.util.DateUtils;

public class ScriptInlineTest {



    private static void testInlineResult(final String script, final String expectedResult) {
        testInlineResult(script, expectedResult, Collections.<String, Object>emptyMap());
    }

    private static void testInlineResult(final String script, final String expectedResult, final String variableName, final Object variableValue) {
        testInlineResult(script, expectedResult, Collections.singletonMap(variableName, variableValue));
    }

    private static void testInlineResult(final String script, final String expectedResult, final Map<String,Object> variables) {

        final String completeScript =
                "<script th:inline=\"javascript\">\n/*<![CDATA[ */\n" +
                script +
                "\n/* ]]> */\n</script>";

        final ITemplateResolver templateResolver = new StringTemplateResolver(completeScript);
        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        final Context ctx = new Context();
        ctx.setVariables(variables);

        final StringWriter stringWriter = new StringWriter();
        templateEngine.process("test-template", ctx, stringWriter);

        final String result = stringWriter.toString();
        final String extractedResult =
                result.substring(0, result.indexOf("\n/* ]]> */\n</script>")).substring(24);


        Assert.assertEquals(expectedResult, extractedResult);

    }


    @Test
    public void testDateInline() throws Exception {

        testInlineResult(
                "[[${a}]]",
                "'something'",
                "a", "something");

        testInlineResult(
                "   /*[[${a}]]*/ 'prototype';",
                "   'something';",
                "a", "something");

        final java.util.Calendar calendar1 =
                DateUtils.create(Integer.valueOf(2013),Integer.valueOf(01),Integer.valueOf(01),Integer.valueOf(14),Integer.valueOf(30));
        final java.util.Date date1 = calendar1.getTime();
        final java.sql.Date dateSql1 = new java.sql.Date(date1.getTime());

        testInlineResult(
                "   /*[[${calendar1}]]*/ 'prototype';",
                // Calendar should be inlined as ISO6801 date string literal
                // e.g.  '2013-01-01T14:30:00.000+01:00'
                "   '" + DateUtils.formatISO(calendar1) + "';",
                "calendar1", calendar1);

        testInlineResult(
                "   /*[[${date1}]]*/ 'prototype';",
                // Date should be inlined as ISO6801 date string literal
                // e.g.  '2013-01-01T14:30:00.000+01:00'
                "   '" + DateUtils.formatISO(date1) + "';",
                "date1", date1);

        testInlineResult(
                "   /*[[${date1}]]*/ 'prototype';",
                // Date should be inlined as ISO6801 date string literal
                // e.g.  '2013-01-01T14:30:00.000+01:00'
                "   '" + DateUtils.formatISO(dateSql1) + "';",
                "date1", dateSql1);


    }
}
