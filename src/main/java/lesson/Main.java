package lesson;

import lesson.fruit.Apple;
import lesson.fruit.Orange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Первое задание
        Book[] bookArray = {new Book("title1", 1),
                new Book("title2", 2),
                new Book("title3", 3)};
        swapTwoArrayElement(bookArray, 0, 1);
        //Второе задание
        arrayToList(bookArray);
        //Третье задание
        Box<Orange> orangeBox1 = new Box<>("orangeBox1");
        orangeBox1.addFruit(new Orange());
        orangeBox1.addFruit(new Orange());
        printBox(orangeBox1);

        Box<Orange> orangeBox2 = new Box<>("orangeBox2");
        orangeBox2.addFruit(new Orange());
        printBox(orangeBox2);

        Box<Apple> appleBox1 = new Box<>("appleBox1");
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());
        printBox(appleBox1);

        Box<Apple> appleBox2 = new Box<>("appleBox2");
        appleBox2.addFruit(new Apple());
        printBox(appleBox2);

        System.out.println();
        System.out.println("Сравнение ");
        System.out.println(appleBox1.compare(orangeBox1) == true ?
                "вес коробок одинаковый" : "вес коробок не одинаковый");

        System.out.println();
        appleBox1.pour(appleBox2);
        printBox(appleBox1);
        printBox(appleBox2);
    }

    /**
     * Меняет местами два указанных элемента массива
     *
     * @param array       массив
     * @param firstIndex  индекс первого элемента
     * @param secondIndex индекс второго элемента
     */
    private static <T> void swapTwoArrayElement(T[] array,
                                                int firstIndex,
                                                int secondIndex) {
        System.out.println("Массив до перемены элементов 1 и 2:");
        printArray(array);
        T bufferFirstElement = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = bufferFirstElement;
        System.out.println("Массив после перемены элементов 1 и 2");
        printArray(array);
    }

    /**
     * Переписывает данные массива в список
     *
     * @param array массив
     * @return список
     */
    private static <T> List<T> arrayToList(T[] array) {
        System.out.println("Список, полученный из массива: ");
        List<T> result = new ArrayList<>();
        for (T element : array) {
            result.add(element);
            System.out.print(element.toString() + "  ");
        }
        System.out.println();
        return result;
    }

    private static <T> void printArray(T[] array) {
        System.out.println(Arrays.toString(array));
    }

    private static void printBox(Box<?> box) {
        System.out.println("Информация о коробке " + box.getTitle());
        System.out.println(box);
    }
}
