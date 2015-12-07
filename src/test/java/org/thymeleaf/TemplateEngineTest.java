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
package org.thymeleaf;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


public final class TemplateEngineTest {



    @Test
    public void testTemplateResolverConfiguration01() {

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(1, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.StringTemplateResolver", templateResolvers.get(0).getName());

    }

    @Test
    public void testTemplateResolverConfiguration02() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(1, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.StringTemplateResolver", templateResolvers.get(0).getName());

    }

    @Test
    public void testTemplateResolverConfiguration03() {

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(1, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());

    }

    @Test
    public void testTemplateResolverConfiguration04() {

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.setTemplateResolver(new FileTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(1, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(0).getName());

    }

    @Test
    public void testTemplateResolverConfiguration05() {

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.addTemplateResolver(new FileTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }

    @Test
    public void testTemplateResolverConfiguration06() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.addTemplateResolver(new FileTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }

    @Test
    public void testTemplateResolverConfiguration07() {

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.addTemplateResolver(new FileTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }

    @Test
    public void testTemplateResolverConfiguration08() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.addTemplateResolver(new FileTemplateResolver());
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }

    @Test
    public void testTemplateResolverConfiguration09() {

        final TemplateEngine templateEngine = new TemplateEngine();
        final Set<ITemplateResolver> resolvers = new LinkedHashSet<ITemplateResolver>();
        resolvers.add(new ClassLoaderTemplateResolver());
        resolvers.add(new FileTemplateResolver());
        templateEngine.setTemplateResolvers(resolvers);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }

    @Test
    public void testTemplateResolverConfiguration10() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        final Set<ITemplateResolver> resolvers = new LinkedHashSet<ITemplateResolver>();
        resolvers.add(new ClassLoaderTemplateResolver());
        resolvers.add(new FileTemplateResolver());
        templateEngine.setTemplateResolvers(resolvers);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.FileTemplateResolver", templateResolvers.get(1).getName());

    }



    @Test
    public void testStringTemplate() {

        final TemplateEngine templateEngine = new TemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);

        Assert.assertEquals("Hello ??something_en??", templateEngine.process("Hello [[#{something}]]", context));
        Assert.assertEquals("Hello 58", templateEngine.process("Hello [[23+35]]", context));

    }

}
