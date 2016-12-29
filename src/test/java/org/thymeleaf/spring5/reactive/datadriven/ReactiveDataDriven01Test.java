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
package org.thymeleaf.spring5.reactive.datadriven;

import java.util.List;

import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.context.reactive.ReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.reactive.AbstractSpring5ReactiveTest;
import org.thymeleaf.spring5.reactive.data.Album;
import org.thymeleaf.spring5.reactive.data.AlbumRepository;
import reactor.core.publisher.Flux;

public final class ReactiveDataDriven01Test extends AbstractSpring5ReactiveTest {



    @Test
    public void testDataDriven01_01() throws Exception {

        final Context ctx1 = new Context();

        testTemplate("datadriven/datadriven01", null, ctx1, "datadriven/datadriven01-01");

    }


    @Test
    public void testDataDriven01_02() throws Exception {

        final List<Album> albums = AlbumRepository.findAllAlbums();

        final Context ctx1 = new Context();
        ctx1.setVariable("albums", albums);

        testTemplate("datadriven/datadriven01", null, ctx1, "datadriven/datadriven01-02");

    }



    @Test
    public void testDataDriven01_03() throws Exception {

        final List<Album> albums = AlbumRepository.findAllAlbums();

        final Context ctx1 = new Context();
        ctx1.setVariable("albums", new ReactiveDataDriverContextVariable(Flux.fromIterable(albums), 10));

        testTemplate("datadriven/datadriven01", null, ctx1, "datadriven/datadriven01-02");

    }


}
