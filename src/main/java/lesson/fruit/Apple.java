package lesson.fruit;

import lesson.Fruit;

/**
 * Класс яблоко
 */
public class Apple implements Fruit {

    private static final float APPLE_WEIGHT = 1.0f;

    @Override
    public float getWeight() {
        return APPLE_WEIGHT;
    }

    @Override
    public String getFruitType() {
        return "Apple";
    }
}
