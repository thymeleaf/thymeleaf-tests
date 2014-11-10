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
        Assert.assertEquals("new", new1.getAttributeName().getCompleteNSAttributeName());
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

        final AttributeDefinition htmlIdDefinition = attributeDefinitions.forHtmlName("id");
        final AttributeDefinition htmlDisabledDefinition = attributeDefinitions.forHtmlName("disabled");
        final AttributeDefinition xmlDisabledDefinition = attributeDefinitions.forXmlName("disabled");
        Assert.assertEquals("{disabled}", htmlDisabledDefinition.getAttributeName().toString());

        Assert.assertFalse(htmlIdDefinition.isBooleanAttribute());
        Assert.assertTrue(htmlDisabledDefinition.isBooleanAttribute());
        Assert.assertFalse(xmlDisabledDefinition.isBooleanAttribute());

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

    }



    
}
