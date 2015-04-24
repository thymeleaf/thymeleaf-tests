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
package org.thymeleaf.engine;

import org.junit.Assert;
import org.junit.Test;


public final class ElementNamesTest {



    @Test
    public void testHTMLBuffer() {
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName(null, "th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHTMLName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHTMLName("abcdefghijkliklmnsomething".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("abcdefghijkliklmnth:something".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHTMLName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "{:something}", ElementNames.forHTMLName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertEquals(
                "{data:th-something,data-th-something}", ElementNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertEquals(
                "{data:something,data-something}", ElementNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forHTMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHTMLName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHTMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());

        try {
            ElementNames.forHTMLName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName("".toCharArray(), 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }



    @Test
    public void testHTMLString() {
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHTMLName("something").toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("th:something").toString());
        Assert.assertEquals(
                "{something}", ElementNames.forHTMLName("SOMETHING").toString());
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "{:something}", ElementNames.forHTMLName(":something").toString());
        Assert.assertEquals(
                "{data:th-something,data-th-something}", ElementNames.forHTMLName("data-th-something").toString());
        Assert.assertEquals(
                "{data:something,data-something}", ElementNames.forHTMLName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forHTMLName("xml:ns").toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHTMLName("xml:space").toString());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forHTMLName("XML:SPACE").toString());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forHTMLName("xmlns:th").toString());

        try {
            ElementNames.forHTMLName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName("");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forHTMLName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testXMLBuffer() {
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName(null, "th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forXMLName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forXMLName("abcdefghijkliklmnsomething".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName("abcdefghijkliklmnth:something".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "th", ElementNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forXMLName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forXMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", ElementNames.forXMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forXMLName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertFalse(
                ElementNames.forXMLName(":something".toCharArray(), 0, ":something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertFalse(
                ElementNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-something}", ElementNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forXMLName("data:something").toString());
        Assert.assertEquals(
                "data", ElementNames.forXMLName("data:something").getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forXMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", ElementNames.forXMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forXMLName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forXMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", ElementNames.forXMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertEquals(
                "xmlns", ElementNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).getPrefix());

        try {
            ElementNames.forXMLName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName("".toCharArray(), 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testXMLString() {
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", ElementNames.forXMLName("something").toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName("th:something").toString());
        Assert.assertEquals(
                "th", ElementNames.forXMLName("th:something").getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forXMLName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forXMLName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", ElementNames.forXMLName("TH:SOMETHING").getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forXMLName(":something").toString());
        Assert.assertFalse(
                ElementNames.forXMLName(":something").isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forXMLName("data-th-something").toString());
        Assert.assertFalse(
                ElementNames.forXMLName("data-th-something").isPrefixed());
        Assert.assertEquals(
                "{data-something}", ElementNames.forXMLName("data-something").toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forXMLName("data:something").toString());
        Assert.assertEquals(
                "data", ElementNames.forXMLName("data:something").getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forXMLName("xml:ns").toString());
        Assert.assertEquals(
                "xml", ElementNames.forXMLName("xml:ns").getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forXMLName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forXMLName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", ElementNames.forXMLName("XML:SPACE").getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forXMLName("xmlns:th").toString());
        Assert.assertEquals(
                "xmlns", ElementNames.forXMLName("xmlns:th").getPrefix());

        try {
            ElementNames.forXMLName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName("");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forXMLName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


}
