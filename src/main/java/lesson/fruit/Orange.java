package lesson.fruit;

import lesson.Fruit;

/**
 * Класс апельсин
 */
public class Orange implements Fruit {

    private static final float ORANGE_WEIGHT = 1.5f;

    @Override
    public float getWeight() {
        return ORANGE_WEIGHT;
    }

    @Override
    public String getFruitType() {
        return "Orange";
    }
}
