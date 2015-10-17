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
package org.thymeleaf.context;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.standard.inline.StandardTextInliner;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.TemplateResolution;


public final class EngineContextTest {


    private static final Locale LOCALE = Locale.US;



    @Test
    public void test01() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

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
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final Map<String,Object> starting = new LinkedHashMap<String, Object>();
        starting.put("one", "ha");
        starting.put("ten", "tieen");

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, starting);

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
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

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

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.removeVariable("one");

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

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

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
    public void test04() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

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

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.setVariable("one", "atwe");

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.increaseLevel();
        vm.increaseLevel();
        vm.increaseLevel();

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.setVariable("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree, four=lotwss}(test01)", vm.toString());

        vm.setVariable("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=itwiii, three=twelloree, four=lotwss}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());

    }





    @Test
    public void test05() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

        vm.setVariable("one", "a value");

        Assert.assertEquals("{0:{one=a value}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=a value}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.setVariable("one", "two values");

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{0:{}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}(test01)", vm.toString());
        Assert.assertEquals(Collections.emptySet(), vm.getVariableNames());

        vm.setVariable("one", "two values");

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.increaseLevel();

        vm.setVariable("one", "hello");

        Assert.assertEquals("{1:{one=hello},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{1:{one=(*removed*)},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
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
        vm.setInliner(StandardTextInliner.INSTANCE);

        Assert.assertEquals("{1:{one=hello, two=twello}[StandardTextInliner],0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello, two=twello}[StandardTextInliner](test01)", vm.toString());
        Assert.assertEquals(createSet("one","two"), vm.getVariableNames());

        vm.removeVariable("two");

        Assert.assertEquals("{1:{one=hello}[StandardTextInliner],0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=hello}[StandardTextInliner](test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.removeVariable("one");

        Assert.assertEquals("{1:{one=(*removed*)}[StandardTextInliner],0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{}[StandardTextInliner](test01)", vm.toString());
        Assert.assertEquals(Collections.emptySet(), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

        vm.increaseLevel();
        vm.setVariable("two", "twellor");

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","two"), vm.getVariableNames());

        vm.increaseLevel();
        vm.setVariable("three", "twelloree");

        Assert.assertEquals("{2:{three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","two"), vm.getVariableNames());

        vm.setVariable("one", "atwe");

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","two"), vm.getVariableNames());

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.increaseLevel();

        vm.removeVariable("two");

        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.increaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.setVariable("four", "lotwss");

        Assert.assertEquals("{5:{four=lotwss},3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","four"), vm.getVariableNames());

        vm.setVariable("two", "itwiii");

        Assert.assertEquals("{5:{four=lotwss, two=itwiii},3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[5]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree, four=lotwss, two=itwiii}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","four","two"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[4]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[3]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{two=twellor},0:{one=two values}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","three","two"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{1:{two=twellor},0:{one=two values}(test01)}[1]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values, two=twellor}(test01)", vm.toString());
        Assert.assertEquals(createSet("one","two"), vm.getVariableNames());

        vm.decreaseLevel();

        Assert.assertEquals("{0:{one=two values}(test01)}[0]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=two values}(test01)", vm.toString());
        Assert.assertEquals(createSet("one"), vm.getVariableNames());

    }


    @Test
    public void test06() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

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
    public void test07() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

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
        Assert.assertEquals("{2:{one=atwe, three=twelloree},1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[3]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[4]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[5]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{5:{four=lotwss},4:<SMALLFORM>,3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[5]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{4:<SMALLFORM>,3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[4]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{3:{two=(*removed*)},2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[3]", vm.getStringRepresentationByLevel());
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
        Assert.assertEquals("{2:{one=atwe, three=twelloree}<BIGFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<BIGFORM>(test01)", vm.toString());

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
        Assert.assertEquals("{2:{one=atwe, three=twelloree}<MEDIUMFORM>,1:{one=hello, two=twellor},0:{one=a value}(test01)}[2]", vm.getStringRepresentationByLevel());
        Assert.assertEquals("{one=atwe, two=twellor, three=twelloree}<MEDIUMFORM>(test01)", vm.toString());


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
    public void test08() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

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
    public void test09() {

        final IEngineConfiguration configuration = TestTemplateEngineConfigurationBuilder.build();
        final TemplateResolution templateResolution1 = TestTemplateResolutionConfigurationBuilder.build("test01", TemplateMode.HTML);
        final TemplateResolution templateResolution2 = TestTemplateResolutionConfigurationBuilder.build("test02", TemplateMode.HTML);
        final TemplateResolution templateResolution3 = TestTemplateResolutionConfigurationBuilder.build("test03", TemplateMode.XML);
        final TemplateResolution templateResolution4 = TestTemplateResolutionConfigurationBuilder.build("test04", TemplateMode.TEXT);

        final EngineContext vm = new EngineContext(configuration, templateResolution1, LOCALE, null);

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateResolution1, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1), vm.getTemplateResolutionStack());

        vm.setVariable("one", "a value");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("a value", vm.getVariable("one"));

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateResolution1, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1), vm.getTemplateResolutionStack());


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
        Assert.assertSame(templateResolution1, vm.getTemplateResolution());

        vm.setTemplateResolution(templateResolution2);

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateResolution2, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1, templateResolution2), vm.getTemplateResolutionStack());

        vm.setVariable("one", "hello");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertEquals("hello", vm.getVariable("one"));

        vm.removeVariable("one");

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateResolution2, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1, templateResolution2), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution1, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1), vm.getTemplateResolutionStack());

        vm.setVariable("two", "twellor");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertTrue(vm.containsVariable("two"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertEquals("twellor", vm.getVariable("two"));

        vm.setTemplateResolution(templateResolution2);

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.HTML, vm.getTemplateMode());
        Assert.assertSame(templateResolution2, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2), vm.getTemplateResolutionStack());

        vm.setTemplateResolution(templateResolution3);

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

        vm.removeVariable("two");

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertTrue(vm.containsVariable("three"));
        Assert.assertEquals("atwe", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertEquals("twelloree", vm.getVariable("three"));

        vm.increaseLevel();

        Assert.assertEquals(TemplateMode.XML, vm.getTemplateMode());
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution3, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2,templateResolution3), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution2, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution2), vm.getTemplateResolutionStack());

        vm.setTemplateResolution(templateResolution4);

        Assert.assertEquals(TemplateMode.TEXT, vm.getTemplateMode());
        Assert.assertSame(templateResolution4, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1,templateResolution4), vm.getTemplateResolutionStack());

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
        Assert.assertSame(templateResolution1, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution1), vm.getTemplateResolutionStack());

        vm.setTemplateResolution(templateResolution4);

        Assert.assertEquals(TemplateMode.TEXT, vm.getTemplateMode());
        Assert.assertSame(templateResolution4, vm.getTemplateResolution());
        Assert.assertEquals(Arrays.asList(templateResolution4), vm.getTemplateResolutionStack());

        Assert.assertTrue(vm.containsVariable("one"));
        Assert.assertFalse(vm.containsVariable("two"));
        Assert.assertFalse(vm.containsVariable("three"));
        Assert.assertFalse(vm.containsVariable("four"));
        Assert.assertEquals("two values", vm.getVariable("one"));
        Assert.assertNull(vm.getVariable("two"));
        Assert.assertNull(vm.getVariable("three"));
        Assert.assertNull(vm.getVariable("four"));

    }


    
    private static Set<String> createSet(final String... elements) {
        final Set<String> result = new LinkedHashSet<String>();
        for (final String element : elements) {
            result.add(element);
        }
        return result;
    }
    
    
}
