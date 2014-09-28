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
package org.thymeleaf.engine.markup.handler;

import java.util.List;

import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.1
 *
 */
public class MarkupSelectorItemsTest extends TestCase {

    private static TestingFragmentReferenceResolver referenceResolver = new TestingFragmentReferenceResolver();

    public MarkupSelectorItemsTest() {
        super();
    }

    
    public void test() throws Exception {

        check(MarkupSelectorMode.HTML, "//div", "(//div || //*[th:fragment='div' OR data-th-fragment='div'])");
        check(MarkupSelectorMode.HTML, "//DIV", "(//div || //*[th:fragment='DIV' OR data-th-fragment='DIV'])");
        check(MarkupSelectorMode.XML, "//DIV", "(//DIV || //*[th:fragment='DIV' OR data-th-fragment='DIV'])");
        checkNoRef(MarkupSelectorMode.HTML, "//div", "//div");
        check(MarkupSelectorMode.HTML, ".main", "//*[class='main']");
        check(MarkupSelectorMode.HTML, "#main", "//*[id='main']");
        check(MarkupSelectorMode.HTML, "[class='main']", "//*[class='main']");
        check(MarkupSelectorMode.HTML, "[id='main']", "//*[id='main']");
        check(MarkupSelectorMode.HTML, "%ref", "(//* && //*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(MarkupSelectorMode.HTML, "p.main", "(//p[class='main'] || //*[class='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "p#main", "(//p[id='main'] || //*[id='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "P#main", "(//p[id='main'] || //*[id='main' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(MarkupSelectorMode.HTML, "p%ref", "(//p && //*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(MarkupSelectorMode.HTML, "/p.main", "(/p[class='main'] || /*[class='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p#main", "(/p[id='main'] || /*[id='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p%ref", "(/p && /*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(MarkupSelectorMode.HTML, "/P%REF", "(/p && /*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(MarkupSelectorMode.HTML, "/p%ref[a = 'x']", "(/p[a='x'] && /*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\"]", "(/p[class='someclass' AND a='x'] || /*[(class='someclass' AND a='x') AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/P.SOMECLASS[A = \"x\"]", "(/p[class='SOMECLASS' AND a='x'] || /*[(class='SOMECLASS' AND a='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        check(MarkupSelectorMode.HTML, "/P[CLASS = 'someClass' AND A = \"x\"]", "(/p[class='someClass' AND a='x'] || /*[(class='someClass' AND a='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        check(MarkupSelectorMode.XML, "/P[CLASS = 'someClass' and A = \"x\"]", "(/P[CLASS='someClass' AND A='x'] || /*[(CLASS='someClass' AND A='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        checkNoRef(MarkupSelectorMode.XML, "/P[CLASS = 'someClass' and A = \"x\"]", "/P[CLASS='someClass' AND A='x']");
        check(MarkupSelectorMode.HTML, ".MAIN", "//*[class='MAIN']");
        check(MarkupSelectorMode.HTML, "#MAIN", "//*[id='MAIN']");
        check(MarkupSelectorMode.HTML, "%REF", "(//* && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(MarkupSelectorMode.HTML, "P.MAIN", "(//p[class='MAIN'] || //*[class='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(MarkupSelectorMode.HTML, "P#MAIN", "(//p[id='MAIN'] || //*[id='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(MarkupSelectorMode.HTML, "P%REF", "(//p && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        checkNoRef(MarkupSelectorMode.HTML, "P%REF", "//p");
        check(MarkupSelectorMode.XML, ".MAIN", "(//.MAIN || //*[th:fragment='.MAIN' OR data-th-fragment='.MAIN'])");
        check(MarkupSelectorMode.XML, "#MAIN", "(//#MAIN || //*[th:fragment='#MAIN' OR data-th-fragment='#MAIN'])");
        check(MarkupSelectorMode.XML, "%REF", "(//* && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(MarkupSelectorMode.XML, "P.MAIN", "(//P.MAIN || //*[th:fragment='P.MAIN' OR data-th-fragment='P.MAIN'])");
        check(MarkupSelectorMode.XML, "P#MAIN", "(//P#MAIN || //*[th:fragment='P#MAIN' OR data-th-fragment='P#MAIN'])");
        check(MarkupSelectorMode.XML, "P%REF", "(//P && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\" and b!='y']", "(/p[class='someclass' AND (a='x' AND b!='y')] || /*[(class='someclass' AND (a='x' AND b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\" aNd b!='y' AND c  ^= 'z']", "(/p[class='someclass' AND (a='x' AND (b!='y' AND c^='z'))] || /*[(class='someclass' AND (a='x' AND (b!='y' AND c^='z'))) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\" or b!='y']", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\" or (b!='y')]", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p.someclass[a = \"x\" OR (b!='y')]", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "/p[a = \"x\" OR (b!='y')]", "(/p[a='x' OR b!='y'] || /*[(a='x' OR b!='y') AND (th:fragment='p' OR data-th-fragment='p')])");
        checkNoRef(MarkupSelectorMode.HTML, "/p[a OR (b!='y')]", "/p[a* OR b!='y']");
        check(MarkupSelectorMode.HTML, "[a OR (b!='y')]", "//*[a* OR b!='y']");
        checkNoRef(MarkupSelectorMode.HTML, "[a OR (b!='y')]", "//*[a* OR b!='y']");
        check(MarkupSelectorMode.HTML, "/p#someclass[a = \"x\" or (b!='y')]", "(/p[id='someclass' AND (a='x' OR b!='y')] || /*[(id='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(MarkupSelectorMode.HTML, "div[th:fragment='copy' or data-th-fragment='copy']","(//div[th:fragment='copy' OR data-th-fragment='copy'] || //*[(th:fragment='copy' OR data-th-fragment='copy') AND (th:fragment='div' OR data-th-fragment='div')])");
        check(MarkupSelectorMode.HTML, "html//p","(//html || //*[th:fragment='html' OR data-th-fragment='html'])(//p || //*[th:fragment='p' OR data-th-fragment='p'])");
        checkNoRef(MarkupSelectorMode.HTML, "html//p","//html//p");
        check(MarkupSelectorMode.HTML, "html//p[2]","(//html || //*[th:fragment='html' OR data-th-fragment='html'])(//p[2] || //*[th:fragment='p' OR data-th-fragment='p'][2])");
        checkNoRef(MarkupSelectorMode.HTML, "html//p[2]","//html//p[2]");
        checkNoRef(MarkupSelectorMode.HTML, "html/comment()","//html/comment()");
        checkNoRef(MarkupSelectorMode.HTML, "comment()","//comment()");
        checkNoRef(MarkupSelectorMode.HTML, "p//comment()","//p//comment()");
        check(MarkupSelectorMode.HTML, "p//comment()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//comment()");
        checkNoRef(MarkupSelectorMode.HTML, "p//comment()[2]","//p//comment()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//comment()[even()]","//p//comment()[even()]");
        checkNoRef(MarkupSelectorMode.HTML, "p//text()[2]","//p//text()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//text()[even()]","//p//text()[even()]");
        checkNoRef(MarkupSelectorMode.HTML, "html/cdata()","//html/cdata()");
        checkNoRef(MarkupSelectorMode.HTML, "cdata()","//cdata()");
        checkNoRef(MarkupSelectorMode.HTML, "p//cdata()","//p//cdata()");
        check(MarkupSelectorMode.HTML, "p//cdata()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//cdata()");
        checkNoRef(MarkupSelectorMode.HTML, "p//cdata()[2]","//p//cdata()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//cdata()[even()]","//p//cdata()[even()]");
        checkNoRef(MarkupSelectorMode.HTML, "html/doctype()","//html/doctype()");
        checkNoRef(MarkupSelectorMode.HTML, "doctype()","//doctype()");
        checkNoRef(MarkupSelectorMode.HTML, "p//doctype()","//p//doctype()");
        check(MarkupSelectorMode.HTML, "p//doctype()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//doctype()");
        checkNoRef(MarkupSelectorMode.HTML, "p//doctype()[2]","//p//doctype()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//doctype()[even()]","//p//doctype()[even()]");
        checkNoRef(MarkupSelectorMode.HTML, "html/xmldecl()","//html/xmldecl()");
        checkNoRef(MarkupSelectorMode.HTML, "xmldecl()","//xmldecl()");
        checkNoRef(MarkupSelectorMode.HTML, "p//xmldecl()","//p//xmldecl()");
        check(MarkupSelectorMode.HTML, "p//xmldecl()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//xmldecl()");
        checkNoRef(MarkupSelectorMode.HTML, "p//xmldecl()[2]","//p//xmldecl()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//xmldecl()[even()]","//p//xmldecl()[even()]");
        checkNoRef(MarkupSelectorMode.HTML, "html/procinstr()","//html/procinstr()");
        checkNoRef(MarkupSelectorMode.HTML, "procinstr()","//procinstr()");
        checkNoRef(MarkupSelectorMode.HTML, "p//procinstr()","//p//procinstr()");
        check(MarkupSelectorMode.HTML, "p//procinstr()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//procinstr()");
        checkNoRef(MarkupSelectorMode.HTML, "p//procinstr()[2]","//p//procinstr()[2]");
        checkNoRef(MarkupSelectorMode.HTML, "p//procinstr()[even()]","//p//procinstr()[even()]");
    }
    




    private static void check(final MarkupSelectorMode mode, final String blockSelector, final String expected) throws Exception{

        final List<IMarkupSelectorItem> items = MarkupSelectorItems.parseSelector(mode, blockSelector, referenceResolver);
        final String result = StringUtils.join(items, "");
        assertEquals(expected, result);

    }

    private static void checkNoRef(final MarkupSelectorMode mode, final String blockSelector, final String expected) throws Exception{

        final List<IMarkupSelectorItem> items = MarkupSelectorItems.parseSelector(mode, blockSelector, null);
        final String result = StringUtils.join(items, "");
        assertEquals(expected, result);

    }





    static final class TestingFragmentReferenceResolver implements IMarkupSelectorReferenceResolver {

        public String resolveSelectorFromReference(final String reference) {
            return "/[th:fragment='" + reference + "' or data-th-fragment='" + reference + "']";
        }
    }



}
