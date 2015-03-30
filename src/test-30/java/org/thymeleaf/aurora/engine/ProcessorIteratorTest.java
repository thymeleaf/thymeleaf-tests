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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.aurora.DialectConfiguration;
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
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration02() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-15-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-15-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration03() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-7-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-7-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration04() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration05() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertEquals("N-ELEMENT-10-null-{th:src,data-th-src}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration06() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration07() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        tag.getAttributes().removeAttribute("th:src");
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration08() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        tag.getAttributes().removeAttribute("data-th-src");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        Assert.assertNull(iterator.next());

    }



    @Test
    public void testProcessorIteration09() {

        final IProcessorDialect dialect =
                buildHTMLDialect("standard", "th",
                        "N-ELEMENT-10-null-src,N-ELEMENT-5-null-src,N-ELEMENT-2-null-one");

        final IOpenElementTag tag = computeHtmlTag("<a th:src='hello'>", dialect);
        final IProcessorIterator iterator = tag.getAssociatedProcessorsIterator();

        Assert.assertEquals("N-ELEMENT-5-null-{th:src,data-th-src}", iterator.next().toString());
        tag.getAttributes().setAttribute("th:one", "somevalue");
        Assert.assertEquals("N-ELEMENT-2-null-{th:one,data-th-one}", iterator.next().toString());
        tag.getAttributes().removeAttribute("th:src");
        Assert.assertNull(iterator.next());

    }













    private static IOpenElementTag computeHtmlTag(final String input, final IDialect dialect) {
        return computeHtmlTag(input, Collections.singleton(dialect));
    }

    private static IOpenElementTag computeHtmlTag(final String input, final Set<IDialect> dialects) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();
        final ITemplateEngineContext templateEngineContext = TestTemplateEngineContextBuilder.build(dialects);

        HTML_PARSER.parse(templateEngineContext, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static IOpenElementTag computeXmlTag(final String input, final IDialect dialect) {
        return computeXmlTag(input, Collections.singleton(dialect));
    }

    private static IOpenElementTag computeXmlTag(final String input, final Set<IDialect> dialects) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();
        final ITemplateEngineContext templateEngineContext = TestTemplateEngineContextBuilder.build(dialects);

        XML_PARSER.parse(templateEngineContext, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {


        IOpenElementTag tag;


        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            this.tag = openElementTag.cloneElementTag();
        }

    }









    private static ProcessorAggregationTestDialect buildDialect(
            final String name, final String prefix, final String htmlProcSpecification, final String xmlProcSpecification) {
        return ProcessorAggregationTestDialect.build(name, prefix, htmlProcSpecification, xmlProcSpecification);
    }

    private static ProcessorAggregationTestDialect buildHTMLDialect(
            final String name, final String prefix, final String htmlProcSpecification) {
        return buildDialect(name, prefix, htmlProcSpecification, "");
    }

    private static ProcessorAggregationTestDialect buildXMLDialect(
            final String name, final String prefix, final String xmlProcSpecification) {
        return buildDialect(name, prefix, "", xmlProcSpecification);
    }


    private static class ProcessorAggregationTestDialect extends AbstractProcessorDialect {


        static ProcessorAggregationTestDialect build(final String name, final String prefix,
                                              final String htmlProcSpecification, final String xmlProcSpecification) {


            final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();
            processors.addAll(buildProcessors(TemplateMode.HTML, htmlProcSpecification));
            processors.addAll(buildProcessors(TemplateMode.XML, xmlProcSpecification));
            return new ProcessorAggregationTestDialect(name, prefix, processors);

        }


        static Set<IProcessor> buildProcessors(final TemplateMode templateMode, final String specification) {

            final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();
            final StringTokenizer specTok = new StringTokenizer(specification,", ");

            while (specTok.hasMoreTokens()) {

                final String procSpec = specTok.nextToken();

                final StringTokenizer procSpecTok = new StringTokenizer(procSpec,"-");

                final String type = procSpecTok.nextToken();
                if (type.equals("CD")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new CDATASectionProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("C")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new CommentProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("DT")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new DocTypeProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("PI")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new ProcessingInstructionProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("T")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new TextProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("XD")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    processors.add(
                            new XMLDeclarationProcessorAggregationTestProcessor(
                                    procSpecTok.nextToken(), templateMode, precedence));
                } else if (type.equals("E")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    String elementName = procSpecTok.nextToken();
                    boolean prefixElementName = true;
                    if (elementName.startsWith("*")) {
                        prefixElementName = false;
                        elementName = elementName.substring(1);
                    }
                    String attributeName = procSpecTok.nextToken();
                    boolean prefixAttributeName = true;
                    if (attributeName.startsWith("*")) {
                        prefixAttributeName = false;
                        attributeName = attributeName.substring(1);
                    }
                    processors.add(
                            new ElementProcessorAggregationTestProcessor(
                                    (elementName.equals("null")? null : elementName), prefixElementName,
                                    (attributeName.equals("null")? null : attributeName), prefixAttributeName,
                                    templateMode, precedence));
                } else if (type.equals("N")) {
                    final INodeProcessor.MatchingNodeType matchingNodeType = INodeProcessor.MatchingNodeType.valueOf(procSpecTok.nextToken());
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    switch(matchingNodeType) {

                        case CDATA_SECTION:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;
                        case COMMENT:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;
                        case DOC_TYPE:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;
                        case ELEMENT:
                            String elementName = procSpecTok.nextToken();
                            boolean prefixElementName = true;
                            if (elementName.startsWith("*")) {
                                prefixElementName = false;
                                elementName = elementName.substring(1);
                            }
                            String attributeName = procSpecTok.nextToken();
                            boolean prefixAttributeName = true;
                            if (attributeName.startsWith("*")) {
                                prefixAttributeName = false;
                                attributeName = attributeName.substring(1);
                            }
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType,
                                            (elementName.equals("null")? null : elementName), prefixElementName,
                                            (attributeName.equals("null")? null : attributeName), prefixAttributeName,
                                            templateMode, precedence));
                            break;
                        case PROCESSING_INSTRUCTION:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;
                        case TEXT:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;
                        case XML_DECLARATION:
                            processors.add(
                                    new NodeProcessorAggregationTestProcessor(
                                            matchingNodeType, procSpecTok.nextToken(), templateMode, precedence));
                            break;

                    }
                } else {
                    throw new IllegalArgumentException("Unrecognized: " + type);
                }

            }

            return processors;

        }


        protected ProcessorAggregationTestDialect(final String name, final String prefix, final Set<IProcessor> processors) {
            super(name, prefix, processors);
        }


        public String toString() {
            return "[" + getName() + "," + getPrefix() + "," + getProcessors() + "]";
        }



        static interface NamedTestProcessor {
            String getName();
        }


        private static class CDATASectionProcessorAggregationTestProcessor extends AbstractCDATASectionProcessor implements NamedTestProcessor {

            private final String name;

            CDATASectionProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "CD-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class CommentProcessorAggregationTestProcessor extends AbstractCommentProcessor implements NamedTestProcessor {

            private final String name;

            CommentProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "C-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class DocTypeProcessorAggregationTestProcessor extends AbstractDocTypeProcessor implements NamedTestProcessor {

            private final String name;

            DocTypeProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "DT-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class ProcessingInstructionProcessorAggregationTestProcessor extends AbstractProcessingInstructionProcessor implements NamedTestProcessor {

            private final String name;

            ProcessingInstructionProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "PI-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class TextProcessorAggregationTestProcessor extends AbstractTextProcessor implements NamedTestProcessor {

            private final String name;

            TextProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "T-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class XMLDeclarationProcessorAggregationTestProcessor extends AbstractXMLDeclarationProcessor implements NamedTestProcessor {

            private final String name;

            XMLDeclarationProcessorAggregationTestProcessor(final String name, final TemplateMode templateMode, final int precedence) {
                super(templateMode, precedence);
                this.name = name;
            }

            public String getName() {
                return "XD-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


        private static class ElementProcessorAggregationTestProcessor extends AbstractElementProcessor implements NamedTestProcessor {

            ElementProcessorAggregationTestProcessor(
                    final String elementName, final boolean prefixElementName,
                    final String attributeName, final boolean prefixAttributeName,
                    final TemplateMode templateMode, final int precedence) {
                super(templateMode, elementName, prefixElementName, attributeName, prefixAttributeName, precedence);
            }

            public String getName() {
                return "E-" + getPrecedence() + "-" + this.getMatchingElementName() + "-" + this.getMatchingAttributeName();
            }

            public String toString() {
                return getName();
            }

        }


        private static class NodeProcessorAggregationTestProcessor extends AbstractNodeProcessor implements NamedTestProcessor {

            private final String name;

            NodeProcessorAggregationTestProcessor(
                    final MatchingNodeType matchingNodeType,
                    final String elementName, final boolean prefixElementName,
                    final String attributeName, final boolean prefixAttributeName,
                    final TemplateMode templateMode, final int precedence) {
                super(matchingNodeType, templateMode, elementName, prefixElementName, attributeName, prefixAttributeName, precedence);
                this.name = null;
            }

            NodeProcessorAggregationTestProcessor(
                    final MatchingNodeType matchingNodeType, final String name, final TemplateMode templateMode, final int precedence) {
                super(matchingNodeType, templateMode, null, false, null, false, precedence);
                this.name = name;
            }

            public String getName() {
                if (getMatchingNodeType() == MatchingNodeType.ELEMENT) {
                    return "N-" + this.getMatchingNodeType() + "-" + getPrecedence() + "-" + this.getMatchingElementName() + "-" + this.getMatchingAttributeName();
                }
                return "N-" + this.getMatchingNodeType() + "-" + getPrecedence() + "-" + this.name;
            }

            public String toString() {
                return getName();
            }

        }


    }







}
