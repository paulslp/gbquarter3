package gb;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cyclicBarrierForStartRace;
    private AtomicInteger startDeclaringCount;
    private Semaphore semaphore;
    private AtomicInteger finishEventCount;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed,
               CyclicBarrier cyclicBarrierForStartRace,
               AtomicInteger startDeclaringCount,
               Semaphore semaphore,
               AtomicInteger finishEventCount) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrierForStartRace = cyclicBarrierForStartRace;
        this.startDeclaringCount = startDeclaringCount;
        this.semaphore = semaphore;
        this.finishEventCount = finishEventCount;
    }

    public void run() {

        System.out.println(this.name + " готовится");
        try {
            Thread.sleep(500 + (int) (Math.random() * 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " готов");
        try {
            cyclicBarrierForStartRace.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        if (startDeclaringCount.incrementAndGet() == 1) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            Stage currentStage = race.getStages().get(i);
            if (currentStage instanceof Tunnel) {
                try {
                    semaphore.acquire();
                    race.getStages().get(i).go(this);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                race.getStages().get(i).go(this);
            }
            if (i == race.getStages().size() - 1) {
                if (finishEventCount.incrementAndGet() == 1) {
                    System.out.println(name + " - WIN");
                } else if (finishEventCount.get() == CARS_COUNT) {
                    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                }
            }
        }
    }
}
