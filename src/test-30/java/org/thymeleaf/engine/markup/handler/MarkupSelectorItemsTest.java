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

        check(false, "//div", "(//div || //*[th:fragment='div' OR data-th-fragment='div'])");
        check(false, "//DIV", "(//div || //*[th:fragment='DIV' OR data-th-fragment='DIV'])");
        check(true, "//DIV", "(//DIV || //*[th:fragment='DIV' OR data-th-fragment='DIV'])");
        checkNoRef(false, "//div", "//div");
        check(false, ".main", "//*[class='main']");
        check(false, "#main", "//*[id='main']");
        check(false, "[class='main']", "//*[class='main']");
        check(false, "[id='main']", "//*[id='main']");
        check(false, "%ref", "(//* && //*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(false, "p.main", "(//p[class='main'] || //*[class='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "p#main", "(//p[id='main'] || //*[id='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "P#main", "(//p[id='main'] || //*[id='main' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(false, "p%ref", "(//p && //*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(false, "/p.main", "(/p[class='main'] || /*[class='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p#main", "(/p[id='main'] || /*[id='main' AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p%ref", "(/p && /*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(false, "/P%REF", "(/p && /*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(false, "/p%ref[a = 'x']", "(/p[a='x'] && /*[th:fragment='ref' OR data-th-fragment='ref'])");
        check(false, "/p.someclass[a = \"x\"]", "(/p[class='someclass' AND a='x'] || /*[(class='someclass' AND a='x') AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/P.SOMECLASS[A = \"x\"]", "(/p[class='SOMECLASS' AND a='x'] || /*[(class='SOMECLASS' AND a='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        check(false, "/P[CLASS = 'someClass' AND A = \"x\"]", "(/p[class='someClass' AND a='x'] || /*[(class='someClass' AND a='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        check(true, "/P[CLASS = 'someClass' and A = \"x\"]", "(/P[CLASS='someClass' AND A='x'] || /*[(CLASS='someClass' AND A='x') AND (th:fragment='P' OR data-th-fragment='P')])");
        checkNoRef(true, "/P[CLASS = 'someClass' and A = \"x\"]", "/P[CLASS='someClass' AND A='x']");
        check(false, ".MAIN", "//*[class='MAIN']");
        check(false, "#MAIN", "//*[id='MAIN']");
        check(false, "%REF", "(//* && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(false, "P.MAIN", "(//p[class='MAIN'] || //*[class='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(false, "P#MAIN", "(//p[id='MAIN'] || //*[id='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(false, "P%REF", "(//p && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        checkNoRef(false, "P%REF", "//p");
        check(true, ".MAIN", "//*[class='MAIN']");
        check(true, "#MAIN", "//*[id='MAIN']");
        check(true, "%REF", "(//* && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(true, "P.MAIN", "(//P[class='MAIN'] || //*[class='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(true, "P#MAIN", "(//P[id='MAIN'] || //*[id='MAIN' AND (th:fragment='P' OR data-th-fragment='P')])");
        check(true, "P%REF", "(//P && //*[th:fragment='REF' OR data-th-fragment='REF'])");
        check(false, "/p.someclass[a = \"x\" and b!='y']", "(/p[class='someclass' AND (a='x' AND b!='y')] || /*[(class='someclass' AND (a='x' AND b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p.someclass[a = \"x\" aNd b!='y' AND c  ^= 'z']", "(/p[class='someclass' AND (a='x' AND (b!='y' AND c^='z'))] || /*[(class='someclass' AND (a='x' AND (b!='y' AND c^='z'))) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p.someclass[a = \"x\" or b!='y']", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p.someclass[a = \"x\" or (b!='y')]", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p.someclass[a = \"x\" OR (b!='y')]", "(/p[class='someclass' AND (a='x' OR b!='y')] || /*[(class='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "/p[a = \"x\" OR (b!='y')]", "(/p[a='x' OR b!='y'] || /*[(a='x' OR b!='y') AND (th:fragment='p' OR data-th-fragment='p')])");
        checkNoRef(false, "/p[a OR (b!='y')]", "/p[a* OR b!='y']");
        check(false, "[a OR (b!='y')]", "//*[a* OR b!='y']");
        checkNoRef(false, "[a OR (b!='y')]", "//*[a* OR b!='y']");
        check(false, "/p#someclass[a = \"x\" or (b!='y')]", "(/p[id='someclass' AND (a='x' OR b!='y')] || /*[(id='someclass' AND (a='x' OR b!='y')) AND (th:fragment='p' OR data-th-fragment='p')])");
        check(false, "div[th:fragment='copy' or data-th-fragment='copy']","(//div[th:fragment='copy' OR data-th-fragment='copy'] || //*[(th:fragment='copy' OR data-th-fragment='copy') AND (th:fragment='div' OR data-th-fragment='div')])");
        check(false, "html//p","(//html || //*[th:fragment='html' OR data-th-fragment='html'])(//p || //*[th:fragment='p' OR data-th-fragment='p'])");
        checkNoRef(false, "html//p","//html//p");
        check(false, "html//p[2]","(//html || //*[th:fragment='html' OR data-th-fragment='html'])(//p[2] || //*[th:fragment='p' OR data-th-fragment='p'][2])");
        checkNoRef(false, "html//p[2]","//html//p[2]");
        checkNoRef(false, "html/comment()","//html/comment()");
        checkNoRef(false, "comment()","//comment()");
        checkNoRef(false, "p//comment()","//p//comment()");
        check(false, "p//comment()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//comment()");
        checkNoRef(false, "p//comment()[2]","//p//comment()[2]");
        checkNoRef(false, "p//comment()[even()]","//p//comment()[even()]");
        checkNoRef(false, "p//text()[2]","//p//text()[2]");
        checkNoRef(false, "p//text()[even()]","//p//text()[even()]");
        checkNoRef(false, "html/cdata()","//html/cdata()");
        checkNoRef(false, "cdata()","//cdata()");
        checkNoRef(false, "p//cdata()","//p//cdata()");
        check(false, "p//cdata()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//cdata()");
        checkNoRef(false, "p//cdata()[2]","//p//cdata()[2]");
        checkNoRef(false, "p//cdata()[even()]","//p//cdata()[even()]");
        checkNoRef(false, "html/doctype()","//html/doctype()");
        checkNoRef(false, "doctype()","//doctype()");
        checkNoRef(false, "p//doctype()","//p//doctype()");
        check(false, "p//doctype()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//doctype()");
        checkNoRef(false, "p//doctype()[2]","//p//doctype()[2]");
        checkNoRef(false, "p//doctype()[even()]","//p//doctype()[even()]");
        checkNoRef(false, "html/xmldecl()","//html/xmldecl()");
        checkNoRef(false, "xmldecl()","//xmldecl()");
        checkNoRef(false, "p//xmldecl()","//p//xmldecl()");
        check(false, "p//xmldecl()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//xmldecl()");
        checkNoRef(false, "p//xmldecl()[2]","//p//xmldecl()[2]");
        checkNoRef(false, "p//xmldecl()[even()]","//p//xmldecl()[even()]");
        checkNoRef(false, "html/procinstr()","//html/procinstr()");
        checkNoRef(false, "procinstr()","//procinstr()");
        checkNoRef(false, "p//procinstr()","//p//procinstr()");
        check(false, "p//procinstr()","(//p || //*[th:fragment='p' OR data-th-fragment='p'])//procinstr()");
        checkNoRef(false, "p//procinstr()[2]","//p//procinstr()[2]");
        checkNoRef(false, "p//procinstr()[even()]","//p//procinstr()[even()]");
    }
    




    private static void check(final boolean caseSensitive, final String blockSelector, final String expected) throws Exception{

        final List<IMarkupSelectorItem> items = MarkupSelectorItems.parseSelector(caseSensitive, blockSelector, referenceResolver);
        final String result = StringUtils.join(items, "");
        assertEquals(expected, result);

    }

    private static void checkNoRef(final boolean caseSensitive, final String blockSelector, final String expected) throws Exception{

        final List<IMarkupSelectorItem> items = MarkupSelectorItems.parseSelector(caseSensitive, blockSelector, null);
        final String result = StringUtils.join(items, "");
        assertEquals(expected, result);

    }





    static final class TestingFragmentReferenceResolver implements IMarkupSelectorReferenceResolver {

        public String resolveSelectorFromReference(final String reference) {
            return "/[th:fragment='" + reference + "' or data-th-fragment='" + reference + "']";
        }
    }



}
