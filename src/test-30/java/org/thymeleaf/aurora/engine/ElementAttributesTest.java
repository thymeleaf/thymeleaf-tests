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

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.aurora.context.ITemplateEngineContext;
import org.thymeleaf.aurora.context.TemplateEngineContext;
import org.thymeleaf.aurora.parser.HtmlTemplateParser;
import org.thymeleaf.aurora.resource.StringResource;


public final class ElementAttributesTest {

    private static final HtmlTemplateParser PARSER = new HtmlTemplateParser(2, 4096);
    private static final ITemplateEngineContext TEMPLATE_ENGINE_CONTEXT = new TemplateEngineContext();




    @Test
    public void testElementAttributesAttrManagement() {

        HtmlElementAttributes attrs;

        attrs = computeAttributes("<input>");
        Assert.assertEquals("", attrs.toString());

        attrs = computeAttributes("<input type=\"text\">");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals("", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals("    ", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" ba", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba >");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba ", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba ", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\" ", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba >");
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

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba=twenty >");
        attrs.setAttribute("ba", "thirty");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba=thirty ", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"value='hello!!!' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' ", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"value='hello!!!' name='one' >");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one' ", attrs.toString());
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!' name='one' ", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"value='hello!!!' name='one'>");
        Assert.assertEquals(" type=\"text\"value='hello!!!' name='one'", attrs.toString());
        attrs.removeAttribute("name");
        Assert.assertEquals(" type=\"text\"value='hello!!!'", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", null);
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("value", null);
        Assert.assertEquals(" type=\"text\"   value    ba= s", attrs.toString());
        attrs.removeAttribute("value");
        Assert.assertEquals(" type=\"text\"   ba= s", attrs.toString());
        attrs.setAttribute("type", null, ElementAttributes.ValueQuotes.SINGLE);
        Assert.assertEquals(" type   ba= s", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
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
        Assert.assertEquals(" value placeholder=\"a\" name=\"\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("type", null);
        Assert.assertEquals(" value='hello!!!'    ba= s type", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("title", null);
        Assert.assertEquals(" value='hello!!!'    ba= s title", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        attrs.removeAttribute("type");
        Assert.assertEquals(" value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("title", "");
        Assert.assertEquals(" value='hello!!!'    ba= s title=\"\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
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

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("value", "one", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals(" type=\"text\"   value=one    ba= s", attrs.toString());
        attrs.setAttribute("value", "");
        Assert.assertEquals(" type=\"text\"   value=\"\"    ba= s", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", "");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= \"\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba= s>");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= s", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'    ba= one", attrs.toString());
        attrs.removeAttribute("ba");
        Assert.assertEquals(" type=\"text\"   value='hello!!!'", attrs.toString());
        attrs.setAttribute("ba", "one");
        Assert.assertEquals(" type=\"text\"   value='hello!!!' ba=\"one\"", attrs.toString());

        attrs = computeAttributes("<input type=\"text\"   value='hello!!!'    ba>");
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

        attrs = computeAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"", attrs.toString());

        attrs = computeAttributes("<input>");
        Assert.assertEquals("", attrs.toString());
        attrs.setAttribute("a", "one", ElementAttributes.ValueQuotes.NONE);
        Assert.assertEquals(" a=one", attrs.toString());

        attrs = computeAttributes("<input   >");
        Assert.assertEquals("   ", attrs.toString());
        attrs.setAttribute("a", "one");
        Assert.assertEquals(" a=\"one\"   ", attrs.toString());
        attrs.setAttribute("b", "two");
        Assert.assertEquals(" a=\"one\" b=\"two\"   ", attrs.toString());

        attrs = computeAttributes("<input\none  />");
        Assert.assertEquals("\none  ", attrs.toString());
        attrs.setAttribute("a", "two");
        Assert.assertEquals("\none a=\"two\"  ", attrs.toString());

        attrs = computeAttributes("<input\none two/>");
        Assert.assertEquals("\none two", attrs.toString());
        attrs.removeAttribute("one");
        Assert.assertEquals("\ntwo", attrs.toString());

    }




    private static HtmlElementAttributes computeAttributes(final String input) {

        final String templateName = "test";
        final StringWriter writer = new StringWriter();
        final ElementAttributeObtentionTemplateHandler handler = new ElementAttributeObtentionTemplateHandler();

        PARSER.parse(TEMPLATE_ENGINE_CONTEXT, new StringResource(templateName, input), handler);

        return (HtmlElementAttributes) handler.elementAttributes;

    }




    private static class ElementAttributeObtentionTemplateHandler extends AbstractTemplateHandler {


        ElementAttributes elementAttributes;


        @Override
        public void handleStandaloneElement(final ElementDefinition elementDefinition, final String elementName, final ElementAttributes elementAttributes, final boolean minimized, final int line, final int col) {
            this.elementAttributes = elementAttributes.cloneElementAttributes();
        }

        @Override
        public void handleOpenElement(final ElementDefinition elementDefinition, final String elementName, final ElementAttributes elementAttributes, final int line, final int col) {
            this.elementAttributes = elementAttributes.cloneElementAttributes();
        }

    }


}
