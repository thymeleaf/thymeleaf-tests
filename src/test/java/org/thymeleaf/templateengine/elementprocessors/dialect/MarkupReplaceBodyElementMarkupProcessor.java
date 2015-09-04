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
import org.thymeleaf.engine.IMarkup;
import org.thymeleaf.engine.Markup;
import org.thymeleaf.processor.element.AbstractAttributeMarkupProcessor;
import org.thymeleaf.templatemode.TemplateMode;

public class MarkupReplaceBodyElementMarkupProcessor extends AbstractAttributeMarkupProcessor {

    public static final String ATTR_NAME = "replacebody";

    public static final String REPLACEMENT = "<p>This is a <span th:text=\"replacement\">prototype</span></p>";


    public MarkupReplaceBodyElementMarkupProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, 1000, true);
    }



    @Override
    protected void doProcess(final ITemplateProcessingContext processingContext, final Markup markup,
                             final AttributeName attributeName, final String attributeValue,
                             final String attributeTemplateName, final int attributeLine, final int attributeCol) {

        final IMarkup replacementMarkup = processingContext.getMarkupFactory().parseAsMarkup(REPLACEMENT);


        for (int i = markup.size() - 2; i > 0; i--) {
            markup.remove(i);
        }

        markup.insertMarkup(1, replacementMarkup);

    }

}
