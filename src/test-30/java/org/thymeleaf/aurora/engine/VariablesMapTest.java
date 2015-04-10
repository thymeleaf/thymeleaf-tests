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

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


public final class VariablesMapTest {



    @Test
    public void test01() {

        final VariablesMap vm = new VariablesMap(null);

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

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final VariablesMap vm = new VariablesMap(starting);

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

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("a value", vm.get("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("two values", vm.get("one"));

        vm.remove("one");

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertNull(vm.get("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("two values", vm.get("one"));

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("hello", vm.get("one"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.contains("one"));
        Assert.assertNull(vm.get("one"));

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("hello", vm.get("one"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertNull(vm.get("twello"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));

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

        vm.remove("two");

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertFalse(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("atwe", vm.get("one"));
        Assert.assertNull(vm.get("two"));
        Assert.assertEquals("twelloree", vm.get("three"));

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
    public void test04() {

        final VariablesMap vm = new VariablesMap(null);

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
    public void test05() {

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}", vm.toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());

        vm.remove("one");

        Assert.assertEquals("{0:{}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}", vm.toString());

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}", vm.toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}", vm.toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}", vm.toString());

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

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());

        vm.increaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());

        vm.put("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}", vm.toString());

        vm.put("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss, two=itwiii}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}", vm.toString());

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
    public void test06() {

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertEquals("a value", vm.get("one"));

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

        vm.put("three", "trwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("trwello", vm.get("three"));

        vm.put("four", "fwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("trwello", vm.get("three"));
        Assert.assertEquals("fwello", vm.get("four"));

        vm.put("five", "vwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertTrue(vm.contains("five"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("trwello", vm.get("three"));
        Assert.assertEquals("fwello", vm.get("four"));
        Assert.assertEquals("vwello", vm.get("five"));

        vm.put("six", "swello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertTrue(vm.contains("five"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("trwello", vm.get("three"));
        Assert.assertEquals("fwello", vm.get("four"));
        Assert.assertEquals("vwello", vm.get("five"));
        Assert.assertEquals("swello", vm.get("six"));

        vm.put("seven", "svwello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.contains("one"));
        Assert.assertTrue(vm.contains("two"));
        Assert.assertTrue(vm.contains("three"));
        Assert.assertTrue(vm.contains("four"));
        Assert.assertTrue(vm.contains("five"));
        Assert.assertTrue(vm.contains("six"));
        Assert.assertTrue(vm.contains("seven"));
        Assert.assertEquals("hello", vm.get("one"));
        Assert.assertEquals("twello", vm.get("two"));
        Assert.assertEquals("trwello", vm.get("three"));
        Assert.assertEquals("fwello", vm.get("four"));
        Assert.assertEquals("vwello", vm.get("five"));
        Assert.assertEquals("swello", vm.get("six"));
        Assert.assertEquals("svwello", vm.get("seven"));

    }

}
