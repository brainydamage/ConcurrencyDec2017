package lesson171204;

import lesson171201.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//нужно посчитать, сколько человек пришло на стадион через некоторое кол-во турникетов.
//будем считать, что через каждый турникет каждое кол-во времени проходит ровно 1 человек
public class RaceConditionExampleWithAtomics {
    static AtomicInteger count = new AtomicInteger(0);

    static class Counter implements Runnable {
        final private int pause;

        public Counter(int pause) {
            this.pause = pause;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Utils.pause(pause);
                //операция атомарна, поэтому synchronized не нужен
                count.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<Thread>();

        //запустили все потоки, сохранили их в список
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Counter(100));
            t.start();
            threadList.add(t);
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        System.out.println(count);
    }
}
