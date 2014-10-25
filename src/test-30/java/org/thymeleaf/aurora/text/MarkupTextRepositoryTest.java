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
package org.thymeleaf.aurora.text;

import org.junit.Assert;
import org.junit.Test;


public final class MarkupTextRepositoryTest {

    //
    // The two character squences 'UKHRHQIQWB' and 'CESIOUUOKO' are used in these tests because they
    // have the same hash code value, in order to provoke a collision
    //

    @Test
    public void testNormal() throws Exception {

        final IMarkupTextRepository repository = new StandardMarkupTextRepository(15, new String[0]);

        int res01 = testTextStr(repository, "UKHRHQIQWB");
        int res02 = testTextStr(repository, "CESIOUUOKO");

        int res03 = testTextStr(repository, "Buuh!");
        int res04 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res04);

        int res05 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res05);
        int res06 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res06);
        int res07 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res07);
        int res08 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res08);
        int res09 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res09);
        int res10 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res10);
        int res11 = testTextStr(repository, "B" + "u" + ((true)? "u" : "o") + "h!");
        Assert.assertEquals(res03, res11);
        int res12 = testTextChar(repository, "B" + "u" + ((true)? "u" : "o") + "h!");
        Assert.assertEquals(res03, res12);

        int res13 = testTextChar(repository, "ABuuh!aa".toCharArray(),1,5);
        Assert.assertEquals(res03, res13);
        int res14 = testTextChar(repository, "AunBuuh!aa".toCharArray(),3,5);
        Assert.assertEquals(res03, res14);

        int res15 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertEquals(res02, res15);

        int res16 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res01, res16);

        int res17 = testTextChar(repository, "Lalala".toCharArray(),1,4);
        int res18 = testTextStr(repository, "alal");
        Assert.assertEquals(res17, res18);

        int res19 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertEquals(res16, res19);
        int res20 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertNotEquals(res15, res20);
        int res21 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res19, res21);
        int res22 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertNotEquals(res20, res22);
        int res25 = testTextChar(repository, "aUKHRHQIQWB".toCharArray(),1,10);
        Assert.assertNotEquals(res21, res25);
        int res26 = testTextChar(repository, "aCESIOUUOKO".toCharArray(),1,10);
        Assert.assertNotEquals(res22, res26);
        int res27 = testTextStr(repository, "Buuh!");
        Assert.assertNotEquals(res03, res27);
        int res28 = testTextChar(repository, "Buuh!".toCharArray(), 0, 5);
        Assert.assertEquals(res27, res28);

    }




    @Test
    public void testWithUnremovable() throws Exception {

        final IMarkupTextRepository repository = new StandardMarkupTextRepository(15, new String[] { "CESIOUUOKO" });

        int res01 = testTextStr(repository, "UKHRHQIQWB");
        int res02 = testTextStr(repository, "CESIOUUOKO");

        int res03 = testTextStr(repository, "Buuh!");
        int res04 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res04);

        int res05 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res05);
        int res06 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res06);
        int res07 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res07);
        int res08 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res08);
        int res09 = testTextStr(repository, new String("Buuh!"));
        Assert.assertEquals(res03, res09);
        int res10 = testTextChar(repository, "Buuh!");
        Assert.assertEquals(res03, res10);
        int res11 = testTextStr(repository, "B" + "u" + ((true)? "u" : "o") + "h!");
        Assert.assertEquals(res03, res11);
        int res12 = testTextChar(repository, "B" + "u" + ((true)? "u" : "o") + "h!");
        Assert.assertEquals(res03, res12);

        int res13 = testTextChar(repository, "ABuuh!aa".toCharArray(),1,5);
        Assert.assertEquals(res03, res13);
        int res14 = testTextChar(repository, "AunBuuh!aa".toCharArray(),3,5);
        Assert.assertEquals(res03, res14);

        int res15 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertEquals(res02, res15);

        int res16 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res01, res16);

        int res17 = testTextChar(repository, "Lalala".toCharArray(),1,4);
        int res18 = testTextStr(repository, "alal");
        Assert.assertEquals(res17, res18);

        int res19 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res16, res19); // No room for it!
        int res20 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertEquals(res02, res20);
        int res21 = testTextStr(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res19, res21);
        int res22 = testTextStr(repository, "CESIOUUOKO");
        Assert.assertEquals(res02, res22);
        int res23 = testTextChar(repository, "UKHRHQIQWB");
        Assert.assertNotEquals(res21, res23);
        int res24 = testTextChar(repository, "CESIOUUOKO");
        Assert.assertEquals(res02, res24);
        int res25 = testTextChar(repository, "aUKHRHQIQWB".toCharArray(),1,10);
        Assert.assertNotEquals(res23, res25);
        int res26 = testTextChar(repository, "aCESIOUUOKO".toCharArray(),1,10);
        Assert.assertEquals(res02, res26);
        int res27 = testTextStr(repository, "Buuh!");
        Assert.assertNotEquals(res03, res27);
        int res28 = testTextChar(repository, "Buuh!".toCharArray(), 0, 5);
        Assert.assertEquals(res27, res28);

    }



    private static int testTextChar(final IMarkupTextRepository repository, final char[] text, final int offset, final int len) {
        final String result = repository.getText(text,offset,len);
        return System.identityHashCode(result);
    }

    private static int testTextChar(final IMarkupTextRepository repository, final String text) {
        final String result = repository.getText(text.toCharArray(),0,text.length());
        return System.identityHashCode(result);
    }

    private static int testTextStr(final IMarkupTextRepository repository, final String text) {
        final String input = new String(text);
        final String result = repository.getText(input);
        return System.identityHashCode(result);
    }




}
