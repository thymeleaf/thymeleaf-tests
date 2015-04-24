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
package org.thymeleaf.context;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.thymeleaf.DialectConfiguration;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.EngineConfiguration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.text.TextRepositories;


public final class TestTemplateEngineConfigurationBuilder {



    public static IEngineConfiguration build() {
        final StandardDialect standardDialect = new StandardDialect();
        final DialectConfiguration standardDialectConfiguration = new DialectConfiguration(standardDialect);
        return new EngineConfiguration(Collections.singleton(standardDialectConfiguration), TextRepositories.createLimitedSizeCacheRepository());
    }


    public static IEngineConfiguration build(final IDialect dialect) {
        return build(Collections.singleton(dialect));
    }


    public static IEngineConfiguration build(final Set<IDialect> dialects) {
        final Set<DialectConfiguration> dialectConfigurations = new LinkedHashSet<DialectConfiguration>();
        for (final IDialect dialect : dialects) {
            dialectConfigurations.add(new DialectConfiguration(dialect));
        }
        return new EngineConfiguration(dialectConfigurations, TextRepositories.createLimitedSizeCacheRepository());
    }


    private TestTemplateEngineConfigurationBuilder() {
        super();
    }

}
