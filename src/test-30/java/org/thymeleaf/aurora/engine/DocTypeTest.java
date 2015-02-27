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


public final class DocTypeTest {


    @Test
    public void test() {

        final String doctypeHTML5UC = "<!DOCTYPE html>";
        final String doctypeHTML5LC = "<!doctype html>";

        final String doctypeXHTMLTransitional = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        final String doctypeXHTMLTransitionalWS = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\" [\n <!-- an internal subset can be embedded here -->\n ]>";
        final String doctypeXHTMLTransitionalKLC = "<!doctype html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";

        final String keywordUC = "DOCTYPE";
        final String keywordLC = "doctype";

        final String elementNameHtml = "html";

        final String typePublicUC = "PUBLIC";
        final String typeSystemUC = "SYSTEM";

        final String publicIdXHTMLTransitional = "-//W3C//DTD XHTML 1.0 Transitional//EN";
        final String systemIdXHTMLTransitional = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";

        final String internalSubsetWSXHTMLTransitional = "\n <!-- an internal subset can be embedded here -->\n ";

        final DocType d1 = new DocType();
        d1.setDocType(
                doctypeXHTMLTransitionalWS,
                keywordUC,
                elementNameHtml,
                typePublicUC,
                publicIdXHTMLTransitional,
                systemIdXHTMLTransitional,
                internalSubsetWSXHTMLTransitional,
                11, 4
        );

        Assert.assertSame(doctypeXHTMLTransitionalWS, d1.getDocType());
        Assert.assertSame(keywordUC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertSame(typePublicUC, d1.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d1.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d1.getSystemId());
        Assert.assertSame(internalSubsetWSXHTMLTransitional, d1.getInternalSubset());
        Assert.assertEquals(11, d1.getLine());
        Assert.assertEquals(4, d1.getCol());

        d1.setDocType(
                doctypeXHTMLTransitionalWS,
                keywordUC,
                elementNameHtml,
                typePublicUC,
                publicIdXHTMLTransitional,
                systemIdXHTMLTransitional,
                internalSubsetWSXHTMLTransitional,
                10, 3
        );

        Assert.assertSame(doctypeXHTMLTransitionalWS, d1.getDocType());
        Assert.assertSame(keywordUC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertSame(typePublicUC, d1.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d1.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d1.getSystemId());
        Assert.assertSame(internalSubsetWSXHTMLTransitional, d1.getInternalSubset());
        Assert.assertEquals(10, d1.getLine());
        Assert.assertEquals(3, d1.getCol());

        d1.setInternalSubset(null);

        Assert.assertEquals(doctypeXHTMLTransitional, d1.getDocType());
        Assert.assertSame(keywordUC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertSame(typePublicUC, d1.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d1.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setKeyword(keywordLC);
        Assert.assertEquals(doctypeXHTMLTransitionalKLC, d1.getDocType());
        Assert.assertSame(keywordLC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertSame(typePublicUC, d1.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d1.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setIDs("something", "someother");
        Assert.assertEquals("<!doctype html PUBLIC \"something\" \"someother\">", d1.getDocType());
        Assert.assertSame(keywordLC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertEquals(typePublicUC, d1.getType());
        Assert.assertSame("something", d1.getPublicId());
        Assert.assertSame("someother", d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setIDs(null, "someother");
        Assert.assertEquals("<!doctype html SYSTEM \"someother\">", d1.getDocType());
        Assert.assertSame(keywordLC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertEquals(typeSystemUC, d1.getType());
        Assert.assertNull(d1.getPublicId());
        Assert.assertSame("someother", d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setIDs("system", null, "someother");
        Assert.assertEquals("<!doctype html system \"someother\">", d1.getDocType());
        Assert.assertSame(keywordLC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertEquals("system", d1.getType());
        Assert.assertNull(d1.getPublicId());
        Assert.assertSame("someother", d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setToHtml5();
        Assert.assertEquals(doctypeHTML5LC, d1.getDocType());
        Assert.assertSame(keywordLC, d1.getKeyword());
        Assert.assertSame(elementNameHtml, d1.getElementName());
        Assert.assertNull(d1.getType());
        Assert.assertNull(d1.getPublicId());
        Assert.assertNull(d1.getSystemId());
        Assert.assertNull(d1.getInternalSubset());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());


        final DocType d2 =
                new DocType(
                    keywordUC,
                    elementNameHtml,
                    typePublicUC,
                    publicIdXHTMLTransitional,
                    systemIdXHTMLTransitional,
                    internalSubsetWSXHTMLTransitional);

        Assert.assertEquals(doctypeXHTMLTransitionalWS, d2.getDocType());
        Assert.assertSame(keywordUC, d2.getKeyword());
        Assert.assertSame(elementNameHtml, d2.getElementName());
        Assert.assertSame(typePublicUC, d2.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d2.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d2.getSystemId());
        Assert.assertSame(internalSubsetWSXHTMLTransitional, d2.getInternalSubset());
        Assert.assertEquals(-1, d2.getLine());
        Assert.assertEquals(-1, d2.getCol());

        d2.setInternalSubset(null);

        Assert.assertEquals(doctypeXHTMLTransitional, d2.getDocType());
        Assert.assertSame(keywordUC, d2.getKeyword());
        Assert.assertSame(elementNameHtml, d2.getElementName());
        Assert.assertSame(typePublicUC, d2.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d2.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d2.getSystemId());
        Assert.assertNull(d2.getInternalSubset());
        Assert.assertEquals(-1, d2.getLine());
        Assert.assertEquals(-1, d2.getCol());



        final DocType d3 =
                new DocType(
                        publicIdXHTMLTransitional,
                        systemIdXHTMLTransitional);

        Assert.assertEquals(doctypeXHTMLTransitional, d3.getDocType());
        Assert.assertEquals(keywordUC, d3.getKeyword());
        Assert.assertEquals(elementNameHtml, d3.getElementName());
        Assert.assertEquals(typePublicUC, d3.getType());
        Assert.assertSame(publicIdXHTMLTransitional, d3.getPublicId());
        Assert.assertSame(systemIdXHTMLTransitional, d3.getSystemId());
        Assert.assertNull(d3.getInternalSubset());
        Assert.assertEquals(-1, d3.getLine());
        Assert.assertEquals(-1, d3.getCol());


        final DocType d4 =
                new DocType(null, null);

        Assert.assertEquals(doctypeHTML5UC, d4.getDocType());
        Assert.assertEquals(keywordUC, d4.getKeyword());
        Assert.assertEquals(elementNameHtml, d4.getElementName());
        Assert.assertNull(d4.getType());
        Assert.assertNull(d4.getPublicId());
        Assert.assertNull(d4.getSystemId());
        Assert.assertNull(d4.getInternalSubset());
        Assert.assertEquals(-1, d4.getLine());
        Assert.assertEquals(-1, d4.getCol());

    }



    
}
