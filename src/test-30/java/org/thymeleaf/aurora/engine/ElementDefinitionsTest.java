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
            final ElementDefinition def1 = elementDefinitions.forHTMLName(name);
            final ElementDefinition def2 = elementDefinitions.forHTMLName(name);
            final ElementDefinition def3 = elementDefinitions.forHTMLName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertSame(def2, def3);
        }
        for (final String name : ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES) {
            final ElementDefinition def1 = elementDefinitions.forXMLName(name);
            final ElementDefinition def2 = elementDefinitions.forXMLName(name);
            final ElementDefinition def3 = elementDefinitions.forXMLName(name.toUpperCase());
            Assert.assertSame(def1, def2);
            Assert.assertNotSame(def2, def3);
            Assert.assertNotEquals(def2, def3);
        }

        final ElementDefinition new1 = elementDefinitions.forHTMLName("NEW");
        Assert.assertNotNull(new1);
        Assert.assertEquals("new", new1.getElementName().getElementName());
        final ElementDefinition new2 = elementDefinitions.forHTMLName("new");
        Assert.assertSame(new1, new2);
        final ElementDefinition new3 = elementDefinitions.forHTMLName("NeW");
        Assert.assertSame(new1, new3);
        final ElementDefinition new4 = elementDefinitions.forXMLName("NeW");
        Assert.assertNotSame(new1, new4);
        final ElementDefinition new5 = elementDefinitions.forXMLName("new");
        Assert.assertNotSame(new1, new5);
        final ElementDefinition new6 = elementDefinitions.forXMLName("new");
        Assert.assertSame(new5, new6);
        final ElementDefinition new7 = elementDefinitions.forHTMLName("new");
        Assert.assertSame(new1, new7);

        Assert.assertEquals(standardSize, ElementDefinitions.ALL_STANDARD_HTML_ELEMENTS.size());
        Assert.assertEquals(standardSize, ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES.size());
        Assert.assertFalse(ElementDefinitions.ALL_STANDARD_HTML_ELEMENT_NAMES.contains("new"));
        Assert.assertFalse(ElementDefinitions.ALL_STANDARD_HTML_ELEMENTS.contains(new1));

        ElementDefinition thtextDefinition_3 = elementDefinitions.forHTMLName("t", "text");
        Assert.assertEquals("{t:text,t-text}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName(null, "text");
        Assert.assertEquals("{text}", thtextDefinition_3.getElementName().toString());
        ElementDefinition thtextDefinition_4 = elementDefinitions.forHTMLName("text");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{text}", thtextDefinition_4.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName("thhhh", "text");
        Assert.assertEquals("{thhhh:text,thhhh-text}", thtextDefinition_3.getElementName().toString());

        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "t");
        Assert.assertEquals("{t:t,t-t}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName(null, "t");
        Assert.assertEquals("{t}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_4 = elementDefinitions.forHTMLName("t");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{t}", thtextDefinition_4.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName("thhhh", "teeee");
        Assert.assertEquals("{thhhh:teeee,thhhh-teeee}", thtextDefinition_3.getElementName().toString());

        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "te");
        Assert.assertEquals("{t:te,t-te}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName(null, "te");
        Assert.assertEquals("{te}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_4 = elementDefinitions.forHTMLName("te");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{te}", thtextDefinition_4.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_3.getElementName().toString());

        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "ta");
        Assert.assertEquals("{t:ta,t-ta}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName(null, "ta");
        Assert.assertEquals("{ta}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_4 = elementDefinitions.forHTMLName("ta");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ta}", thtextDefinition_4.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_3.getElementName().toString());

        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "ti");
        Assert.assertEquals("{t:ti,t-ti}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName(null, "ti");
        Assert.assertEquals("{ti}", thtextDefinition_3.getElementName().toString());
        thtextDefinition_4 = elementDefinitions.forHTMLName("ti");
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);
        Assert.assertEquals("{ti}", thtextDefinition_4.getElementName().toString());
        thtextDefinition_3 = elementDefinitions.forHTMLName("t", "teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_3.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName("t:teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_4.getElementName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "t:teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_4.getElementName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = elementDefinitions.forHTMLName("t-teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_4.getElementName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "t-teeee");
        Assert.assertEquals("{t:teeee,t-teeee}", thtextDefinition_4.getElementName().toString());
        Assert.assertSame(thtextDefinition_3, thtextDefinition_4);

        try {
            thtextDefinition_4 = elementDefinitions.forHTMLName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = elementDefinitions.forHTMLName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = elementDefinitions.forHTMLName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = elementDefinitions.forXMLName(null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = elementDefinitions.forXMLName(null, "");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            thtextDefinition_4 = elementDefinitions.forXMLName(null, " ");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "data:teeee");
        Assert.assertEquals("{data:teeee,data-teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee,dataa-teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "data:data");
        Assert.assertEquals("{data:data,data-data}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "DATA:TEEEE");
        Assert.assertEquals("{data:teeee,data-teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "DATA");
        Assert.assertEquals("{data}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "DATAA:TEEEE");
        Assert.assertEquals("{dataa:teeee,dataa-teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forHTMLName(null, "DATA:DATA");
        Assert.assertEquals("{data:data,data-data}", thtextDefinition_4.getElementName().toString());


        thtextDefinition_4 = elementDefinitions.forXMLName(null, "data:teeee");
        Assert.assertEquals("{data:teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "data");
        Assert.assertEquals("{data}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "dataa:teeee");
        Assert.assertEquals("{dataa:teeee}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "data:data");
        Assert.assertEquals("{data:data}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "DATA:TEEEE");
        Assert.assertEquals("{DATA:TEEEE}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "DATA");
        Assert.assertEquals("{DATA}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "DATAA:TEEEE");
        Assert.assertEquals("{DATAA:TEEEE}", thtextDefinition_4.getElementName().toString());

        thtextDefinition_4 = elementDefinitions.forXMLName(null, "DATA:DATA");
        Assert.assertEquals("{DATA:DATA}", thtextDefinition_4.getElementName().toString());

    }



    
}
