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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;


public final class AttributeDefinitionsTest {



    @Test
    public void test() {

        final AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);

        final int standardSize = AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size();
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());

        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final AttributeDefinition def1 = attributeDefinitions.forHTMLName(name);
            final AttributeDefinition def2 = attributeDefinitions.forHTMLName(name);
            final AttributeDefinition def3 = attributeDefinitions.forHTMLName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertSame(def2, def3);
        }
        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final AttributeDefinition def1 = attributeDefinitions.forXMLName(name);
            final AttributeDefinition def2 = attributeDefinitions.forXMLName(name);
            final AttributeDefinition def3 = attributeDefinitions.forXMLName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertNotSame(def2, def3);
            Assert.assertNotEquals(def2, def3);
        }

        final AttributeDefinition new1 = attributeDefinitions.forHTMLName("NEW");
        Assert.assertNotNull(new1);
        Assert.assertEquals("new", new1.getAttributeName().getAttributeName());
        final AttributeDefinition new2 = attributeDefinitions.forHTMLName("new");
        Assert.assertSame(new1, new2);
        final AttributeDefinition new3 = attributeDefinitions.forHTMLName("NeW");
        Assert.assertSame(new1, new3);
        final AttributeDefinition new4 = attributeDefinitions.forXMLName("NeW");
        Assert.assertNotSame(new1, new4);
        final AttributeDefinition new5 = attributeDefinitions.forXMLName("new");
        Assert.assertNotSame(new4, new5);
        final AttributeDefinition new6 = attributeDefinitions.forXMLName("new");
        Assert.assertSame(new5, new6);
        final AttributeDefinition new7 = attributeDefinitions.forHTMLName("new");
        Assert.assertSame(new1, new7);

        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.contains("new"));
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.contains(new1.getAttributeName().attributeName));

        final HTMLAttributeDefinition htmlIdDefinition = attributeDefinitions.forHTMLName("id");
        final HTMLAttributeDefinition htmlDisabledDefinition = attributeDefinitions.forHTMLName("disabled");
        Assert.assertEquals("{disabled}", htmlDisabledDefinition.getAttributeName().toString());

        Assert.assertFalse(htmlIdDefinition.isBooleanAttribute());
        Assert.assertTrue(htmlDisabledDefinition.isBooleanAttribute());

        final AttributeDefinition thtextDefinition = attributeDefinitions.forHTMLName("th:text");
        Assert.assertEquals("{th:text,data-th-text}", thtextDefinition.getAttributeName().toString());
        final AttributeDefinition thtextDefinition2 = attributeDefinitions.forHTMLName("th:text");
        final AttributeDefinition thtextDefinition3 = attributeDefinitions.forHTMLName("th:TEXT");
        final AttributeDefinition thtextDefinition4 = attributeDefinitions.forHTMLName("data-th-TEXT");
        Assert.assertSame(thtextDefinition, thtextDefinition2);
        Assert.assertSame(thtextDefinition, thtextDefinition3);
        Assert.assertSame(thtextDefinition, thtextDefinition4);

        final AttributeDefinition xmlthtextDefinition = attributeDefinitions.forXMLName("th:text");
        Assert.assertEquals("{th:text}", xmlthtextDefinition.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition2 = attributeDefinitions.forXMLName("th:text");
        final AttributeDefinition xmlthtextDefinition3 = attributeDefinitions.forXMLName("th:TEXT");
        Assert.assertEquals("{th:TEXT}", xmlthtextDefinition3.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition4 = attributeDefinitions.forXMLName("data-th-TEXT");
        Assert.assertEquals("{data-th-TEXT}", xmlthtextDefinition4.getAttributeName().toString());
        Assert.assertSame(xmlthtextDefinition, xmlthtextDefinition2);
        Assert.assertNotSame(xmlthtextDefinition, xmlthtextDefinition3);
        Assert.assertNotSame(xmlthtextDefinition, xmlthtextDefinition4);

        final AttributeDefinition thtextDefinition_2 = attributeDefinitions.forHTMLName("th", "text");
        Assert.assertEquals("{th:text,data-th-text}", thtextDefinition_2.getAttributeName().toString());
        final AttributeDefinition thtextDefinition2_2 = attributeDefinitions.forHTMLName("th:text");
        final AttributeDefinition thtextDefinition3_2 = attributeDefinitions.forHTMLName("th:TEXT");
        final AttributeDefinition thtextDefinition4_2 = attributeDefinitions.forHTMLName("data-th-TEXT");
        Assert.assertSame(thtextDefinition_2, thtextDefinition2_2);
        Assert.assertSame(thtextDefinition_2, thtextDefinition3_2);
        Assert.assertSame(thtextDefinition_2, thtextDefinition4_2);

        final AttributeDefinition xmlthtextDefinition_2 = attributeDefinitions.forXMLName("th", "text");
        Assert.assertEquals("{th:text}", xmlthtextDefinition_2.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition2_2 = attributeDefinitions.forXMLName("th:text");
        final AttributeDefinition xmlthtextDefinition3_2 = attributeDefinitions.forXMLName("th:TEXT");
        Assert.assertEquals("{th:TEXT}", xmlthtextDefinition3_2.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition4_2 = attributeDefinitions.forXMLName("data-th-TEXT");
        Assert.assertEquals("{data-th-TEXT}", xmlthtextDefinition4_2.getAttributeName().toString());
        Assert.assertSame(xmlthtextDefinition_2, xmlthtextDefinition2_2);
        Assert.assertNotSame(xmlthtextDefinition_2, xmlthtextDefinition3_2);
        Assert.assertNotSame(xmlthtextDefinition_2, xmlthtextDefinition4_2);

        AttributeDefinition thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "text");
        Assert.assertEquals("{t:text,data-t-text}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName(null, "text");
        Assert.assertEquals("{text}", thtextDefinition_3.getAttributeName().toString());
        AttributeDefinition thtextDefinition_4 = attributeDefinitions.forHTMLName("text");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{text}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName("thhhh", "text");
        Assert.assertEquals("{thhhh:text,data-thhhh-text}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "t");
        Assert.assertEquals("{t:t,data-t-t}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName(null, "t");
        Assert.assertEquals("{t}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHTMLName("t");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{t}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName("thhhh", "teeee");
        Assert.assertEquals("{thhhh:teeee,data-thhhh-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "te");
        Assert.assertEquals("{t:te,data-t-te}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName(null, "te");
        Assert.assertEquals("{te}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHTMLName("te");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{te}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "ta");
        Assert.assertEquals("{t:ta,data-t-ta}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName(null, "ta");
        Assert.assertEquals("{ta}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHTMLName("ta");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ta}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "ti");
        Assert.assertEquals("{t:ti,data-t-ti}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName(null, "ti");
        Assert.assertEquals("{ti}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHTMLName("ti");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ti}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName("t:teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "t:teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHTMLName("data-t-teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "data-t-teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        try {
            thtextDefinition_4 = attributeDefinitions.forHTMLName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forHTMLName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXMLName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXMLName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXMLName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forTextName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forTextName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forTextName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forCSSName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forCSSName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forCSSName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "data:teeee");
        Assert.assertEquals("{data:teeee,data-data-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee,data-dataa-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "data:data");
        Assert.assertEquals("{data:data,data-data-data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "DATA:TEEEE");
        Assert.assertEquals("{data:teeee,data-data-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "DATA");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "DATAA:TEEEE");
        Assert.assertEquals("{dataa:teeee,data-dataa-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHTMLName(null, "DATA:DATA");
        Assert.assertEquals("{data:data,data-data-data}", thtextDefinition_4.getAttributeName().toString());


        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXMLName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getAttributeName().toString());


        thtextDefinition_4 = attributeDefinitions.forTextName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forTextName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getAttributeName().toString());


        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forJavaScriptName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getAttributeName().toString());


        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forCSSName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getAttributeName().toString());

    }



    @Test
    public void testEmptyPrefix() {

        final AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);

        final AttributeDefinition ad01 = attributeDefinitions.forHTMLName("", "one");
        final AttributeDefinition ad02 = attributeDefinitions.forXMLName("", "one");
        final AttributeDefinition ad03 = attributeDefinitions.forTextName("", "one");
        final AttributeDefinition ad04 = attributeDefinitions.forJavaScriptName("", "one");
        final AttributeDefinition ad05 = attributeDefinitions.forCSSName("", "one");


        Assert.assertEquals("{one}", ad01.getAttributeName().toString());
        Assert.assertEquals("{one}", ad02.getAttributeName().toString());
        Assert.assertEquals("{one}", ad03.getAttributeName().toString());
        Assert.assertEquals("{one}", ad04.getAttributeName().toString());
        Assert.assertEquals("{one}", ad05.getAttributeName().toString());

    }



    @Test
    public void testNullPrefix() {

        final AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);

        final AttributeDefinition ad01 = attributeDefinitions.forHTMLName(null, "one");
        final AttributeDefinition ad02 = attributeDefinitions.forXMLName(null, "one");
        final AttributeDefinition ad03 = attributeDefinitions.forTextName(null, "one");
        final AttributeDefinition ad04 = attributeDefinitions.forJavaScriptName(null, "one");
        final AttributeDefinition ad05 = attributeDefinitions.forCSSName(null, "one");


        Assert.assertEquals("{one}", ad01.getAttributeName().toString());
        Assert.assertEquals("{one}", ad02.getAttributeName().toString());
        Assert.assertEquals("{one}", ad03.getAttributeName().toString());
        Assert.assertEquals("{one}", ad04.getAttributeName().toString());
        Assert.assertEquals("{one}", ad05.getAttributeName().toString());

    }



    @Test
    public void testWhitespacePrefix() {

        final AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);

        final AttributeDefinition ad01 = attributeDefinitions.forHTMLName(" ", "one");
        final AttributeDefinition ad02 = attributeDefinitions.forXMLName(" ", "one");
        final AttributeDefinition ad03 = attributeDefinitions.forTextName(" ", "one");
        final AttributeDefinition ad04 = attributeDefinitions.forJavaScriptName(" ", "one");
        final AttributeDefinition ad05 = attributeDefinitions.forCSSName(" ", "one");


        Assert.assertEquals("{one}", ad01.getAttributeName().toString());
        Assert.assertEquals("{one}", ad02.getAttributeName().toString());
        Assert.assertEquals("{one}", ad03.getAttributeName().toString());
        Assert.assertEquals("{one}", ad04.getAttributeName().toString());
        Assert.assertEquals("{one}", ad05.getAttributeName().toString());

    }



    
}
