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
package org.thymeleaf.dom2;

import org.junit.Test;


public final class MarkupTextRepositoryTest {



    @Test
    public void test() throws Exception {

        final IMarkupTextRepository repository = new MarkupTextRepository(8, new String[] { "Siblings" });

        testTextStr(repository, "Teheran");
        testTextStr(repository, "Siblings");

        testTextStr(repository, "Buuh!");
        testTextChar(repository, "Buuh!");
        testTextStr(repository, new String("Buuh!"));
        testTextChar(repository, "Buuh!");
        testTextStr(repository, new String("Buuh!"));
        testTextChar(repository, "Buuh!");
        testTextStr(repository, new String("Buuh!"));
        testTextChar(repository, "Buuh!");
        testTextStr(repository, "B" + "u" + ((true)? "u" : "o") + "h!");
        testTextChar(repository, "B" + "u" + ((true)? "u" : "o") + "h!");

        testTextChar(repository, "ABuuh!aa".toCharArray(),1,5);
        testTextChar(repository, "AunBuuh!aa".toCharArray(),3,5);

        testTextStr(repository, "Siblings");
        testTextStr(repository, "Teheran");
        testTextChar(repository, "Lalala".toCharArray(),1,4);
        testTextStr(repository, "alal");
        testTextStr(repository, "Teheran");
        testTextStr(repository, "Siblings");
        testTextStr(repository, "Teheran");
        testTextStr(repository, "Siblings");
        testTextChar(repository, "Teheran");
        testTextChar(repository, "Siblings");
        testTextChar(repository, "aTeheran".toCharArray(),1,7);
        testTextChar(repository, "aSiblings".toCharArray(),1,8);
        testTextStr(repository, "Buuh!");

    }

    private static void testTextChar(final IMarkupTextRepository repository, final char[] text, final int offset, final int len) {
        final String result = repository.getText(text,offset,len);
        System.out.println(result + "[" + System.identityHashCode(result) + "]");
    }

    private static void testTextChar(final IMarkupTextRepository repository, final String text) {
        final String result = repository.getText(text.toCharArray(),0,text.length());
        System.out.println(result + "[" + System.identityHashCode(result) + "]");
    }

    private static void testTextStr(final IMarkupTextRepository repository, final String text) {
        final String input = new String(text);
        final String result = repository.getText(input);
        System.out.println(result + "[" + System.identityHashCode(input) + "->" + System.identityHashCode(result) + "]");
    }




}
