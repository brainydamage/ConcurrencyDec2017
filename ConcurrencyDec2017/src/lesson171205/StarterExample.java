package lesson171205;

import lesson171201.Utils;

import java.util.Arrays;
import java.util.List;

//TODO см.лекцию после перерыва
public class StarterExample {

    static class Runner {

        public Runner(Object mutex) {

        }
    }

//    public static void main(String[] args) {
//        Object mutex = new Object();
//
//        List<Runner> list = Arrays.asList(new Runner(mutex), new Runner(mutex), new Runner(mutex), new Runner(mutex));
//
//        for (Runner runner : list) {
//            new Thread(runner).start();
//        }
//
//        System.out.println("ready");
//        Utils.pause(1000);
//        System.out.println("steady");
//        Utils.pause(1000);
//        System.out.println("go");
//
//        synchronized (mutex) {
//            //все потоки из листа проснутся
//            mutex.notifyAll();
//        }
//    }
}
