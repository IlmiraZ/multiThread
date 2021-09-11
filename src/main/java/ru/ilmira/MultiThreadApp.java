package ru.ilmira;

public class MultiThreadApp {
    static final Object mon = new Object();
    static volatile char letter = 'A';

    public static void main(String[] args) {
        new Thread(new PrintLetter('A', 'B')).start();
        new Thread(new PrintLetter('B', 'C')).start();
        new Thread(new PrintLetter('C', 'A')).start();

    }

    static class PrintLetter implements Runnable {
        private final char firstLetter;
        private final char nextLetter;

        public PrintLetter(char firstLetter, char nextLetter) {
            this.firstLetter = firstLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (mon) {
                    try {
                        while (letter != firstLetter)
                            mon.wait();
                        System.out.print(firstLetter);
                        letter = nextLetter;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
