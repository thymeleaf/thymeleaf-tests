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

import org.junit.Assert;
import org.junit.Test;


public final class AttributeNameTest {



    @Test
    public void testHTMLBuffer() {
        Assert.assertEquals(
                "{something}", AttributeName.forHtmlName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeName.forHtmlName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "{:something}", AttributeName.forHtmlName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertEquals(
                "{data-something}", AttributeName.forHtmlName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeName.forHtmlName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forHtmlName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forHtmlName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
    }



    @Test
    public void testHTMLString() {
        Assert.assertEquals(
                "{something}", AttributeName.forHtmlName("something").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("th:something").toString());
        Assert.assertEquals(
                "{something}", AttributeName.forHtmlName("SOMETHING").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "{:something}", AttributeName.forHtmlName(":something").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeName.forHtmlName("data-th-something").toString());
        Assert.assertEquals(
                "{data-something}", AttributeName.forHtmlName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeName.forHtmlName("xml:ns").toString());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forHtmlName("xml:space").toString());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forHtmlName("XML:SPACE").toString());
    }


    @Test
    public void testXMLBuffer() {
        Assert.assertEquals(
                "{something}", AttributeName.forXmlName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", AttributeName.forXmlName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "th", AttributeName.forXmlName("th:something".toCharArray(), 0, "th:something".length()).getDialectPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeName.forXmlName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeName.forXmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", AttributeName.forXmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).getDialectPrefix());
        Assert.assertEquals(
                "{:something}", AttributeName.forXmlName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertNull(
                AttributeName.forXmlName(":something".toCharArray(), 0, ":something".length()).getDialectPrefix());
        Assert.assertEquals(
                "{data-th-something}", AttributeName.forXmlName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertNull(
                AttributeName.forXmlName("data-th-something".toCharArray(), 0, "data-th-something".length()).getDialectPrefix());
        Assert.assertEquals(
                "{data-something}", AttributeName.forXmlName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeName.forXmlName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", AttributeName.forXmlName("xml:ns".toCharArray(), 0, "xml:ns".length()).getDialectPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forXmlName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeName.forXmlName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", AttributeName.forXmlName("XML:SPACE".toCharArray(), 0, "xml:space".length()).getDialectPrefix());
    }


    @Test
    public void testXMLString() {
        Assert.assertEquals(
                "{something}", AttributeName.forXmlName("something").toString());
        Assert.assertEquals(
                "{th:something}", AttributeName.forXmlName("th:something").toString());
        Assert.assertEquals(
                "th", AttributeName.forXmlName("th:something").getDialectPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeName.forXmlName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeName.forXmlName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", AttributeName.forXmlName("TH:SOMETHING").getDialectPrefix());
        Assert.assertEquals(
                "{:something}", AttributeName.forXmlName(":something").toString());
        Assert.assertNull(
                AttributeName.forXmlName(":something").getDialectPrefix());
        Assert.assertEquals(
                "{data-th-something}", AttributeName.forXmlName("data-th-something").toString());
        Assert.assertNull(
                AttributeName.forXmlName("data-th-something").getDialectPrefix());
        Assert.assertEquals(
                "{data-something}", AttributeName.forXmlName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeName.forXmlName("xml:ns").toString());
        Assert.assertEquals(
                "xml", AttributeName.forXmlName("xml:ns").getDialectPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeName.forXmlName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeName.forXmlName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", AttributeName.forXmlName("XML:SPACE").getDialectPrefix());
    }


}
