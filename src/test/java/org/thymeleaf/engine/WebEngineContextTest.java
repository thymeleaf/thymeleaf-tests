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
package org.thymeleaf.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.TestTemplateEngineConfigurationBuilder;
import org.thymeleaf.standard.inline.StandardTextInliner;
import org.thymeleaf.templatemode.TemplateMode;


public final class WebEngineContextTest {


    private static final Locale LOCALE = Locale.US;



    @Test
    public void test01() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        vm.setVariable("one", "two values");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));

        vm.increaseLevel();
        vm.setVariable("two", "twellor");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.setVariable("one", "atwe");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.setVariable("four", "lotwss");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.setVariable("two", "itwiii");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertFalse(vm.containsVariable("five"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("itwiii", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

    }




    @Test
    public void test02() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, starting);

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

    }




    @Test
    public void test03() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("a value", mockRequest.getAttribute("one"));

        vm.setVariable("one", "two values");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.removeVariable("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.setVariable("one", "two values");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.removeVariable("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.removeVariable("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));

        vm.increaseLevel();
        vm.setVariable("two", "twellor");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twellor", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.setVariable("one", "atwe");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("atwe", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.setVariable("four", "lotwss");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.setVariable("two", "itwiii");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertFalse(vm.containsVariable("five"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("itwiii", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));
        Assert.assertEquals("itwiii", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertEquals("atwe", mockRequest.getAttribute("one"));
        Assert.assertEquals("twellor", mockRequest.getAttribute("two"));
        Assert.assertEquals("twelloree", mockRequest.getAttribute("three"));
        Assert.assertNull(mockRequest.getAttribute("four"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "three"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertNull(mockRequest.getAttribute("three"));
        Assert.assertNull(mockRequest.getAttribute("four"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "three"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "four"));

    }




    @Test
    public void test04() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, starting);

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        // This are directly set into the request, so they should only be affected by higher levels, never by decreasing levels
        mockRequest.setAttribute("one", "outer1");
        mockRequest.setAttribute("six", "outer6");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertEquals("outer1", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertEquals("outer6", vm.getVariable("six"));
        Assert.assertEquals("outer1", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));

        vm.increaseLevel();

        vm.setVariable("one", "helloz");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertEquals("helloz", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertEquals("outer6", vm.getVariable("six"));
        Assert.assertEquals("helloz", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));


        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertEquals("outer1", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertEquals("outer6", vm.getVariable("six"));
        Assert.assertEquals("outer1", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));


        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertEquals("outer1", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertEquals("outer6", vm.getVariable("six"));
        Assert.assertEquals("outer1", mockRequest.getAttribute("one"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));

    }




    @Test
    public void test05() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, starting);

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        mockRequest.removeAttribute("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));

        vm.increaseLevel();

        vm.setVariable("one", "helloz");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("helloz", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertEquals("helloz", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));


        vm.decreaseLevel();

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));


        vm.decreaseLevel();

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));

    }


    @Test
    public void test06() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertEquals("{0:{one=a value}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}(test01)", vm.toString());

        vm.setVariable("one", "two values");

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());

        vm.setVariable("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());

        vm.increaseLevel();
        vm.setVariable("two", "twellor");
        vm.setInliner(StandardTextInliner.INSTANCE);

        Assert.assertEquals("{1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[StandardTextInliner](test01)", vm.toString());

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.setVariable("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.setVariable("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree, four=lotwss}[StandardTextInliner](test01)", vm.toString());

        vm.setVariable("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=itwiii, three=twelloree, four=lotwss}[StandardTextInliner](test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[StandardTextInliner](test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor}[StandardTextInliner],0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[StandardTextInliner](test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());

    }




    @Test
    public void test07() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, starting);

        Assert.assertEquals("{0:{one=ha, ten=tieen}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=ha, ten=tieen}(test01)", vm.toString());

        vm.setVariable("one", "a value");

        Assert.assertEquals("{0:{one=a value, ten=tieen}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value, ten=tieen}(test01)", vm.toString());

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=a value, ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen}(test01)", vm.toString());

        vm.setVariable("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=a value, ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen, two=twello}(test01)", vm.toString());

        mockRequest.removeAttribute("one");

        Assert.assertEquals("{1:{two=twello},0:{ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello}(test01)", vm.toString());

        vm.increaseLevel();

        vm.setVariable("one", "helloz");

        Assert.assertEquals("{2:{one=helloz},1:{two=twello},0:{ten=tieen}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello, one=helloz}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twello},0:{ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{ten=tieen}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen}(test01)", vm.toString());

    }





    @Test
    public void test08() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertEquals("{0:{one=a value}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());
        Assert.assertFalse(vm.isVariableLocal("one"));

        vm.setVariable("one", "two values");

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{0:{}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}(test01)", vm.toString());
        Assert.assertEquals(Collections.emptySet(), vm.getVariableNames());

        vm.setVariable("one", "two values");

        Assert.assertFalse(vm.isVariableLocal("one"));
        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.increaseLevel();

        Assert.assertFalse(vm.isVariableLocal("one"));

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.isVariableLocal("one"));

        vm.decreaseLevel();

        Assert.assertFalse(vm.isVariableLocal("one"));

        vm.increaseLevel();

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.isVariableLocal("one"));
        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{1:{one=null},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}(test01)", vm.toString());
        Assert.assertEquals(Collections.emptySet(), vm.getVariableNames());

        vm.setVariable("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.setVariable("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one"), vm.getVariableNames());

        vm.removeVariable("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{1:{one=null},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}(test01)", vm.toString());
        Assert.assertEquals(Collections.emptySet(), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.increaseLevel();
        vm.setVariable("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one"), vm.getVariableNames());

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one","three"), vm.getVariableNames());

        vm.setVariable("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one","three"), vm.getVariableNames());

        vm.increaseLevel();

        Assert.assertTrue(vm.isVariableLocal("two"));

        vm.removeVariable("two");

        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());
        Assert.assertFalse(vm.isVariableLocal("two"));

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.increaseLevel();

        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.setVariable("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","four"), vm.getVariableNames());

        vm.setVariable("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss, two=itwiii}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one","three","four"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one","three"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","one"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

    }


    @Test
    public void test09() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        vm.increaseLevel();
        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

        vm.setVariable("three", "trwello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));

        vm.setVariable("four", "fwello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));
        Assert.assertEquals("fwello", vm.getVariable("four"));

        vm.setVariable("five", "vwello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertTrue(vm.containsVariable("five"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));
        Assert.assertEquals("fwello", vm.getVariable("four"));
        Assert.assertEquals("vwello", vm.getVariable("five"));

        vm.setVariable("six", "swello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertTrue(vm.containsVariable("five"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));
        Assert.assertEquals("fwello", vm.getVariable("four"));
        Assert.assertEquals("vwello", vm.getVariable("five"));
        Assert.assertEquals("swello", vm.getVariable("six"));

        vm.setVariable("seven", "svwello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertTrue(vm.containsVariable("five"));
        Assert.assertTrue(vm.containsVariable("six"));
        Assert.assertTrue(vm.containsVariable("seven"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));
        Assert.assertEquals("fwello", vm.getVariable("four"));
        Assert.assertEquals("vwello", vm.getVariable("five"));
        Assert.assertEquals("swello", vm.getVariable("six"));
        Assert.assertEquals("svwello", vm.getVariable("seven"));

    }




    @Test
    public void test10() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, starting);

        Assert.assertEquals("{0:{one=ha, ten=tieen}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=ha, ten=tieen}(test01)", vm.toString());
        Assert.assertEquals(createSet("ten","one"), vm.getVariableNames());
        Assert.assertFalse(vm.isVariableLocal("ten"));

        vm.setVariable("one", "a value");
        Assert.assertFalse(vm.isVariableLocal("one"));

        Assert.assertEquals("{0:{one=a value, ten=tieen}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value, ten=tieen}(test01)", vm.toString());
        Assert.assertEquals(createSet("ten","one"), vm.getVariableNames());

        vm.increaseLevel();
        vm.setVariable("one", "hello");
        Assert.assertTrue(vm.isVariableLocal("one"));

        Assert.assertEquals("{1:{one=hello},0:{one=a value, ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen}(test01)", vm.toString());
        Assert.assertEquals(createSet("ten","one"), vm.getVariableNames());

        vm.setVariable("two", "twello");
        Assert.assertTrue(vm.isVariableLocal("two"));

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=a value, ten=tieen}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen, two=twello}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","ten","one"), vm.getVariableNames());

        // This are directly set into the request, so they should only be affected by higher levels, never by decreasing levels
        mockRequest.setAttribute("one", "outer1");
        mockRequest.setAttribute("six", "outer6");

        Assert.assertEquals("{1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, two=twello, six=outer6}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","ten","one","six"), vm.getVariableNames());

        vm.increaseLevel();

        vm.setVariable("one", "helloz");

        Assert.assertEquals("{2:{one=helloz},1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=helloz, ten=tieen, two=twello, six=outer6}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","ten","one","six"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, two=twello, six=outer6}(test01)", vm.toString());
        Assert.assertEquals(createSet("two","ten","one","six"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=outer1, ten=tieen, six=outer6}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, six=outer6}(test01)", vm.toString());
        Assert.assertEquals(createSet("ten","one","six"), vm.getVariableNames());

    }


    @Test
    public void test11() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a value");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}(test01)", vm.toString());

        vm.increaseLevel();

        vm.setVariable("one", "hello");
        vm.removeVariable("one");
        vm.setVariable("one", "hello");
        vm.removeVariable("two");
        vm.setVariable("two", "twello");
        vm.setVariable("two", "twellor");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}(test01)", vm.toString());

        vm.increaseLevel();

        vm.setVariable("three", "twelloree");
        vm.setVariable("one", "atwe");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.setSelectionTarget("BIGFORM");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<BIGFORM>(test01)", vm.toString());

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>(test01)", vm.toString());

        vm.increaseLevel();

        vm.removeVariable("two");
        vm.setSelectionTarget("SMALLFORM");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>(test01)", vm.toString());


        vm.increaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>(test01)", vm.toString());

        vm.setVariable("four", "lotwss");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{5:{four=lotwss},4:<SMALLFORM>,3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}<SMALLFORM>(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{3:{two=null},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}<BIGFORM>(test01)", vm.toString());

        vm.setSelectionTarget("MEDIUMFORM");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("MEDIUMFORM", vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<MEDIUMFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}<MEDIUMFORM>(test01)", vm.toString());


        vm.decreaseLevel();

        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}(test01)", vm.toString());


        vm.decreaseLevel();

        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}(test01)", vm.toString());

        vm.setSelectionTarget("TOTALFORM");

        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>(test01)", vm.toString());


        vm.increaseLevel();

        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>(test01)", vm.toString());

    }



    @Test
    public void test12() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        vm.setVariable("one", "a val1");

        vm.increaseLevel();

        vm.setVariable("one", "a val2");
        vm.setSelectionTarget("FORM");

        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("FORM", vm.getSelectionTarget());

        vm.decreaseLevel();

        vm.setVariable("one", "a val3");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

        vm.increaseLevel();

        vm.setVariable("one", "a val4");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

        vm.increaseLevel();

        vm.setVariable("one", "a val5");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

    }




    @Test
    public void test13() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateData templateData1 = TestTemplateDataConfigurationBuilder.build("test01", TemplateMode.HTML);
        final TemplateData templateData2 = TestTemplateDataConfigurationBuilder.build("test02", TemplateMode.HTML);
        final TemplateData templateData3 = TestTemplateDataConfigurationBuilder.build("test03", TemplateMode.XML);
        final TemplateData templateData4 = TestTemplateDataConfigurationBuilder.build("test04", TemplateMode.TEXT);

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, LOCALE);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebEngineContext vm = new WebEngineContext(configuration, templateData1, null, mockRequest, mockResponse, mockServletContext, LOCALE, null);

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData1, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1), vm.getTemplateStack());

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData1, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1), vm.getTemplateStack());


        vm.setVariable("one", "two values");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.removeVariable("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));

        vm.setVariable("one", "two values");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData1, vm.getTemplateData());

        vm.setTemplateData(templateData2);

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData2, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1, templateData2), vm.getTemplateStack());

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.removeVariable("one");

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData2, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1, templateData2), vm.getTemplateStack());

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.setVariable("two", "twello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.removeVariable("one");

        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.decreaseLevel();

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData1, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1), vm.getTemplateStack());

        vm.setVariable("two", "twellor");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));

        vm.setTemplateData(templateData2);

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData2, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2), vm.getTemplateStack());

        vm.setTemplateData(templateData3);

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        vm.setVariable("three", "twelloree");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.setVariable("one", "atwe");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.setVariable("four", "lotwss");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.setVariable("two", "itwiii");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertFalse(vm.containsVariable("five"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("itwiii", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateData3, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2,templateData3), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData2, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData2), vm.getTemplateStack());

        vm.setTemplateData(templateData4);

        Assert.assertEquals(TemplateMode.TEXT, vm.getTemplateMode());
        Assert.assertSame(templateData4, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1,templateData4), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateData1, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData1), vm.getTemplateStack());

        vm.setTemplateData(templateData4);

        Assert.assertEquals(TemplateMode.TEXT, vm.getTemplateMode());
        Assert.assertSame(templateData4, vm.getTemplateData());
        Assert.assertEquals(Arrays.asList(templateData4), vm.getTemplateStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

    }




    private static boolean enumerationContains(final Enumeration<String> enumeration, final String value) {
        while (enumeration.hasMoreElements()) {
            final String enumValue = enumeration.nextElement();
            if (enumValue == null) {
                if (value == null) {
                    return true;
                }
            } else {
                if (enumValue.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }



    private static Set<String> createSet(final String... elements) {
        final Set<String> result = new LinkedHashSet<String>();
        for (final String element : elements) {
            result.add(element);
        }
        return result;
    }


}
