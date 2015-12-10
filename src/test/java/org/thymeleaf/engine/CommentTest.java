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

import java.io.IOException;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.model.IComment;
import org.thymeleaf.model.IModelVisitor;
import org.thymeleaf.text.ITextRepository;
import org.thymeleaf.text.TextRepositories;


public final class CommentTest {



    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final char[] buf1 = "<!--hello-->".toCharArray();

        final Comment c1 = new Comment(textRepository);
        c1.reset(buf1, 0, 12, "template", 10, 3);
        Assert.assertEquals("<!--hello-->", extractText(c1));
        final String c1all = c1.getComment();
        final String c1content = c1.getContent();
        Assert.assertEquals("<!--hello-->", c1all);
        Assert.assertEquals("hello", c1content);
        Assert.assertSame(c1all, c1.getComment());
        Assert.assertSame(c1content, c1.getContent());
        Assert.assertEquals("template", c1.getTemplateName());
        Assert.assertEquals(10, c1.getLine());
        Assert.assertEquals(3, c1.getCol());
        Assert.assertSame(textRepository.getText("<!--hello-->"), c1.getComment());

        final String c1c0 = " something\nhere ";
        c1.setContent(c1c0);
        Assert.assertSame(c1c0, c1.getContent());
        Assert.assertEquals("<!-- something\nhere -->", c1.getComment());
        Assert.assertSame(textRepository.getText("<!-- something\nhere -->"), c1.getComment());
        Assert.assertNull(c1.getTemplateName());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String c1cs1 = "<!-- something\nhere -->";
        final char[] c1cs1Buf = c1cs1.toCharArray();
        final String c1c1 = " something\nhere ";
        c1.reset(c1cs1Buf, 0, 23, "template", 11, 4);
        Assert.assertEquals(c1cs1, c1.getComment());
        final String c1c1_2 = c1.getContent();
        Assert.assertEquals(c1c1, c1c1_2);
        Assert.assertSame(c1c1_2, c1.getContent());
        Assert.assertEquals("template", c1.getTemplateName());
        Assert.assertEquals(11, c1.getLine());
        Assert.assertEquals(4, c1.getCol());

        final String c1c2 = "hey!";
        c1.setContent(c1c2);
        final String c1c2_2 = c1.getContent();
        Assert.assertSame(c1c2, c1c2_2);
        Assert.assertSame(c1c2, c1.getContent());
        Assert.assertNull(c1.getTemplateName());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());

        final String c1cs2 = "<!--hey!-->";
        Assert.assertEquals(c1cs2, c1.getComment());

        final String c1c3 = "huy!";
        final String c1cs3 = "<!--huy!-->";
        final char[] c1cs3Buf = c1cs3.toCharArray();
        c1.reset(c1cs3Buf, 0, 11, "template", 11, 4);
        Assert.assertEquals(c1c3, c1.getContent());
        Assert.assertEquals("template", c1.getTemplateName());
        Assert.assertEquals(11, c1.getLine());
        Assert.assertEquals(4, c1.getCol());

        c1.setContent(c1c2);
        Assert.assertSame(c1c2, c1.getContent());
        Assert.assertEquals("<!--hey!-->", c1.getComment());
        Assert.assertNull(c1.getTemplateName());
        Assert.assertEquals(-1, c1.getLine());
        Assert.assertEquals(-1, c1.getCol());


        c1.reset(c1cs3.toCharArray(), 0, c1cs3.length(), "template", 12, 5);
        final String c1c3_2 = c1.getContent();
        Assert.assertEquals(c1c3, c1c3_2);
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals(c1cs3, c1.getComment());
        Assert.assertSame(c1c3_2, c1.getContent());
        Assert.assertEquals("template", c1.getTemplateName());
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
        c1.reset(emptyBuf, 0, 7, "template", 9, 3);
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





    @Test
    public void testSubsection() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        Comment c1 = new Comment(textRepository);

        c1.setContent("something");

        Assert.assertEquals("!--s", c1.subSequence(1, 5));
        Assert.assertEquals("some", c1.subSequence(4, 8));


        c1 = new Comment(textRepository);

        c1.reset("<!--something-->".toCharArray(), 0, 16, "test", 1, 1);

        Assert.assertEquals("!--s", c1.subSequence(1, 5));
        Assert.assertEquals("some", c1.subSequence(4, 8));

    }





    @Test
    public void testContentFlags() {
        testFlags("", false, false);
        testFlags(" ", true, false);
        testFlags("   ", true, false);
        testFlags("\n", true, false);
        testFlags("\n  \t", true, false);
        testFlags("\n  [asd]", false, false);
        testFlags("\n  asdasdasd 23123 [ [asd ]]", false, false);
        testFlags("\n  asdasdasd 23123 [[asd ]]", false, true);
        testFlags("\n  asdasdasd 23123 [[asd ]]    [[asd]]", false, true);
        testFlags("\n  asdasdasd 23123  [ [asd ]]    [[asd] ]", false, false);
        testFlags("[[asd]]", false, true);
        testFlags("[[asd]", false, false);
        testFlags("[asd]]", false, false);
        testFlags("]]", false, false);
        testFlags("[[", false, false);
        testFlags("[[asd]]asd", false, true);
        testFlags("asd[[asd]]", false, true);
        testFlags("asd[[asd]]asd", false, true);
    }




    private static String extractText(final Comment comment) {

        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < comment.length(); i++) {
            strBuilder.append(comment.charAt(i));
        }
        return strBuilder.toString();

    }



    private static void testFlags(final String text, final boolean whitespace, final boolean inlineable) {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        Comment t1 = new Comment(textRepository);
        t1.reset(("<!--" + text + "-->").toCharArray(), 0, 4 + text.length() + 3, "test", 1, 1);
        t1.computeContentFlags();

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.reset(("----[[...]]" + ("<!--" + text + "-->") + "[[...]]----").toCharArray(), 11, 4 + text.length() + 3, "test", 1, 1);
        t1.computeContentFlags();

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.reset(("----" + ("<!--" + text + "-->") + "----").toCharArray(), 4, 4 + text.length() + 3, "test", 1, 1);
        t1.computeContentFlags();

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }

        t1.setContent(text);
        t1.computeContentFlags();

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.reset(("<!--" + text + "-->").toCharArray(), 0, 4 + text.length() + 3, "test", 1, 1);
        t1.computeContentFlags();

        Comment t2 = t1.cloneEvent();

        if (whitespace) {
            Assert.assertTrue(t2.isWhitespace());
        } else {
            Assert.assertFalse(t2.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t2.isInlineable());
        } else {
            Assert.assertFalse(t2.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.setContent(text);
        t1.computeContentFlags();

        t2 = t1.cloneEvent();

        if (whitespace) {
            Assert.assertTrue(t2.isWhitespace());
        } else {
            Assert.assertFalse(t2.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t2.isInlineable());
        } else {
            Assert.assertFalse(t2.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.reset(("<!--" + text + "-->").toCharArray(), 0, 4 + text.length() + 3, "test", 1, 1);

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }

        t1 = new Comment(textRepository);
        t1.setContent(text);

        if (whitespace) {
            Assert.assertTrue(t1.isWhitespace());
        } else {
            Assert.assertFalse(t1.isWhitespace());
        }
        if (inlineable) {
            Assert.assertTrue(t1.isInlineable());
        } else {
            Assert.assertFalse(t1.isInlineable());
        }


        t1 = new Comment(textRepository);
        t1.setContent(text);
        // By using the wrappers we avoid the utils methods calling the engine implementations (which are already tested above)
        boolean bWhitespace1 = EngineEventUtils.isWhitespace(new CommentWrapper(t1));
        boolean bInlineable1 = EngineEventUtils.isInlineable(new CommentWrapper(t1));
        if (whitespace) {
            Assert.assertTrue(bWhitespace1);
        } else {
            Assert.assertFalse(bWhitespace1);
        }
        if (inlineable) {
            Assert.assertTrue(bInlineable1);
        } else {
            Assert.assertFalse(bInlineable1);
        }

    }



    private static final class CommentWrapper implements IComment {

        private final Comment delegate;

        CommentWrapper(final Comment delegate) {
            super();
            this.delegate = delegate;
        }

        public void computeContentFlags() {
            delegate.computeContentFlags();
        }

        public static Comment asEngineComment(final IEngineConfiguration configuration, final IComment comment, final boolean cloneAlways) {
            return Comment.asEngineComment(configuration, comment, cloneAlways);
        }

        public void resetTemplateEvent(final String templateName, final int line, final int col) {
            delegate.resetTemplateEvent(templateName, line, col);
        }

        public boolean isWhitespace() {
            return delegate.isWhitespace();
        }

        public void resetAsCloneOf(final Comment original) {
            delegate.resetAsCloneOf(original);
        }

        public void resetAsCloneOfTemplateEvent(final AbstractTemplateEvent original) {
            delegate.resetAsCloneOfTemplateEvent(original);
        }

        public boolean isInlineable() {
            return delegate.isInlineable();
        }

        public void reset(final char[] buffer, final int outerOffset, final int outerLen, final String templateName, final int line, final int col) {
            delegate.reset(buffer, outerOffset, outerLen, templateName, line, col);
        }

        public String getComment() {
            return delegate.getComment();
        }

        public String getContent() {
            return delegate.getContent();
        }

        public int length() {
            return delegate.length();
        }

        public char charAt(final int index) {
            return delegate.charAt(index);
        }

        public CharSequence subSequence(final int start, final int end) {
            return delegate.subSequence(start, end);
        }

        public void setContent(final String content) {
            delegate.setContent(content);
        }

        public void accept(final IModelVisitor visitor) {
            delegate.accept(visitor);
        }

        public void write(final Writer writer) throws IOException {
            delegate.write(writer);
        }

        public Comment cloneEvent() {
            return delegate.cloneEvent();
        }

        @Override
        public String toString() {
            return delegate.toString();
        }

        public int getCol() {
            return delegate.getCol();
        }

        public int getLine() {
            return delegate.getLine();
        }

        public boolean hasLocation() {
            return delegate.hasLocation();
        }

        public String getTemplateName() {
            return delegate.getTemplateName();
        }
    }

}
