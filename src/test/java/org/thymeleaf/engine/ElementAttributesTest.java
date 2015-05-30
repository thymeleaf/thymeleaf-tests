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
import org.thymeleaf.model.IElementAttributes;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.templateparser.HTMLTemplateParser;
import org.thymeleaf.templateparser.XMLTemplateParser;
import org.thymeleaf.resource.StringResource;
import org.thymeleaf.templatemode.TemplateMode;


public final class ElementAttributesTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);
    private static final IEngineConfiguration TEMPLATE_ENGINE_CONFIGURATION = TestTemplateEngineConfigurationBuilder.build();




    @Test
    public void testHtmlElementAttributesAttrManagement() {

        IElementAttributes attrs;

        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\">");
        Assert.assertEquals("[type]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'>");
        Assert.assertEquals("[type, value]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals("[{type}, {value}]", attrs.getAllAttributeNames().toString());
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'", attrs.toString());
        Assert.assertEquals("[value]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals("[{value}]", attrs.getAllAttributeNames().toString());
        attrs.removeAttribute("value");
        Assert.assertEquals("", attrs.toString());
        Assert.assertEquals("[]", attrs.getAllCompleteNames().toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(null, "type");
        Assert.assertEquals(" value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(null, "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("th", "type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("th", "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("TH", "TYPE");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("tH", "Value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("data-th-type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("data-th-value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forHTMLName("th:type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forHTMLName("th:value"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forHTMLName("th", "type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forHTMLName("TH", "VALUE"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" ba", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba ", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba ", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\" ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba ", attrs.toString());
        attrs.setAttribute("value", "bye! :(");
        Assert.assertEquals(" type=\"text\"   value='bye! :('    ba ", attrs.toString());
        attrs.setAttribute("type", "one");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba ", attrs.toString());
        attrs.setAttribute("ba", "two");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=\"two\" ", attrs.toString());
        attrs.setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='three' ", attrs.toString());
        attrs.setAttribute("ba", "four", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=four ", attrs.toString());
        attrs.setAttribute("ba", "five");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=five ", attrs.toString());
        attrs.setAttribute("ba", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba ", attrs.toString());
        attrs.setAttribute("ba", "six");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=six ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba=twenty >");
        attrs.setAttribute("ba", "thirty");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=thirty ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' name='one' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' name='one' ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' name='one'>");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one'", attrs.toString());
        attrs.removeAttribute("name");
        Assert.assertEquals(" type=\"text\"value='hello!!!'", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("value", null);
        Assert.assertEquals(" type=\"text\"   value    ba= s", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba= s", attrs.toString());
        attrs.setAttribute("type", null, ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type   ba= s", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals("[type, value, ba]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.clearAll();
        Assert.assertEquals("", attrs.toString());
        attrs.clearAll();
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("name", "onename");
        Assert.assertEquals(" name=\"onename\"", attrs.toString());
        attrs.setAttribute("value", "val");
        Assert.assertEquals(" name=\"onename\" value=\"val\"", attrs.toString());
        attrs.setAttribute("placeholder", null);
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder", attrs.toString());
        attrs.setAttribute("placeholder", "a");
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("value", null);
        Assert.assertEquals(" name=\"onename\" value placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "");
        Assert.assertEquals(" name=\"\" value placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" name='' value placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", null);
        Assert.assertEquals(" name value placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "");
        Assert.assertEquals(" name='' value placeholder=\"a\"", attrs.toString());
        attrs.removeAttribute("name");
        Assert.assertEquals(" value placeholder=\"a\"", attrs.toString());
        Assert.assertEquals(2, attrs.size());
        attrs.setAttribute("name", "");
        Assert.assertEquals("[value, placeholder, name]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals(" value placeholder=\"a\" name=\"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("type", null);
        Assert.assertEquals(" value='hello!!!'    ba= s type", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("title", null);
        Assert.assertEquals(" value='hello!!!'    ba= s title", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("title", "");
        Assert.assertEquals(" value='hello!!!'    ba= s title=\"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("title", "", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" value='hello!!!'    ba= s title=''", attrs.toString());
        try {
            // Shouldn't be able to set an empty-string value with no quotes
            attrs.setAttribute("title", "", ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("value", "one", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals(" type=\"text\"   value=one    ba= s", attrs.toString());
        attrs.setAttribute("value", "");
        Assert.assertEquals(" type=\"text\"   value=\"\"    ba= s", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", "");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= \"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= one", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"one\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        attrs.setAttribute("ba", "one", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='one'", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.setAttribute("ba", "two");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\"", attrs.toString());
        attrs.setAttribute("be", "three");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\"", attrs.toString());
        attrs.setAttribute("bi", "four");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\"", attrs.toString());
        attrs.setAttribute("bo", "five");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs.setAttribute("bu", "six");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\" bu=\"six\"", attrs.toString());
        attrs.removeAttribute("be");
        attrs.removeAttribute("bu");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs.setAttribute("bi", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi bo=\"five\"", attrs.toString());
        attrs.removeAttribute("bi");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());

        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"", attrs.toString());

        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals(" a=one", attrs.toString());

        attrs = computeHtmlAttributes("<input   >");
        Assert.assertEquals("   ", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"   ", attrs.toString());
        attrs.setAttribute("b", "two");
        Assert.assertEquals(" a=\"one\" b=\"two\"   ", attrs.toString());

        attrs = computeHtmlAttributes("<input\none  />");
        Assert.assertEquals("\none  ", attrs.toString());
        attrs.setAttribute("a", "two");
        Assert.assertEquals("\none a=\"two\"  ", attrs.toString());

        attrs = computeHtmlAttributes("<input\none two/>");
        Assert.assertEquals("\none two", attrs.toString());
        attrs.removeAttribute("one");
        Assert.assertEquals("\ntwo", attrs.toString());

    }




    @Test
    public void testXmlElementAttributesAttrManagement() {

        IElementAttributes attrs;

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"/>");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals("", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(null, "type");
        Assert.assertEquals(" value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(null, "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("th", "type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("th", "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("TH", "TYPE");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("tH", "Value");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("data-th-type");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("data-th-value");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forXMLName("th:type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forXMLName("th:value"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals("[th:type, th:value]", attrs.getAllCompleteNames().toString());
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forXMLName("th", "type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute(AttributeNames.forXMLName("TH", "VALUE"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=''/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=''", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba=''", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=''/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=''", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba=''", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" ba=''", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='' />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='' ", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba='' ", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\" ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='' />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='' ", attrs.toString());
        attrs.setAttribute("value", "bye! :(");
        Assert.assertEquals(" type=\"text\"   value='bye! :('    ba='' ", attrs.toString());
        attrs.setAttribute("type", "one");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='' ", attrs.toString());
        attrs.setAttribute("ba", "two");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='two' ", attrs.toString());
        attrs.setAttribute("ba", "three", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='three' ", attrs.toString());

        try {
            attrs.setAttribute("ba", "four", ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            attrs.setAttribute("ba", null, ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            attrs.setAttribute("ba", null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        attrs.setAttribute("ba", "five");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='five' ", attrs.toString());
        attrs.setAttribute("ba", "six");
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='six' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='twenty' />");
        attrs.setAttribute("ba", "thirty");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='thirty' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' />");
        Assert.assertEquals(" type=\"text\"value='hello!!!' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' name='one' />");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' name='one' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' name='one'/>");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one'", attrs.toString());
        attrs.removeAttribute("name");
        Assert.assertEquals(" type=\"text\"value='hello!!!'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs.clearAll();
        Assert.assertEquals("", attrs.toString());
        attrs.clearAll();
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("name", "onename");
        Assert.assertEquals(" name=\"onename\"", attrs.toString());
        attrs.setAttribute("value", "val");
        Assert.assertEquals(" name=\"onename\" value=\"val\"", attrs.toString());
        attrs.setAttribute("placeholder", "a");
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "");
        Assert.assertEquals(" name=\"\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" name='' value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs.setAttribute("name", "");
        Assert.assertEquals(" name='' value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs.removeAttribute("name");
        Assert.assertEquals(" value=\"val\" placeholder=\"a\"", attrs.toString());
        Assert.assertEquals(2, attrs.size());
        attrs.setAttribute("name", "");
        Assert.assertEquals(" value=\"val\" placeholder=\"a\" name=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("type", "");
        Assert.assertEquals(" value='hello!!!'    ba= 's' type=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("title", " ");
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=\" \"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("title", "");
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("title", "", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=''", attrs.toString());
        try {
            // Shouldn't be able to set an empty-string value with no quotes
            attrs.setAttribute("title", "", ElementAttributes.ValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("value", "one", ElementAttributes.ValueQuotes.DOUBLE);
        Assert.assertEquals(" type=\"text\"   value=\"one\"    ba= 's'", attrs.toString());
        attrs.setAttribute("value", "");
        Assert.assertEquals(" type=\"text\"   value=\"\"    ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("ba", "");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= ''", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 'one'", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"one\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=\"\"/>");
        attrs.setAttribute("ba", "one", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='one'", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.setAttribute("ba", "two");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\"", attrs.toString());
        attrs.setAttribute("be", "three");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\"", attrs.toString());
        attrs.setAttribute("bi", "four");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\"", attrs.toString());
        attrs.setAttribute("bo", "five");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs.setAttribute("bu", "six");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\" bu=\"six\"", attrs.toString());
        attrs.removeAttribute("be");
        attrs.removeAttribute("bu");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs.removeAttribute("bi");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"", attrs.toString());

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one", ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" a='one'", attrs.toString());

        attrs = computeXmlAttributes("<input   />");
        Assert.assertEquals("   ", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"   ", attrs.toString());
        attrs.setAttribute("b", "two");
        Assert.assertEquals(" a=\"one\" b=\"two\"   ", attrs.toString());

        attrs = computeXmlAttributes("<input\none=\"\"  />");
        Assert.assertEquals("\none=\"\"  ", attrs.toString());
        attrs.setAttribute("a", "two");
        Assert.assertEquals("\none=\"\" a=\"two\"  ", attrs.toString());

        attrs = computeXmlAttributes("<input\none=\"\" two=\"\"/>");
        Assert.assertEquals("\none=\"\" two=\"\"", attrs.toString());
        attrs.removeAttribute("one");
        Assert.assertEquals("\ntwo=\"\"", attrs.toString());

    }







    @Test
    public void testHtmlElementAttributesAttrObtention() {

        IElementAttributes attrs;
        final AttributeDefinitions attributeDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getAttributeDefinitions();

        attrs = computeHtmlAttributes("<input>");
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getValue("type"));
        Assert.assertNull(attrs.getValue(null, "type"));
        Assert.assertNull(attrs.getValue("", "type"));
        Assert.assertNull(attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getAttributeDefinition("type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "type"));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(0, attrs.size());

        attrs = computeHtmlAttributes("<input type=\"text\">");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(1, attrs.size());

        attrs = computeHtmlAttributes("<input type='text'>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(1, attrs.size());

        attrs = computeHtmlAttributes("<input type=text>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(1, attrs.size());

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\">");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertTrue(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertTrue(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertTrue(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("th:type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertEquals(2, attrs.size());

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one=auth>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertTrue(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertTrue(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertTrue(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("th:type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertTrue(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertTrue(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertTrue(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getValue("sec:one"));
        Assert.assertEquals("auth", attrs.getValue("SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("sec", "one"));
        Assert.assertEquals("auth", attrs.getValue("SEC", "One"));
        Assert.assertEquals("auth", attrs.getValue(null, "sec:one"));
        Assert.assertEquals("auth", attrs.getValue(null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("", "sec:one"));
        Assert.assertEquals("auth", attrs.getValue("", "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forHTMLName("sec:one")));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forHTMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("sec", "one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("SEC", "One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(null, "sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(null, "SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("", "sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("", "SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("sec:one")));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("SEC:One")));
        Assert.assertEquals(3, attrs.size());

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one=auth>");
        attrs.removeAttribute("type");
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getValue("type"));
        Assert.assertNull(attrs.getValue(null, "type"));
        Assert.assertNull(attrs.getValue("", "type"));
        Assert.assertNull(attrs.getValue(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getAttributeDefinition("type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "type"));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forHTMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertTrue(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertTrue(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertTrue(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertEquals("${thetype}", attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("th:type")));
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertTrue(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertTrue(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertTrue(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getValue("sec:one"));
        Assert.assertEquals("auth", attrs.getValue("SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("sec", "one"));
        Assert.assertEquals("auth", attrs.getValue("SEC", "One"));
        Assert.assertEquals("auth", attrs.getValue(null, "sec:one"));
        Assert.assertEquals("auth", attrs.getValue(null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("", "sec:one"));
        Assert.assertEquals("auth", attrs.getValue("", "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forHTMLName("sec:one")));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forHTMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("sec", "one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("SEC", "One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(null, "sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(null, "SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("", "sec:one"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition("", "SEC:One"));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("sec:one")));
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forHTMLName("SEC:One")));
        Assert.assertEquals(2, attrs.size());


        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one=auth>");
        attrs.clearAll();
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertFalse(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertFalse(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals(0, attrs.size());

        attrs = computeHtmlAttributes("<input type=text th:type=\"${thetype}\">");
        Assert.assertEquals(IElementAttributes.ValueQuotes.NONE, attrs.getValueQuotes("type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.NONE, attrs.getValueQuotes("", "type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.NONE, attrs.getValueQuotes(AttributeNames.forHTMLName("", "type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.NONE, attrs.getValueQuotes(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("th:type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("", "th:type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("th", "type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("", "TH:Type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("TH", "Type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forHTMLName("th", "type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forHTMLName("TH", "Type")));

        attrs = computeHtmlAttributes("<input type='text' \nth:type=\"${thetype}\">");
        Assert.assertEquals(1, attrs.getLine("type"));
        Assert.assertEquals(1, attrs.getLine("", "type"));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forHTMLName("", "type")));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(8, attrs.getCol("type"));
        Assert.assertEquals(8, attrs.getCol("", "type"));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forHTMLName("", "type")));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(2, attrs.getLine("th:type"));
        Assert.assertEquals(2, attrs.getLine("th", "type"));
        Assert.assertEquals(2, attrs.getLine("", "th:type"));
        Assert.assertEquals(2, attrs.getLine("TH", "Type"));
        Assert.assertEquals(2, attrs.getLine("", "TH:Type"));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("th", "type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("", "TH:Type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("TH", "Type")));
        Assert.assertEquals(1, attrs.getCol("th:type"));
        Assert.assertEquals(1, attrs.getCol("", "th:type"));
        Assert.assertEquals(1, attrs.getCol("th", "type"));
        Assert.assertEquals(1, attrs.getCol("TH", "Type"));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forHTMLName("th", "type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forHTMLName("TH", "Type")));

        attrs = computeHtmlAttributes("<input type='text' \na=\"b\" th:type=\"${thetype}\">");
        attrs.removeAttribute("a");
        Assert.assertEquals(1, attrs.getLine("type"));
        Assert.assertEquals(1, attrs.getLine("", "type"));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forHTMLName("", "type")));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(8, attrs.getCol("type"));
        Assert.assertEquals(8, attrs.getCol("", "type"));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forHTMLName("", "type")));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forHTMLName("type")));
        Assert.assertEquals(2, attrs.getLine("th:type"));
        Assert.assertEquals(2, attrs.getLine("th", "type"));
        Assert.assertEquals(2, attrs.getLine("", "th:type"));
        Assert.assertEquals(2, attrs.getLine("TH", "Type"));
        Assert.assertEquals(2, attrs.getLine("", "TH:Type"));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("th", "type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("", "TH:Type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forHTMLName("TH", "Type")));
        Assert.assertEquals(7, attrs.getCol("th:type"));
        Assert.assertEquals(7, attrs.getCol("", "th:type"));
        Assert.assertEquals(7, attrs.getCol("th", "type"));
        Assert.assertEquals(7, attrs.getCol("TH", "Type"));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forHTMLName("th:type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forHTMLName("th", "type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forHTMLName("TH", "Type")));

        Assert.assertTrue(attrs.hasLocation("th:type"));
        Assert.assertTrue(attrs.hasLocation("", "th:type"));
        Assert.assertTrue(attrs.hasLocation("th", "type"));
        Assert.assertTrue(attrs.hasLocation("TH", "Type"));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forHTMLName("", "th:type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forHTMLName("th", "type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forHTMLName("TH:Type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forHTMLName("TH", "Type")));
        attrs.setAttribute("one", "two");
        Assert.assertFalse(attrs.hasLocation("one"));
        Assert.assertFalse(attrs.hasLocation("", "one"));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forHTMLName("", "one")));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forHTMLName("one")));

    }








    @Test
    public void testXmlElementAttributesAttrObtention() {

        IElementAttributes attrs;
        final AttributeDefinitions attributeDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getAttributeDefinitions();

        attrs = computeXmlAttributes("<input/>");
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getValue("type"));
        Assert.assertNull(attrs.getValue(null, "type"));
        Assert.assertNull(attrs.getValue("", "type"));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getAttributeDefinition("type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "type"));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertEquals(0, attrs.size());

        attrs = computeXmlAttributes("<input type=\"text\"/>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertEquals(1, attrs.size());

        attrs = computeXmlAttributes("<input type='text'/>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertEquals(1, attrs.size());

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\"/>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertNull(attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertNull(attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertNull(attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertNull(attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("TH:Type")));
        Assert.assertEquals(2, attrs.size());

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one='auth'/>");
        Assert.assertTrue(attrs.hasAttribute("type"));
        Assert.assertTrue(attrs.hasAttribute(null, "type"));
        Assert.assertTrue(attrs.hasAttribute("", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getValue("type"));
        Assert.assertEquals("text", attrs.getValue(null, "type"));
        Assert.assertEquals("text", attrs.getValue("", "type"));
        Assert.assertEquals("text", attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(null, "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition("", "type"));
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertNull(attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertNull(attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertNull(attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertNull(attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("TH:Type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getValue("sec:one"));
        Assert.assertNull(attrs.getValue("SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("sec", "one"));
        Assert.assertNull(attrs.getValue("SEC", "One"));
        Assert.assertEquals("auth", attrs.getValue(null, "sec:one"));
        Assert.assertNull(attrs.getValue(null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("", "sec:one"));
        Assert.assertNull(attrs.getValue("", "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forXMLName("sec:one")));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition("SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("sec", "one"));
        Assert.assertNull(attrs.getAttributeDefinition("SEC", "One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition(null, "sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("", "sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition("", "SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forXMLName("sec:one")));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("SEC:One")));
        Assert.assertEquals(3, attrs.size());

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one='auth'/>");
        attrs.removeAttribute("type");
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getValue("type"));
        Assert.assertNull(attrs.getValue(null, "type"));
        Assert.assertNull(attrs.getValue("", "type"));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getAttributeDefinition("type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "type"));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getValue("th:type"));
        Assert.assertNull(attrs.getValue("TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("th", "type"));
        Assert.assertNull(attrs.getValue("TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(null, "th:type"));
        Assert.assertNull(attrs.getValue(null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue("", "th:type"));
        Assert.assertNull(attrs.getValue("", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getValue(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("th", "type"));
        Assert.assertNull(attrs.getAttributeDefinition("TH", "Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(null, "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition("", "th:type"));
        Assert.assertNull(attrs.getAttributeDefinition("", "TH:Type"));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttributeDefinition(AttributeNames.forXMLName("th:type")));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("TH:Type")));
        Assert.assertTrue(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getValue("sec:one"));
        Assert.assertNull(attrs.getValue("SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("sec", "one"));
        Assert.assertNull(attrs.getValue("SEC", "One"));
        Assert.assertEquals("auth", attrs.getValue(null, "sec:one"));
        Assert.assertNull(attrs.getValue(null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue("", "sec:one"));
        Assert.assertNull(attrs.getValue("", "SEC:One"));
        Assert.assertEquals("auth", attrs.getValue(AttributeNames.forXMLName("sec:one")));
        Assert.assertNull(attrs.getValue(AttributeNames.forXMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition("SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("sec", "one"));
        Assert.assertNull(attrs.getAttributeDefinition("SEC", "One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition(null, "sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition(null, "SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition("", "sec:one"));
        Assert.assertNull(attrs.getAttributeDefinition("", "SEC:One"));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttributeDefinition(AttributeNames.forXMLName("sec:one")));
        Assert.assertNull(attrs.getAttributeDefinition(AttributeNames.forXMLName("SEC:One")));
        Assert.assertEquals(2, attrs.size());


        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one='auth'/>");
        attrs.clearAll();
        Assert.assertFalse(attrs.hasAttribute("type"));
        Assert.assertFalse(attrs.hasAttribute(null, "type"));
        Assert.assertFalse(attrs.hasAttribute("", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertFalse(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertFalse(attrs.hasAttribute("th:type"));
        Assert.assertFalse(attrs.hasAttribute("TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("th", "type"));
        Assert.assertFalse(attrs.hasAttribute("TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute("", "th:type"));
        Assert.assertFalse(attrs.hasAttribute("", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals(0, attrs.size());

        attrs = computeXmlAttributes("<input type='text' th:type=\"${thetype}\"/>");
        Assert.assertEquals(IElementAttributes.ValueQuotes.SINGLE, attrs.getValueQuotes("type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.SINGLE, attrs.getValueQuotes("", "type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.SINGLE, attrs.getValueQuotes(AttributeNames.forXMLName("", "type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.SINGLE, attrs.getValueQuotes(AttributeNames.forXMLName("type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("th:type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("", "th:type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes("th", "type"));
        Assert.assertNull(attrs.getValueQuotes("", "TH:Type"));
        Assert.assertNull(attrs.getValueQuotes("TH", "Type"));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forXMLName("", "th:type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forXMLName("th:type")));
        Assert.assertEquals(IElementAttributes.ValueQuotes.DOUBLE, attrs.getValueQuotes(AttributeNames.forXMLName("th", "type")));
        Assert.assertNull(attrs.getValueQuotes(AttributeNames.forXMLName("TH:Type")));
        Assert.assertNull(attrs.getValueQuotes(AttributeNames.forXMLName("TH", "Type")));

        attrs = computeXmlAttributes("<input type='text' \nth:type=\"${thetype}\"/>");
        Assert.assertEquals(1, attrs.getLine("type"));
        Assert.assertEquals(1, attrs.getLine("", "type"));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forXMLName("", "type")));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forXMLName("type")));
        Assert.assertEquals(8, attrs.getCol("type"));
        Assert.assertEquals(8, attrs.getCol("", "type"));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forXMLName("", "type")));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forXMLName("type")));
        Assert.assertEquals(2, attrs.getLine("th:type"));
        Assert.assertEquals(2, attrs.getLine("th", "type"));
        Assert.assertEquals(2, attrs.getLine("", "th:type"));
        Assert.assertEquals(-1, attrs.getLine("TH", "Type"));
        Assert.assertEquals(-1, attrs.getLine("", "TH:Type"));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forXMLName("", "th:type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forXMLName("th", "type")));
        Assert.assertEquals(-1, attrs.getLine(AttributeNames.forXMLName("", "TH:Type")));
        Assert.assertEquals(-1, attrs.getLine(AttributeNames.forXMLName("TH", "Type")));
        Assert.assertEquals(1, attrs.getCol("th:type"));
        Assert.assertEquals(1, attrs.getCol("", "th:type"));
        Assert.assertEquals(1, attrs.getCol("th", "type"));
        Assert.assertEquals(-1, attrs.getCol("TH", "Type"));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forXMLName("", "th:type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forXMLName("th:type")));
        Assert.assertEquals(1, attrs.getCol(AttributeNames.forXMLName("th", "type")));
        Assert.assertEquals(-1, attrs.getCol(AttributeNames.forXMLName("TH:Type")));
        Assert.assertEquals(-1, attrs.getCol(AttributeNames.forXMLName("TH", "Type")));

        attrs = computeXmlAttributes("<input type='text' \na=\"b\" th:type=\"${thetype}\"/>");
        attrs.removeAttribute("a");
        Assert.assertEquals(1, attrs.getLine("type"));
        Assert.assertEquals(1, attrs.getLine("", "type"));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forXMLName("", "type")));
        Assert.assertEquals(1, attrs.getLine(AttributeNames.forXMLName("type")));
        Assert.assertEquals(8, attrs.getCol("type"));
        Assert.assertEquals(8, attrs.getCol("", "type"));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forXMLName("", "type")));
        Assert.assertEquals(8, attrs.getCol(AttributeNames.forXMLName("type")));
        Assert.assertEquals(2, attrs.getLine("th:type"));
        Assert.assertEquals(2, attrs.getLine("th", "type"));
        Assert.assertEquals(2, attrs.getLine("", "th:type"));
        Assert.assertEquals(-1, attrs.getLine("TH", "Type"));
        Assert.assertEquals(-1, attrs.getLine("", "TH:Type"));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forXMLName("", "th:type")));
        Assert.assertEquals(2, attrs.getLine(AttributeNames.forXMLName("th", "type")));
        Assert.assertEquals(-1, attrs.getLine(AttributeNames.forXMLName("", "TH:Type")));
        Assert.assertEquals(-1, attrs.getLine(AttributeNames.forXMLName("TH", "Type")));
        Assert.assertEquals(7, attrs.getCol("th:type"));
        Assert.assertEquals(7, attrs.getCol("", "th:type"));
        Assert.assertEquals(7, attrs.getCol("th", "type"));
        Assert.assertEquals(-1, attrs.getCol("TH", "Type"));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forXMLName("", "th:type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forXMLName("th:type")));
        Assert.assertEquals(7, attrs.getCol(AttributeNames.forXMLName("th", "type")));
        Assert.assertEquals(-1, attrs.getCol(AttributeNames.forXMLName("TH:Type")));
        Assert.assertEquals(-1, attrs.getCol(AttributeNames.forXMLName("TH", "Type")));

        Assert.assertTrue(attrs.hasLocation("th:type"));
        Assert.assertTrue(attrs.hasLocation("", "th:type"));
        Assert.assertTrue(attrs.hasLocation("th", "type"));
        Assert.assertFalse(attrs.hasLocation("TH", "Type"));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forXMLName("", "th:type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forXMLName("th:type")));
        Assert.assertTrue(attrs.hasLocation(AttributeNames.forXMLName("th", "type")));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forXMLName("TH:Type")));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forXMLName("TH", "Type")));
        attrs.setAttribute("one", "two");
        Assert.assertFalse(attrs.hasLocation("one"));
        Assert.assertFalse(attrs.hasLocation("", "one"));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forXMLName("", "one")));
        Assert.assertFalse(attrs.hasLocation(AttributeNames.forXMLName("one")));

    }




    private static IElementAttributes computeHtmlAttributes(final String input) {

        final String templateName = "test";
        final ElementAttributeObtentionTemplateHandler handler = new ElementAttributeObtentionTemplateHandler();

        HTML_PARSER.parse(TEMPLATE_ENGINE_CONFIGURATION, TemplateMode.HTML, new StringResource(templateName, input), handler);

        return handler.elementAttributes;

    }




    private static IElementAttributes computeXmlAttributes(final String input) {

        final String templateName = "test";
        final ElementAttributeObtentionTemplateHandler handler = new ElementAttributeObtentionTemplateHandler();

        XML_PARSER.parse(TEMPLATE_ENGINE_CONFIGURATION, TemplateMode.XML, new StringResource(templateName, input), handler);

        return handler.elementAttributes;

    }




    private static class ElementAttributeObtentionTemplateHandler extends AbstractTemplateHandler {


        IElementAttributes elementAttributes;


        @Override
        public void handleStandaloneElement(final IStandaloneElementTag standaloneElementTag) {
            this.elementAttributes = standaloneElementTag.cloneElementTag().getAttributes();
        }

        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            this.elementAttributes = openElementTag.cloneElementTag().getAttributes();
        }

    }


}
