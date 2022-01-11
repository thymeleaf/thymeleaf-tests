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
package org.thymeleaf.spring5.reactive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.thymeleaf.spring6.context.webflux.ISpringWebFluxContext;
import org.thymeleaf.spring6.reactive.data.Album;

public final class Spring5Reactive07Test extends AbstractSpring5ReactiveTest {



    @Test
    public void testEmptyNameFormBean() throws Exception {

        final Album album = new Album(1, null);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("album", album);

        final ISpringWebFluxContext context = ReactiveTestUtils.buildReactiveContext(model);

        testTemplate("reactive07", null, context, "reactive07-01");

    }


    @Test
    public void testFullFormBean() throws Exception {

        final Album album = new Album(100, "Whatever");

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("album", album);

        final ISpringWebFluxContext context = ReactiveTestUtils.buildReactiveContext(model);

        testTemplate("reactive07", null, context, "reactive07-02");

    }




}
