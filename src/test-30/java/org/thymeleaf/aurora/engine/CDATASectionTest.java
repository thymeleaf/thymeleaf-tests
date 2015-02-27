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
import org.thymeleaf.aurora.text.ITextRepository;
import org.thymeleaf.aurora.text.TextRepositories;


public final class CDATASectionTest {



    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final char[] buf1 = "<![CDATA[hello]]>".toCharArray();

        final CDATASection c1 = new CDATASection(textRepository);
        c1.setCDATASection(buf1, 0, 17, 10, 3);
        Assert.assertEquals("<![CDATA[hello]]>", extractText(c1));
        final String c1all = c1.getCDATASection();
        final String c1content = c1.getContent();
        Assert.assertEquals("<![CDATA[hello]]>", c1all);
        Assert.assertEquals("hello", c1content);
        Assert.assertSame(c1all, c1.getCDATASection());
        Assert.assertSame(c1content, c1.getContent());
        Assert.assertEquals(10, c1.getLine());
        Assert.assertEquals(3, c1.getCol());
        Assert.assertSame(textRepository.getText("<![CDATA[hello]]>"), c1.getCDATASection());

        final String c1c0 = " something\nhere ";
        c1.setContent(c1c0);
        Assert.assertSame(c1c0, c1.getContent());
        Assert.assertEquals("<![CDATA[ something\nhere ]]>", c1.getCDATASection());
        Assert.assertNotSame(textRepository.getText("<![CDATA[ something\nhere ]]>"), c1.getCDATASection());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String c1cs1 = "<![CDATA[ something\nhere ]]>";
        final char[] c1cs1Buf = c1cs1.toCharArray();
        final String c1c1 = " something\nhere ";
        c1.setCDATASection(c1cs1Buf, 0, 28, 11, 4);
        Assert.assertEquals(c1cs1, c1.getCDATASection());
        final String c1c1_2 = c1.getContent();
        Assert.assertEquals(c1c1, c1c1_2);
        Assert.assertSame(c1c1_2, c1.getContent());
        Assert.assertEquals(11, c1.getLine());
        Assert.assertEquals(4, c1.getCol());

        final String c1c2 = "hey!";
        c1.setContent(c1c2);
        final String c1c2_2 = c1.getContent();
        Assert.assertSame(c1c2, c1c2_2);
        Assert.assertSame(c1c2, c1.getContent());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String c1cs2 = "<![CDATA[hey!]]>";
        Assert.assertEquals(c1cs2, c1.getCDATASection());

        final String c1c3 = "huy!";
        final String c1cs3 = "<![cdata[huy!]]>";
        final char[] c1cs3Buf = c1cs3.toCharArray();
        c1.setCDATASection(c1cs3Buf, 0, 16, 11, 4);
        Assert.assertEquals(c1c3, c1.getContent());
        Assert.assertEquals(11, c1.getLine());
        Assert.assertEquals(4, c1.getCol());

        c1.setContent(c1c2);
        Assert.assertSame(c1c2, c1.getContent());
        Assert.assertEquals("<![CDATA[hey!]]>", c1.getCDATASection());


        c1.setCDATASection(c1cs3.toCharArray(), 0, c1cs3.length(), 12, 5);
        final String c1c3_2 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_2);
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals(c1cs3, c1.getCDATASection());
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals(12, c1.getLine());
        Assert.assertEquals(5, c1.getCol());


        c1.setContent(c1c3);
        final String c1c3_3 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_3);
        Assert.assertSame(c1c3_3, c1.getContent());
        Assert.assertEquals("<![CDATA[huy!]]>", c1.getCDATASection());
        Assert.assertSame(c1c3_3, c1.getContent());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String empty = "<![CDATA[]]>"; // Set keyword to upper case
        final char[] emptyBuf = empty.toCharArray();
        c1.setCDATASection(emptyBuf, 0, 12, 9, 3);
        final String c1cs3_2 = "<![CDATA[huy!]]>";
        c1.setContent(new String(c1cs3.toCharArray(), 9, 4));
        final String c1c3_4 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_4);
        Assert.assertSame(c1c3_4, c1.getContent());
        Assert.assertNotEquals(c1cs3, c1.getCDATASection()); // case changed!
        final String c1cs3_3 = c1.getCDATASection();
        Assert.assertEquals(c1cs3_2, c1cs3_3);
        Assert.assertSame(c1cs3_3, c1.getCDATASection());
        Assert.assertSame(c1c3_4, c1.getContent());

        final String c2cs1 = "<![CDATA[hello]]>";
        final String c2c1 = "hello";
        final CDATASection c2 = new CDATASection(c2c1);
        final String c2cs1_2 = c2.getCDATASection();
        final String c2c1_2 = c2.getContent();
        Assert.assertEquals(c2cs1, c2cs1_2);
        Assert.assertEquals(c2c1, c2c1_2);
        Assert.assertSame(c2cs1_2, c2.getCDATASection());
        Assert.assertSame(c2c1_2, c2.getContent());

    }




    private static String extractText(final CDATASection cdataSection) {

        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < cdataSection.length(); i++) {
            strBuilder.append(cdataSection.charAt(i));
        }
        return strBuilder.toString();

    }

    
}
