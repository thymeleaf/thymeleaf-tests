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


public final class AttributeDefinitionsTest {



    @Test
    public void test() {

        final AttributeDefinitions attributeDefinitions = new AttributeDefinitions();

        final int standardSize = AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.size();
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());

        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final AttributeDefinition def1 = attributeDefinitions.forHtmlName(name);
            final AttributeDefinition def2 = attributeDefinitions.forHtmlName(name);
            final AttributeDefinition def3 = attributeDefinitions.forHtmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertSame(def2, def3);
        }
        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final AttributeDefinition def1 = attributeDefinitions.forXmlName(name);
            final AttributeDefinition def2 = attributeDefinitions.forXmlName(name);
            final AttributeDefinition def3 = attributeDefinitions.forXmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertNotSame(def2, def3);
            Assert.assertNotEquals(def2, def3);
        }

        final AttributeDefinition new1 = attributeDefinitions.forHtmlName("NEW");
        Assert.assertNotNull(new1);
        Assert.assertEquals("new", new1.getAttributeName().getAttributeName());
        final AttributeDefinition new2 = attributeDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new2);
        final AttributeDefinition new3 = attributeDefinitions.forHtmlName("NeW");
        Assert.assertSame(new1, new3);
        final AttributeDefinition new4 = attributeDefinitions.forXmlName("NeW");
        Assert.assertNotSame(new1, new4);
        final AttributeDefinition new5 = attributeDefinitions.forXmlName("new");
        Assert.assertNotSame(new4, new5);
        final AttributeDefinition new6 = attributeDefinitions.forXmlName("new");
        Assert.assertSame(new5, new6);
        final AttributeDefinition new7 = attributeDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new7);

        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.size());
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.contains("new"));
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.contains(new1));

        final HtmlAttributeDefinition htmlIdDefinition = attributeDefinitions.forHtmlName("id");
        final HtmlAttributeDefinition htmlDisabledDefinition = attributeDefinitions.forHtmlName("disabled");
        Assert.assertEquals("{disabled}", htmlDisabledDefinition.getAttributeName().toString());

        Assert.assertFalse(htmlIdDefinition.isBooleanAttribute());
        Assert.assertTrue(htmlDisabledDefinition.isBooleanAttribute());

        final AttributeDefinition thtextDefinition = attributeDefinitions.forHtmlName("th:text");
        Assert.assertEquals("{th:text,data-th-text}", thtextDefinition.getAttributeName().toString());
        final AttributeDefinition thtextDefinition2 = attributeDefinitions.forHtmlName("th:text");
        final AttributeDefinition thtextDefinition3 = attributeDefinitions.forHtmlName("th:TEXT");
        final AttributeDefinition thtextDefinition4 = attributeDefinitions.forHtmlName("data-th-TEXT");
        Assert.assertSame(thtextDefinition, thtextDefinition2);
        Assert.assertSame(thtextDefinition, thtextDefinition3);
        Assert.assertSame(thtextDefinition, thtextDefinition4);

        final AttributeDefinition xmlthtextDefinition = attributeDefinitions.forXmlName("th:text");
        Assert.assertEquals("{th:text}", xmlthtextDefinition.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition2 = attributeDefinitions.forXmlName("th:text");
        final AttributeDefinition xmlthtextDefinition3 = attributeDefinitions.forXmlName("th:TEXT");
        Assert.assertEquals("{th:TEXT}", xmlthtextDefinition3.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition4 = attributeDefinitions.forXmlName("data-th-TEXT");
        Assert.assertEquals("{data-th-TEXT}", xmlthtextDefinition4.getAttributeName().toString());
        Assert.assertSame(xmlthtextDefinition, xmlthtextDefinition2);
        Assert.assertNotSame(xmlthtextDefinition, xmlthtextDefinition3);
        Assert.assertNotSame(xmlthtextDefinition, xmlthtextDefinition4);

        final AttributeDefinition thtextDefinition_2 = attributeDefinitions.forHtmlName("th", "text");
        Assert.assertEquals("{th:text,data-th-text}", thtextDefinition_2.getAttributeName().toString());
        final AttributeDefinition thtextDefinition2_2 = attributeDefinitions.forHtmlName("th:text");
        final AttributeDefinition thtextDefinition3_2 = attributeDefinitions.forHtmlName("th:TEXT");
        final AttributeDefinition thtextDefinition4_2 = attributeDefinitions.forHtmlName("data-th-TEXT");
        Assert.assertSame(thtextDefinition_2, thtextDefinition2_2);
        Assert.assertSame(thtextDefinition_2, thtextDefinition3_2);
        Assert.assertSame(thtextDefinition_2, thtextDefinition4_2);

        final AttributeDefinition xmlthtextDefinition_2 = attributeDefinitions.forXmlName("th","text");
        Assert.assertEquals("{th:text}", xmlthtextDefinition_2.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition2_2 = attributeDefinitions.forXmlName("th:text");
        final AttributeDefinition xmlthtextDefinition3_2 = attributeDefinitions.forXmlName("th:TEXT");
        Assert.assertEquals("{th:TEXT}", xmlthtextDefinition3_2.getAttributeName().toString());
        final AttributeDefinition xmlthtextDefinition4_2 = attributeDefinitions.forXmlName("data-th-TEXT");
        Assert.assertEquals("{data-th-TEXT}", xmlthtextDefinition4_2.getAttributeName().toString());
        Assert.assertSame(xmlthtextDefinition_2, xmlthtextDefinition2_2);
        Assert.assertNotSame(xmlthtextDefinition_2, xmlthtextDefinition3_2);
        Assert.assertNotSame(xmlthtextDefinition_2, xmlthtextDefinition4_2);

        AttributeDefinition thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "text");
        Assert.assertEquals("{t:text,data-t-text}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName(null, "text");
        Assert.assertEquals("{text}", thtextDefinition_3.getAttributeName().toString());
        AttributeDefinition thtextDefinition_4 = attributeDefinitions.forHtmlName("text");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{text}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName("thhhh", "text");
        Assert.assertEquals("{thhhh:text,data-thhhh-text}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "t");
        Assert.assertEquals("{t:t,data-t-t}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName(null, "t");
        Assert.assertEquals("{t}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHtmlName("t");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{t}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName("thhhh", "teeee");
        Assert.assertEquals("{thhhh:teeee,data-thhhh-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "te");
        Assert.assertEquals("{t:te,data-t-te}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName(null, "te");
        Assert.assertEquals("{te}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHtmlName("te");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{te}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "ta");
        Assert.assertEquals("{t:ta,data-t-ta}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName(null, "ta");
        Assert.assertEquals("{ta}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHtmlName("ta");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ta}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "ti");
        Assert.assertEquals("{t:ti,data-t-ti}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName(null, "ti");
        Assert.assertEquals("{ti}", thtextDefinition_3.getAttributeName().toString());
        thtextDefinition_4 = attributeDefinitions.forHtmlName("ti");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ti}", thtextDefinition_4.getAttributeName().toString());
        thtextDefinition_3 = attributeDefinitions.forHtmlName("t", "teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_3.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName("t:teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "t:teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHtmlName("data-t-teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "data-t-teeee");
        Assert.assertEquals("{t:teeee,data-t-teeee}", thtextDefinition_4.getAttributeName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        try {
            thtextDefinition_4 = attributeDefinitions.forHtmlName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forHtmlName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXmlName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXmlName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = attributeDefinitions.forXmlName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "data:teeee");
        Assert.assertEquals("{data:teeee,data-data-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee,data-dataa-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "data:data");
        Assert.assertEquals("{data:data,data-data-data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "DATA:TEEEE");
        Assert.assertEquals("{data:teeee,data-data-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "DATA");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "DATAA:TEEEE");
        Assert.assertEquals("{dataa:teeee,data-dataa-teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forHtmlName(null, "DATA:DATA");
        Assert.assertEquals("{data:data,data-data-data}", thtextDefinition_4.getAttributeName().toString());


        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getAttributeName().toString());

        thtextDefinition_4 = attributeDefinitions.forXmlName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getAttributeName().toString());

    }



    
}
