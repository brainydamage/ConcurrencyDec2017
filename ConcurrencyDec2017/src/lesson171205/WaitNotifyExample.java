package lesson171205;

import lesson171201.Utils;

//wait, notify, notifyAll объявлены в Object потому, что именно Object может быть монитором.
public class WaitNotifyExample {
    public static void main(String[] args) {
        Object mutex = new Object();

        Thread thread = new Thread(() -> {
            System.out.println("before");
            //используем этот объект в качестве индикатора наступления какого-то события
            //когда событие наступит, мы выйдем из состояния ожидания
            //wait можно прервать:
            synchronized (mutex) {
                try {
                    //поймаем IllegalMonitorStateException, если вызовем wait() без блока synchronized
                    mutex.wait();
                    System.out.println("woke up");
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
            System.out.println("after");
        });
        thread.start();

        Utils.pause(2000);
        System.out.println("signal");
        //поймаем IllegalMonitorStateException, если вызовем notify() без блока synchronized
        synchronized (mutex) {
            //разбудим поток, но монитор ВСЕ ЕЩЕ будет захвачен: notify не освобождает монитор,
            //а просто сообщает сигнал и переводит поток из WAITING в BLOCKED
            mutex.notify();
            Utils.pause(2000);
        }

//        thread.interrupt();

        //TODO см. 16:40
        //0. "before"
        //1. в thread захватили mutex в монопольное использование (или попали в BLOCKED, если mutex занят)
        //2. вызвали wait - освободили mutex, вернулись в основной поток
        //3. "signal"
        //4. вызвали notify: WAITING -> BLOCKED
        //5. освобождение mutex
        //6. основной поток захватил mutex, продолжив основную работу
    }
}
