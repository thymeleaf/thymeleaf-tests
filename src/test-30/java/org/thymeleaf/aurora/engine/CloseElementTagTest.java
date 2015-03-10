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
import org.thymeleaf.aurora.context.ITemplateEngineContext;
import org.thymeleaf.aurora.context.TemplateEngineContext;
import org.thymeleaf.aurora.parser.HTMLTemplateParser;
import org.thymeleaf.aurora.parser.XMLTemplateParser;
import org.thymeleaf.aurora.resource.StringResource;
import org.thymeleaf.aurora.templatemode.TemplateMode;


public final class CloseElementTagTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);
    private static final ITemplateEngineContext TEMPLATE_ENGINE_CONTEXT = new TemplateEngineContext();




    @Test
    public void testHtmlCloseElementPropertyManagement() {

        ICloseElementTag tag;
        final ElementDefinitions elementDefinitions = TEMPLATE_ENGINE_CONTEXT.getElementDefinitions();

        tag = computeHtmlTag("<div></div>");
        Assert.assertSame(elementDefinitions.forHTMLName("div"), tag.getElementDefinition());
        tag.setElementName("span");
        Assert.assertEquals("</span>", tag.toString());
        Assert.assertSame(elementDefinitions.forHTMLName("span"), tag.getElementDefinition());

        tag = computeHtmlTag("<div></div>");
        Assert.assertSame(elementDefinitions.forHTMLName("div"), tag.getElementDefinition());
        tag.setElementName("voodoo");
        Assert.assertEquals("</voodoo>", tag.toString());
        Assert.assertSame(elementDefinitions.forHTMLName("voodoo"), tag.getElementDefinition());

        tag = computeHtmlTag("<div></div>");
        try {
            tag.setElementName("meta");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        tag = computeHtmlTag("<div></div>");
        try {
            tag.setElementName("br");
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }








    @Test
    public void testXmlCloseElementPropertyManagement() {

        ICloseElementTag tag;
        final ElementDefinitions elementDefinitions = TEMPLATE_ENGINE_CONTEXT.getElementDefinitions();

        tag = computeXmlTag("<input></input>");
        Assert.assertSame(elementDefinitions.forXMLName("input"), tag.getElementDefinition());
        tag.setElementName("voodoo");
        Assert.assertEquals("</voodoo>", tag.toString());
        Assert.assertSame(elementDefinitions.forXMLName("voodoo"), tag.getElementDefinition());

        tag = computeXmlTag("<input type=\"text\"></input>");
        tag.setElementName("voodoo");
        Assert.assertEquals("</voodoo>", tag.toString());

    }




    private static ICloseElementTag computeHtmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        HTML_PARSER.parse(TEMPLATE_ENGINE_CONTEXT, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static ICloseElementTag computeXmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        XML_PARSER.parse(TEMPLATE_ENGINE_CONTEXT, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {


        ICloseElementTag tag;


        @Override
        public void handleCloseElement(final ICloseElementTag closeElementTag) {
            this.tag = closeElementTag.cloneElementTag();
        }

    }


}
