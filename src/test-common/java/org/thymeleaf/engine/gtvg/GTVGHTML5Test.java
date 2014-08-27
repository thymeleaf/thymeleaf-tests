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
package org.thymeleaf.engine.gtvg;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;





public class GTVGHTML5Test {
    
    
    public GTVGHTML5Test() {
        super();
    }
    
    
    /*
     * Given Thymeleaf 3.0 changes the way in which XHTML is parsed (basically, it is now treated as any other
     * type of markup), structures coming from the XHTML DTD definitions like shape="rect" in <a>'s and things like
     * that do not appear on output anymore. So the "normal" GTVG application has been moved to each of the per-version
     * profiles (2.0, 2.1, 3.0) in order to specify different outputs for them, and an HTML5-ized version of the GTVG
     * app (this one) has been left here mainly so that it can be a part of the benchmarks.
     */
    
    @Test
    public void testGTVGHTML5() throws Exception {

        final TestExecutor executor = new TestExecutor();
        executor.execute("classpath:engine/gtvg");
        
        Assert.assertTrue(executor.isAllOK());
        
    }
    
    
    
}
