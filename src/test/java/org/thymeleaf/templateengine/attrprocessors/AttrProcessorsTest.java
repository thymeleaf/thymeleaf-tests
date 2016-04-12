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
package org.thymeleaf.templateengine.attrprocessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;




@RunWith(Parameterized.class)
public class AttrProcessorsTest {



    private final int throttleStep;

    
    public AttrProcessorsTest(final Integer throttleStep) {
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
    public void testRemove() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/remove");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testIf() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/if");

        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testUnless() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/unless");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testInline() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/inline");
        
        Assert.assertTrue(executor.isAllOK());
        
    }

    @Test
    public void testInclude() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/include");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testInsert() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/insert");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testSubstituteby() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/substituteby");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testReplace() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/replace");

        Assert.assertTrue(executor.isAllOK());

    }

    @Theory
    public void testEach(final int throttleStep) throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/each");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testObject() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/object");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testAttr() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/attr");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    @Test
    public void testSimpleValue() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/simplevalue");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    
    @Test
    public void testDoubleValue() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/doublevalue");
        
        Assert.assertTrue(executor.isAllOK());
        
    }

    
    @Test
    public void testAppendPrepend() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/appendprepend");
        
        Assert.assertTrue(executor.isAllOK());
        
    }

    
    @Test
    public void testFixedValue() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/fixedvalue");
        
        Assert.assertTrue(executor.isAllOK());
        
    }


    @Test
    public void testSwitch() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/switch");

        Assert.assertTrue(executor.isAllOK());

    }


    @Test
    public void testWith() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/with");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testDOMEvent() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/domevent");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testAssert() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/assert");

        Assert.assertTrue(executor.isAllOK());

    }

    @Test
    public void testDefault() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.setThrottleStep(this.throttleStep);
        executor.execute("classpath:templateengine/attrprocessors/default");

        Assert.assertTrue(executor.isAllOK());

    }


    
}
