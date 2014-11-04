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
package org.thymeleaf.aurora.engine;

import org.junit.Assert;
import org.junit.Test;
import org.thymeleaf.aurora.text.ITextRepository;
import org.thymeleaf.aurora.text.TextRepositories;


public final class TextTest {



    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final Text c1 = new Text(textRepository);
        Assert.assertNull(c1.getBuffer());

        final String c1c0 = " something\nhere ";
        c1.setText(c1c0);
        Assert.assertSame(c1c0, c1.getText());
        Assert.assertEquals(c1c0, new String(c1.getBuffer(), c1.getOffset(), c1.getLen()));
        Assert.assertSame(textRepository.getText(" something\nhere "), c1.getText());

        final String c1c2 = "hey!";
        c1.setText(c1c2);
        final String c1c2_2 = c1.getText();
        Assert.assertSame(c1c2, c1c2_2);
        Assert.assertSame(c1c2, c1.getText());

        c1.setText(c1c0.toCharArray(), 0, c1c0.length());
        final String c1c3_2 = c1.getText();
        Assert.assertEquals(c1c0, c1c3_2);
        Assert.assertSame(c1c3_2, c1.getText());
        Assert.assertEquals(c1c3_2, new String(c1.getBuffer(), c1.getOffset(), c1.getLen()));


        final String c2c1 = "hello";
        final Text c2 = new Text(c2c1);
        Assert.assertEquals(c2c1, new String(c2.getBuffer(), c2.getOffset(), c2.getLen()));
        final String c2cs1_2 = c2.getText();
        Assert.assertEquals(c2c1, c2cs1_2);
        Assert.assertSame(c2cs1_2, c2.getText());

        final char[] c2Buffer1 = c2.getBuffer();

        c2.setText("huruhuhuuu");
        // The internal buffer will need growth, so it cannot be the same object
        final char[] c2Buffer2 = c2.getBuffer();
        Assert.assertNotSame(c2Buffer1, c2Buffer2);

        c2.setText("huu");
        // The internal buffer didn't need to grow, so this time it must be the same
        final char[] c2Buffer3 = c2.getBuffer();
        Assert.assertSame(c2Buffer2, c2Buffer3);

    }



    
}
