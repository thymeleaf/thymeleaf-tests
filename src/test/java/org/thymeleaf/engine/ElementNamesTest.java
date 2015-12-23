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
                "{th:something,th-something}", ElementNames.forHTMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
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

        Assert.assertSame(
                ElementNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()), ElementNames.forHTMLName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                ElementNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), ElementNames.forHTMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                ElementNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()));
        Assert.assertSame(
                ElementNames.forHTMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forHTMLName("DATA-TH-SOMETHING".toCharArray(), 0, "data-th-something".length()));

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
        Assert.assertEquals(
                "{th:something,th-something}", ElementNames.forHTMLName("th","something").toString());

        Assert.assertSame(
                ElementNames.forHTMLName("data-something"), ElementNames.forHTMLName("data-something"));
        Assert.assertSame(
                ElementNames.forHTMLName("xmlns:th"), ElementNames.forHTMLName("xmlns:th"));
        Assert.assertSame(
                ElementNames.forHTMLName("data-th-something"), ElementNames.forHTMLName("data-th-something"));
        Assert.assertNotSame(
                ElementNames.forHTMLName("data-th-something"), ElementNames.forHTMLName("th:something"));
        Assert.assertSame(
                ElementNames.forHTMLName("th-something"), ElementNames.forHTMLName("th:something"));
        Assert.assertSame(
                ElementNames.forHTMLName("xmlns","th"), ElementNames.forHTMLName("xmlns","th"));
        Assert.assertSame(
                ElementNames.forHTMLName("th","something"), ElementNames.forHTMLName("th","something"));
        Assert.assertSame(
                ElementNames.forHTMLName("","something"), ElementNames.forHTMLName("something"));
        Assert.assertSame(
                ElementNames.forHTMLName(null,"something"), ElementNames.forHTMLName("something"));
        Assert.assertSame(
                ElementNames.forHTMLName("  ","something"), ElementNames.forHTMLName("something"));
        Assert.assertSame(
                ElementNames.forHTMLName("  ","SOMETHING"), ElementNames.forHTMLName("something"));
        Assert.assertSame(
                ElementNames.forHTMLName("TH-SOMETHING"), ElementNames.forHTMLName("th:something"));
        Assert.assertSame(
                ElementNames.forHTMLName("XMLNS","TH"), ElementNames.forHTMLName("xmlns","th"));
        Assert.assertSame(
                ElementNames.forHTMLName("th-something"), ElementNames.forHTMLName("TH:SOMETHING"));

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
                "{th:something}", ElementNames.forXMLName("th:something".toCharArray(), 0, "th:something".length()).toString());
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

        Assert.assertSame(
                ElementNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()), ElementNames.forXMLName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                ElementNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), ElementNames.forXMLName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                ElementNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()));
        Assert.assertNotSame(
                ElementNames.forXMLName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forXMLName("DATA-TH-SOMETHING".toCharArray(), 0, "data-th-something".length()));

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
        Assert.assertEquals(
                "{th:something}", ElementNames.forXMLName("th","something").toString());

        Assert.assertSame(
                ElementNames.forXMLName("data-something"), ElementNames.forXMLName("data-something"));
        Assert.assertSame(
                ElementNames.forXMLName("xmlns:th"), ElementNames.forXMLName("xmlns:th"));
        Assert.assertSame(
                ElementNames.forXMLName("data-th-something"), ElementNames.forXMLName("data-th-something"));
        Assert.assertNotSame(
                ElementNames.forXMLName("data-th-something"), ElementNames.forXMLName("th:something"));
        Assert.assertSame(
                ElementNames.forXMLName("xmlns","th"), ElementNames.forXMLName("xmlns","th"));
        Assert.assertSame(
                ElementNames.forXMLName("th","something"), ElementNames.forXMLName("th","something"));
        Assert.assertSame(
                ElementNames.forXMLName("","something"), ElementNames.forXMLName("something"));
        Assert.assertSame(
                ElementNames.forXMLName(null,"something"), ElementNames.forXMLName("something"));
        Assert.assertSame(
                ElementNames.forXMLName("  ","something"), ElementNames.forXMLName("something"));

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


    @Test
    public void testTextBuffer() {
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forTextName("something".toCharArray(), 0, "something".length()).toString());
        Assert.assertEquals(
                "{something}", ElementNames.forTextName("abcdefghijkliklmnsomething".toCharArray(), 17, "something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName("abcdefghijkliklmnth:something".toCharArray(), 17, "th:something".length()).toString());
        Assert.assertEquals(
                "th", ElementNames.forTextName("th:something".toCharArray(), 0, "th:something".length()).getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forTextName("SOMETHING".toCharArray(), 0, "SOMETHING".length()).toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forTextName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).toString());
        Assert.assertEquals(
                "TH", ElementNames.forTextName("TH:SOMETHING".toCharArray(), 0, "TH:SOMETHING".length()).getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forTextName(":something".toCharArray(), 0, ":something".length()).toString());
        Assert.assertFalse(
                ElementNames.forTextName(":something".toCharArray(), 0, ":something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()).toString());
        Assert.assertFalse(
                ElementNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()).isPrefixed());
        Assert.assertEquals(
                "{data-something}", ElementNames.forTextName("data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forTextName("data:something").toString());
        Assert.assertEquals(
                "data", ElementNames.forTextName("data:something").getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forTextName("xml:ns".toCharArray(), 0, "xml:ns".length()).toString());
        Assert.assertEquals(
                "xml", ElementNames.forTextName("xml:ns".toCharArray(), 0, "xml:ns".length()).getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forTextName("xml:space".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forTextName("XML:SPACE".toCharArray(), 0, "xml:space".length()).toString());
        Assert.assertEquals(
                "XML", ElementNames.forTextName("XML:SPACE".toCharArray(), 0, "xml:space".length()).getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).toString());
        Assert.assertEquals(
                "xmlns", ElementNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()).getPrefix());

        Assert.assertSame(
                ElementNames.forTextName("data-something".toCharArray(), 0, "data-something".length()), ElementNames.forTextName("data-something".toCharArray(), 0, "data-something".length()));
        Assert.assertSame(
                ElementNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()), ElementNames.forTextName("xmlns:th".toCharArray(), 0, "xmlns:th".length()));
        Assert.assertSame(
                ElementNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()));
        Assert.assertNotSame(
                ElementNames.forTextName("data-th-something".toCharArray(), 0, "data-th-something".length()), ElementNames.forTextName("DATA-TH-SOMETHING".toCharArray(), 0, "data-th-something".length()));

        try {
            ElementNames.forTextName(null, 0, 0);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forTextName("".toCharArray(), 0, 0);
            Assert.assertTrue(true); // In text mode, element names CAN have empty names
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(false);
        }

        try {
            ElementNames.forTextName(" ".toCharArray(), 0, 1);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testTextString() {
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName(null, "th:something").toString());
        Assert.assertEquals(
                "{something}", ElementNames.forTextName("something").toString());
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName("th:something").toString());
        Assert.assertEquals(
                "th", ElementNames.forTextName("th:something").getPrefix());
        Assert.assertEquals(
                "{SOMETHING}", ElementNames.forTextName("SOMETHING").toString());
        Assert.assertEquals(
                "{TH:SOMETHING}", ElementNames.forTextName("TH:SOMETHING").toString());
        Assert.assertEquals(
                "TH", ElementNames.forTextName("TH:SOMETHING").getPrefix());
        Assert.assertEquals(
                "{:something}", ElementNames.forTextName(":something").toString());
        Assert.assertFalse(
                ElementNames.forTextName(":something").isPrefixed());
        Assert.assertEquals(
                "{data-th-something}", ElementNames.forTextName("data-th-something").toString());
        Assert.assertFalse(
                ElementNames.forTextName("data-th-something").isPrefixed());
        Assert.assertEquals(
                "{data-something}", ElementNames.forTextName("data-something").toString());
        Assert.assertEquals(
                "{data:something}", ElementNames.forTextName("data:something").toString());
        Assert.assertEquals(
                "data", ElementNames.forTextName("data:something").getPrefix());
        Assert.assertEquals(
                "{xml:ns}", ElementNames.forTextName("xml:ns").toString());
        Assert.assertEquals(
                "xml", ElementNames.forTextName("xml:ns").getPrefix());
        Assert.assertEquals(
                "{xml:space}", ElementNames.forTextName("xml:space").toString());
        Assert.assertEquals(
                "{XML:SPACE}", ElementNames.forTextName("XML:SPACE").toString());
        Assert.assertEquals(
                "XML", ElementNames.forTextName("XML:SPACE").getPrefix());
        Assert.assertEquals(
                "{xmlns:th}", ElementNames.forTextName("xmlns:th").toString());
        Assert.assertEquals(
                "xmlns", ElementNames.forTextName("xmlns:th").getPrefix());
        Assert.assertEquals(
                "{th:something}", ElementNames.forTextName("th","something").toString());

        Assert.assertSame(
                ElementNames.forTextName("data-something"), ElementNames.forTextName("data-something"));
        Assert.assertSame(
                ElementNames.forTextName("xmlns:th"), ElementNames.forTextName("xmlns:th"));
        Assert.assertSame(
                ElementNames.forTextName("data-th-something"), ElementNames.forTextName("data-th-something"));
        Assert.assertNotSame(
                ElementNames.forTextName("data-th-something"), ElementNames.forTextName("th:something"));
        Assert.assertSame(
                ElementNames.forTextName("xmlns","th"), ElementNames.forTextName("xmlns","th"));
        Assert.assertSame(
                ElementNames.forTextName("th","something"), ElementNames.forTextName("th","something"));
        Assert.assertSame(
                ElementNames.forTextName("","something"), ElementNames.forTextName("something"));
        Assert.assertSame(
                ElementNames.forTextName(null,"something"), ElementNames.forTextName("something"));
        Assert.assertSame(
                ElementNames.forTextName("  ","something"), ElementNames.forTextName("something"));

        try {
            ElementNames.forTextName(null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forTextName("");
            Assert.assertTrue(true);
        } catch (final IllegalArgumentException e) {
            // Empty-name elements ARE allowed in TEXT modes
            Assert.assertTrue(false);
        }

        try {
            ElementNames.forTextName("t", "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forTextName(" ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            ElementNames.forTextName("t", " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


}
