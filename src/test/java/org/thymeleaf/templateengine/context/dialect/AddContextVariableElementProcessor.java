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
package org.thymeleaf.templateengine.context.dialect;

import org.thymeleaf.context.IEngineContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IJavaxWebContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class AddContextVariableElementProcessor extends AbstractElementTagProcessor {

    
    public AddContextVariableElementProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, "add-context-variable", true, null, false, 100);
    }



    @Override
    protected void doProcess(
            final ITemplateContext processingContext,
            final IProcessableElementTag tag,
            final IElementTagStructureHandler structureHandler) {

        final IJavaxWebContext webContext = (IJavaxWebContext) processingContext;
        final IEngineContext engineContext = (IEngineContext) webContext;

        engineContext.setVariable("newvar0", "LocalVariablesNewVar0");
        engineContext.setVariable("newvar1", "LocalVariablesNewVar1");

        webContext.getRequest().setAttribute("newvar2", "RequestAttributesNewVar2");
        webContext.getRequest().setAttribute("newvar3", "RequestAttributesNewVar3");

        webContext.getServletContext().setAttribute("newvar4", "ApplicationAttributesNewVar4");
        webContext.getServletContext().setAttribute("newvar5", "ApplicationAttributesNewVar5");

        webContext.getSession().setAttribute("newvar6", "SessionAttributesNewVar6");
        webContext.getSession().setAttribute("newvar7", "SessionAttributesNewVar7");

        structureHandler.setLocalVariable("one", "one");
        structureHandler.removeElement();

    }


}
