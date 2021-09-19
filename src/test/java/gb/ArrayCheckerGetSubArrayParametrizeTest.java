package gb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArrayCheckerGetSubArrayParametrizeTest {

    @Parameterized.Parameters
    public static Collection<Object[]> dataForMassGetSubArrayTest() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 3, 4, 6, 7, 4, 5, 0}, new int[]{5, 0}},
                {new int[]{0, 3, 0, 6, 7, 0, 5, 4}, new int[]{}},
                {new int[]{4, 3, 0, 6, 7, 0, 5, 0}, new int[]{3, 0, 6, 7, 0, 5, 0}}
        });
    }

    private int[] array;
    private int[] resultArray;

    public ArrayCheckerGetSubArrayParametrizeTest(int[] array, int[] resultArray) {
        this.array = array;
        this.resultArray = resultArray;
    }

    ArrayChecker arrayChecker;

    @Before
    public void init() {
        arrayChecker = new ArrayChecker();
    }

    @Test
    public void massGetSubArray() {
        Assert.assertArrayEquals(resultArray, arrayChecker.getSubArrayAfterLastFour(array));
    }
}
