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
import org.thymeleaf.aurora.context.DialectContext;
import org.thymeleaf.aurora.dialect.AbstractProcessorDialect;
import org.thymeleaf.aurora.dialect.IDialect;
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
import org.thymeleaf.aurora.templatemode.TemplateMode;


public final class ElementTagProcessorComputationTest {





    @Test
    public void testProcessorComputation01() {

        final IDialect dialect =
                buildDialect("standard", "th",
                        "CD-10-cdataone,CD-5-cdatatwo,C-20-comone,E-20-null-src,N-10-ELEMENT-test-null",
                        "CD-5-cdataxml");

        final DialectConfiguration dialectConfiguration = new DialectConfiguration("wo",dialect);
        final DialectContext dialectContext = DialectContext.build(Collections.singleton(dialectConfiguration));

        Assert.assertEquals("[standard,th,[CD-10-cdataone, CD-5-cdatatwo, C-20-comone, E-20-null-{wo:src,data-wo-src}, N-10-ELEMENT-{wo:test,wo-test}-null, CD-5-cdataxml]]", dialect.toString());
        Assert.assertEquals("[CD-5-cdatatwo, CD-10-cdataone]", dialectContext.getCDATASectionProcessors(TemplateMode.HTML).toString());
        Assert.assertEquals("[CD-5-cdataxml]", dialectContext.getCDATASectionProcessors(TemplateMode.XML).toString());

    }






    private static IDialect buildDialect(
            final String name, final String prefix, final String htmlProcSpecification, final String xmlProcSpecification) {
        return ProcessorAggregationTestDialect.build(name, prefix, htmlProcSpecification, xmlProcSpecification);
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
                    final String elementName = procSpecTok.nextToken();
                    final String attributeName = procSpecTok.nextToken();
                    processors.add(
                            new ElementProcessorAggregationTestProcessor(
                                    (elementName.equals("null")? null : elementName),
                                    (attributeName.equals("null")? null : attributeName),
                                    templateMode, precedence));
                } else if (type.equals("N")) {
                    final int precedence = Integer.valueOf(procSpecTok.nextToken());
                    final INodeProcessor.MatchingNodeType matchingNodeType = INodeProcessor.MatchingNodeType.valueOf(procSpecTok.nextToken());
                    final String elementName = procSpecTok.nextToken();
                    final String attributeName = procSpecTok.nextToken();
                    processors.add(
                            new NodeProcessorAggregationTestProcessor(
                                    matchingNodeType,
                                    (elementName.equals("null")? null : elementName),
                                    (attributeName.equals("null")? null : attributeName),
                                    templateMode, precedence));
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

            ElementProcessorAggregationTestProcessor(final String elementName, final String attributeName, final TemplateMode templateMode, final int precedence) {
                super(templateMode, elementName, attributeName, precedence);
            }

            public String getName() {
                return "E-" + getPrecedence() + "-" + this.getMatchingElementName() + "-" + this.getMatchingAttributeName();
            }

            public String toString() {
                return getName();
            }

        }


        private static class NodeProcessorAggregationTestProcessor extends AbstractNodeProcessor implements NamedTestProcessor {

            NodeProcessorAggregationTestProcessor(final MatchingNodeType matchingNodeType, final String elementName, final String attributeName, final TemplateMode templateMode, final int precedence) {
                super(matchingNodeType, templateMode, elementName, attributeName, precedence);
            }

            public String getName() {
                return "N-" + getPrecedence() + "-" + this.getMatchingNodeType() + "-" + this.getMatchingElementName() + "-" + this.getMatchingAttributeName();
            }

            public String toString() {
                return getName();
            }

        }


    }



}
