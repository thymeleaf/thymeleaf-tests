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


public final class ElementNamesTest {



    @Test
    public void testHTMLBuffer() {
        Assert.assertEquals(
                "{something}", ElementNames.forHtmlName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHtmlName("abcdefghijkliklmnsomething".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHtmlName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHtmlName("abcdefghijkliklmnth:something".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHtmlName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHtmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "{:something}", ElementNames.forHtmlName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertEquals(
                "{data:th-something,data-th-something}", ElementNames.forHtmlName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertEquals(
                "{data:something,data-something}", ElementNames.forHtmlName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forHtmlName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHtmlName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHtmlName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forHtmlName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
    }



    @Test
    public void testHTMLString() {
        Assert.assertEquals(
                "{something}", ElementNames.forHtmlName("something").toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHtmlName("th:something").toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHtmlName("SOMETHING").toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHtmlName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "{:something}", ElementNames.forHtmlName(":something").toString());
        Assert.assertEquals(
                "{data:th-something,data-th-something}", ElementNames.forHtmlName("data-th-something").toString());
        Assert.assertEquals(
                "{data:something,data-something}", ElementNames.forHtmlName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forHtmlName("xml:ns").toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHtmlName("xml:space").toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHtmlName("XML:SPACE").toString());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forHtmlName("xmlns:th").toString());
    }


    @Test
    public void testXMLBuffer() {
        Assert.assertEquals(
                "{something}", ElementNames.forXmlName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forXmlName("abcdefghijkliklmnsomething".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXmlName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXmlName("abcdefghijkliklmnth:something".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "th", ((XmlPrefixedElementName)ElementNames.forXmlName("th:something".toCharArray(), 0, "th:something".length())).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forXmlName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forXmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", ((XmlPrefixedElementName)ElementNames.forXmlName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length())).getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forXmlName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertFalse(
                ElementNames.forXmlName(":something".toCharArray(), 0, ":something".length()) instanceof XmlPrefixedElementName);
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forXmlName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertFalse(
                ElementNames.forXmlName("data-th-something".toCharArray(), 0, "data-th-something".length()) instanceof XmlPrefixedElementName);
        Assert.assertEquals(
                "{data-something}", ElementNames.forXmlName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forXmlName("data:something").toString());
        Assert.assertEquals(
                "data", ((XmlPrefixedElementName)ElementNames.forXmlName("data:something")).getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forXmlName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", ((XmlPrefixedElementName)ElementNames.forXmlName("xml:ns".toCharArray(), 0, "xml:ns".length())).getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forXmlName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forXmlName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", ((XmlPrefixedElementName)ElementNames.forXmlName("XML:SPACE".toCharArray(), 0, "xml:space".length())).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forXmlName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertEquals(
                "xmlns", ((XmlPrefixedElementName)ElementNames.forXmlName("xmlns:th".toCharArray(), 0, "xmlns:th".length())).getPrefix());
    }


    @Test
    public void testXMLString() {
        Assert.assertEquals(
                "{something}", ElementNames.forXmlName("something").toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXmlName("th:something").toString());
        Assert.assertEquals(
                "th", ((XmlPrefixedElementName)ElementNames.forXmlName("th:something")).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forXmlName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forXmlName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", ((XmlPrefixedElementName)ElementNames.forXmlName("TH:SOMETHING")).getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forXmlName(":something").toString());
        Assert.assertFalse(
                ElementNames.forXmlName(":something") instanceof XmlPrefixedElementName);
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forXmlName("data-th-something").toString());
        Assert.assertFalse(
                ElementNames.forXmlName("data-th-something") instanceof XmlPrefixedElementName);
        Assert.assertEquals(
                "{data-something}", ElementNames.forXmlName("data-something").toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forXmlName("data:something").toString());
        Assert.assertEquals(
                "data", ((XmlPrefixedElementName)ElementNames.forXmlName("data:something")).getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forXmlName("xml:ns").toString());
        Assert.assertEquals(
                "xml", ((XmlPrefixedElementName)ElementNames.forXmlName("xml:ns")).getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forXmlName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forXmlName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", ((XmlPrefixedElementName)ElementNames.forXmlName("XML:SPACE")).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forXmlName("xmlns:th").toString());
        Assert.assertEquals(
                "xmlns", ((XmlPrefixedElementName)ElementNames.forXmlName("xmlns:th")).getPrefix());
    }


}
