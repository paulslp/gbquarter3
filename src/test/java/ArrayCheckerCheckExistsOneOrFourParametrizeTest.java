import gb.ArrayChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArrayCheckerCheckExistsOneOrFourParametrizeTest {

    @Parameterized.Parameters
    public static Collection<Object[]> dataForCheckExistsOneOrFourArrayTest() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 3, 4, 6, 7, 4, 5, 0}, true},
                {new int[]{0, 3, 0, 6, 7, 0, 5, 0}, false},
                {new int[]{}, false},
                {new int[]{0, 3, 1, 6, 7, 0, 5, 0}, false},
                {new int[]{0, 3, 0, 6, 7, 0, 5, 4}, false}
        });
    }

    ArrayChecker arrayChecker;

    private int[] array;
    private boolean result;

    public ArrayCheckerCheckExistsOneOrFourParametrizeTest(int[] array, boolean result) {
        this.array = array;
        this.result = result;
    }

    @Before
    public void init() {
        arrayChecker = new ArrayChecker();
    }

    @Test
    public void massCheckExistsOneOrFour() {
        Assert.assertEquals(result, arrayChecker.checkExistsOneOrFour(array));
    }
}
