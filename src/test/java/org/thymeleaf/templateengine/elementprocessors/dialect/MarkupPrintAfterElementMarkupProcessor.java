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
package org.thymeleaf.templateengine.elementprocessors.dialect;

import org.thymeleaf.context.ITemplateProcessingContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.Markup;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.processor.element.AbstractAttributeMarkupProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class MarkupPrintAfterElementMarkupProcessor extends AbstractAttributeMarkupProcessor {

    public static final String ATTR_NAME = "printafter";


    public MarkupPrintAfterElementMarkupProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, 1500, true);
    }



    @Override
    protected void doProcess(final ITemplateProcessingContext processingContext, final Markup markup,
                             final AttributeName attributeName, final String attributeValue,
                             final String attributeTemplateName, final int attributeLine, final int attributeCol) {

        final String markupStr = HtmlEscape.escapeHtml4Xml(markup.renderMarkup().replaceAll("\\r\\n|\\r|\\n", "\\\\n"));
        ((IOpenElementTag)markup.get(0)).getAttributes().setAttribute("aggafter", markupStr);

    }

}
