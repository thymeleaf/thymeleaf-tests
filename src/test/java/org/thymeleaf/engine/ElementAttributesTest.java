/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.TestTemplateEngineConfigurationBuilder;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateparser.markup.HTMLTemplateParser;
import org.thymeleaf.templateparser.markup.XMLTemplateParser;
import org.thymeleaf.templateresource.StringTemplateResource;


public final class ElementAttributesTest {

    private static final HTMLTemplateParser HTML_PARSER = new HTMLTemplateParser(2, 4096);
    private static final XMLTemplateParser XML_PARSER = new XMLTemplateParser(2, 4096);
    private static final IEngineConfiguration TEMPLATE_ENGINE_CONFIGURATION = TestTemplateEngineConfigurationBuilder.build();




    @Test
    public void testHtmlElementAttributesAttrManagement() {

        Attributes attrs;
        
        AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);
        
        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\">");
        Assert.assertEquals("[type]", attrs.getAttributeMap().keySet().toString());
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'>");
        Assert.assertEquals("[type, value]", attrs.getAttributeMap().keySet().toString());
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!'", attrs.toString());
        Assert.assertEquals("[value]", attrs.getAttributeMap().keySet().toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "value");
        Assert.assertEquals("", attrs.toString());
        Assert.assertEquals("[]", attrs.getAttributeMap().keySet().toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, null, "type");
        Assert.assertEquals(" value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, null, "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "th", "type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "th", "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "TH", "TYPE");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "tH", "Value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "data-th-type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "data-th-value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forHTMLName("th:type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forHTMLName("th:value"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    >");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forHTMLName("th", "type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forHTMLName("TH", "VALUE"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "ba");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" ba", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "value");
        Assert.assertEquals(" type=\"text\"   ba ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "ba");
        Assert.assertEquals(" type=\"text\" ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", "bye! :(", null);
        Assert.assertEquals(" type=\"text\"   value='bye! :('    ba ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "type", "one", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "two", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=\"two\" ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "three", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='three' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "four", AttributeValueQuotes.NONE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=four ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "five", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=five ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", null, null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "six", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba=\"six\" ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba=twenty >");
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "thirty", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=thirty ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!' ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' name='one' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!' name='one' ", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"value='hello!!!' name='one'>");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "name");
        Assert.assertEquals(" type=\"text\"value='hello!!!'", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", null, null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", null, null);
        Assert.assertEquals(" type=\"text\"   value    ba= s", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "value");
        Assert.assertEquals(" type=\"text\"   ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "type", null, AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type   ba= s", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals("[type, value, ba]", attrs.getAttributeMap().keySet().toString());
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = new Attributes(null, null);
        Assert.assertEquals("", attrs.toString());
        attrs = new Attributes(null, null);
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", "onename", null);
        Assert.assertEquals(" name=\"onename\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", "val", null);
        Assert.assertEquals(" name=\"onename\" value=\"val\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "placeholder", null, null);
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "placeholder", "a", null);
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", null, null);
        Assert.assertEquals(" name=\"onename\" value placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", "", null);
        Assert.assertEquals(" name=\"\" value placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", "", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" name='' value placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", null, null);
        Assert.assertEquals(" name value placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", "", null);
        Assert.assertEquals(" name=\"\" value placeholder=\"a\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "name");
        Assert.assertEquals(" value placeholder=\"a\"", attrs.toString());
        Assert.assertEquals(2, attrs.attributes.length);
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "name", "", null);
        Assert.assertEquals("[value, placeholder, name]", attrs.getAttributeMap().keySet().toString());
        Assert.assertEquals(" value placeholder=\"a\" name=\"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "type", null, null);
        Assert.assertEquals(" value='hello!!!'    ba= s type", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "title", null, null);
        Assert.assertEquals(" value='hello!!!'    ba= s title", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "title", "", null);
        Assert.assertEquals(" value='hello!!!'    ba= s title=\"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "title", "", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" value='hello!!!'    ba= s title=''", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "title", "", AttributeValueQuotes.NONE);
        Assert.assertEquals(" value='hello!!!'    ba= s title=\"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", "one", AttributeValueQuotes.NONE);
        Assert.assertEquals(" type=\"text\"   value=one    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "value", "", null);
        Assert.assertEquals(" type=\"text\"   value=\"\"    ba= s", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= \"\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "one", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= one", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "one", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"one\"", attrs.toString());

        attrs = computeHtmlAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "one", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='one'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "ba", "two", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "be", "three", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "bi", "four", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "bo", "five", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "bu", "six", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\" bu=\"six\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "be");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "bu");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "bi", null, null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "bi");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());

        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "a", "one", null);
        Assert.assertEquals(" a=\"one\"", attrs.toString());

        attrs = computeHtmlAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "a", "one", AttributeValueQuotes.NONE);
        Assert.assertEquals(" a=one", attrs.toString());

        attrs = computeHtmlAttributes("<input   >");
        Assert.assertEquals("   ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "a", "one", null);
        Assert.assertEquals(" a=\"one\"   ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "b", "two", null);
        Assert.assertEquals(" a=\"one\" b=\"two\"   ", attrs.toString());

        attrs = computeHtmlAttributes("<input\none  />");
        Assert.assertEquals("\none  ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "a", "two", null);
        Assert.assertEquals("\none a=\"two\"  ", attrs.toString());

        attrs = computeHtmlAttributes("<input\none two/>");
        Assert.assertEquals("\none two", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.HTML, "one");
        Assert.assertEquals("\ntwo", attrs.toString());

    }




    @Test
    public void testXmlElementAttributesAttrManagement() {

        Attributes attrs;

        AttributeDefinitions attributeDefinitions = new AttributeDefinitions(Collections.EMPTY_MAP);

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"/>");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "value");
        Assert.assertEquals("", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, null, "type");
        Assert.assertEquals(" value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, null, "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "th", "type");
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "th", "value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "TH", "TYPE");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "tH", "Value");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "data-th-type");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "data-th-value");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forXMLName("th:type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forXMLName("th:value"));
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeXmlAttributes("<input th:type=\"text\"   th:value='hello!!!'    />");
        Assert.assertEquals("[th:type, th:value]", attrs.getAttributeMap().keySet().toString());
        Assert.assertEquals(" th:type=\"text\"   th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forXMLName("th", "type"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());
        attrs = attrs.removeAttribute(AttributeNames.forXMLName("TH", "VALUE"));
        Assert.assertEquals(" th:value='hello!!!'    ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=''/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=''", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "value");
        Assert.assertEquals(" type=\"text\"   ba=''", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "ba");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=''/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=''", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "value");
        Assert.assertEquals(" type=\"text\"   ba=''", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" ba=''", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='' />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "value");
        Assert.assertEquals(" type=\"text\"   ba='' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "ba");
        Assert.assertEquals(" type=\"text\" ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='' />");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "value", "bye! :(", null);
        Assert.assertEquals(" type=\"text\"   value='bye! :('    ba='' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "type", "one", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "two", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='two' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "three", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='three' ", attrs.toString());

        try {
            attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "four", AttributeValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", null, AttributeValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", null, null);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "five", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='five' ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "six", null);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='six' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba='twenty' />");
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "thirty", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='thirty' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' />");
        Assert.assertEquals(" type=\"text\"value='hello!!!' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' name='one' />");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one' ", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!' name='one' ", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"value='hello!!!' name='one'/>");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "name");
        Assert.assertEquals(" type=\"text\"value='hello!!!'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "value");
        Assert.assertEquals(" type=\"text\"   ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs = new Attributes(null, null);
        Assert.assertEquals("", attrs.toString());
        attrs = new Attributes(null, null);
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "name", "onename", null);
        Assert.assertEquals(" name=\"onename\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "value", "val", null);
        Assert.assertEquals(" name=\"onename\" value=\"val\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "placeholder", "a", null);
        Assert.assertEquals(" name=\"onename\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "name", "", null);
        Assert.assertEquals(" name=\"\" value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "name", "", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" name='' value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "name", "", null);
        Assert.assertEquals(" name='' value=\"val\" placeholder=\"a\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "name");
        Assert.assertEquals(" value=\"val\" placeholder=\"a\"", attrs.toString());
        Assert.assertEquals(2, attrs.attributes.length);
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "name", "", null);
        Assert.assertEquals(" value=\"val\" placeholder=\"a\" name=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "type", "", null);
        Assert.assertEquals(" value='hello!!!'    ba= 's' type=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "title", " ", null);
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=\" \"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "title", "", null);
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=\"\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "title", "", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" value='hello!!!'    ba= 's' title=''", attrs.toString());
        try {
            // Shouldn't be able to set an empty-string value with no quotes
            attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "title", "", AttributeValueQuotes.NONE);
            Assert.assertTrue(false);
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "value", "one", AttributeValueQuotes.DOUBLE);
        Assert.assertEquals(" type=\"text\"   value=\"one\"    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "value", "", null);
        Assert.assertEquals(" type=\"text\"   value=\"\"    ba= 's'", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= ''", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba= 's'/>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 's'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "one", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= 'one'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, null, "ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "one", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"one\"", attrs.toString());

        attrs = computeXmlAttributes("<input type=\"text\"   value='hello!!!'    ba=\"\"/>");
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "one", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba='one'", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "ba", "two", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "be", "three", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "bi", "four", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "bo", "five", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "bu", "six", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" be=\"three\" bi=\"four\" bo=\"five\" bu=\"six\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "be");
        attrs = attrs.removeAttribute(TemplateMode.XML, "bu");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bi=\"four\" bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "bi");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertEquals(" value='hello!!!' ba=\"two\" bo=\"five\"", attrs.toString());

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "a", "one", null);
        Assert.assertEquals(" a=\"one\"", attrs.toString());

        attrs = computeXmlAttributes("<input/>");
        Assert.assertEquals("", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "a", "one", AttributeValueQuotes.SINGLE);
        Assert.assertEquals(" a='one'", attrs.toString());

        attrs = computeXmlAttributes("<input   />");
        Assert.assertEquals("   ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "a", "one", null);
        Assert.assertEquals(" a=\"one\"   ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "b", "two", null);
        Assert.assertEquals(" a=\"one\" b=\"two\"   ", attrs.toString());

        attrs = computeXmlAttributes("<input\none=\"\"  />");
        Assert.assertEquals("\none=\"\"  ", attrs.toString());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "a", "two", null);
        Assert.assertEquals("\none=\"\" a=\"two\"  ", attrs.toString());

        attrs = computeXmlAttributes("<input\none=\"\" two=\"\"/>");
        Assert.assertEquals("\none=\"\" two=\"\"", attrs.toString());
        attrs = attrs.removeAttribute(TemplateMode.XML, "one");
        Assert.assertEquals("\ntwo=\"\"", attrs.toString());

    }







    @Test
    public void testHtmlElementAttributesAttrObtention() {

        Attributes attrs;
        final AttributeDefinitions attributeDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getAttributeDefinitions();

        attrs = computeHtmlAttributes("<input>");
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertNull(attrs.getAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.attributes);

        attrs = computeHtmlAttributes("<input type=\"text\">");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forHTMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertEquals(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertEquals(1, attrs.attributes.length);

        attrs = computeHtmlAttributes("<input type='text'>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forHTMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertEquals(1, attrs.attributes.length);

        attrs = computeHtmlAttributes("<input type=text>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forHTMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertEquals(1, attrs.attributes.length);

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\">");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forHTMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th", "type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH", "Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("th:type")).getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH", "Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("th:type")).definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).definition);
        Assert.assertEquals(2, attrs.attributes.length);

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one=auth>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.HTML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forHTMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(TemplateMode.HTML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("type"), attrs.getAttribute(AttributeNames.forHTMLName("type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th", "type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH", "Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("th:type")).getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH", "Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("th:type")).definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "sec", "one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "SEC", "One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, null, "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, null, "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "", "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "", "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forHTMLName("sec:one")).getValue());
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forHTMLName("SEC:One")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "sec", "one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "SEC", "One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, null, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, null, "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "", "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "", "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(AttributeNames.forHTMLName("sec:one")).definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(AttributeNames.forHTMLName("SEC:One")).definition);
        Assert.assertEquals(3, attrs.attributes.length);

        attrs = computeHtmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one=auth>");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "type");
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertNull(attrs.getAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "th", "type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "TH", "Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "th:type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("th:type")).getValue());
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "TH", "Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, null, "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("th:type")).definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("th:type"), attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "sec", "one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "SEC", "One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, null, "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, null, "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "", "sec:one").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.HTML, "", "SEC:One").getValue());
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forHTMLName("sec:one")).getValue());
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forHTMLName("SEC:One")).getValue());
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "sec", "one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "SEC", "One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, null, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, null, "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "", "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(TemplateMode.HTML, "", "SEC:One").definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(AttributeNames.forHTMLName("sec:one")).definition);
        Assert.assertSame(attributeDefinitions.forHTMLName("sec:one"), attrs.getAttribute(AttributeNames.forHTMLName("SEC:One")).definition);
        Assert.assertEquals(2, attrs.attributes.length);


        attrs = new Attributes(null, null);
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("type")));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.HTML, "", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forHTMLName("TH:TYPE")));
        Assert.assertNull(attrs.attributes);

        attrs = computeHtmlAttributes("<input type=text th:type=\"${thetype}\">");
        Assert.assertEquals(AttributeValueQuotes.NONE, attrs.getAttribute(TemplateMode.HTML, "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.NONE, attrs.getAttribute(TemplateMode.HTML, "", "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.NONE, attrs.getAttribute(AttributeNames.forHTMLName("", "type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.NONE, attrs.getAttribute(AttributeNames.forHTMLName("type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.HTML, "th:type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.HTML, "", "th:type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.HTML, "th", "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.HTML, "TH", "Type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forHTMLName("th:type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).valueQuotes);

        attrs = computeHtmlAttributes("<input type='text' \nth:type=\"${thetype}\">");
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "type").line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "", "type").line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("", "type")).line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("type")).line);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.HTML, "type").col);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.HTML, "", "type").col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forHTMLName("", "type")).col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forHTMLName("type")).col);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "th", "type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "", "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "TH", "Type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("", "TH:Type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "th:type").col);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "", "th:type").col);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "th", "type").col);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "TH", "Type").col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("th:type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).col);

        attrs = computeHtmlAttributes("<input type='text' \na=\"b\" th:type=\"${thetype}\">");
        attrs = attrs.removeAttribute(TemplateMode.HTML, "a");
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "type").line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "", "type").line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("", "type")).line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forHTMLName("type")).line);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.HTML, "type").col);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.HTML, "", "type").col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forHTMLName("", "type")).col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forHTMLName("type")).col);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "th", "type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "", "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "TH", "Type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.HTML, "", "TH:Type").line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("", "TH:Type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).line);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.HTML, "th:type").col);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.HTML, "", "th:type").col);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.HTML, "th", "type").col);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.HTML, "TH", "Type").col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forHTMLName("th:type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).col);

        Assert.assertTrue(attrs.getAttribute(TemplateMode.HTML, "th:type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(TemplateMode.HTML, "", "th:type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(TemplateMode.HTML, "th", "type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(TemplateMode.HTML, "TH", "Type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forHTMLName("", "th:type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forHTMLName("th:type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forHTMLName("th", "type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forHTMLName("TH:Type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forHTMLName("TH", "Type")).hasLocation());
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.HTML, null, "one", "two", null);
        Assert.assertFalse(attrs.getAttribute(TemplateMode.HTML, "one").hasLocation());
        Assert.assertFalse(attrs.getAttribute(TemplateMode.HTML, "", "one").hasLocation());
        Assert.assertFalse(attrs.getAttribute(AttributeNames.forHTMLName("", "one")).hasLocation());
        Assert.assertFalse(attrs.getAttribute(AttributeNames.forHTMLName("one")).hasLocation());

    }








    @Test
    public void testXmlElementAttributesAttrObtention() {

        Attributes attrs;
        final AttributeDefinitions attributeDefinitions = TEMPLATE_ENGINE_CONFIGURATION.getAttributeDefinitions();

        attrs = computeXmlAttributes("<input/>");
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "type"));
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.attributes);

        attrs = computeXmlAttributes("<input type=\"text\"/>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forXMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(AttributeNames.forXMLName("type")).definition);
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "type").definition);
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, null, "type").definition);
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "", "type").definition);
        Assert.assertEquals(attributeDefinitions.forXMLName("type"), attrs.getAttribute(AttributeNames.forXMLName("type")).definition);
        Assert.assertEquals(1, attrs.attributes.length);

        attrs = computeXmlAttributes("<input type='text'/>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forXMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(AttributeNames.forXMLName("type")).definition);
        Assert.assertEquals(1, attrs.attributes.length);

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\"/>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forXMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(AttributeNames.forXMLName("type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th", "type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, null, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "", "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forXMLName("th:type")).getValue());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(AttributeNames.forXMLName("th:type")).definition);
        Assert.assertEquals(2, attrs.attributes.length);

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one='auth'/>");
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, null, "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(TemplateMode.XML, "", "type").getValue());
        Assert.assertEquals("text", attrs.getAttribute(AttributeNames.forXMLName("type")).getValue());
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, null, "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(TemplateMode.XML, "", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("type"), attrs.getAttribute(AttributeNames.forXMLName("type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th", "type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, null, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "", "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forXMLName("th:type")).getValue());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(AttributeNames.forXMLName("th:type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "sec", "one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "SEC", "One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, null, "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "", "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forXMLName("sec:one")).getValue());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "sec", "one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, null, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "", "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(AttributeNames.forXMLName("sec:one")).definition);
        Assert.assertEquals(3, attrs.attributes.length);

        attrs = computeXmlAttributes("<input type=\"text\" th:type=\"${thetype}\" sec:one='auth'/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "type");
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "type"));
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("type")));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "th", "type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, null, "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(TemplateMode.XML, "", "th:type").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertEquals("${thetype}", attrs.getAttribute(AttributeNames.forXMLName("th:type")).getValue());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("TH:Type")));
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "th", "type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, null, "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(TemplateMode.XML, "", "th:type").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("th:type"), attrs.getAttribute(AttributeNames.forXMLName("th:type")).definition);
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertTrue(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "sec", "one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "SEC", "One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, null, "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, null, "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(TemplateMode.XML, "", "sec:one").getValue());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "SEC:One"));
        Assert.assertEquals("auth", attrs.getAttribute(AttributeNames.forXMLName("sec:one")).getValue());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("SEC:One")));
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "sec", "one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, null, "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(TemplateMode.XML, "", "sec:one").definition);
        Assert.assertSame(attributeDefinitions.forXMLName("sec:one"), attrs.getAttribute(AttributeNames.forXMLName("sec:one")).definition);
        Assert.assertEquals(2, attrs.attributes.length);


        attrs = new Attributes(null, null);
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("type")));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "th", "type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, null, "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "th:type"));
        Assert.assertFalse(attrs.hasAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("th:type")));
        Assert.assertFalse(attrs.hasAttribute(AttributeNames.forXMLName("TH:TYPE")));
        Assert.assertNull(attrs.attributes);

        attrs = computeXmlAttributes("<input type='text' th:type=\"${thetype}\"/>");
        Assert.assertEquals(AttributeValueQuotes.SINGLE, attrs.getAttribute(TemplateMode.XML, "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.SINGLE, attrs.getAttribute(TemplateMode.XML, "", "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.SINGLE, attrs.getAttribute(AttributeNames.forXMLName("", "type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.SINGLE, attrs.getAttribute(AttributeNames.forXMLName("type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.XML, "th:type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.XML, "", "th:type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(TemplateMode.XML, "th", "type").valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forXMLName("th:type")).valueQuotes);
        Assert.assertEquals(AttributeValueQuotes.DOUBLE, attrs.getAttribute(AttributeNames.forXMLName("th", "type")).valueQuotes);

        attrs = computeXmlAttributes("<input type='text' \nth:type=\"${thetype}\"/>");
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.HTML, "type").line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "", "type").line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("", "type")).line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("type")).line);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.XML, "type").col);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.XML, "", "type").col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forXMLName("", "type")).col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forXMLName("type")).col);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "th", "type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "", "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forXMLName("th", "type")).line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "th:type").col);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "", "th:type").col);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "th", "type").col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("th:type")).col);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("th", "type")).col);

        attrs = computeXmlAttributes("<input type='text' \na=\"b\" th:type=\"${thetype}\"/>");
        attrs = attrs.removeAttribute(TemplateMode.XML, "a");
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "type").line);
        Assert.assertEquals(1, attrs.getAttribute(TemplateMode.XML, "", "type").line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("", "type")).line);
        Assert.assertEquals(1, attrs.getAttribute(AttributeNames.forXMLName("type")).line);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.XML, "type").col);
        Assert.assertEquals(8, attrs.getAttribute(TemplateMode.XML, "", "type").col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forXMLName("", "type")).col);
        Assert.assertEquals(8, attrs.getAttribute(AttributeNames.forXMLName("type")).col);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "th:type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "th", "type").line);
        Assert.assertEquals(2, attrs.getAttribute(TemplateMode.XML, "", "th:type").line);
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "", "TH:Type"));
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).line);
        Assert.assertEquals(2, attrs.getAttribute(AttributeNames.forXMLName("th", "type")).line);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.XML, "th:type").col);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.XML, "", "th:type").col);
        Assert.assertEquals(7, attrs.getAttribute(TemplateMode.XML, "th", "type").col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forXMLName("th:type")).col);
        Assert.assertEquals(7, attrs.getAttribute(AttributeNames.forXMLName("th", "type")).col);

        Assert.assertTrue(attrs.getAttribute(TemplateMode.XML, "th:type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(TemplateMode.XML, "", "th:type").hasLocation());
        Assert.assertTrue(attrs.getAttribute(TemplateMode.XML, "th", "type").hasLocation());
        Assert.assertNull(attrs.getAttribute(TemplateMode.XML, "TH", "Type"));
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forXMLName("", "th:type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forXMLName("th:type")).hasLocation());
        Assert.assertTrue(attrs.getAttribute(AttributeNames.forXMLName("th", "type")).hasLocation());
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("TH:Type")));
        Assert.assertNull(attrs.getAttribute(AttributeNames.forXMLName("TH", "Type")));
        attrs = attrs.setAttribute(attributeDefinitions, TemplateMode.XML, null, "one", "two", null);
        Assert.assertFalse(attrs.getAttribute(TemplateMode.XML, "one").hasLocation());
        Assert.assertFalse(attrs.getAttribute(TemplateMode.XML, "", "one").hasLocation());
        Assert.assertFalse(attrs.getAttribute(AttributeNames.forXMLName("", "one")).hasLocation());
        Assert.assertFalse(attrs.getAttribute(AttributeNames.forXMLName("one")).hasLocation());

    }




    private static Attributes computeHtmlAttributes(final String input) {

        final ElementAttributeObtentionTemplateHandler handler = new ElementAttributeObtentionTemplateHandler();

        HTML_PARSER.parseStandalone(TEMPLATE_ENGINE_CONFIGURATION, "test", "test", null, new StringTemplateResource(input), TemplateMode.HTML, false, handler);

        return (handler.attributes != null ? handler.attributes : Attributes.EMPTY_ATTRIBUTES);

    }




    private static Attributes computeXmlAttributes(final String input) {

        final ElementAttributeObtentionTemplateHandler handler = new ElementAttributeObtentionTemplateHandler();

        XML_PARSER.parseStandalone(TEMPLATE_ENGINE_CONFIGURATION, "test", "test", null, new StringTemplateResource(input), TemplateMode.XML, false, handler);

        return (handler.attributes != null ? handler.attributes : Attributes.EMPTY_ATTRIBUTES);

    }




    private static class ElementAttributeObtentionTemplateHandler extends AbstractTemplateHandler {


        Attributes attributes;


        @Override
        public void handleStandaloneElement(final IStandaloneElementTag standaloneElementTag) {
            this.attributes = ((AbstractProcessableElementTag)standaloneElementTag).attributes;
        }

        @Override
        public void handleOpenElement(final IOpenElementTag openElementTag) {
            this.attributes = ((AbstractProcessableElementTag)openElementTag).attributes;
        }

    }


}
