/*
 * =============================================================================
 *
 *   Copyright (c) 2012-2014, The ATTOPARSER team (http://www.attoparser.org)
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
package org.thymeleaf.engine.markup.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.attoparser.IMarkupHandler;
import org.attoparser.discard.DiscardMarkupHandler;
import org.attoparser.output.OutputMarkupHandler;
import org.attoparser.select.BlockSelectorMarkupHandler;
import org.attoparser.select.IMarkupSelectorReferenceResolver;

/*
 *
 * @author Daniel Fernandez
 * @since 2.0.0
 */
public class TemplateFragmentMarkupReferenceResolverTest extends TestCase {


    public void test() throws Exception {

        final TemplateFragmentMarkupReferenceResolver rr01 = TemplateFragmentMarkupReferenceResolver.forPrefix(null);
        final TemplateFragmentMarkupReferenceResolver rr02 = TemplateFragmentMarkupReferenceResolver.forPrefix("th");

        final TemplateFragmentMarkupReferenceResolver rr03 = TemplateFragmentMarkupReferenceResolver.forPrefix(null);
        final TemplateFragmentMarkupReferenceResolver rr04 = TemplateFragmentMarkupReferenceResolver.forPrefix("th");
        assertSame(rr01, rr03);
        assertSame(rr02, rr04);

        final TemplateFragmentMarkupReferenceResolver rr05 = TemplateFragmentMarkupReferenceResolver.forPrefix("q");

        final String result01 = rr01.resolveSelectorFromReference("abc");
        final String result02 = rr01.resolveSelectorFromReference("abc");
        assertEquals("/[fragment='abc' or data-fragment='abc']", result01);
        assertSame(result01, result02);

        final String result03 = rr02.resolveSelectorFromReference("abc");
        final String result04 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or data-th-fragment='abc']", result03);
        assertSame(result03, result04);

        final String result05 = rr05.resolveSelectorFromReference("abc");
        final String result06 = rr05.resolveSelectorFromReference("abc");
        assertEquals("/[q:fragment='abc' or data-q-fragment='abc']", result05);
        assertSame(result05, result06);

        final String result07 = rr02.resolveSelectorFromReference("abc");
        final String result08 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or data-th-fragment='abc']", result07);
        assertSame(result07, result08);

    }


}
