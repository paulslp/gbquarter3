package gb;

import java.util.Arrays;

public class ArrayChecker {

    public int[] getSubArrayAfterLastFour(int[] array) {
        int fourIndex = array.length;
        if (fourIndex == 0) {
            throw new RuntimeException();
        }
        do {
            fourIndex--;
        } while (array[fourIndex] != 4 && fourIndex > 0);
        if (array[fourIndex] != 4) {
            throw new RuntimeException();
        } else {
            return Arrays.copyOfRange(array, fourIndex + 1, array.length);
        }
    }

    public boolean checkExistsOneOrFour(int[] array) {
        int fourIndex = -1;
        int oneIndex = -1;
        int index = 0;
        while ((fourIndex == -1 || oneIndex == -1) && (index < array.length)) {
            if (array[index] == 1 && oneIndex == -1) {
                oneIndex = index;
            } else if (array[index] == 4 && fourIndex == -1) {
                fourIndex = index;
            }
            index++;
        }
        return oneIndex > -1 && fourIndex > -1;
    }

}
