package lesson171205;

public class SynchNull {
    public static void main(String[] args) {
        Object o = null;

        //NullPointerException, так как объекта не существует
        synchronized (o) {

        }
    }
}
