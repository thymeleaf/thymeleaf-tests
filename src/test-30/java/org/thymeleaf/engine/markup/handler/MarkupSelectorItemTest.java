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
public class MarkupSelectorItemTest extends TestCase {

    public MarkupSelectorItemTest() {
        super();
    }
    
    
    public void test() throws Exception {

        check(false, "//div", "//div");
        check(false, ".main", "//*[class='main']");
        check(false, "#main", "//*[id='main']");
        check(false, "%ref", "//*[$ref$='ref']");
        check(false, "p.main", "//p[class='main']");
        check(false, "p#main", "//p[id='main']");
        check(false, "p%ref", "//p[$ref$='ref']");
        check(false, "/p.main", "/p[class='main']");
        check(false, "/p#main", "/p[id='main']");
        check(false, "/p%ref", "/p[$ref$='ref']");
        check(false, "/P%REF", "/p[$ref$='REF']");
        check(false, "/p%ref[a = 'x']", "/p[$ref$='ref'][a='x']");
        check(false, "/p.someclass[a = \"x\"]", "/p[class='someclass' and a='x']");
        check(false, "/P.SOMECLASS[A = \"x\"]", "/p[class='SOMECLASS' and a='x']");
        check(false, "/P[CLASS = 'someClass' and A = \"x\"]", "/p[class='someClass' and a='x']");
        check(true, "/P[CLASS = 'someClass' and A = \"x\"]", "/P[CLASS='someClass' and A='x']");
        check(false, ".MAIN", "//*[class='MAIN']");
        check(false, "#MAIN", "//*[id='MAIN']");
        check(false, "%REF", "//*[$ref$='REF']");
        check(false, "P.MAIN", "//p[class='MAIN']");
        check(false, "P#MAIN", "//p[id='MAIN']");
        check(false, "P%REF", "//p[$ref$='REF']");
        check(true, ".MAIN", "//*[class='MAIN']");
        check(true, "#MAIN", "//*[id='MAIN']");
        check(true, "%REF", "//*[$ref$='REF']");
        check(true, "P.MAIN", "//P[class='MAIN']");
        check(true, "P#MAIN", "//P[id='MAIN']");
        check(true, "P%REF", "//P[$ref$='REF']");
        check(false, "/p.someclass[a = \"x\" and b!='y']", "/p[class='someclass' and a='x' and b!='y']");
        check(false, "/p.someclass[a = \"x\" and b!='y' and c  ^= 'z']", "/p[class='someclass' and a='x' and b!='y' and c^='z']");

    }
    




    private static void check(final boolean caseSensitive, final String blockSelector, final String expected) throws Exception{

        final List<MarkupSelectorItem> items = MarkupSelectorItems.parseSelector(caseSensitive, blockSelector);
        final String result = StringUtils.join(items, "");
        assertEquals(expected, result);

    }
    
    
    

}
