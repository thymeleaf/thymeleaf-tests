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

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public final class ThymeleafTemplateReaderTest {


    @Test
    public void testReader() throws Exception {

        final String[] allMessages = computeAllMessages();

        for (int i = 0; i < allMessages.length; i++) {

            for (int j = 1; j <= (allMessages[i].length() + 10); j++) {

                for (int k = 1; k <= j; k++) {

                    for (int l = 0; l < k; l++) {

                        final Reader stringReader =
                                new ThymeleafTemplateReader(new StringReader(allMessages[i]));

                        final char[] buffer = new char[j];

                        final StringBuilder strBuilder = new StringBuilder();
                        int read = 0;
                        while (read >= 0) {
                            read = stringReader.read(buffer, l, (k - l));
                            if (read >= 0) {
                                strBuilder.append(buffer, l, read);
                            }
                        }

                        final String result = strBuilder.toString();

                        if (result.equals("0123456789")) {
                            continue;
                        }

                        final int suffixIdx = result.indexOf("/*/-->");
                        if (suffixIdx != -1) {
                            final StringBuilder resultBuilder = new StringBuilder(result);
                            resultBuilder.delete(suffixIdx, suffixIdx + ("/*/-->".length()));
                            if (resultBuilder.toString().equals("0123456789")) {
                                continue;
                            }
                        }

                        System.out.println(result);

                    }

                }


            }

        }

    }




    private static String[] computeAllMessages() {

        final List<String> allMessages = new ArrayList<String>();

        final String prefix = "<!--/*/";
        final String suffix = "/*/-->";
        final String message = "0123456789";


        for (int i = 0; i <= message.length(); i++) {

            final StringBuilder msb1 = new StringBuilder(message);
            msb1.insert(i, suffix);

            for (int j = 0; j <= i; j++) {

                final StringBuilder msb2 = new StringBuilder(msb1);
                msb2.insert(j, prefix);

                for (int k = 0; k <= j; k++) {

                    final StringBuilder msb3 = new StringBuilder(msb2);
                    msb3.insert(k, suffix);

                    allMessages.add(msb3.toString());

                    for (int l = 0; l <= k; l++) {

                        final StringBuilder msb4 = new StringBuilder(msb3);
                        msb4.insert(l, prefix);

                        allMessages.add(msb4.toString());

                    }

                }

            }

        }

        return allMessages.toArray(new String[allMessages.size()]);

    }


}
