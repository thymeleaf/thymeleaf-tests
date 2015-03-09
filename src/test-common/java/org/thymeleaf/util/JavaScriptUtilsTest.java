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
package org.thymeleaf.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Gregory Fouquet
 * 
 */
public class JavaScriptUtilsTest {
    public static enum SimpleEnum {
        FIRST(true);
        private final boolean odd;

        private SimpleEnum(boolean odd) {
            this.odd = odd;
        }

        public boolean isOdd() {
            return odd;
        }
    }

    /**
     * The items of this enum are instances of anonymous classes.
     * 
     * @author Gregory Fouquet
     *
     */
    public static enum AnonymousEnum {
        FIRST {
            @Override
            public boolean isOdd() {
                return true;
            }
        };
        public abstract boolean isOdd();
    }

    public JavaScriptUtilsTest() {
        super();
    }

    @Test
    public void testPrintTestEnum() {
        String res = JavaScriptUtils.print(SimpleEnum.FIRST);
        assertThat(res, is("{'$type':'SimpleEnum','$name':'FIRST'}"));
    }

    @Test
    public void testPrintAnonymousEnum() {
        String res = JavaScriptUtils.print(AnonymousEnum.FIRST);
        assertThat(res, is("{'$type':'AnonymousEnum','$name':'FIRST'}"));
    }
}
