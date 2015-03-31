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

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.aurora.context.ITemplateEngineContext;
import org.thymeleaf.aurora.context.TestTemplateEngineContextBuilder;
import org.thymeleaf.aurora.dialect.AbstractProcessorDialect;
import org.thymeleaf.aurora.dialect.IDialect;
import org.thymeleaf.aurora.dialect.IProcessorDialect;
import org.thymeleaf.aurora.parser.HTMLTemplateParser;
import org.thymeleaf.aurora.parser.XMLTemplateParser;
import org.thymeleaf.aurora.processor.IProcessor;
import org.thymeleaf.aurora.processor.cdatasection.AbstractCDATASectionProcessor;
import org.thymeleaf.aurora.processor.comment.AbstractCommentProcessor;
import org.thymeleaf.aurora.processor.doctype.AbstractDocTypeProcessor;
import org.thymeleaf.aurora.processor.element.AbstractElementProcessor;
import org.thymeleaf.aurora.processor.node.AbstractNodeProcessor;
import org.thymeleaf.aurora.processor.node.INodeProcessor;
import org.thymeleaf.aurora.processor.processinginstruction.AbstractProcessingInstructionProcessor;
import org.thymeleaf.aurora.processor.text.AbstractTextProcessor;
import org.thymeleaf.aurora.processor.xmldeclaration.AbstractXMLDeclarationProcessor;
import org.thymeleaf.aurora.resource.StringResource;
import org.thymeleaf.aurora.templatemode.TemplateMode;


public final class ProcessorIteratorTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);





    @Test
    public void testProcessorIteration01() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration02() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-15-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-15-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration03() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-7-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-7-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration04() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration05() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration06() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration07() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        tag.getAttributes().removeAttribute("th:src");
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration08() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        tag.getAttributes().removeAttribute("data-th-src");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration09() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration10() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect, true);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration11() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration12() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><a th:src='hello'>", dialect, true);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration13() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-*a-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><p th:src='uuuh'><a th:src='hello'>", dialect, false);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-{a}-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration14() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-*a-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><p th:src='uuuh'><a th:src='hello'>", dialect, true);
        final ProcessorIterator iterator = handler.iter;
        final IOpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-{a}-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }













    private static TagObtentionTemplateHandler computeHtmlTag(final String input, final IDialect dialect, final boolean wrapTag) {
        return computeHtmlTag(input, Collections.singleton(dialect), wrapTag);
    }

    private static TagObtentionTemplateHandler computeHtmlTag(final String input, final Set<IDialect> dialects, final boolean wrapTag) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler(wrapTag);
        final ITemplateEngineContext templateEngineContext = TestTemplateEngineContextBuilder.build(dialects);

        HTML_PARSER.parse(templateEngineContext, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler;

    }




    private static TagObtentionTemplateHandler computeXmlTag(final String input, final IDialect dialect, final boolean wrapTag) {
        return computeXmlTag(input, Collections.singleton(dialect), wrapTag);
    }

    private static TagObtentionTemplateHandler computeXmlTag(final String input, final Set<IDialect> dialects, final boolean wrapTag) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler(wrapTag);
        final ITemplateEngineContext templateEngineContext = TestTemplateEngineContextBuilder.build(dialects);

        XML_PARSER.parse(templateEngineContext, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {

        final boolean wrapTag;
        IOpenElementTag tag;
        ProcessorIterator iter = new ProcessorIterator();

        TagObtentionTemplateHandler(final boolean wrapTag) {
            super();
            this.wrapTag = wrapTag;
        }


        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            if (this.tag != null) {
                this.iter.next(); // Force the creation and computation of the iterator, and leave it not-completed for more thorough testing
            }
            this.tag = openElementTag.cloneElementTag();
            if (this.wrapTag) {
                this.tag = new TagWrapper(this.tag);
            }
            this.iter.reset(this.tag);
        }

    }




    private static class TagWrapper implements IOpenElementTag {

        // Used for checking the correctness of iterators with non-standard implementations of IOpenElementTag

        private final IOpenElementTag tag;

        TagWrapper(final IOpenElementTag tag) {
            super();
            this.tag = tag;
        }


        public IOpenElementTag cloneElementTag() {
            return this.tag.cloneElementTag();
        }

        public IElementAttributes getAttributes() {
            return this.tag.getAttributes();
        }

        public boolean hasAssociatedProcessors() {
            return this.tag.hasAssociatedProcessors();
        }

        public List<IProcessor> getAssociatedProcessorsInOrder() {
            return this.tag.getAssociatedProcessorsInOrder();
        }

        public ElementDefinition getElementDefinition() {
            return this.tag.getElementDefinition();
        }

        public String getElementName() {
            return this.tag.getElementName();
        }

        public boolean hasLocation() {
            return this.tag.hasLocation();
        }

        public int getLine() {
            return this.tag.getLine();
        }

        public int getCol() {
            return this.tag.getCol();
        }

        public void write(final Writer writer) throws IOException {
            this.tag.write(writer);
        }
    }










}
