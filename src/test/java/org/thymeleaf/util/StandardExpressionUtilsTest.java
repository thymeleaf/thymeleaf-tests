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
package org.thymeleaf.util;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.standard.util.StandardExpressionUtils;


public final class StandardExpressionUtilsTest {



    @Test
    public void testMightNeedExpressionObjects() {

        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("${execInfo}"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("${exexecInfo}"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("${exexecInfofo}"));
        Assert.assertFalse(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad lj"));
        Assert.assertFalse(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad\n"));
        Assert.assertFalse(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad\nexecInf"));
        Assert.assertFalse(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad\naxecInfo\na"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad\nexecInfo\na"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad#\n"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("alasdasd#isad\nexecInf"));
        Assert.assertTrue(StandardExpressionUtils.mightNeedExpressionObjects("alasdasdisad\n#axecInfo\na"));

    }


    @Test
    public void testContainsOGNLInstantiationOrStatic() {

        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abcnew"));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abcnew "));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc3new "));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc_new "));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc$new "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc-new "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc new "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc.new "));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc newnew"));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abcnew ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc new ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc new w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc (new )w ewnew"));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc (new)w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("abc +new )w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new "));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("newnew"));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("(new )w ewnew"));
        Assert.assertFalse(StandardExpressionUtils.containsOGNLInstantiationOrStatic("(new)w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("+new )w ewnew"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("!new )w ewnew"));

        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.SomeClass@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.SomenewClass@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.Some Class@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.Some newClass@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.Some new Class@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.Some newClass@new"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("@a.b.Some newClass@new "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("new@a.b.Some newClass@new"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a@a.b.Some newClass@a"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.Some newClass@ a"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic(" a@a.b.Some newClass@a "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a@a.b.SomeClass@a"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.SomeClass@ a"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic(" a@a.b.SomeClass@a "));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.SomeClass@ a @a.b.Some Class@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.Some Class@ a @a.b.SomeClass@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.Some Class@ a @a.b.Some Class@"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.SomeClass@ @"));
        Assert.assertTrue(StandardExpressionUtils.containsOGNLInstantiationOrStatic("a @a.b.SomeClass@@"));

    }

}
