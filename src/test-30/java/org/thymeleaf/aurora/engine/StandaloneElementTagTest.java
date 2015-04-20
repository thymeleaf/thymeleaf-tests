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
import org.thymeleaf.aurora.IEngineConfiguration;
import org.thymeleaf.aurora.context.TestTemplateEngineConfigurationBuilder;
import org.thymeleaf.aurora.model.IStandaloneElementTag;
import org.thymeleaf.aurora.parser.HTMLTemplateParser;
import org.thymeleaf.aurora.parser.XMLTemplateParser;
import org.thymeleaf.aurora.resource.StringResource;
import org.thymeleaf.aurora.templatemode.TemplateMode;


public final class StandaloneElementTagTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);
    private static final IEngineConfiguration TEMPLATE_ENGINE_CONFIGURATION = TestTemplateEngineConfigurationBuilder.build();




    @Test
    public void testHtmlStandaloneElementAttrManagement() {

        IStandaloneElementTag tag;

        tag = computeHtmlTag("<input>");
        Assert.assertEquals("<input>", tag.toString());

        tag = computeHtmlTag("<input type=\"text\">");
        Assert.assertEquals("<input type=\"text\">", tag.toString());

        tag = computeHtmlTag("<input type=\"text\"   value='hello!!!'>");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("type");
        Assert.assertEquals("<input value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("value");
        Assert.assertEquals("<input>", tag.toString());

        tag = computeHtmlTag("<input type=\"text\"   value='hello!!!'    >");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "type");
        Assert.assertEquals("<input value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "value");
        Assert.assertEquals("<input    >", tag.toString());

        tag = computeHtmlTag("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    ba >", tag.toString());
        tag.getAttributes().setAttribute("value", "bye! :(");
        Assert.assertEquals("<input type=\"text\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("type", "one");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("ba", "two");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba=\"two\" >", tag.toString());
        tag.getAttributes().setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='three' >", tag.toString());
        tag.getAttributes().setAttribute("ba", "four", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba=four >", tag.toString());
        tag.getAttributes().setAttribute("ba", "five");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba=five >", tag.toString());
        tag.getAttributes().setAttribute("ba", null);
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("ba", "six");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba=six >", tag.toString());

        tag = computeHtmlTag("<input type=\"text\"   value='hello!!!'    ba=twenty >");
        tag.getAttributes().setAttribute("ba", "thirty");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    ba=thirty >", tag.toString());

        tag = computeHtmlTag("<input type=\"text\"   value='hello!!!'    ba=twenty ><p id='one'/>");
        Assert.assertEquals("<p id='one'/>", tag.toString());

    }




    @Test
    public void testXmlStandaloneElementAttrManagement() {

        IStandaloneElementTag tag;

        tag = computeXmlTag("<input/>");
        Assert.assertEquals("<input/>", tag.toString());

        tag = computeXmlTag("<input type=\"text\"/>");
        Assert.assertEquals("<input type=\"text\"/>", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'/>");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'/>", tag.toString());
        tag.getAttributes().removeAttribute("type");
        Assert.assertEquals("<input value='hello!!!'/>", tag.toString());
        tag.getAttributes().removeAttribute("value");
        Assert.assertEquals("<input/>", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'    />");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    />", tag.toString());
        tag.getAttributes().removeAttribute(null, "type");
        Assert.assertEquals("<input value='hello!!!'    />", tag.toString());
        tag.getAttributes().removeAttribute(null, "value");
        Assert.assertEquals("<input    />", tag.toString());

        tag = computeXmlTag("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals("<input th:type=\"text\"   th:value='hello!!!'    />", tag.toString());
        tag.getAttributes().removeAttribute("th", "type");
        Assert.assertEquals("<input th:value='hello!!!'    />", tag.toString());
        tag.getAttributes().removeAttribute("th", "value");
        Assert.assertEquals("<input    />", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'    ba='' />");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    ba='' />", tag.toString());
        tag.getAttributes().setAttribute("value", "bye! :(");
        Assert.assertEquals("<input type=\"text\"   value='bye! :('    ba='' />", tag.toString());
        tag.getAttributes().setAttribute("type", "one");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='' />", tag.toString());
        tag.getAttributes().setAttribute("ba", "two");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='two' />", tag.toString());
        tag.getAttributes().setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='three' />", tag.toString());

        try {
            tag.getAttributes().setAttribute("ba", "four", ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            tag.getAttributes().setAttribute("ba", null, ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            tag.getAttributes().setAttribute("ba", null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        tag.getAttributes().setAttribute("ba", "five");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='five' />", tag.toString());
                tag.getAttributes().setAttribute("ba", "six");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='six' />", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'    ba='twenty' /><meta id='one' />");
        Assert.assertEquals("<meta id='one' />", tag.toString());

    }







    @Test
    public void testHtmlStandaloneElementPropertyManagement() {

        IStandaloneElementTag tag;
        final ElementDefinitions elementDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getElementDefinitions();

        tag = computeHtmlTag("<input>");
        Assert.assertSame(elementDefinitions.forHTMLName("input"), tag.getElementDefinition());
        tag.setMinimized(true);
        Assert.assertEquals("<input/>", tag.toString());
        Assert.assertSame(elementDefinitions.forHTMLName("input"), tag.getElementDefinition());

        tag = computeHtmlTag("<input />");
        Assert.assertSame(elementDefinitions.forHTMLName("input"), tag.getElementDefinition());
        tag.setMinimized(false);
        Assert.assertEquals("<input >", tag.toString());
        Assert.assertSame(elementDefinitions.forHTMLName("input"), tag.getElementDefinition());

    }








    @Test
    public void testXmlStandaloneElementPropertyManagement() {

        IStandaloneElementTag tag;
        final ElementDefinitions elementDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getElementDefinitions();

        tag = computeXmlTag("<input/>");
        Assert.assertSame(elementDefinitions.forXMLName("input"), tag.getElementDefinition());
        try {
            tag.setMinimized(false); // XML standalone elements cannot be un-minimized
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

    }




    private static IStandaloneElementTag computeHtmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        HTML_PARSER.parse(TEMPLATE_ENGINE_CONFIGURATION, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static IStandaloneElementTag computeXmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        XML_PARSER.parse(TEMPLATE_ENGINE_CONFIGURATION, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler.tag;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {


        IStandaloneElementTag tag;


        @Override
        public void handleStandaloneElement(final IStandaloneElementTag standaloneElementTag) {
            this.tag = standaloneElementTag.cloneElementTag();
        }

    }


}
