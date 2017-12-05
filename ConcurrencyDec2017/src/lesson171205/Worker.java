package lesson171205;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;

public class Worker implements Executor {

    private final Object mutex = new Object();
    private Queue<Runnable> tasks = new LinkedList<>();

    public Worker() {
        new Thread(this::processTasks).start();
    }

    public void execute(Runnable task) {

        //добавление задачи в список задач
        synchronized (mutex) {
            //здесь несколько потоков могут пытаться изменить состояние одного объекта - race condition!
            //следовательно необходима критическая секция synchronized{}
            tasks.offer(task);
            //подаем сигнал о новой задаче тому потоку, который будет их обрабатывать
            mutex.notify();
        }
    }

    //ссылку на этот метод будем использовать в качестве Runnable для запуска потока,
    //так как он ничего не принимает и не возвращает
    //
    private void processTasks() {
        //TODO сделать правильное завершение работы потока (shutdown, poison pill...)
        while (true) {
            Runnable task = null;
            //нужен блок synchronized над tasks.isEmpty() из-за проблемы visibility -
            //изменения в tasks, внесенные другими потоками, не будут видны этого без критической секции
            synchronized (mutex) {
                //TODO while, а не if? read spirious waikup
                while (tasks.isEmpty()) {
                    try {
                        //не забывать о критическом блоке вокруг wait()
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                task = tasks.poll();
            }
            task.run();
        }
    }
}
