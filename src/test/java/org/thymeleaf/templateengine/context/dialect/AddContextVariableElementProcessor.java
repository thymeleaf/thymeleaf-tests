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
package org.thymeleaf.templateengine.context.dialect;

import org.thymeleaf.context.ILocalVariableAwareVariablesMap;
import org.thymeleaf.context.ITemplateProcessingContext;
import org.thymeleaf.context.IWebVariablesMap;
import org.thymeleaf.engine.IElementStructureHandler;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

public class AddContextVariableElementProcessor extends AbstractElementTagProcessor {

    
    public AddContextVariableElementProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, "add-context-variable", true, null, false, 100);
    }



    @Override
    protected void doProcess(
            final ITemplateProcessingContext processingContext,
            final IProcessableElementTag tag,
            final String tagTemplateName, final int tagLine, final int tagCol,
            final IElementStructureHandler structureHandler) {

        final IWebVariablesMap variablesMap = (IWebVariablesMap) processingContext.getVariables();
        final ILocalVariableAwareVariablesMap localVariableAwareVariablesMap = (ILocalVariableAwareVariablesMap) variablesMap;

        localVariableAwareVariablesMap.put("newvar0", "LocalVariablesNewVar0");
        localVariableAwareVariablesMap.put("newvar1", "LocalVariablesNewVar1");

        variablesMap.getRequest().setAttribute("newvar2", "RequestAttributesNewVar2");
        variablesMap.getRequest().setAttribute("newvar3", "RequestAttributesNewVar3");

        variablesMap.getServletContext().setAttribute("newvar4", "ApplicationAttributesNewVar4");
        variablesMap.getServletContext().setAttribute("newvar5", "ApplicationAttributesNewVar5");

        variablesMap.getSession().setAttribute("newvar6", "SessionAttributesNewVar6");
        variablesMap.getSession().setAttribute("newvar7", "SessionAttributesNewVar7");

        structureHandler.setLocalVariable("one", "one");
        structureHandler.removeElement();

    }


}
