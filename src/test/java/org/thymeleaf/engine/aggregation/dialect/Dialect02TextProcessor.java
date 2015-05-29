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
package org.thymeleaf.engine.aggregation.dialect;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.AbstractTextNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.TextNodeProcessorMatcher;
import org.thymeleaf.processor.text.AbstractTextNodeProcessor;

public class Dialect02TextProcessor extends AbstractTextNodeProcessor {


    public Dialect02TextProcessor() {
        super(new TextNodeProcessorMatcher());
    }

    @Override
    public int getPrecedence() {
        return 200;
    }



    @Override
    protected ProcessorResult processTextNode(final Arguments arguments, final AbstractTextNode textNode) {
        textNode.setContent(textNode.getContent() + "[02]");
        return ProcessorResult.OK;
    }
}
