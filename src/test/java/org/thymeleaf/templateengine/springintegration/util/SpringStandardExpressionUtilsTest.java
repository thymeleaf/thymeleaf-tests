/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
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
package org.thymeleaf.templateengine.springintegration.util;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.spring6.util.SpringStandardExpressionUtils;


public final class SpringStandardExpressionUtilsTest {



    @Test
    public void testcontainsSpELInstantiationOrStaticOrParam() {

        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abcnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abcnew "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc3new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc_new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc$new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc-new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc.new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc newnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abcnew ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc new ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc (new )w ewnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc (new)w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("abc +new )w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("newnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("new ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("(new )w ewnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("(new)w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("+new )w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("!new )w ewnew"));

        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T()"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.SomeClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.SomenewClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.Some Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.Some newClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.Some new Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.Some newClass)new"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("T(a.b.Some newClass)new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("newT(a.b.Some newClass)new"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("aT(a.b.Some newClass)a"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Some newClass) a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" aT(a.b.Some newClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a-T(a.b.SomeClass)a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("aT(a.b.SomeClass)a"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.SomeClass) a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" aT(a.b.SomeClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" a T(a.b.SomeClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.SomeClass) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Some Class) a T(a.b.SomeClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Some Class) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.SomeClass) )"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.SomeClass))"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Some(Class) )"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Some)Class) )"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("a T(a.b.Som(e)Class) )"));


        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("param.a"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" param.a"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" param['a']"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam("_param['a']"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStaticOrParam(" param_a"));

    }

}
