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
package org.thymeleaf.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public final class SSEOutputStreamTest {


    @Test
    public void testSSE01() throws Exception {

        checkSSE("",
                "");
        checkSSE("data: \ndata: \n\n",
                "\n");
        checkSSE("data: abc\n\n",
                "abc");
        checkSSE("data: abc\n\ndata:  \n\n",
                "abc", " ");
        checkSSE("data: abc\ndata:  \n\n",
                "abc\n ");
        checkSSE("data: abc\n\ndata: def\n\n",
                 "abc", "def");
        checkSSE("data: abc\ndata: 123\n\ndata: def\ndata: 456\n\n",
                 "abc\n123", "def\n456");
        checkSSE("data: abcdefghijkl\ndata: 123\n\ndata: defxqzpolrtwiom\ndata: 456\n\n",
                 "abcdefghijkl\n123", "defxqzpolrtwiom\n456");
        checkSSE("data: abcdefghijkl\ndata: 123\ndata: \n\ndata: defxqzpolrtwiom\ndata: 456\n\n",
                 "abcdefghijkl\n123\n", "defxqzpolrtwiom\n456");
        checkSSE("data: abcdefghijkl\ndata: 123\ndata: \n\ndata: defxqzpolrtwiom\ndata: 456\ndata: pwpwpwpweee\ndata: lwsd\n\n",
                 "abcdefghijkl\n123\n", "defxqzpolrtwiom\n456\npwpwpwpweee\nlwsd");

    }



    private void checkSSE(final String expectedResult, final String... text) throws IOException {

        final Charset[] charsets =
                new Charset[] {
                        Charset.forName("UTF-8"), Charset.forName("ISO-8859-1"), Charset.forName("JIS"),
                        Charset.forName("Shift-JIS"), Charset.forName("Big5")};

        final String[] ids = new String[] { null, "1", "abc" };
        final String[] events = new String[] { null, "1", "abc" };

        for (int i = 0; i < charsets.length; i++) {
            for (int j = 0; j < ids.length; j++) {
                for (int k = 0; k < events.length; k++) {

                    final StringBuilder prefixBuilder = new StringBuilder();
                    if (ids[j] != null) {
                        prefixBuilder.append("id: " + ids[j] + "\n");
                    }
                    if (events[k] != null) {
                        prefixBuilder.append("event: " + events[k] + "\n");
                    }
                    final String prefix = prefixBuilder.toString();

                    String expectedres = expectedResult;
                    if (prefix.length() > 0) {
                        expectedres = prefix + expectedres.replaceAll("\\n\\n", "\n\n" + prefix);
                        if (expectedResult.length() > 0) {
                            expectedres = expectedres.substring(0, expectedres.length() - prefix.length());
                        } else { // only meta then
                            expectedres += "\n";
                        }
                    }

                    sseize(ids[j], events[k], charsets[i], expectedres, text);

                }
            }
        }

    }



    private void sseize(final String id, final String event, final Charset charset,
                        final String expectedResult, final String... text) throws IOException {

        final ByteArrayOutputStream baosResult = new ByteArrayOutputStream();

        final byte[][] orig = new byte[text.length][];
        for (int i = 0; i < text.length; i++) {
            orig[i] = text[i].getBytes(charset);
        }

        final SSEOutputStream sseOutputStreamResult = new SSEOutputStream(baosResult, charset);
        for (int i = 0; i < orig.length; i++) {
            sseOutputStreamResult.startEvent(id, event);
            sseOutputStreamResult.write(orig[i]);
            sseOutputStreamResult.endEvent();
        }

        final String result = new String(baosResult.toByteArray(), charset);

        System.out.println("=============\n" + expectedResult + "-------------\n" + result + "=============");

        Assert.assertEquals(expectedResult, result);

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length; j++) {
                    sseOutputStream.write(orig[i][j]);
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length; j++) {
                    sseOutputStream.write(orig[i], j, 1);
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length;) {
                    if (j + 1 < orig[i].length) {
                        sseOutputStream.write(orig[i], j, 2);
                        j += 2;
                    } else {
                        sseOutputStream.write(orig[i], j, 1);
                        j++;
                    }
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length;) {
                    if (j + 2 < orig[i].length) {
                        sseOutputStream.write(orig[i], j, 3);
                        j += 3;
                    } else {
                        sseOutputStream.write(orig[i], j, 1);
                        j++;
                    }
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length;) {
                    if (j + 7 < orig[i].length) {
                        sseOutputStream.write(orig[i], j, 7);
                        j += 7;
                    } else {
                        sseOutputStream.write(orig[i][j]);
                        j++;
                    }
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }

        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final SSEOutputStream sseOutputStream = new SSEOutputStream(baos, charset);
            for (int i = 0; i < orig.length; i++) {
                sseOutputStream.startEvent(id, event);
                for (int j = 0; j < orig[i].length;) {
                    if (j + 11 < orig[i].length) {
                        sseOutputStream.write(orig[i], j, 11);
                        j += 11;
                    } else {
                        sseOutputStream.write(orig[i][j]);
                        j++;
                    }
                }
                sseOutputStream.endEvent();
            }

            Assert.assertEquals(result, new String(baos.toByteArray(), charset));

        }


    }


}
