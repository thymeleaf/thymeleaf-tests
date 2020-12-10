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
import org.thymeleaf.spring5.util.SpringExpressionUtils;


public final class SpringExpressionUtilsTest {



    @Test
    public void testcontainsSpELInstantiationOrStatic() {

        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abcnew"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abcnew "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc3new "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc_new "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc$new "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc-new "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc new "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc.new "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc newnew"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abcnew ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc new ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc (new )w ewnew"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc (new)w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("abc +new )w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("new "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("new "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("newnew"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("new ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("(new )w ewnew"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("(new)w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("+new )w ewnew"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("!new )w ewnew"));

        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T()"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a)"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.SomeClass)"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.SomenewClass)"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some Class)"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some new Class)"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)new"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("T(a.b.Some newClass)new "));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("newT(a.b.Some newClass)new"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("aT(a.b.Some newClass)a"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some newClass) a"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic(" aT(a.b.Some newClass)a "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a-T(a.b.SomeClass)a"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("aT(a.b.SomeClass)a"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) a"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic(" aT(a.b.SomeClass)a "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic(" a T(a.b.SomeClass)a "));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some Class) a T(a.b.SomeClass)"));
        Assert.assertFalse(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.Some Class) a T(a.b.Some Class)"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass) )"));
        Assert.assertTrue(SpringExpressionUtils.containsSpELInstantiationOrStatic("a T(a.b.SomeClass))"));

    }

}
