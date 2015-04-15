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

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.aurora.ITemplateEngineConfiguration;
import org.thymeleaf.aurora.context.TestTemplateEngineConfigurationBuilder;
import org.thymeleaf.aurora.dialect.IDialect;
import org.thymeleaf.aurora.dialect.IProcessorDialect;
import org.thymeleaf.aurora.model.IOpenElementTag;
import org.thymeleaf.aurora.parser.HTMLTemplateParser;
import org.thymeleaf.aurora.parser.XMLTemplateParser;
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

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(handler.tag).toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next(handler.tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration02() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-15-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next(tag).toString());
        Assert.assertEquals("N-ELEMENT-15-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration03() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-7-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-7-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration04() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration05() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration06() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration07() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        tag.getAttributes().removeAttribute("th:src");
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration08() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        tag.getAttributes().removeAttribute("data-th-src");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration09() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration10() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration11() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration12() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration13() {

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-*a-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><p th:src='uuuh'><a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-{a}-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }



    @Test
    public void testProcessorIteration14() {

        // This one checks that iteration also works OK for tags using a non-standard implementation

        final IProcessorDialect dialect =
                ProcessorAggregationTestDialect.buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-*a-src,N-ELEMENT-2-null-one");

        final TagObtentionTemplateHandler handler = computeHtmlTag("<div class='one'><p th:src='uuuh'><a th:src='hello'>", dialect);
        final ProcessorIterator iterator = handler.iter;
        final OpenElementTag tag = handler.tag;

        Assert.assertEquals("N-ELEMENT-5-{a}-{th:src,data-th-src}", iterator.next(tag).toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next(tag).toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next(tag));

    }













    private static TagObtentionTemplateHandler computeHtmlTag(final String input, final IDialect dialect) {
        return computeHtmlTag(input, Collections.singleton(dialect));
    }

    private static TagObtentionTemplateHandler computeHtmlTag(final String input, final Set<IDialect> dialects) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();
        final ITemplateEngineConfiguration templateEngineContext = TestTemplateEngineConfigurationBuilder.build(dialects);

        HTML_PARSER.parse(templateEngineContext, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler;

    }




    private static TagObtentionTemplateHandler computeXmlTag(final String input, final IDialect dialect) {
        return computeXmlTag(input, Collections.singleton(dialect));
    }

    private static TagObtentionTemplateHandler computeXmlTag(final String input, final Set<IDialect> dialects) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();
        final ITemplateEngineConfiguration templateEngineContext = TestTemplateEngineConfigurationBuilder.build(dialects);

        XML_PARSER.parse(templateEngineContext, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {

        OpenElementTag tag;
        ProcessorIterator iter = new ProcessorIterator();

        TagObtentionTemplateHandler() {
            super();
        }


        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            final OpenElementTag oetag = (OpenElementTag) openElementTag;
            if (this.tag != null) {
                this.iter.next(this.tag); // Force the creation and computation of the iterator, and leave it not-completed for more thorough testing
            }
            this.tag = oetag;
            this.iter.reset();
        }

    }






}
