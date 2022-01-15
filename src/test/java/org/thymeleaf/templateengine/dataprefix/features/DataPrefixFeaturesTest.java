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
package org.thymeleaf.templateengine.dataprefix.features;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;
import org.thymeleaf.tests.util.TestExecutorFactory;


public class DataPrefixFeaturesTest {


    public DataPrefixFeaturesTest() {
        super();
    }




    @Test
    public void testText() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/text");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testLink() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/link");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testUtil() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/util");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testExpression() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/expression");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testMessages() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/messages");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testNormalization() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.execute("classpath:templateengine/dataprefix/features/normalization");

        Assert.assertTrue(executor.isAllOK());

    }



}
