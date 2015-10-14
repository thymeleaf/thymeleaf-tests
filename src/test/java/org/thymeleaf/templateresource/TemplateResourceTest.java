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
package org.thymeleaf.templateresource;

import java.io.File;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.testing.templateengine.context.web.WebProcessingContextBuilder;


public final class TemplateResourceTest {


    @Test
    public void testTemplateResourceUtils() throws Exception {

        Assert.assertEquals("/", TemplateResourceUtils.computeRelativePath("/", "/"));
        Assert.assertEquals("/something", TemplateResourceUtils.computeRelativePath("/", "something"));
        Assert.assertEquals("/", TemplateResourceUtils.computeRelativePath("/something", "/"));
        Assert.assertEquals("/", TemplateResourceUtils.computeRelativePath("something", "/"));
        Assert.assertEquals("something/", TemplateResourceUtils.computeRelativePath("something/else", "/"));
        Assert.assertEquals("something/else/", TemplateResourceUtils.computeRelativePath("something/else/more", "/"));
        Assert.assertEquals("something/else/less", TemplateResourceUtils.computeRelativePath("something/else/more", "less"));
        Assert.assertEquals("something/else/more.properties", TemplateResourceUtils.computeRelativePath("something/else/more.html", "more.properties"));
        Assert.assertEquals("something/else/more_es.properties", TemplateResourceUtils.computeRelativePath("something/else/more.html", "more_es.properties"));
        Assert.assertEquals("something/else/../more_es.properties", TemplateResourceUtils.computeRelativePath("something/else/more.html", "../more_es.properties"));
        Assert.assertEquals("something/else/../../more_es.properties", TemplateResourceUtils.computeRelativePath("something/else/more.html", "../../more_es.properties"));

        Assert.assertEquals("/", TemplateResourceUtils.cleanPath("/"));
        Assert.assertEquals("something", TemplateResourceUtils.cleanPath("something"));
        Assert.assertEquals("/something", TemplateResourceUtils.cleanPath("/something"));
        Assert.assertEquals("something/else", TemplateResourceUtils.cleanPath("something/else"));
        Assert.assertEquals("/something/else", TemplateResourceUtils.cleanPath("//something//else"));
        Assert.assertEquals("/something/else", TemplateResourceUtils.cleanPath("//something//a//..//else"));
        Assert.assertEquals("something/else/more", TemplateResourceUtils.cleanPath("something/else/more"));
        Assert.assertEquals("something/else/more", TemplateResourceUtils.cleanPath("something/else//more"));
        Assert.assertEquals("something/more", TemplateResourceUtils.cleanPath("something/else/../more"));
        Assert.assertEquals("something/else/more", TemplateResourceUtils.cleanPath("something/else/./more"));
        Assert.assertEquals("../something/else/more", TemplateResourceUtils.cleanPath("../something/else/./more"));
        Assert.assertEquals("something/else/more", TemplateResourceUtils.cleanPath("./something/else/./more"));
        Assert.assertEquals("something/else/more.html", TemplateResourceUtils.cleanPath("something/else/more.html"));
        Assert.assertEquals("../something/else/more.html", TemplateResourceUtils.cleanPath("../something/else/more.html"));
        Assert.assertEquals("../something/else", TemplateResourceUtils.cleanPath("../something/else/more.html/.."));
        Assert.assertEquals("something/more_es.properties", TemplateResourceUtils.cleanPath("something/else/more.html/../../more_es.properties"));

        Assert.assertEquals("/", TemplateResourceUtils.computeBaseName("/"));
        Assert.assertEquals("something", TemplateResourceUtils.computeBaseName("something"));
        Assert.assertEquals("something", TemplateResourceUtils.computeBaseName("/something"));
        Assert.assertEquals("else", TemplateResourceUtils.computeBaseName("something/else"));
        Assert.assertEquals("else", TemplateResourceUtils.computeBaseName("//something//else"));
        Assert.assertEquals("else", TemplateResourceUtils.computeBaseName("//something//a//..//else"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("something/else/more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("something/else//more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("something/else/../more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("something/else/./more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("../something/else/./more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("./something/else/./more"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("something/else/more.html"));
        Assert.assertEquals("more", TemplateResourceUtils.computeBaseName("../something/else/more.html"));
        Assert.assertEquals("..", TemplateResourceUtils.computeBaseName("../something/else/more.html/.."));
        Assert.assertEquals("more_es", TemplateResourceUtils.computeBaseName("something/else/more.html/../../more_es.properties"));

    }


    @Test
    public void testServletContextResource() throws Exception {

        final ServletContext servletContext = WebProcessingContextBuilder.createMockServletContext(new HashMap<String, Object>());

        Assert.assertEquals("/", (new ServletContextTemplateResource(servletContext, "/", null)).getName());
        Assert.assertEquals("/something", (new ServletContextTemplateResource(servletContext, "something", null)).getName());
        Assert.assertEquals("/something", (new ServletContextTemplateResource(servletContext, "/something", null)).getName());
        Assert.assertEquals("/something/else", (new ServletContextTemplateResource(servletContext, "something/else", null)).getName());
        Assert.assertEquals("/something/else", (new ServletContextTemplateResource(servletContext, "//something//else", null)).getName());
        Assert.assertEquals("/something/else", (new ServletContextTemplateResource(servletContext, "//something//a//..//else", null)).getName());
        Assert.assertEquals("/something/else/more", (new ServletContextTemplateResource(servletContext, "something/else/more", null)).getName());
        Assert.assertEquals("/something/else/more", (new ServletContextTemplateResource(servletContext, "something/else//more", null)).getName());
        Assert.assertEquals("/something/more", (new ServletContextTemplateResource(servletContext, "something/else/../more", null)).getName());
        Assert.assertEquals("/something/else/more", (new ServletContextTemplateResource(servletContext, "something/else/./more", null)).getName());
        Assert.assertEquals("/../something/else/more", (new ServletContextTemplateResource(servletContext, "../something/else/./more", null)).getName());
        Assert.assertEquals("/something/else/more", (new ServletContextTemplateResource(servletContext, "./something/else/./more", null)).getName());
        Assert.assertEquals("/something/else/more.html", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null)).getName());
        Assert.assertEquals("/../something/else/more.html", (new ServletContextTemplateResource(servletContext, "../something/else/more.html", null)).getName());
        Assert.assertEquals("/../something/else", (new ServletContextTemplateResource(servletContext, "../something/else/more.html/..", null)).getName());
        Assert.assertEquals("/something/more_es.properties", (new ServletContextTemplateResource(servletContext, "something/else/more.html/../../more_es.properties", null)).getName());

        Assert.assertEquals("/", (new ServletContextTemplateResource(servletContext, "/", null).relative("/")).getName());
        Assert.assertEquals("/something", (new ServletContextTemplateResource(servletContext, "/", null).relative("something")).getName());
        Assert.assertEquals("/", (new ServletContextTemplateResource(servletContext, "/something", null).relative("/")).getName());
        Assert.assertEquals("/", (new ServletContextTemplateResource(servletContext, "something", null).relative("/")).getName());
        Assert.assertEquals("/something/", (new ServletContextTemplateResource(servletContext, "something/else", null).relative("/")).getName());
        Assert.assertEquals("/something/else/", (new ServletContextTemplateResource(servletContext, "something/else/more", null).relative("/")).getName());
        Assert.assertEquals("/something/else/less", (new ServletContextTemplateResource(servletContext, "something/else/more", null).relative("less")).getName());
        Assert.assertEquals("/something/else/more/less", (new ServletContextTemplateResource(servletContext, "something/else/more/", null).relative("less")).getName());
        Assert.assertEquals("/something/else/more.properties", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null).relative("more.properties")).getName());
        Assert.assertEquals("/something/else/more_es.properties", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null).relative("more_es.properties")).getName());
        Assert.assertEquals("/something/more_es.properties", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals("/something/more_es.properties", (new ServletContextTemplateResource(servletContext, "something/more/../else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals("/more_es.properties", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null).relative("../../more_es.properties")).getName());

        Assert.assertEquals("/", (new ServletContextTemplateResource(servletContext, "/", null)).getBaseName());
        Assert.assertEquals("something", (new ServletContextTemplateResource(servletContext, "something", null)).getBaseName());
        Assert.assertEquals("something", (new ServletContextTemplateResource(servletContext, "/something", null)).getBaseName());
        Assert.assertEquals("else", (new ServletContextTemplateResource(servletContext, "something/else", null)).getBaseName());
        Assert.assertEquals("else", (new ServletContextTemplateResource(servletContext, "//something//else", null)).getBaseName());
        Assert.assertEquals("else", (new ServletContextTemplateResource(servletContext, "//something//a//..//else", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "something/else/more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "something/else//more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "something/else/../more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "../something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "./something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "../something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "../something/else/more.html/", null)).getBaseName());
        Assert.assertEquals("more", (new ServletContextTemplateResource(servletContext, "../something/else/more.html/..", null)).getBaseName());
        Assert.assertEquals("more_es", (new ServletContextTemplateResource(servletContext, "something/else/more.html/../../more_es.properties", null)).getBaseName());

    }


    @Test
    public void testClassLoaderResource() throws Exception {

        final ClassLoader classLoader = TemplateEngine.class.getClassLoader();

        Assert.assertEquals("", (new ClassLoaderTemplateResource(classLoader, "/", null)).getName());
        Assert.assertEquals("something", (new ClassLoaderTemplateResource(classLoader, "something", null)).getName());
        Assert.assertEquals("something", (new ClassLoaderTemplateResource(classLoader, "/something", null)).getName());
        Assert.assertEquals("something/else", (new ClassLoaderTemplateResource(classLoader, "something/else", null)).getName());
        Assert.assertEquals("something/else", (new ClassLoaderTemplateResource(classLoader, "//something//else", null)).getName());
        Assert.assertEquals("something/else", (new ClassLoaderTemplateResource(classLoader, "//something//a//..//else", null)).getName());
        Assert.assertEquals("something/else/more", (new ClassLoaderTemplateResource(classLoader, "something/else/more", null)).getName());
        Assert.assertEquals("something/else/more", (new ClassLoaderTemplateResource(classLoader, "something/else//more", null)).getName());
        Assert.assertEquals("something/more", (new ClassLoaderTemplateResource(classLoader, "something/else/../more", null)).getName());
        Assert.assertEquals("something/else/more", (new ClassLoaderTemplateResource(classLoader, "something/else/./more", null)).getName());
        Assert.assertEquals("../something/else/more", (new ClassLoaderTemplateResource(classLoader, "../something/else/./more", null)).getName());
        Assert.assertEquals("something/else/more", (new ClassLoaderTemplateResource(classLoader, "./something/else/./more", null)).getName());
        Assert.assertEquals("something/else/more.html", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null)).getName());
        Assert.assertEquals("../something/else/more.html", (new ClassLoaderTemplateResource(classLoader, "../something/else/more.html", null)).getName());
        Assert.assertEquals("../something/else", (new ClassLoaderTemplateResource(classLoader, "../something/else/more.html/..", null)).getName());
        Assert.assertEquals("something/more_es.properties", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html/../../more_es.properties", null)).getName());

        Assert.assertEquals("", (new ClassLoaderTemplateResource(classLoader, "/", null).relative("/")).getName());
        Assert.assertEquals("something", (new ClassLoaderTemplateResource(classLoader, "/", null).relative("something")).getName());
        Assert.assertEquals("", (new ClassLoaderTemplateResource(classLoader, "/something", null).relative("/")).getName());
        Assert.assertEquals("", (new ClassLoaderTemplateResource(classLoader, "something", null).relative("/")).getName());
        Assert.assertEquals("something/", (new ClassLoaderTemplateResource(classLoader, "something/else", null).relative("/")).getName());
        Assert.assertEquals("something/else/", (new ClassLoaderTemplateResource(classLoader, "something/else/more", null).relative("/")).getName());
        Assert.assertEquals("something/else/less", (new ClassLoaderTemplateResource(classLoader, "something/else/more", null).relative("less")).getName());
        Assert.assertEquals("something/else/more/less", (new ClassLoaderTemplateResource(classLoader, "something/else/more/", null).relative("less")).getName());
        Assert.assertEquals("something/else/more.properties", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null).relative("more.properties")).getName());
        Assert.assertEquals("something/else/more_es.properties", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null).relative("more_es.properties")).getName());
        Assert.assertEquals("something/more_es.properties", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals("something/more_es.properties", (new ClassLoaderTemplateResource(classLoader, "something/more/../else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals("more_es.properties", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null).relative("../../more_es.properties")).getName());

        Assert.assertEquals("/", (new ClassLoaderTemplateResource(classLoader, "/", null)).getBaseName());
        Assert.assertEquals("something", (new ClassLoaderTemplateResource(classLoader, "something", null)).getBaseName());
        Assert.assertEquals("something", (new ClassLoaderTemplateResource(classLoader, "/something", null)).getBaseName());
        Assert.assertEquals("else", (new ClassLoaderTemplateResource(classLoader, "something/else", null)).getBaseName());
        Assert.assertEquals("else", (new ClassLoaderTemplateResource(classLoader, "//something//else", null)).getBaseName());
        Assert.assertEquals("else", (new ClassLoaderTemplateResource(classLoader, "//something//a//..//else", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "something/else/more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "something/else//more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "something/else/../more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "../something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "./something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "../something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "../something/else/more.html/", null)).getBaseName());
        Assert.assertEquals("more", (new ClassLoaderTemplateResource(classLoader, "../something/else/more.html/..", null)).getBaseName());
        Assert.assertEquals("more_es", (new ClassLoaderTemplateResource(classLoader, "something/else/more.html/../../more_es.properties", null)).getBaseName());

    }


    @Test
    public void testFileResource() throws Exception {

        Assert.assertEquals(new File("/").getAbsolutePath(), (new FileTemplateResource("/", null)).getName());
        Assert.assertEquals(new File("something").getAbsolutePath(), (new FileTemplateResource("something", null)).getName());
        Assert.assertEquals(new File("/something").getAbsolutePath(), (new FileTemplateResource("/something", null)).getName());
        Assert.assertEquals(new File("something/else").getAbsolutePath(), (new FileTemplateResource("something/else", null)).getName());
        Assert.assertEquals(new File("/something/else").getAbsolutePath(), (new FileTemplateResource("//something//else", null)).getName());
        Assert.assertEquals(new File("/something/a/../else").getAbsolutePath(), (new FileTemplateResource("//something//a//..//else", null)).getName());
        Assert.assertEquals(new File("something/else/more").getAbsolutePath(), (new FileTemplateResource("something/else/more", null)).getName());
        Assert.assertEquals(new File("something/else/more").getAbsolutePath(), (new FileTemplateResource("something/else//more", null)).getName());
        Assert.assertEquals(new File("something/else/../more").getAbsolutePath(), (new FileTemplateResource("something/else/../more", null)).getName());
        Assert.assertEquals(new File("something/else/./more").getAbsolutePath(), (new FileTemplateResource("something/else/./more", null)).getName());
        Assert.assertEquals(new File("../something/else/./more").getAbsolutePath(), (new FileTemplateResource("../something/else/./more", null)).getName());
        Assert.assertEquals(new File("./something/else/./more").getAbsolutePath(), (new FileTemplateResource("./something/else/./more", null)).getName());
        Assert.assertEquals(new File("something/else/more.html").getAbsolutePath(), (new FileTemplateResource("something/else/more.html", null)).getName());
        Assert.assertEquals(new File("../something/else/more.html").getAbsolutePath(), (new FileTemplateResource("../something/else/more.html", null)).getName());
        Assert.assertEquals(new File("../something/else/more.html/..").getAbsolutePath(), (new FileTemplateResource("../something/else/more.html/..", null)).getName());
        Assert.assertEquals(new File("something/else/more.html/../../more_es.properties").getAbsolutePath(), (new FileTemplateResource("something/else/more.html/../../more_es.properties", null)).getName());

        Assert.assertEquals(new File("/").getAbsolutePath(), (new FileTemplateResource("/", null).relative("/")).getName());
        Assert.assertEquals(new File("/something").getAbsolutePath(), (new FileTemplateResource("/", null).relative("something")).getName());
        Assert.assertEquals(new File("/").getAbsolutePath(), (new FileTemplateResource("/something", null).relative("/")).getName());
        Assert.assertEquals(new File("/").getAbsolutePath(), (new FileTemplateResource("something", null).relative("/")).getName());
        Assert.assertEquals(new File("something/").getAbsolutePath(), (new FileTemplateResource("something/else", null).relative("/")).getName());
        Assert.assertEquals(new File("something/else/").getAbsolutePath(), (new FileTemplateResource("something/else/more", null).relative("/")).getName());
        Assert.assertEquals(new File("something/else/less").getAbsolutePath(), (new FileTemplateResource("something/else/more", null).relative("less")).getName());
        Assert.assertEquals(new File("something/else/more/less").getAbsolutePath(), (new FileTemplateResource("something/else/more/", null).relative("less")).getName());
        Assert.assertEquals(new File("something/else/more.properties").getAbsolutePath(), (new FileTemplateResource("something/else/more.html", null).relative("more.properties")).getName());
        Assert.assertEquals(new File("something/else/more_es.properties").getAbsolutePath(), (new FileTemplateResource("something/else/more.html", null).relative("more_es.properties")).getName());
        Assert.assertEquals(new File("something/else/../more_es.properties").getAbsolutePath(), (new FileTemplateResource("something/else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals(new File("something/else/../more_es.properties").getAbsolutePath(), (new FileTemplateResource("something/more/../else/more.html", null).relative("../more_es.properties")).getName());
        Assert.assertEquals(new File("something/else/../../more_es.properties").getAbsolutePath(), (new FileTemplateResource("something/else/more.html", null).relative("../../more_es.properties")).getName());

        Assert.assertEquals("/", (new FileTemplateResource("/", null)).getBaseName());
        Assert.assertEquals("something", (new FileTemplateResource("something", null)).getBaseName());
        Assert.assertEquals("something", (new FileTemplateResource("/something", null)).getBaseName());
        Assert.assertEquals("else", (new FileTemplateResource("something/else", null)).getBaseName());
        Assert.assertEquals("else", (new FileTemplateResource("//something//else", null)).getBaseName());
        Assert.assertEquals("else", (new FileTemplateResource("//something//a//..//else", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("something/else/more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("something/else//more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("something/else/../more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("../something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("./something/else/./more", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("../something/else/more.html", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("../something/else/more.html/", null)).getBaseName());
        Assert.assertEquals("more", (new FileTemplateResource("../something/else/more.html/..", null)).getBaseName());
        Assert.assertEquals("more_es", (new FileTemplateResource("something/else/more.html/../../more_es.properties", null)).getBaseName());

    }


    @Test
    public void testURLResource() throws Exception {

        Assert.assertEquals("http://www.thymeleaf.org/", (new UrlTemplateResource("http://www.thymeleaf.org/", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org", (new UrlTemplateResource("http://www.thymeleaf.org", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org/something", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/", (new UrlTemplateResource("http://www.thymeleaf.org/something/", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/else", (new UrlTemplateResource("http://www.thymeleaf.org/something/else", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/else.html", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/./else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./else.html", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/more/../else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/more/../else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/./more/../else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html", null)).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/./more/../else.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html", null)).getName());

        Assert.assertEquals("http://www.thymeleaf.org/", (new UrlTemplateResource("http://www.thymeleaf.org/", null).relative("/")).getName());
        Assert.assertEquals("http://www.thymeleaf.org", (new UrlTemplateResource("http://www.thymeleaf.org", null).relative("/")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org", null).relative("/something")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org", null).relative("something")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org/more", null).relative("something")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/more/something", (new UrlTemplateResource("http://www.thymeleaf.org/more/", null).relative("something")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/more", (new UrlTemplateResource("http://www.thymeleaf.org/something/else", null).relative("more")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/something/more.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/else.html", null).relative("more.html")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/more.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/else.html", null).relative("../more.html")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null).relative("../less.html")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null).relative("../even/less.html")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html", null).relative("../even/less.html")).getName());
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html", null).relative("../even/./less.html")).getName());

        Assert.assertEquals("http://www.thymeleaf.org/", (new UrlTemplateResource("http://www.thymeleaf.org/", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org", (new UrlTemplateResource("http://www.thymeleaf.org", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/something", (new UrlTemplateResource("http://www.thymeleaf.org/more", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/more/something", (new UrlTemplateResource("http://www.thymeleaf.org/more/", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/something/more", (new UrlTemplateResource("http://www.thymeleaf.org/something/else", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/something/more.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/else.html", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/more.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/else.html", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html/", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/more/../else.html/a/..", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html", null).getBaseName()));
        Assert.assertEquals("http://www.thymeleaf.org/even/less.html", (new UrlTemplateResource("http://www.thymeleaf.org/something/./more/../else.html?param=a", null).getBaseName()));

    }

}
