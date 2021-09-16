package gb.multifunctional.device;

import gb.multifunctional.device.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Task implements Runnable {

    private final CyclicBarrier cyclicBarrierForStartRace;
    private final AtomicInteger startDeclaringCount;
    private final AtomicInteger finishEventCount;
    private String name;

    private ArrayList<Stage> stages;


    private ReentrantLock executeStageLock;

    private Document document;

    private static int TASKS_COUNT;

    static {
        TASKS_COUNT = 0;
    }

    public Task(String name,
                Document document,
                CyclicBarrier cyclicBarrierForStartRace,
                AtomicInteger startDeclaringCount,
                AtomicInteger finishEventCount,
                ReentrantLock executeStageLock,
                Stage... stages
    ) {
        this.name = name;
        this.executeStageLock = executeStageLock;
        this.document = document;
        this.stages = new ArrayList<Stage>(Arrays.asList(stages));
        this.cyclicBarrierForStartRace = cyclicBarrierForStartRace;
        this.startDeclaringCount = startDeclaringCount;
        this.finishEventCount = finishEventCount;
        TASKS_COUNT++;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void run() {
        System.out.println(this.document.getName() + " по заданию " + this.name + " готовится");
        try {
            Thread.sleep(500 + (int) (Math.random() * 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.document.getName() + " по заданию " + this.name + " готов");
        try {
            cyclicBarrierForStartRace.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        if (startDeclaringCount.incrementAndGet() == 1) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Использование МФУ началось!!!");
        }
        executeStageLock.lock();
        for (int i = 0; i < getStages().size(); i++) {

            getStages().get(i).go(this.document, this.name);

            if (i == getStages().size() - 1) {
                if (finishEventCount.incrementAndGet() == 1) {
                    System.out.println(this.name + " - Выполнено первым");
                } else if (finishEventCount.get() == TASKS_COUNT) {
                    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Использование МФУ закончилось!!!");
                }
            }
        }
        executeStageLock.unlock();
    }
}
