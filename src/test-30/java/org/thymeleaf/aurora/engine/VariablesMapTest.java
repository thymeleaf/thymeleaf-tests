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

        final VariablesMap vm = new VariablesMap(starting);

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

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.remove("one");

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));

        vm.put("one", "two values");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("two values", vm.getVariable("one"));

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.remove("one");

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.containsVariable("one"));
        Assert.assertNull(vm.getVariable("one"));

        vm.put("one", "hello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.remove("two");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("twello"));

        vm.put("two", "twello");

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("hello", vm.getVariable("one"));
        Assert.assertEquals("twello", vm.getVariable("two"));

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

        vm.remove("two");

        Assert.assertEquals(3, vm.level());
        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

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
    public void test04() {

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}[true]", vm.toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());

        vm.increaseLevel();
        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[true]", vm.toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[true]", vm.toString());

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}[true]", vm.toString());

        vm.put("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

        vm.put("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree, four=lotwss}[true]", vm.toString());

        vm.put("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=itwiii, three=twelloree, four=lotwss}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[true]", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());

    }





    @Test
    public void test05() {

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");

        Assert.assertEquals("{0:{one=a value}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{0:{}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}[true]", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.put("one", "two values");

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}[true]", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.put("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.put("two", "twello");

        Assert.assertEquals("{1:{one=hello, two=twello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}[true]", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.remove("two");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.remove("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}[true]", vm.toString());
        Assert.assertEquals("[]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

        vm.increaseLevel();
        vm.put("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[true]", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.increaseLevel();
        vm.put("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, two, three]", vm.getVariableNames().toString());

        vm.put("one", "atwe");

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, two, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        vm.remove("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.increaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.put("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}[true]", vm.toString());
        Assert.assertEquals("[one, three, four]", vm.getVariableNames().toString());

        vm.put("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss, two=itwiii}[true]", vm.toString());
        Assert.assertEquals("[one, three, four, two]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, three]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{two=twellor},0:{one=two values}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());
        Assert.assertEquals("[one, two, three]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}[true]", vm.toString());
        Assert.assertEquals("[one, two]", vm.getVariableNames().toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}[true]", vm.toString());
        Assert.assertEquals("[one]", vm.getVariableNames().toString());

    }


    @Test
    public void test06() {

        final VariablesMap vm = new VariablesMap(null);

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
    public void test07() {

        final VariablesMap vm = new VariablesMap(null);

        vm.put("one", "a value");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}[true]", vm.toString());

        vm.increaseLevel();

        vm.put("one", "hello");
        vm.remove("one");
        vm.put("one", "hello");
        vm.remove("two");
        vm.put("two", "twello");
        vm.put("two", "twellor");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}[true]", vm.toString());

        vm.increaseLevel();

        vm.put("three", "twelloree");
        vm.put("one", "atwe");
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{2:{three=twelloree, one=atwe},1:{one=hello, two=twellor},0:{one=a value}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}[true]", vm.toString());

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
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<BIGFORM>[true]", vm.toString());

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
        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>[true]", vm.toString());

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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>[true]", vm.toString());


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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>[true]", vm.toString());

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
        Assert.assertEquals("{5:{four=lotwss},4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}<SMALLFORM>[true]", vm.toString());

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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<SMALLFORM>[true]", vm.toString());

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
        Assert.assertEquals("{3:{two=(*removed*)},2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}<BIGFORM>[true]", vm.toString());

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
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<BIGFORM>[true]", vm.toString());

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
        Assert.assertEquals("{2:{three=twelloree, one=atwe}<MEDIUMFORM>,1:{one=hello, two=twellor},0:{one=a value}[true]}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<MEDIUMFORM>[true]", vm.toString());


        vm.decreaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{1:{one=hello, two=twellor},0:{one=a value}[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twellor}[true]", vm.toString());


        vm.decreaseLevel();

        Assert.assertEquals(0, vm.level());
        Assert.assertFalse(vm.hasSelectionTarget());
        Assert.assertNull(vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}[true]", vm.toString());

        vm.setSelectionTarget("TOTALFORM");

        Assert.assertEquals(0, vm.level());
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>[true]", vm.toString());


        vm.increaseLevel();

        Assert.assertEquals(1, vm.level());
        Assert.assertTrue(vm.hasSelectionTarget());
        Assert.assertEquals("TOTALFORM", vm.getSelectionTarget());
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>[true]}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>[true]", vm.toString());

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
        Assert.assertEquals("{0:{one=a value}<TOTALFORM>[true]}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}<TOTALFORM>[true]", vm.toString());

    }


    @Test
    public void test08() {

        final VariablesMap vm = new VariablesMap(null);

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


}
