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
import org.thymeleaf.engine.IMarkup;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.processor.element.AbstractElementMarkupProcessor;
import org.thymeleaf.templatemode.TemplateMode;

public class AggBadAggregatorElementMarkupProcessor extends AbstractElementMarkupProcessor {

    public static final String ATTR_NAME = "badaggregate";


    public AggBadAggregatorElementMarkupProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, 1000);
    }



    @Override
    protected IMarkup doProcess(final ITemplateProcessingContext processingContext, final IMarkup markup) {

        /*
         * This should raise an exception, as the event object returned by markup.get(0) should be IMMUTABLE
         */
        final String markupStr = markup.renderMarkup();
        ((IOpenElementTag) markup.get(0)).getAttributes().setAttribute("agg", markupStr);
        ((IOpenElementTag) markup.get(0)).getAttributes().removeAttribute(getMatchingAttributeName().getMatchingAttributeName());
        return markup;

    }

}
