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
package org.thymeleaf.standard.serializer;

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Gregory Fouquet
 *
 */
public class StandardJavaScriptSerializerTest {



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


    public static enum AnonymousEnum {
        FIRST {
            @Override
            public boolean isOdd() {
                return true;
            }
        };
        public abstract boolean isOdd();
    }

    public StandardJavaScriptSerializerTest() {
        super();
    }



    @Test
    public void testPrintTestEnumDefault() {

        final IStandardJavaScriptSerializer serializer = new StandardJavaScriptSerializer(false);

        final StringWriter stringWriter = new StringWriter();
        serializer.serializeValue(SimpleEnum.FIRST, stringWriter);
        Assert.assertEquals("\"FIRST\"", stringWriter.toString());

    }


    @Test
    public void testPrintTestEnumJackson() {

        final IStandardJavaScriptSerializer serializer = new StandardJavaScriptSerializer(true);

        final StringWriter stringWriter = new StringWriter();
        serializer.serializeValue(SimpleEnum.FIRST, stringWriter);
        Assert.assertEquals("\"FIRST\"", stringWriter.toString());

    }


    @Test
    public void testPrintAnonymousEnumDefault() {

        final IStandardJavaScriptSerializer serializer = new StandardJavaScriptSerializer(false);

        final StringWriter stringWriter = new StringWriter();
        serializer.serializeValue(AnonymousEnum.FIRST, stringWriter);
        Assert.assertEquals("\"FIRST\"", stringWriter.toString());

    }


    @Test
    public void testPrintAnonymousEnumJackson() {

        final IStandardJavaScriptSerializer serializer = new StandardJavaScriptSerializer(true);

        final StringWriter stringWriter = new StringWriter();
        serializer.serializeValue(AnonymousEnum.FIRST, stringWriter);
        Assert.assertEquals("\"FIRST\"", stringWriter.toString());

    }


}