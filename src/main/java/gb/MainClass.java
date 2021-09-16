package gb;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/*
* 1. Организуем гонки:
     Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
     В туннель не может заехать одновременно больше половины участников (условность).
     Попробуйте всё это синхронизировать.
     Только после того как все завершат гонку, нужно выдать объявление об окончании.
     Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
* */
public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CyclicBarrier cyclicBarrierForStartRace = new CyclicBarrier(CARS_COUNT);
        AtomicInteger startDeclaringCount = new AtomicInteger(0);
        AtomicInteger finishEventCount = new AtomicInteger(0);
        Semaphore semaphore = new Semaphore(CARS_COUNT / 2);
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(
                    race,
                    20 + (int) (Math.random() * 10),
                    cyclicBarrierForStartRace,
                    startDeclaringCount,
                    semaphore,
                    finishEventCount
            );
        }
        ExecutorService pool = Executors.newFixedThreadPool(CARS_COUNT);
        for (int i = 0; i < cars.length; i++) {
            pool.submit(cars[i]);
        }
        pool.shutdown();
    }
}
