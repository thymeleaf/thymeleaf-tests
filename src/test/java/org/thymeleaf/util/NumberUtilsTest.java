package org.thymeleaf.util;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {



    @Test
    public void testSequence() {

        Assert.assertArrayEquals(new Integer[] {new Integer(1),new Integer(2),new Integer(3)}, NumberUtils.sequence(new Integer(1),new Integer(3)));
        Assert.assertArrayEquals(new Integer[] {new Integer(1),new Integer(2),new Integer(3)}, NumberUtils.sequence(new Integer(1),new Integer(3), new Integer(1)));
        Assert.assertArrayEquals(new Integer[] {new Integer(1),new Integer(3)}, NumberUtils.sequence(new Integer(1),new Integer(3), new Integer(2)));
        Assert.assertArrayEquals(new Integer[] {new Integer(3)}, NumberUtils.sequence(new Integer(3),new Integer(3), new Integer(1)));
        Assert.assertArrayEquals(new Integer[] {new Integer(3)}, NumberUtils.sequence(new Integer(3),new Integer(3), new Integer(2)));
        Assert.assertArrayEquals(new Integer[] {new Integer(3)}, NumberUtils.sequence(new Integer(3),new Integer(3)));

        Assert.assertArrayEquals(new Integer[] {new Integer(-1),new Integer(-2),new Integer(-3)}, NumberUtils.sequence(new Integer(-1),new Integer(-3)));
        Assert.assertArrayEquals(new Integer[] {new Integer(-1),new Integer(-2),new Integer(-3)}, NumberUtils.sequence(new Integer(-1),new Integer(-3), new Integer(-1)));
        Assert.assertArrayEquals(new Integer[] {new Integer(-1),new Integer(-3)}, NumberUtils.sequence(new Integer(-1),new Integer(-3), new Integer(-2)));
        Assert.assertArrayEquals(new Integer[] {new Integer(-3)}, NumberUtils.sequence(new Integer(-3),new Integer(-3), new Integer(-1)));
        Assert.assertArrayEquals(new Integer[] {new Integer(-3)}, NumberUtils.sequence(new Integer(-3),new Integer(-3), new Integer(-2)));
        Assert.assertArrayEquals(new Integer[] {new Integer(-3)}, NumberUtils.sequence(new Integer(-3),new Integer(-3)));

        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(1),new Integer(3), new Integer(-1)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(-1),new Integer(-3), new Integer(1)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(1),new Integer(3), new Integer(-2)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(-1),new Integer(-3), new Integer(2)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(3),new Integer(1), new Integer(1)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(-3),new Integer(-1), new Integer(-1)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(3),new Integer(1), new Integer(2)));
        Assert.assertArrayEquals(new Integer[0], NumberUtils.sequence(new Integer(-3),new Integer(-1), new Integer(-2)));

    }


}
