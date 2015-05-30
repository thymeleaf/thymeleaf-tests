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
package org.thymeleaf.templateengine.aggregation.dialect;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.AbstractTextNode;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.AbstractProcessor;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.TextNodeProcessorMatcher;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.processor.text.AbstractTextNodeProcessor;

public class Dialect01TextProcessor extends AbstractTextNodeProcessor {


    public Dialect01TextProcessor() {
        super(new TextNodeProcessorMatcher());
    }

    @Override
    public int getPrecedence() {
        return 100;
    }



    @Override
    protected ProcessorResult processTextNode(final Arguments arguments, final AbstractTextNode textNode) {
        textNode.setContent(textNode.getContent() + "[01]");
        return ProcessorResult.OK;
    }
}
