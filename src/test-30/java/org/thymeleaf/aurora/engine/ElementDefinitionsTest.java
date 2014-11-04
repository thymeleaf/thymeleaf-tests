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


public final class ElementDefinitionsTest {



    @Test
    public void test() {

        final ElementDefinitions elementDefinitions = new ElementDefinitions();

        final int standardSize = ElementDefinitions.ALL_STANDARD_HTML_ELEMENTS.size();
        Assert.assertEquals(standardSize, elementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES.size());

        for (final String name : ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES) {
            final ElementDefinition def1 = elementDefinitions.forHtmlName(name);
            final ElementDefinition def2 = elementDefinitions.forHtmlName(name);
            final ElementDefinition def3 = elementDefinitions.forHtmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertSame(def2, def3);
        }
        for (final String name : ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES) {
            final ElementDefinition def1 = elementDefinitions.forXmlName(name);
            final ElementDefinition def2 = elementDefinitions.forXmlName(name);
            final ElementDefinition def3 = elementDefinitions.forXmlName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertNotSame(def2, def3);
            Assert.assertNotEquals(def2, def3);
        }

        final ElementDefinition new1 = elementDefinitions.forHtmlName("NEW");
        Assert.assertNotNull(new1);
        Assert.assertEquals("new", new1.getName());
        final ElementDefinition new2 = elementDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new2);
        final ElementDefinition new3 = elementDefinitions.forHtmlName("NeW");
        Assert.assertSame(new1, new3);
        final ElementDefinition new4 = elementDefinitions.forXmlName("NeW");
        Assert.assertNotSame(new1, new4);
        final ElementDefinition new5 = elementDefinitions.forXmlName("new");
        Assert.assertNotSame(new1, new5);
        final ElementDefinition new6 = elementDefinitions.forXmlName("new");
        Assert.assertSame(new5, new6);
        final ElementDefinition new7 = elementDefinitions.forHtmlName("new");
        Assert.assertSame(new1, new7);

        Assert.assertEquals(standardSize, ElementDefinitions.ALL_STANDARD_HTML_ELEMENTS.size());
        Assert.assertEquals(standardSize, ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES.size());
        Assert.assertFalse(ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES.contains("new"));
        Assert.assertFalse(ElementDefinitions.ALL_STANDARD_HTML_ELEMENTS.contains(new1));

    }



    
}
