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

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.TestTemplateEngineConfigurationBuilder;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateparser.markup.HTMLTemplateParser;
import org.thymeleaf.templateparser.markup.XMLTemplateParser;
import org.thymeleaf.templateresource.StringTemplateResource;


public final class OpenElementTagTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);
    private static final IEngineConfiguration TEMPLATE_ENGINE_CONFIGURATION = TestTemplateEngineConfigurationBuilder.build();




    @Test
    public void testHtmlOpenElementAttrManagement() {

        IOpenElementTag tag;

        tag = computeHtmlTag("<div>");
        Assert.assertEquals("<div>", tag.toString());

        tag = computeHtmlTag("<div type=\"text\">");
        Assert.assertEquals("<div type=\"text\">", tag.toString());

        tag = computeHtmlTag("<div type=\"text\"   value='hello!!!'>");
        Assert.assertEquals("<div type=\"text\"   value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("type");
        Assert.assertEquals("<div value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("value");
        Assert.assertEquals("<div>", tag.toString());

        tag = computeHtmlTag("<div type=\"text\"   value='hello!!!'    >");
        Assert.assertEquals("<div type=\"text\"   value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "type");
        Assert.assertEquals("<div value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "value");
        Assert.assertEquals("<div    >", tag.toString());

        tag = computeHtmlTag("<div type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals("<div type=\"text\"   value='hello!!!'    ba >", tag.toString());
        tag.getAttributes().setAttribute("value", "bye! :(");
        Assert.assertEquals("<div type=\"text\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("type", "one");
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("ba", "two");
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba=\"two\" >", tag.toString());
        tag.getAttributes().setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba='three' >", tag.toString());
        tag.getAttributes().setAttribute("ba", "four", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba=four >", tag.toString());
        tag.getAttributes().setAttribute("ba", "five");
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba=five >", tag.toString());
        tag.getAttributes().setAttribute("ba", null);
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba >", tag.toString());
        tag.getAttributes().setAttribute("ba", "six");
        Assert.assertEquals("<div type=\"one\"   value='bye! :('    ba=six >", tag.toString());

        tag = computeHtmlTag("<div type=\"text\"   value='hello!!!'    ba=twenty >");
        tag.getAttributes().setAttribute("ba", "thirty");
        Assert.assertEquals("<div type=\"text\"   value='hello!!!'    ba=thirty >", tag.toString());

        tag = computeHtmlTag("<div type=\"text\"   value='hello!!!'    ba=twenty ><p id='one'>");
        Assert.assertEquals("<p id='one'>", tag.toString());

    }




    @Test
    public void testXmlOpenElementAttrManagement() {

        IOpenElementTag tag;

        tag = computeXmlTag("<input></input>");
        Assert.assertEquals("<input>", tag.toString());

        tag = computeXmlTag("<input type=\"text\"></input>");
        Assert.assertEquals("<input type=\"text\">", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'></input>");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("type");
        Assert.assertEquals("<input value='hello!!!'>", tag.toString());
        tag.getAttributes().removeAttribute("value");
        Assert.assertEquals("<input>", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'    ></input>");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "type");
        Assert.assertEquals("<input value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute(null, "value");
        Assert.assertEquals("<input    >", tag.toString());

        tag = computeXmlTag("<input th:type=\"text\"   th:value='hello!!!'    ></input>");
        Assert.assertEquals("<input th:type=\"text\"   th:value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute("th", "type");
        Assert.assertEquals("<input th:value='hello!!!'    >", tag.toString());
        tag.getAttributes().removeAttribute("th", "value");
        Assert.assertEquals("<input    >", tag.toString());

        tag = computeXmlTag("<input type=\"text\"   value='hello!!!'    ba='' ></input>");
        Assert.assertEquals("<input type=\"text\"   value='hello!!!'    ba='' >", tag.toString());
        tag.getAttributes().setAttribute("value", "bye! :(");
        Assert.assertEquals("<input type=\"text\"   value='bye! :('    ba='' >", tag.toString());
        tag.getAttributes().setAttribute("type", "one");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='' >", tag.toString());
        tag.getAttributes().setAttribute("ba", "two");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='two' >", tag.toString());
        tag.getAttributes().setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='three' >", tag.toString());

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
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='five' >", tag.toString());
        tag.getAttributes().setAttribute("ba", "six");
        Assert.assertEquals("<input type=\"one\"   value='bye! :('    ba='six' >", tag.toString());

        tag = computeXmlTag("<div type=\"text\"   value='hello!!!'    ba='twenty' ><p id='one'></p></div>");
        Assert.assertEquals("<p id='one'>", tag.toString());

    }










    private static IOpenElementTag computeHtmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        HTML_PARSER.parseStandalone(TEMPLATE_ENGINE_CONFIGURATION, templateName, new StringTemplateResource(templateName, input), null, TemplateMode.HTML, handler);

        return handler.tag;

    }




    private static IOpenElementTag computeXmlTag(final String input) {

        final String templateName = "test";
        final TagObtentionTemplateHandler handler = new TagObtentionTemplateHandler();

        XML_PARSER.parseStandalone(TEMPLATE_ENGINE_CONFIGURATION, templateName,  new StringTemplateResource(templateName, input), null, TemplateMode.XML, handler);

        return handler.tag;

    }




    private static class TagObtentionTemplateHandler extends AbstractTemplateHandler {


        IOpenElementTag tag;


        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            this.tag = openElementTag.cloneEvent();
        }

    }


}
