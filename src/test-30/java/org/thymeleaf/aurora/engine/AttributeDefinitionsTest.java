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
import org.thymeleaf.aurora.engine.AttributeDefinition;
import org.thymeleaf.aurora.engine.AttributeDefinitions;
import org.thymeleaf.aurora.engine.ElementDefinition;
import org.thymeleaf.aurora.engine.ElementDefinitions;


public final class AttributeDefinitionsTest {



    @Test
    public void test() {

        final int standardSize = AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.size();
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());

        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final AttributeDefinition def1 = AttributeDefinitions.forHtmlName(name);
            final AttributeDefinition def2 = AttributeDefinitions.forHtmlName(name);
            final AttributeDefinition def3 = AttributeDefinitions.forHtmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertSame(def2, def3);
        }
        for (final String name : AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES) {
            final ElementDefinition def1 = ElementDefinitions.forXmlName(name);
            final ElementDefinition def2 = ElementDefinitions.forXmlName(name);
            final ElementDefinition def3 = ElementDefinitions.forXmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertNotSame(def2, def3);
            Assert.assertNotEquals(def2, def3);
        }

        final AttributeDefinition new1 = AttributeDefinitions.forHtmlName("NEW");
        Assert.assertNotNull(new1);
        Assert.assertEquals("new", new1.getName());
        final AttributeDefinition new2 = AttributeDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new2);
        final AttributeDefinition new3 = AttributeDefinitions.forHtmlName("NeW");
        Assert.assertSame(new1, new3);
        final AttributeDefinition new4 = AttributeDefinitions.forXmlName("NeW");
        Assert.assertNotSame(new1, new4);
        final AttributeDefinition new5 = AttributeDefinitions.forXmlName("new");
        Assert.assertNotSame(new4, new5);
        final AttributeDefinition new6 = AttributeDefinitions.forXmlName("new");
        Assert.assertSame(new5, new6);
        final AttributeDefinition new7 = AttributeDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new7);

        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.size());
        Assert.assertEquals(standardSize, AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.size());
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTE_NAMES.contains("new"));
        Assert.assertFalse(AttributeDefinitions.ALL_STANDARD_HTML_ATTRIBUTES.contains(new1));

    }



    
}
