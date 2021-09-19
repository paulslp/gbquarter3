package gb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayCheckerErrorTest {

    ArrayChecker arrayChecker;

    @Before
    public void init() {
        arrayChecker = new ArrayChecker();
    }

    @Test(expected = RuntimeException.class)
    public void massGetSubArrayFromEmptyArray() {
        Assert.assertArrayEquals(new int[]{}, arrayChecker.getSubArrayAfterLastFour(new int[]{}));
    }

    @Test(expected = RuntimeException.class)
    public void massGetSubArrayFromArrayWithoutFour() {
        Assert.assertArrayEquals(new int[]{}, arrayChecker.getSubArrayAfterLastFour(new int[2]));
    }
}
