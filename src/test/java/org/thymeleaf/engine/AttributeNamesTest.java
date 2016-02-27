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
package org.thymeleaf.engine;

import org.junit.Assert;
import org.junit.Test;


public final class AttributeNamesTest {



    @Test
    public void testHTMLBuffer() {
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertNull(AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).getPrefix());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("absomethingba".toCharArray(), 2, "something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("abcdefghijkliklmnsomethingba".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("abcdefghijkliklmnth:somethingba".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "{:something}", AttributeNames.forHTMLName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forHTMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forHTMLName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forHTMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertFalse(
                AttributeNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).isPrefixed());

        Assert.assertSame(
                AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()), AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                AttributeNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), AttributeNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), AttributeNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), AttributeNames.forHTMLName("DATA-TH-SOMETHING".toCharArray(), 0, "data-th-something".length()));

        try {
            AttributeNames.forHTMLName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName("".toCharArray(), 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }



    @Test
    public void testHTMLString() {
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertNull(AttributeNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()).getPrefix());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName(null, "data-something").toString());
        Assert.assertNull(AttributeNames.forHTMLName(null, "data-something").getPrefix());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("something").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("th:something").toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forHTMLName("SOMETHING").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "{:something}", AttributeNames.forHTMLName(":something").toString());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("data-th-something").toString());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forHTMLName("xml:ns").toString());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forHTMLName("xml:space").toString());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forHTMLName("XML:SPACE").toString());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forHTMLName("xmlns:th").toString());
        Assert.assertFalse(
                AttributeNames.forHTMLName("xmlns:th").isPrefixed());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName("th","something").toString());

        Assert.assertSame(
                AttributeNames.forHTMLName("data-something"), AttributeNames.forHTMLName("data-something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("xmlns:th"), AttributeNames.forHTMLName("xmlns:th"));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-th-something"), AttributeNames.forHTMLName("data-th-something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-th-something"), AttributeNames.forHTMLName("th:something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("xmlns","th"), AttributeNames.forHTMLName("xmlns","th"));
        Assert.assertSame(
                AttributeNames.forHTMLName("th","something"), AttributeNames.forHTMLName("th","something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("","something"), AttributeNames.forHTMLName("something"));
        Assert.assertSame(
                AttributeNames.forHTMLName(null,"something"), AttributeNames.forHTMLName("something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("  ","something"), AttributeNames.forHTMLName("something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("  ","SOMETHING"), AttributeNames.forHTMLName("something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-TH-SOMETHING"), AttributeNames.forHTMLName("th:something"));
        Assert.assertSame(
                AttributeNames.forHTMLName("XMLNS","TH"), AttributeNames.forHTMLName("xmlns","th"));
        Assert.assertSame(
                AttributeNames.forHTMLName("data-th-something"), AttributeNames.forHTMLName("TH:SOMETHING"));

        try {
            AttributeNames.forHTMLName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName("");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forHTMLName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }


    @Test
    public void testXMLBuffer() {
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forXMLName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forXMLName("abcdefghijkliklmnsomethingba".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName("abcdefghijkliklmnth:somethingba".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "th", AttributeNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeNames.forXMLName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeNames.forXMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", AttributeNames.forXMLName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).getPrefix());
        Assert.assertEquals(
                "{:something}", AttributeNames.forXMLName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertFalse(
                AttributeNames.forXMLName(":something".toCharArray(), 0, ":something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", AttributeNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertFalse(
                AttributeNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forXMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", AttributeNames.forXMLName("xml:ns".toCharArray(), 0, "xml:ns".length()).getPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forXMLName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeNames.forXMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", AttributeNames.forXMLName("XML:SPACE".toCharArray(), 0, "xml:space".length()).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertEquals(
                "xmlns", AttributeNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).getPrefix());

        Assert.assertSame(
                AttributeNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()), AttributeNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                AttributeNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), AttributeNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                AttributeNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), AttributeNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()));
        Assert.assertNotSame(
                AttributeNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), AttributeNames.forXMLName("DATA-TH-SOMETHING".toCharArray(), 0, "data-th-something".length()));

        try {
            AttributeNames.forXMLName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName("".toCharArray(), 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }


    @Test
    public void testXMLString() {
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName(null, "data-something").toString());
        Assert.assertNull(AttributeNames.forHTMLName(null, "data-something").getPrefix());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forXMLName("something").toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName("th:something").toString());
        Assert.assertEquals(
                "th", AttributeNames.forXMLName("th:something").getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeNames.forXMLName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeNames.forXMLName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", AttributeNames.forXMLName("TH:SOMETHING").getPrefix());
        Assert.assertEquals(
                "{:something}", AttributeNames.forXMLName(":something").toString());
        Assert.assertFalse(
                AttributeNames.forXMLName(":something").isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", AttributeNames.forXMLName("data-th-something").toString());
        Assert.assertFalse(
                AttributeNames.forXMLName("data-th-something").isPrefixed());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forXMLName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forXMLName("xml:ns").toString());
        Assert.assertEquals(
                "xml", AttributeNames.forXMLName("xml:ns").getPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forXMLName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeNames.forXMLName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", AttributeNames.forXMLName("XML:SPACE").getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forHTMLName("xmlns:th").toString());
        Assert.assertEquals(
                "xmlns", AttributeNames.forXMLName("xmlns:th").getPrefix());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forXMLName("th","something").toString());

        Assert.assertSame(
                AttributeNames.forXMLName("data-something"), AttributeNames.forXMLName("data-something"));
        Assert.assertSame(
                AttributeNames.forXMLName("xmlns:th"), AttributeNames.forXMLName("xmlns:th"));
        Assert.assertSame(
                AttributeNames.forXMLName("data-th-something"), AttributeNames.forXMLName("data-th-something"));
        Assert.assertNotSame(
                AttributeNames.forXMLName("data-th-something"), AttributeNames.forXMLName("th:something"));
        Assert.assertSame(
                AttributeNames.forXMLName("xmlns","th"), AttributeNames.forXMLName("xmlns","th"));
        Assert.assertSame(
                AttributeNames.forXMLName("th","something"), AttributeNames.forXMLName("th","something"));
        Assert.assertSame(
                AttributeNames.forXMLName("","something"), AttributeNames.forXMLName("something"));
        Assert.assertSame(
                AttributeNames.forXMLName(null,"something"), AttributeNames.forXMLName("something"));
        Assert.assertSame(
                AttributeNames.forXMLName("  ","something"), AttributeNames.forXMLName("something"));

        try {
            AttributeNames.forXMLName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName("");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forXMLName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }


    @Test
    public void testTextBuffer() {
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forTextName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forTextName("abcdefghijkliklmnsomethingba".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName("abcdefghijkliklmnth:somethingba".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "th", AttributeNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeNames.forTextName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeNames.forTextName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", AttributeNames.forTextName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).getPrefix());
        Assert.assertEquals(
                "{:something}", AttributeNames.forTextName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertFalse(
                AttributeNames.forTextName(":something".toCharArray(), 0, ":something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", AttributeNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertFalse(
                AttributeNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forTextName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forTextName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", AttributeNames.forTextName("xml:ns".toCharArray(), 0, "xml:ns".length()).getPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forTextName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeNames.forTextName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", AttributeNames.forTextName("XML:SPACE".toCharArray(), 0, "xml:space".length()).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertEquals(
                "xmlns", AttributeNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).getPrefix());

        Assert.assertSame(
                AttributeNames.forTextName("data-something".toCharArray(), 0, "data-something".length()), AttributeNames.forTextName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                AttributeNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), AttributeNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                AttributeNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()), AttributeNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()));

        try {
            AttributeNames.forTextName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName("".toCharArray(), 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }


    @Test
    public void testTextString() {
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName(null, "data-something").toString());
        Assert.assertNull(AttributeNames.forHTMLName(null, "data-something").getPrefix());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", AttributeNames.forTextName("something").toString());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName("th:something").toString());
        Assert.assertEquals(
                "th", AttributeNames.forTextName("th:something").getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", AttributeNames.forTextName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", AttributeNames.forTextName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", AttributeNames.forTextName("TH:SOMETHING").getPrefix());
        Assert.assertEquals(
                "{:something}", AttributeNames.forTextName(":something").toString());
        Assert.assertFalse(
                AttributeNames.forTextName(":something").isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", AttributeNames.forTextName("data-th-something").toString());
        Assert.assertFalse(
                AttributeNames.forTextName("data-th-something").isPrefixed());
        Assert.assertEquals(
                "{data-something}", AttributeNames.forTextName("data-something").toString());
        Assert.assertEquals(
                "{xml:ns}", AttributeNames.forTextName("xml:ns").toString());
        Assert.assertEquals(
                "xml", AttributeNames.forTextName("xml:ns").getPrefix());
        Assert.assertEquals(
                "{xml:space}", AttributeNames.forTextName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", AttributeNames.forTextName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", AttributeNames.forTextName("XML:SPACE").getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", AttributeNames.forHTMLName("xmlns:th").toString());
        Assert.assertEquals(
                "xmlns", AttributeNames.forTextName("xmlns:th").getPrefix());
        Assert.assertEquals(
                "{th:something}", AttributeNames.forTextName("th","something").toString());

        Assert.assertSame(
                AttributeNames.forTextName("data-something"), AttributeNames.forTextName("data-something"));
        Assert.assertSame(
                AttributeNames.forTextName("xmlns:th"), AttributeNames.forTextName("xmlns:th"));
        Assert.assertSame(
                AttributeNames.forTextName("data-th-something"), AttributeNames.forTextName("data-th-something"));
        Assert.assertNotSame(
                AttributeNames.forTextName("data-th-something"), AttributeNames.forTextName("th:something"));
        Assert.assertSame(
                AttributeNames.forTextName("xmlns","th"), AttributeNames.forTextName("xmlns","th"));
        Assert.assertSame(
                AttributeNames.forTextName("th","something"), AttributeNames.forTextName("th","something"));
        Assert.assertSame(
                AttributeNames.forTextName("","something"), AttributeNames.forTextName("something"));
        Assert.assertSame(
                AttributeNames.forTextName(null,"something"), AttributeNames.forTextName("something"));
        Assert.assertSame(
                AttributeNames.forTextName("  ","something"), AttributeNames.forTextName("something"));

        try {
            AttributeNames.forTextName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName("");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            AttributeNames.forTextName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }


}
