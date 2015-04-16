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

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;


public final class WebVariablesMapTest {



    @Test
    public void test01() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.put("one", "atwe");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.put("four", "lotwss");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.put("two", "itwiii");

        Assert.assertEquals(5, vm.level());
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

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
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

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

    }




    @Test
    public void test03() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("a value", mockRequest.getAttribute("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("one");

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("two values", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(mockRequest.getAttribute("one"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("hello", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("twello", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertNull(vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twellor", mockRequest.getAttribute("two"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.put("one", "atwe");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("atwe", mockRequest.getAttribute("one"));
        Assert.assertTrue(enumerationContains(mockRequest.getAttributeNames(), "one"));

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(mockRequest.getAttribute("two"));
        Assert.assertFalse(enumerationContains(mockRequest.getAttributeNames(), "two"));

        vm.put("four", "lotwss");

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertEquals("lotwss", vm.getVariable("four"));

        vm.put("two", "itwiii");

        Assert.assertEquals(5, vm.level());
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

        Assert.assertEquals(4, vm.level());
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

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(2, vm.level());
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

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
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

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        // This are directly set into the request, so they should only be affected by higher levels, never by decreasing levels
        mockRequest.setAttribute("one", "outer1");
        mockRequest.setAttribute("six", "outer6");

        Assert.assertEquals(1, vm.level());
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

        vm.put("one", "helloz");

        Assert.assertEquals(2, vm.level());
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

        Assert.assertEquals(1, vm.level());
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

        Assert.assertEquals(0, vm.level());
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

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, starting);

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("ha", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("a value", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("ten"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("tieen", vm.getVariable("ten"));

        mockRequest.removeAttribute("one");

        Assert.assertEquals(1, vm.level());
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

        vm.put("one", "helloz");

        Assert.assertEquals(2, vm.level());
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

        Assert.assertEquals(1, vm.level());
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

        Assert.assertEquals(0, vm.level());
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

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}", vm.toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}", vm.toString());

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}", vm.toString());

        vm.put("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.put("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree, four=lotwss}", vm.toString());

        vm.put("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=itwiii, three=twelloree, four=lotwss}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());

    }




    @Test
    public void test07() {

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, starting);

        Assert.assertEquals("{0:{one=ha, ten=tieen}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=ha, ten=tieen}", vm.toString());

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value, ten=tieen}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value, ten=tieen}", vm.toString());

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=a value, ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen}", vm.toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=a value, ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen, two=twello}", vm.toString());

        mockRequest.removeAttribute("one");

        Assert.assertEquals("{1:{two=twello},0:{ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello}", vm.toString());

        vm.increaseLevel();

        vm.put("one", "helloz");

        Assert.assertEquals("{2:{one=helloz},1:{two=twello},0:{ten=tieen}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello, one=helloz}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twello},0:{ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen, two=twello}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{ten=tieen}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{ten=tieen}", vm.toString());

    }





    @Test
    public void test08() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{0:{}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, two, three]", vm.getVariableNames().toString());

        vm.put("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, two, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.put("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}", vm.toString());
        Assert.assertEquals("[one, three, four]", vm.getVariableNames().toString());

        vm.put("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss, two=itwiii}", vm.toString());
        Assert.assertEquals("[one, three, four, two]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}", vm.toString());
        Assert.assertEquals("[one, three, two]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

    }


    @Test
    public void test09() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

        vm.put("three", "trwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));

        vm.put("four", "fwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertTrue(vm.containsVariable("four"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));
        Assert.assertEquals("trwello", vm.getVariable("three"));
        Assert.assertEquals("fwello", vm.getVariable("four"));

        vm.put("five", "vwello");

        Assert.assertEquals(1, vm.level());
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

        vm.put("six", "swello");

        Assert.assertEquals(1, vm.level());
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

        vm.put("seven", "svwello");

        Assert.assertEquals(1, vm.level());
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

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, starting);

        Assert.assertEquals("{0:{one=ha, ten=tieen}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=ha, ten=tieen}", vm.toString());
        Assert.assertEquals("[one, ten]", vm.getVariableNames().toString());

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value, ten=tieen}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value, ten=tieen}", vm.toString());
        Assert.assertEquals("[one, ten]", vm.getVariableNames().toString());

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=a value, ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen}", vm.toString());
        Assert.assertEquals("[one, ten]", vm.getVariableNames().toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=a value, ten=tieen}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, ten=tieen, two=twello}", vm.toString());
        Assert.assertEquals("[one, ten, two]", vm.getVariableNames().toString());

        // This are directly set into the request, so they should only be affected by higher levels, never by decreasing levels
        mockRequest.setAttribute("one", "outer1");
        mockRequest.setAttribute("six", "outer6");

        Assert.assertEquals("{1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, two=twello, six=outer6}", vm.toString());
        Assert.assertEquals("[one, ten, two, six]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.put("one", "helloz");

        Assert.assertEquals("{2:{one=helloz},1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=helloz, ten=tieen, two=twello, six=outer6}", vm.toString());
        Assert.assertEquals("[one, ten, two, six]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twello},0:{one=outer1, ten=tieen, six=outer6}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, two=twello, six=outer6}", vm.toString());
        Assert.assertEquals("[one, ten, two, six]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=outer1, ten=tieen, six=outer6}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=outer1, ten=tieen, six=outer6}", vm.toString());
        Assert.assertEquals("[one, ten, six]", vm.getVariableNames().toString());

    }


    @Test
    public void test11() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a value");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}", vm.toString());

        vm.increaseLevel();

        vm.put("one", "hello");
        vm.remove("one");
        vm.put("one", "hello");
        vm.remove("two");
        vm.put("two", "twello");
        vm.put("two", "twellor");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}", vm.toString());

        vm.increaseLevel();

        vm.put("three", "twelloree");
        vm.put("one", "atwe");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{one=hello, two=twellor},0:{one=a value}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}", vm.toString());

        vm.setSelectionTarget("BIGFORM");

        Assert.assertEquals(2, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<BIGFORM>", vm.toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("BIGFORM", vm.getSelectionTarget());
        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>", vm.toString());

        vm.increaseLevel();

        vm.remove("two");
        vm.setSelectionTarget("SMALLFORM");

        Assert.assertEquals(4, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>", vm.toString());


        vm.increaseLevel();

        Assert.assertEquals(5, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("SMALLFORM", vm.getSelectionTarget());
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>", vm.toString());

        vm.put("four", "lotwss");

        Assert.assertEquals(5, vm.level());
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
        Assert.assertEquals("{5:{four=lotwss},4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}<SMALLFORM>", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals(4, vm.level());
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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals(3, vm.level());
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
        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals(2, vm.level());
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
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}<BIGFORM>", vm.toString());

        vm.setSelectionTarget("MEDIUMFORM");

        Assert.assertEquals(2, vm.level());
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
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<MEDIUMFORM>,1:{one=hello, two=twellor},0:{one=a value}}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, two=twellor}<MEDIUMFORM>", vm.toString());


        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}", vm.toString());


        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}", vm.toString());

        vm.setSelectionTarget("TOTALFORM");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>", vm.toString());


        vm.increaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
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
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>", vm.toString());

    }



    @Test
    public void test12() {

        final Map<String,Object> requestAttributes = new LinkedHashMap<String, Object>();
        final Map<String,Object[]> requestParameters = new LinkedHashMap<String, Object[]>();
        final Locale requestLocale = Locale.US;
        final HttpServletRequest mockRequest =
                TestMockServletUtil.createHttpServletRequest("WebVariablesMap", null, requestAttributes, requestParameters, requestLocale);
        final HttpServletResponse mockResponse = TestMockServletUtil.createHttpServletResponse();

        final Map<String,Object> servletContextAttributes = new LinkedHashMap<String, Object>();
        final ServletContext mockServletContext =
                TestMockServletUtil.createServletContext(servletContextAttributes);

        final WebVariablesMap vm = new WebVariablesMap(mockRequest, mockResponse, mockServletContext, null);

        vm.put("one", "a val1");

        vm.increaseLevel();

        vm.put("one", "a val2");
        vm.setSelectionTarget("FORM");

        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("FORM", vm.getSelectionTarget());

        vm.decreaseLevel();

        vm.put("one", "a val3");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

        vm.increaseLevel();

        vm.put("one", "a val4");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

        vm.increaseLevel();

        vm.put("one", "a val5");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());

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
