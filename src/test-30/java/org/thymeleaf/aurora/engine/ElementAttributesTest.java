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
    public void testHTMLElementAttributes() {

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
        attrs.setAttribute("ba", "three", ElementAttribute.ElementAttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type=\"one\"   value='bye! :('    ba='three' ", attrs.toString());
        attrs.setAttribute("ba", "four", ElementAttribute.ElementAttributeValueQuotes.NONE);
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
        attrs.setAttribute("type", null, ElementAttribute.ElementAttributeValueQuotes.SINGLE);
        Assert.assertEquals(" type   ba= s", attrs.toString());

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
