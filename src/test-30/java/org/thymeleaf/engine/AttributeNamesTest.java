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


public final class AttributeNamesTest {



    @Test
    public void testHTMLBuffer() {
        Assert.assertEquals(
                "{data-something}", AttributeNames.forHTMLName(null, "data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertNull(AttributeNames.forHTMLName(null, "data-something".toCharArray(), 0, "data-something".length()).getPrefix());
        Assert.assertEquals(
                "{th:something,data-th-something}", AttributeNames.forHTMLName(null, "th:something".toCharArray(), 0, "th:something".length()).toString());
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
                "{data-something}", AttributeNames.forHTMLName(null, "data-something".toCharArray(), 0, "data-something".length()).toString());
        Assert.assertNull(AttributeNames.forHTMLName(null, "data-something".toCharArray(), 0, "data-something".length()).getPrefix());
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
                "{th:something}", AttributeNames.forXMLName(null, "th:something".toCharArray(), 0, "th:something".length()).toString());
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


}
