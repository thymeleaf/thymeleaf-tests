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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.DefaultTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;


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


    @Test
    public void testDefaultTemplateResolver01() {

        final TemplateEngine templateEngine = new TemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setCheckExistence(true);
        templateEngine.addTemplateResolver(classLoaderTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p>something</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.DefaultTemplateResolver", templateResolvers.get(1).getName());

        Assert.assertEquals("<p>something</p>", templateEngine.process("nonexisting", context));

    }

    @Test
    public void testDefaultTemplateResolver02() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setCheckExistence(true);
        templateEngine.addTemplateResolver(classLoaderTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p>something</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.DefaultTemplateResolver", templateResolvers.get(1).getName());

        Assert.assertEquals("<p>something</p>", templateEngine.process("nonexisting", context));

    }

    @Test
    public void testDefaultTemplateResolver03() {

        final TemplateEngine templateEngine = new TemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setCheckExistence(true);
        templateEngine.addTemplateResolver(classLoaderTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p th:text=\"${one}\">something</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.DefaultTemplateResolver", templateResolvers.get(1).getName());

        Assert.assertEquals("<p>this value</p>", templateEngine.process("nonexisting", context));

    }

    @Test
    public void testDefaultTemplateResolver04() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setCheckExistence(true);
        templateEngine.addTemplateResolver(classLoaderTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p th:text=\"${one}\">something</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        final List<ITemplateResolver> templateResolvers = new ArrayList<ITemplateResolver>(templateEngine.getTemplateResolvers());
        Assert.assertEquals(2, templateResolvers.size());
        Assert.assertEquals("org.thymeleaf.templateresolver.ClassLoaderTemplateResolver", templateResolvers.get(0).getName());
        Assert.assertEquals("org.thymeleaf.templateresolver.DefaultTemplateResolver", templateResolvers.get(1).getName());

        Assert.assertEquals("<p>this value</p>", templateEngine.process("nonexisting", context));

    }

    @Test
    public void testDefaultTemplateResolver05() {

        final TemplateEngine templateEngine = new TemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setResolvablePatterns(Collections.singleton("<div*"));
        templateEngine.addTemplateResolver(stringTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p>inserted!</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        Assert.assertEquals(
                "<div>some text <p><p>inserted!</p></p> other text</div>",
                templateEngine.process("<div>some text <p th:insert=\"nonexisting\">...</p> other text</div>", context));

    }

    @Test
    public void testDefaultTemplateResolver06() {

        final TemplateEngine templateEngine = new SpringTemplateEngine();
        final Context context = new Context();
        context.setLocale(Locale.ENGLISH);
        context.setVariable("one", "this value");

        final StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setResolvablePatterns(Collections.singleton("<div*"));
        templateEngine.addTemplateResolver(stringTemplateResolver);
        final DefaultTemplateResolver defaultTemplateResolver = new DefaultTemplateResolver();
        defaultTemplateResolver.setTemplate("<p>inserted!</p>");
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        templateEngine.initialize();

        Assert.assertEquals(
                "<div>some text <p><p>inserted!</p></p> other text</div>",
                templateEngine.process("<div>some text <p th:insert=\"nonexisting\">...</p> other text</div>", context));

    }


}
