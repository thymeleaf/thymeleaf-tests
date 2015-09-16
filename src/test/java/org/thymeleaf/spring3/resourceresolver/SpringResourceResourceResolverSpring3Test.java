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
package org.thymeleaf.spring3.resourceresolver;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.Context;
import org.thymeleaf.resource.IResource;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.testing.templateengine.util.ResourceUtils;
import org.thymeleaf.util.ClassLoaderUtils;


public final class SpringResourceResourceResolverSpring3Test {



    @Test
    public void testGetResourceAsStream() throws Exception {

        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        final IEngineConfiguration configuration = templateEngine.getConfiguration();

        final String templateLocation = "spring3/resourceresolver/test.html";

        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring3/resourceresolver/applicationContext.xml");

        final SpringResourceResourceResolver resolver =
                (SpringResourceResourceResolver) context.getBean("springResourceResourceResolver");

        final IResource resource =
                resolver.resolveResource(configuration, new Context(), "classpath:" + templateLocation, "US-ASCII");

        final String testResource = resource.readFully().replace("\r", "");

        final String expected =
                ResourceUtils.read(
                        ClassLoaderUtils.getClassLoader(SpringResourceResourceResolverSpring3Test.class).getResourceAsStream(templateLocation),
                        "US-ASCII", true).replace("\r", "");

        Assert.assertEquals(expected, testResource);

    }

    
}
