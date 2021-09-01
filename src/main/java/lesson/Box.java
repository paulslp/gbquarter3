package lesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс коробки
 */
public class Box<T extends Fruit> {

    private final String title;

    private final List<T> fruits = new ArrayList<>();

    public Box(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Добавляет фрукт в коробку
     *
     * @param fruit фрукт
     */
    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    /**
     * Считает и возвращает вес коробки
     */
    public float getWeight() {
        return (!fruits.isEmpty()) ? fruits.size() * fruits.get(0).getWeight() : 0;
    }

    /**
     * Сравнивает вес текущей коробки с весом другой коробки
     *
     * @param other другая коробка
     * @return true (вес одинаковый) или false (вес не одинаковый)
     */
    public boolean compare(Box<?> other) {
        System.out.println("Результат сравнения " + this.getTitle() + " и " + other.getTitle() + ":");
        return this.getWeight() == other.getWeight();
    }

    /**
     * Пересыпает фрукты из текущей коробки в коробку с тем же типом фруктов
     *
     * @param other другая коробка
     */
    public void pour(Box<T> other) {
        fruits.forEach(other::addFruit);
        fruits.clear();
        System.out.println("Пересыпаны фрукты из " + getTitle() + " в " + other.getTitle());
    }

    @Override
    public String toString() {
        int size = fruits.size();
        return "Коробка " + getTitle() + ": " +
                "количество фруктов=" + size +
                (size > 0 ? ", тип фрукта=" + fruits.get(0).getFruitType()
                        + ", вес коробки=" + getWeight() : "") +
                '}';
    }
}
