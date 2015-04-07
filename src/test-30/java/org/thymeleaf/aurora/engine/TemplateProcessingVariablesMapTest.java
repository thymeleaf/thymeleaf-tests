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

import org.junit.Assert;
import org.junit.Test;


public final class TemplateProcessingVariablesMapTest {



    @Test
    public void test() {

        final TemplateProcessingVariablesMap vm = new TemplateProcessingVariablesMap();

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

    
}
