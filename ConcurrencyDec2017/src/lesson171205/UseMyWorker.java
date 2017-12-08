package lesson171205;

import lesson171201.Utils;

public class UseMyWorker {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main started");

        MyWorker myWorker = new MyWorker();

        myWorker.execute(() -> {
            Utils.pause(1000);
            System.out.println("one");
        });

        myWorker.execute(() -> {
            Utils.pause(1000);
            System.out.println("two");
        });

        myWorker.execute(() -> {
            Utils.pause(1000);
            System.out.println("three");
        });

        //TODO подать какой-то сигнал, что здесь нужно завершить работу =>
        //TODO MyWorker может завершать работу
        myWorker.shutdown();

        myWorker.execute(() -> {
            Utils.pause(1000);
            System.out.println("four");
        });

        myWorker.getThread().join();
        System.out.println("main finished");
    }
}
