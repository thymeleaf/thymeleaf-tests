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
import org.thymeleaf.text.ITextRepository;
import org.thymeleaf.text.TextRepositories;


public final class XmlDeclarationTest {


    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final String keyword = XMLDeclaration.DEFAULT_KEYWORD;

        final String xmlDeclar1utfno = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
        final String xmlDeclar1utf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        final String xmlDeclar1 = "<?xml version=\"1.0\"?>";

        final String xmlDeclar1isono = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>";
        final String xmlDeclar11isoyes = "<?xml version=\"1.1\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>";

        final String version1 = "1.0";
        final String version11 = "1.1";
        final String encodingUtf = "UTF-8";
        final String encodingIso = "ISO-8859-1";
        final String standaloneno = "no";
        final String standaloneyes = "yes";


        final XMLDeclaration d1 = new XMLDeclaration(textRepository);
        d1.reset(
                xmlDeclar1utfno,
                keyword,
                version1,
                encodingUtf,
                standaloneno,
                "template", 11, 4
        );

        Assert.assertSame(xmlDeclar1utfno, d1.getXmlDeclaration());
        Assert.assertSame(keyword, d1.getKeyword());
        Assert.assertSame(version1, d1.getVersion());
        Assert.assertSame(encodingUtf, d1.getEncoding());
        Assert.assertSame(standaloneno, d1.getStandalone());
        Assert.assertEquals("template", d1.getTemplateName());
        Assert.assertEquals(11, d1.getLine());
        Assert.assertEquals(4, d1.getCol());

        d1.reset(
                xmlDeclar1utfno,
                keyword,
                version1,
                encodingUtf,
                standaloneno,
                "template", 10, 3
        );

        Assert.assertSame(xmlDeclar1utfno, d1.getXmlDeclaration());
        Assert.assertSame(keyword, d1.getKeyword());
        Assert.assertSame(version1, d1.getVersion());
        Assert.assertSame(encodingUtf, d1.getEncoding());
        Assert.assertSame(standaloneno, d1.getStandalone());
        Assert.assertEquals("template", d1.getTemplateName());
        Assert.assertEquals(10, d1.getLine());
        Assert.assertEquals(3, d1.getCol());

        d1.setStandalone(null);

        Assert.assertEquals(xmlDeclar1utf, d1.getXmlDeclaration());
        Assert.assertSame(keyword, d1.getKeyword());
        Assert.assertSame(version1, d1.getVersion());
        Assert.assertSame(encodingUtf, d1.getEncoding());
        Assert.assertNull(d1.getStandalone());
        Assert.assertNull(d1.getTemplateName());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setEncoding(null);

        Assert.assertEquals(xmlDeclar1, d1.getXmlDeclaration());
        Assert.assertSame(keyword, d1.getKeyword());
        Assert.assertSame(version1, d1.getVersion());
        Assert.assertNull(d1.getEncoding());
        Assert.assertNull(d1.getStandalone());
        Assert.assertNull(d1.getTemplateName());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setVersion(version11);
        d1.setEncoding(encodingIso);
        d1.setStandalone(standaloneyes);

        Assert.assertEquals(xmlDeclar11isoyes, d1.getXmlDeclaration());
        Assert.assertSame(keyword, d1.getKeyword());
        Assert.assertSame(version11, d1.getVersion());
        Assert.assertSame(encodingIso, d1.getEncoding());
        Assert.assertSame(standaloneyes, d1.getStandalone());
        Assert.assertNull(d1.getTemplateName());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());



        final XMLDeclaration d2 =
                new XMLDeclaration(
                    textRepository,
                    version1,
                    encodingIso,
                    standaloneno);

        Assert.assertEquals(xmlDeclar1isono, d2.getXmlDeclaration());
        Assert.assertSame(version1, d2.getVersion());
        Assert.assertSame(encodingIso, d2.getEncoding());
        Assert.assertSame(standaloneno, d2.getStandalone());
        Assert.assertNull(d2.getTemplateName());
        Assert.assertEquals(-1, d2.getLine());
        Assert.assertEquals(-1, d2.getCol());


        final XMLDeclaration d3 =
                new XMLDeclaration(
                        textRepository,
                        version1,
                        null,
                        null);

        Assert.assertEquals(xmlDeclar1, d3.getXmlDeclaration());
        Assert.assertSame(version1, d3.getVersion());
        Assert.assertNull(d3.getEncoding());
        Assert.assertNull(d3.getStandalone());
        Assert.assertNull(d3.getTemplateName());
        Assert.assertEquals(-1, d3.getLine());
        Assert.assertEquals(-1, d3.getCol());


    }



    
}
