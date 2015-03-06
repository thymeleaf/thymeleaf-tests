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


public final class CommentTest {



    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final char[] buf1 = "<!--hello-->".toCharArray();

        final Comment c1 = new Comment(textRepository);
        c1.setComment(buf1, 0, 12, 10, 3);
        Assert.assertEquals("<!--hello-->", extractText(c1));
        final String c1all = c1.getComment();
        final String c1content = c1.getContent();
        Assert.assertEquals("<!--hello-->", c1all);
        Assert.assertEquals("hello", c1content);
        Assert.assertSame(c1all, c1.getComment());
        Assert.assertSame(c1content, c1.getContent());
        Assert.assertEquals(10, c1.getLine());
        Assert.assertEquals(3, c1.getCol());
        Assert.assertSame(textRepository.getText("<!--hello-->"), c1.getComment());

        final String c1c0 = " something\nhere ";
        c1.setContent(c1c0);
        Assert.assertSame(c1c0, c1.getContent());
        Assert.assertEquals("<!-- something\nhere -->", c1.getComment());
        Assert.assertSame(textRepository.getText("<!-- something\nhere -->"), c1.getComment());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String c1cs1 = "<!-- something\nhere -->";
        final char[] c1cs1Buf = c1cs1.toCharArray();
        final String c1c1 = " something\nhere ";
        c1.setComment(c1cs1Buf, 0, 23, 11, 4);
        Assert.assertEquals(c1cs1, c1.getComment());
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

        final String c1cs2 = "<!--hey!-->";
        Assert.assertEquals(c1cs2, c1.getComment());

        final String c1c3 = "huy!";
        final String c1cs3 = "<!--huy!-->";
        final char[] c1cs3Buf = c1cs3.toCharArray();
        c1.setComment(c1cs3Buf, 0, 11, 11, 4);
        Assert.assertEquals(c1c3, c1.getContent());
        Assert.assertEquals(11, c1.getLine());
        Assert.assertEquals(4, c1.getCol());

        c1.setContent(c1c2);
        Assert.assertSame(c1c2, c1.getContent());
        Assert.assertEquals("<!--hey!-->", c1.getComment());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());


        c1.setComment(c1cs3.toCharArray(), 0, c1cs3.length(), 12, 5);
        final String c1c3_2 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_2);
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals(c1cs3, c1.getComment());
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals(12, c1.getLine());
        Assert.assertEquals(5, c1.getCol());


        c1.setContent(c1c3);
        final String c1c3_3 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_3);
        Assert.assertSame(c1c3_3, c1.getContent());
        Assert.assertEquals(c1cs3, c1.getComment());
        Assert.assertSame(c1c3_3, c1.getContent());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String empty = "<!---->"; // Set keyword to upper case
        final char[] emptyBuf = empty.toCharArray();
        c1.setComment(emptyBuf, 0, 7, 9, 3);
        final String c1cs3_2 = "<!--huy!-->";
        c1.setContent(new String(c1cs3.toCharArray(), 4, 4));
        final String c1c3_4 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_4);
        Assert.assertSame(c1c3_4, c1.getContent());
        final String c1cs3_3 = c1.getComment();
        Assert.assertEquals(c1cs3_2, c1cs3_3);
        Assert.assertSame(c1cs3_3, c1.getComment());
        Assert.assertSame(c1c3_4, c1.getContent());

        final String c2cs1 = "<!--hello-->";
        final String c2c1 = "hello";
        final Comment c2 = new Comment(textRepository, c2c1);
        final String c2cs1_2 = c2.getComment();
        final String c2c1_2 = c2.getContent();
        Assert.assertEquals(c2cs1, c2cs1_2);
        Assert.assertEquals(c2c1, c2c1_2);
        Assert.assertSame(c2cs1_2, c2.getComment());
        Assert.assertSame(c2c1_2, c2.getContent());

    }




    private static String extractText(final Comment comment) {

        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < comment.length(); i++) {
            strBuilder.append(comment.charAt(i));
        }
        return strBuilder.toString();

    }



    
}
