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
package org.thymeleaf.aurora.engine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.thymeleaf.testing.templateengine.testable.ITest;


public final class TemplateProcessingWebVariablesMapTest {



    @Test
    public void test01() {

        final Map<String,Object> requestAttributes = new HashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new HashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);

        final Map<String,Object> servletContextAttributes = new HashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final TemplateProcessingWebVariablesMap vm = new TemplateProcessingWebVariablesMap(mockRequest, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("a value", vm.get("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("two values", vm.get("one"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("hello", vm.get("one"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertNull(vm.get("two"));

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

        vm.put("one", "atwe");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

        vm.put("four", "lotwss");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertEquals("lotwss", vm.get("four"));

        vm.put("two", "itwiii");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertFalse(vm.contains("five"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("itwiii", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertEquals("lotwss", vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertFalse(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertNull(vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertFalse(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertNull(vm.get("three"));
        Assert.assertNull(vm.get("four"));

    }




    @Test
    public void test02() {

        final Map<String,Object> starting = new HashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new HashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new HashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);

        final Map<String,Object> servletContextAttributes = new HashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final TemplateProcessingWebVariablesMap vm = new TemplateProcessingWebVariablesMap(mockRequest, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("ha", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("a value", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("a value", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

    }




    @Test
    public void test03() {

        final Map<String,Object> requestAttributes = new HashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new HashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);

        final Map<String,Object> servletContextAttributes = new HashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final TemplateProcessingWebVariablesMap vm = new TemplateProcessingWebVariablesMap(mockRequest, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("a value", vm.get("one"));
        Assert.assertEquals("a value", mockRequest.getAttribute("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("one");

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertNull(vm.get("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertNull(vm.get("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertNull(vm.get("twello"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertNull(vm.get("twello"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertNull(vm.get("one"));
        Assert.assertNull(vm.get("twello"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertNull(vm.get("two"));

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twellor", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

        vm.put("one", "atwe");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertEquals("atwe", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

        vm.increaseLevel();

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.put("four", "lotwss");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertEquals("lotwss", vm.get("four"));

        vm.put("two", "itwiii");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertFalse(vm.contains("five"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("itwiii", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertEquals("lotwss", vm.get("four"));
        Assert.assertEquals("itwiii", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.decreaseLevel();

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.decreaseLevel();

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));
        Assert.assertNull(vm.get("four"));
        Assert.assertEquals("atwe", mockRequest.getAttribute("one"));
        Assert.assertEquals("twellor", mockRequest.getAttribute("two"));
        Assert.assertEquals("twelloree", mockRequest.getAttribute("three"));
        Assert.assertNull(mockRequest.getAttribute("four"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "three"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "four"));

        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertFalse(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertEquals("twellor", vm.get("two"));
        Assert.assertNull(vm.get("three"));
        Assert.assertNull(vm.get("four"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertFalse(vm.contains("three"));
        Assert.assertFalse(vm.contains("four"));
        Assert.assertEquals("two values", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertNull(vm.get("three"));
        Assert.assertNull(vm.get("four"));
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

        final Map<String,Object> starting = new HashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new HashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new HashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);

        final Map<String,Object> servletContextAttributes = new HashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final TemplateProcessingWebVariablesMap vm = new TemplateProcessingWebVariablesMap(mockRequest, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("ha", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("a value", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));

        // This are directly set into the request, so they should only be affected by higher levels, never by decreasing levels
        mockRequest.setAttribute("one", "outer1");
        mockRequest.setAttribute("six", "outer6");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertEquals("outer1", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertEquals("outer6", vm.get("six"));
        Assert.assertEquals("outer1", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));

        vm.increaseLevel();

        vm.put("one", "helloz");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertEquals("helloz", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertEquals("outer6", vm.get("six"));
        Assert.assertEquals("helloz", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));


        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertEquals("outer1", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertEquals("outer6", vm.get("six"));
        Assert.assertEquals("outer1", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertEquals("outer6", mockRequest.getAttribute("six"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "six"));


        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertEquals("outer1", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertEquals("outer6", vm.get("six"));
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

        final Map<String,Object> starting = new HashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new HashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new HashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);

        final Map<String,Object> servletContextAttributes = new HashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final TemplateProcessingWebVariablesMap vm = new TemplateProcessingWebVariablesMap(mockRequest, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("ha", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("a value", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("tieen", vm.get("ten"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));

        mockRequest.removeAttribute("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertNull(vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));

        vm.increaseLevel();

        vm.put("one", "helloz");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertEquals("helloz", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertEquals("helloz", mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));


        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertNull(vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));


        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("ten"));
        Assert.assertNull(vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("tieen", vm.get("ten"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertEquals("tieen", mockRequest.getAttribute("ten"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "ten"));

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


}
