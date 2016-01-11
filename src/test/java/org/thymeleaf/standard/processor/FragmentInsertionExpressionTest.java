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
package org.thymeleaf.standard.processor;

import org.junit.Assert;
import org.junit.Test;


public class FragmentInsertionExpressionTest {


    public FragmentInsertionExpressionTest() {
        super();
    }



    @Test
    public void testFragmentExpressionSelection() throws Exception {
        checkExpression("template", false);
        checkExpression("template::f", false);
        checkExpression("template::frag", false);
        checkExpression("template :: frag", false);
        checkExpression("  template :: frag   ", false);
        checkExpression("   :: frag   ", false);
        checkExpression("::frag   ", false);
        checkExpression("::frag", false);
        checkExpression("this::frag", false);
        checkExpression(" this   ::frag", false);
        checkExpression(" this   :: frag", false);
        checkExpression(" ${lala slatr} + 'ele'   :: 'index_' + 2 * 2", false);
        checkExpression(" ${lala slatr} + 'ele'   :: ('index_' + 2 * 2)", false);
        checkExpression(" ${lala slatr} + 'ele'   :: ('index_' + (2 * 2)) (somePar)", false);
        checkExpression(" ${lala slatr} + 'ele'   :: ('index_' + (2 * 2)) (a='something')", false);
        checkExpression(" ${lala slatr} + 'ele'   :: ('index_' + (2 * 2)) (a='something',b=4123)", false);
        checkExpression(" ${lala slatr} + 'ele'   :: ('index_' + (2 * 2)) (a=('something'),b=4123)", false);
        checkExpression(" ${lala slatr} + ('ele')   :: ('index_' + (2 * 2)) (a=('something'),b=4123)", false);
        checkExpression(" ${lala slatr} + ('ele')   :: ('index_' + (2 * 2)) (a=('something' + 23),b=4123)", false);
        checkExpression(" ${lala slatr}+'ele'   :: ('index_'+(2*2)) (a=('something'+23),b=4123)", false);
        checkExpression(" ${lala slatr}+'ele'   :: ('index_'+(2*2)) (${name}=('something'+23),b=4123)", false);
        checkExpression(" ${lala slatr}+'ele'   :: ('index_'+(2*2)) ((${name} + 0)=('something'+23),b=4123)", false);
        checkExpression("C:\\Program Files\\apps\\templates\\WEB-INF\\temp.html", false);
        checkExpression("C:\\Program Files\\apps\\templates\\WEB-INF\\temp.html :: 'fragment number one'", false);
        checkExpression("/home/user/apps/templates/WEB-INF/temp.html :: 'fragment number one'", false);
        checkExpression("home/user :: 'fragment number one'", false);
        checkExpression("${something}", false);
        checkExpression("${this} :: ${that}", false);
        checkExpression("~{whatever}", true);
        checkExpression("${cond} ? ~{this} : ~{that}", true);
        checkExpression("${something} :: /div", false);
        checkExpression("template :: f (~{some})", false);
        checkExpression("folder/template :: f (~{some})", false);
        checkExpression("folder/template :: f (~{some})", false);
        checkExpression("~folder/template :: f (~{some})", false);
        checkExpression("~/folder/template :: f (~{some})", false);
        checkExpression("${~{impossible}} :: f (~{some})", false);
        checkExpression("'~{impossible}' :: f (~{some})", false);
        checkExpression("folder/template (title=~{some})", false);
        checkExpression("(~{some})", true);
        checkExpression("(${cond}) ? (~{this}) : (~{that})", true);
        checkExpression("folder/template (title='one',body=~{that})", false);
        checkExpression("folder/template (title=(~{some}))", false);
        checkExpression("folder/template (title=('one'),body=(~{that}))", false);
        checkExpression("folder/template (title=('one'))", false);
        checkExpression("folder/template (body=~{(that)})", false);
        checkExpression("folder/template\n (body=~{(that)})", false);
        checkExpression("~{folder/template :: f (~{some})}", true);
        checkExpression("     ~{folder/template :: f (~{some})}   ", true);
    }




    private static void checkExpression(final String expression, final boolean result) {
        if (result) {
            Assert.assertTrue(AbstractStandardFragmentInsertionTagProcessor.isCompleteStandardExpressionForSure(expression));
        } else {
            Assert.assertFalse(AbstractStandardFragmentInsertionTagProcessor.isCompleteStandardExpressionForSure(expression));
        }
    }

}
