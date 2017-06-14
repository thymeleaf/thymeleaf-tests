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
package org.thymeleaf.templateengine.springintegration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateengine.springintegration.context.ErrorsSpringIntegrationWebProcessingContextBuilder;
import org.thymeleaf.templateengine.springintegration.context.SpringIntegrationWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;
import org.thymeleaf.tests.util.SpringSpecificVersionUtils;
import org.thymeleaf.tests.util.TestExecutorFactory;


@RunWith(Parameterized.class)
public class SpringIntegrationTest {



    private final int throttleStep;


    public SpringIntegrationTest(final Integer throttleStep) {
        super();
        this.throttleStep = throttleStep.intValue();
    }


    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {

        final int[] throttleSteps = new int[] { Integer.MAX_VALUE, 1000, 100, 11, 9, 5, 1};

        final List<Object[]> params = new ArrayList<Object[]>();
        for (int i = 0; i < throttleSteps.length; i++) {
            params.add(new Object[] { Integer.valueOf(i) });
        }
        return params;

    }




    @Test
    public void testForm() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new SpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/form");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testFormCompiledSpEL() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new SpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance(true)}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/form");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testErrors() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new ErrorsSpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/errors");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testErrorsCompiledSpEL() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new ErrorsSpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance(true)}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/errors");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testBeans() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(
                new SpringIntegrationWebProcessingContextBuilder("classpath:templateengine/springintegration/applicationContext-beans.xml"));
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/beans");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testBeansCompiledSpEL() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(
                new SpringIntegrationWebProcessingContextBuilder("classpath:templateengine/springintegration/applicationContext-beans.xml"));
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance(true)}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/beans");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testExpression() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new SpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/expression");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testExpressionCompiledSpEL() throws Exception {

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(new SpringIntegrationWebProcessingContextBuilder());
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance(true)}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/expression");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testMvc() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/mvc/applicationContext.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/mvc");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testMvcCompiledSpEL() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/mvc/applicationContext.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance(true)}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/mvc");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testXmlNs() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation(null);

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/xmlns");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testRequestDataFormWith() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/requestdata/applicationContext-with.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/requestdata/formwith");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testRequestDataFormWithout() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/requestdata/applicationContext-without.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/requestdata/formwithout");

        Assert.assertTrue(executor.isAllOK());

    }



    @Test
    public void testRequestDataUrlsWith() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/requestdata/applicationContext-with.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/requestdata/urlswith");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testRequestDataUrlsWithout() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/requestdata/applicationContext-without.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/requestdata/urlswithout");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testRequestUrlsExpOobject() throws Exception {

        final SpringWebProcessingContextBuilder contextBuilder = new SpringWebProcessingContextBuilder();
        contextBuilder.setApplicationContextConfigLocation("classpath:templateengine/springintegration/requestdata/applicationContext-with.xml");

        final TestExecutor executor = TestExecutorFactory.createTestExecutor();
        executor.setProcessingContextBuilder(contextBuilder);
        executor.setDialects(Arrays.asList(new IDialect[] { SpringSpecificVersionUtils.createSpringStandardDialectInstance()}));
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/springintegration/requestdata/urlsexpobject");

        Assert.assertTrue(executor.isAllOK());

    }



}
