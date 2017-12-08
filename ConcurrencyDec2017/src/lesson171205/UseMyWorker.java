package lesson171205;

import lesson171201.Utils;

public class UseWorker {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main started");

        Worker worker = new Worker();

        worker.execute(() -> {
            Utils.pause(1000);
            System.out.println("one");
        });

        worker.execute(() -> {
            Utils.pause(1000);
            System.out.println("two");
        });

        worker.execute(() -> {
            Utils.pause(1000);
            System.out.println("three");
        });

        //TODO подать какой-то сигнал, что здесь нужно завершить работу =>
        //TODO Worker может завершать работу
        worker.shutdown();

        worker.execute(() -> {
            Utils.pause(1000);
            System.out.println("four");
        });

        worker.getThread().join();
        System.out.println("main finished");
    }
}
