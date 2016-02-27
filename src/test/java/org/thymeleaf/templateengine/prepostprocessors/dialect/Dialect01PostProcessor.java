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
package org.thymeleaf.templateengine.prepostprocessors.dialect;

import org.thymeleaf.engine.AbstractTemplateHandler;
import org.thymeleaf.model.ICDATASection;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IComment;
import org.thymeleaf.model.IDocType;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessingInstruction;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.model.ITemplateEnd;
import org.thymeleaf.model.ITemplateStart;
import org.thymeleaf.model.IText;
import org.thymeleaf.model.IXMLDeclaration;

public class Dialect01PostProcessor extends AbstractTemplateHandler {

    private int processingInstructions = 0;
    private int openElementTags = 0;
    private int standaloneElementTags = 0;
    private int texts = 0;
    private int comments = 0;
    private int cdataSections = 0;
    private int docTypes = 0;
    private int xmlDeclarations = 0;



    public Dialect01PostProcessor() {
        super();
    }


    @Override
    public void handleProcessingInstruction(final IProcessingInstruction processingInstruction) {
        final String content = nullToEmpty(processingInstruction.getContent());
        processingInstruction.setContent(content + "(post:" + this.processingInstructions++ + ")");
        super.handleProcessingInstruction(processingInstruction);
    }

    @Override
    public void handleCloseElement(final ICloseElementTag closeElementTag) {
        // Nothing to be done here
        super.handleCloseElement(closeElementTag);
    }

    @Override
    public void handleOpenElement(final IOpenElementTag openElementTag) {
        openElementTag.getAttributes().setAttribute("post", "" + this.openElementTags++);
        super.handleOpenElement(openElementTag);
    }

    @Override
    public void handleStandaloneElement(final IStandaloneElementTag standaloneElementTag) {
        standaloneElementTag.getAttributes().setAttribute("post", "" + this.standaloneElementTags++);
        super.handleStandaloneElement(standaloneElementTag);
    }

    @Override
    public void handleText(final IText text) {
        final String t = nullToEmpty(text.getText());
        text.setText(t + "(post:" + this.texts++ + ")");
        super.handleText(text);
    }

    @Override
    public void handleComment(final IComment comment) {
        final String c = nullToEmpty(comment.getContent());
        comment.setContent(c + "(post:" + this.comments++ + ")");
        super.handleComment(comment);
    }

    @Override
    public void handleCDATASection(final ICDATASection cdataSection) {
        final String c = nullToEmpty(cdataSection.getContent());
        cdataSection.setContent(c + "(post:" + this.cdataSections++ + ")");
        super.handleCDATASection(cdataSection);
    }

    @Override
    public void handleDocType(final IDocType docType) {
        final String is = nullToEmpty(docType.getInternalSubset());
        docType.setInternalSubset(is + "(post:" + this.docTypes++ + ")");
        super.handleDocType(docType);
    }

    @Override
    public void handleXMLDeclaration(final IXMLDeclaration xmlDeclaration) {
        final String is = nullToEmpty(xmlDeclaration.getEncoding());
        xmlDeclaration.setEncoding(is + "(post:" + this.xmlDeclarations++ + ")");
        super.handleXMLDeclaration(xmlDeclaration);
    }

    @Override
    public void handleTemplateEnd(final ITemplateEnd templateEnd) {
        // Nothing to be done here
        super.handleTemplateEnd(templateEnd);
    }

    @Override
    public void handleTemplateStart(final ITemplateStart templateStart) {
        // Nothing to be done here
        super.handleTemplateStart(templateStart);
    }


    private static String nullToEmpty(final String value) {
        if (value == null) {
            return "";
        }
        return value + " ";
    }

}
