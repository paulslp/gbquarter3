package gb;

import gb.multifunctional.device.Device;
import gb.multifunctional.device.Document;
import gb.multifunctional.device.Task;
import gb.multifunctional.device.stage.Print;
import gb.multifunctional.device.stage.Scan;
import gb.multifunctional.device.stage.SendEmail;
import gb.race.Car;
import gb.race.Race;
import gb.race.Road;
import gb.race.Tunnel;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/*
  1. Организуем гонки:
     Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
     В туннель не может заехать одновременно больше половины участников (условность).
     Попробуйте всё это синхронизировать.
     Только после того как все завершат гонку, нужно выдать объявление об окончании.
     Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.

  2. Создать MFU c функциями, сканирования, печати и ксерокопирования
* */
public class MainClass {
    public static final int CARS_COUNT = 4;

    public static final int TASK_COUNT = 4;

    public static void main(String[] args) {
        racingTask();
        multiFunctionalDeviceTask();
    }

    private static void racingTask() {
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

    private static void multiFunctionalDeviceTask() {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        ReentrantLock executeStageLock = new ReentrantLock();
        AtomicInteger startDeclaringCount = new AtomicInteger(0);
        AtomicInteger finishEventCount = new AtomicInteger(0);
        CyclicBarrier cyclicBarrierForStartRace = new CyclicBarrier(TASK_COUNT);
        Device device = new Device(5, 20, 10);

        Document documentForCopyTask = new Document(30, "Документ для копирования");
        Document documentForSendEmailTask = new Document(30, "Документ для отправки по эл. почте");
        Document documentForPrint = new Document(30, "Документ для печати");
        Document documentForScan = new Document(30, "Документ для сканирования");

        new Thread(new Task("Копирование",
                documentForCopyTask,
                cyclicBarrierForStartRace,
                startDeclaringCount,
                finishEventCount,
                executeStageLock,
                new Scan(documentForCopyTask, device),
                new Print(documentForCopyTask, device))).start();
        new Thread(new Task("Отправка по эл. почте",
                documentForSendEmailTask,
                cyclicBarrierForStartRace,
                startDeclaringCount,
                finishEventCount,
                executeStageLock,
                new Scan(documentForSendEmailTask, device),
                new SendEmail(documentForSendEmailTask, device))).start();
        new Thread(new Task("Сканирование",
                documentForScan,
                cyclicBarrierForStartRace,
                startDeclaringCount,
                finishEventCount,
                executeStageLock,
                new Scan(documentForSendEmailTask, device))).start();
        new Thread(new Task("Печать",
                documentForPrint,
                cyclicBarrierForStartRace,
                startDeclaringCount,
                finishEventCount,
                executeStageLock,
                new Print(documentForSendEmailTask, device))).start();
    }
}
