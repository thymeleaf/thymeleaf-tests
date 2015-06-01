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
package org.thymeleaf.templateparser;

import junit.framework.TestCase;

/*
 *
 * @author Daniel Fernandez
 * @since 2.0.0
 */
public class TemplateFragmentMarkupReferenceResolverTest extends TestCase {


    public void testHtml() throws Exception {

        final TemplateFragmentMarkupReferenceResolver rr01 = TemplateFragmentMarkupReferenceResolver.forPrefix(true, null);
        final TemplateFragmentMarkupReferenceResolver rr02 = TemplateFragmentMarkupReferenceResolver.forPrefix(true, "th");

        final TemplateFragmentMarkupReferenceResolver rr03 = TemplateFragmentMarkupReferenceResolver.forPrefix(true, null);
        final TemplateFragmentMarkupReferenceResolver rr04 = TemplateFragmentMarkupReferenceResolver.forPrefix(true, "th");
        assertSame(rr01, rr03);
        assertSame(rr02, rr04);

        final TemplateFragmentMarkupReferenceResolver rr05 = TemplateFragmentMarkupReferenceResolver.forPrefix(true, "q");

        final String result01 = rr01.resolveSelectorFromReference("abc");
        final String result02 = rr01.resolveSelectorFromReference("abc");
        assertEquals("/[fragment='abc' or data-fragment='abc' or fragment^='abc(' or data-fragment^='abc(' or fragment^='abc (' or data-fragment^='abc (']", result01);
        assertSame(result01, result02);

        final String result03 = rr02.resolveSelectorFromReference("abc");
        final String result04 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or data-th-fragment='abc' or th:fragment^='abc(' or data-th-fragment^='abc(' or th:fragment^='abc (' or data-th-fragment^='abc (']", result03);
        assertSame(result03, result04);

        final String result05 = rr05.resolveSelectorFromReference("abc");
        final String result06 = rr05.resolveSelectorFromReference("abc");
        assertEquals("/[q:fragment='abc' or data-q-fragment='abc' or q:fragment^='abc(' or data-q-fragment^='abc(' or q:fragment^='abc (' or data-q-fragment^='abc (']", result05);
        assertSame(result05, result06);

        final String result07 = rr02.resolveSelectorFromReference("abc");
        final String result08 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or data-th-fragment='abc' or th:fragment^='abc(' or data-th-fragment^='abc(' or th:fragment^='abc (' or data-th-fragment^='abc (']", result07);
        assertSame(result07, result08);

    }


    public void testXml() throws Exception {

        final TemplateFragmentMarkupReferenceResolver rr01 = TemplateFragmentMarkupReferenceResolver.forPrefix(false, null);
        final TemplateFragmentMarkupReferenceResolver rr02 = TemplateFragmentMarkupReferenceResolver.forPrefix(false, "th");

        final TemplateFragmentMarkupReferenceResolver rr03 = TemplateFragmentMarkupReferenceResolver.forPrefix(false, null);
        final TemplateFragmentMarkupReferenceResolver rr04 = TemplateFragmentMarkupReferenceResolver.forPrefix(false, "th");
        assertSame(rr01, rr03);
        assertSame(rr02, rr04);

        final TemplateFragmentMarkupReferenceResolver rr05 = TemplateFragmentMarkupReferenceResolver.forPrefix(false, "q");

        final String result01 = rr01.resolveSelectorFromReference("abc");
        final String result02 = rr01.resolveSelectorFromReference("abc");
        assertEquals("/[fragment='abc' or fragment^='abc(' or fragment^='abc (']", result01);
        assertSame(result01, result02);

        final String result03 = rr02.resolveSelectorFromReference("abc");
        final String result04 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or th:fragment^='abc(' or th:fragment^='abc (']", result03);
        assertSame(result03, result04);

        final String result05 = rr05.resolveSelectorFromReference("abc");
        final String result06 = rr05.resolveSelectorFromReference("abc");
        assertEquals("/[q:fragment='abc' or q:fragment^='abc(' or q:fragment^='abc (']", result05);
        assertSame(result05, result06);

        final String result07 = rr02.resolveSelectorFromReference("abc");
        final String result08 = rr02.resolveSelectorFromReference("abc");
        assertEquals("/[th:fragment='abc' or th:fragment^='abc(' or th:fragment^='abc (']", result07);
        assertSame(result07, result08);

    }

}
