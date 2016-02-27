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
package org.thymeleaf.templateengine.features;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templateengine.aggregation.dialect.Dialect01;
import org.thymeleaf.templateengine.features.interaction.InteractionDialect01;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;





public class FeaturesTest {
    
    
    public FeaturesTest() {
        super();
    }




    @Test
    public void testText() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/text");

        Assert.assertTrue(executor.isAllOK());

    }

    
    
    @Test
    public void testLink() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/link");
        
        Assert.assertTrue(executor.isAllOK());
        
    }


    @Test
    public void testUtil() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/util");
        
        Assert.assertTrue(executor.isAllOK());
        
    }


    @Test
    public void testExpression() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/expression");

        Assert.assertTrue(executor.isAllOK());

    }

    
    @Test
    public void testMessages() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/messages");
        
        Assert.assertTrue(executor.isAllOK());
        
    }


    @Test
    public void testRequest() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/request");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testResponse() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/response");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testServletContext() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/servletcontext");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testSession() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/session");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testNormalization() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/normalization");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testExecInfo() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/execinfo");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testAccessRestrictions() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/accessrestrictions");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testInliningStandard() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/inlining/standard");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testInliningNoStandard() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setDialects(Arrays.asList(new IDialect[]{new Dialect01()}));
        executor.execute("classpath:templateengine/features/inlining/nostandard");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testInliningInteraction() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setDialects(Arrays.asList(new IDialect[]{new StandardDialect(), new InteractionDialect01()}));
        executor.execute("classpath:templateengine/features/inlining/interaction");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testLazy() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:templateengine/features/lazy");

        Assert.assertTrue(executor.isAllOK());

    }





}
