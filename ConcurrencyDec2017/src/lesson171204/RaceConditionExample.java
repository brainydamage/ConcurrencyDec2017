package lesson171204;

import lesson171201.Utils;

import java.util.ArrayList;
import java.util.List;

//нужно посчитать, сколько человек пришло на стадион через некоторое кол-во турникетов.
//будем считать, что через каждый турникет каждое кол-во времени проходит ровно 1 человек
public class RaceConditionExample {
    volatile static int count = 0;

    //здесь нет локального контекста, то есть внутри класса ничего не меняется, а меняется только внешняя переменная
    static class Counter implements Runnable {
        final private int pause;

        public Counter(int pause) {
            this.pause = pause;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Utils.pause(pause);
                synchronized (Counter.class) {
                    count++;
                }
            }
        }
    }

    //должны по идеи увидеть 100 (10 секунд, 10 потоков), но получаем 81... ?
    //коротко: потоки не синхронизированы: один поток перезаписывает значение, сохраненное другим потоком
    //необходимо организовать критическую секцию - {synchronized} блок. Тем самым мы гарантируем атомарность
    //с точки зрения стороннего наблюдателя.
    //
    //RaceCondition.txt!
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

    //synchronized у метода - захват мьютекса (объекта класса), а нашем случае counter. Если мы напишем synchronized public void run(),
    //то нащ код превратится в однопоточное приложение: потоки будут выполняться последовательно.
    //если мы создадим каждому потоку свой Counter, то снова получим меньше 100, так как каждый блок будет работать со своим мьютексом.
    //то есть следует в synchronized(Object) (стр.25) добавить какой-то общий для всех мьютекс, в нашем случае это класс Counter.
    //можно было бы в качестве мьютекса использовать сам изменяемый объект, НО мьютекс - это объект, а int - это не объект.
    //Integer count не поможет, так как в count++ будет создан НОВЫЙ объект. Можно использовать AtomicInteger, см.RaceConditionExampleWithAtomics

    //в случае с примитивами атомарность можно обеспечить так:
    //1. synchronized
    //2. lock
    //3. семафор
    //4. Atomic - в общем случае этот механизм быстрее

    //в критической секции нужно держать только то, что считывает или изменяет общее состояние (count++ в нашем случае)
}
