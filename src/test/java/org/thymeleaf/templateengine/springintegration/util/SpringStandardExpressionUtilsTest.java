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
import org.thymeleaf.spring4.util.SpringStandardExpressionUtils;


public final class SpringStandardExpressionUtilsTest {



    @Test
    public void testcontainsSpELInstantiationOrStatic() {

        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abcnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abcnew "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc3new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc_new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc$new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc-new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc.new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc newnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abcnew ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc new ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc (new )w ewnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc (new)w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("abc +new )w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("new "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("newnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("new ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("(new )w ewnew"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("(new)w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("+new )w ewnew"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("!new )w ewnew"));

        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T()"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.SomeClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.SomenewClass)"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some Class)"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some new Class)"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)new"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)new "));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("newT(a.b.Some newClass)new"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("aT(a.b.Some newClass)a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some newClass) a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic(" aT(a.b.Some newClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a-T(a.b.SomeClass)a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("aT(a.b.SomeClass)a"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) a"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic(" aT(a.b.SomeClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic(" a T(a.b.SomeClass)a "));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some Class) a T(a.b.SomeClass)"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some Class) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) )"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass))"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some(Class) )"));
        Assert.assertTrue(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some)Class) )"));
        Assert.assertFalse(SpringStandardExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Som(e)Class) )"));

    }

}
