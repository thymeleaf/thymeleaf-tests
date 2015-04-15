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


public final class ProcessingInstructionTest {


    @Test
    public void test() {

        final ITextRepository textRepository = TextRepositories.createLimitedSizeCacheRepository();

        final String procInstr1 = "<?something someother and other and other?>";
        final String procInstr2 = "<?anything-else someother and other and other?>";
        final String procInstr3 = "<?anything-else nothing here?>";
        final String procInstr4 = "<?anything-else?>";

        final String target1 = "something";
        final String target2 = "anything-else";

        final String content1 = "someother and other and other";
        final String content2 = "nothing here";
        final String content3 = null;


        final ProcessingInstruction d1 = new ProcessingInstruction(textRepository);
        d1.reset(
                procInstr1,
                target1,
                content1,
                11, 4);

        Assert.assertSame(procInstr1, d1.getProcessingInstruction());
        Assert.assertSame(target1, d1.getTarget());
        Assert.assertSame(content1, d1.getContent());
        Assert.assertEquals(11, d1.getLine());
        Assert.assertEquals(4, d1.getCol());

        d1.reset(
                procInstr1,
                target1,
                content1,
                10, 3
        );

        Assert.assertSame(procInstr1, d1.getProcessingInstruction());
        Assert.assertSame(target1, d1.getTarget());
        Assert.assertSame(content1, d1.getContent());
        Assert.assertEquals(10, d1.getLine());
        Assert.assertEquals(3, d1.getCol());

        d1.setTarget(target2);

        Assert.assertEquals(procInstr2, d1.getProcessingInstruction());
        Assert.assertSame(target2, d1.getTarget());
        Assert.assertSame(content1, d1.getContent());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setContent(content2);

        Assert.assertEquals(procInstr3, d1.getProcessingInstruction());
        Assert.assertSame(target2, d1.getTarget());
        Assert.assertSame(content2, d1.getContent());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());

        d1.setContent(content3);

        Assert.assertEquals(procInstr4, d1.getProcessingInstruction());
        Assert.assertSame(target2, d1.getTarget());
        Assert.assertNull(d1.getContent());
        Assert.assertEquals(-1, d1.getLine());
        Assert.assertEquals(-1, d1.getCol());



        final ProcessingInstruction d2 =
                new ProcessingInstruction(
                    textRepository,
                    target1,
                    content1);

        Assert.assertEquals(procInstr1, d2.getProcessingInstruction());
        Assert.assertSame(target1, d2.getTarget());
        Assert.assertSame(content1, d2.getContent());
        Assert.assertEquals(-1, d2.getLine());
        Assert.assertEquals(-1, d2.getCol());


        final ProcessingInstruction d3 =
                new ProcessingInstruction(
                        textRepository,
                        target2,
                        null);

        Assert.assertEquals(procInstr4, d3.getProcessingInstruction());
        Assert.assertSame(target2, d3.getTarget());
        Assert.assertNull(d3.getContent());
        Assert.assertEquals(-1, d2.getLine());
        Assert.assertEquals(-1, d2.getCol());


    }



    
}
