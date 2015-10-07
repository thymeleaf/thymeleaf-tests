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
package org.thymeleaf.standard.expression;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.NonCacheableCacheEntryValidity;
import org.thymeleaf.context.IContext;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.resourceresolver.StringResourceResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.1
 *
 */
public class TestTemplateResolver implements ITemplateResolver {

    private final String template;
    

    
    public TestTemplateResolver(final String template) {
        super();
        this.template = template;
    }

    public String getName() {
        return "TEST EXPRESSION TEMPLATE RESOLVER";
    }

    public Integer getOrder() {
        return Integer.valueOf(1);
    }

    public TemplateResolution resolveTemplate(final IEngineConfiguration configuration, final String template) {

        final int placeholderPos = this.template.indexOf("{%%}");
        final String resource =
                this.template.substring(0,placeholderPos) +
                        template +
                        this.template.substring(placeholderPos + 4);

        final IResourceResolver resourceResolver = new StringResourceResolver();

        final TemplateResolution templateResolution =
                new TemplateResolution(
                        template, resource, resourceResolver,
                        "UTF-8", TemplateMode.HTML,
                        new NonCacheableCacheEntryValidity());

        return templateResolution;

    }


    public void initialize() {
        // nothing to be done;
    }

}
