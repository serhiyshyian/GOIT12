public class FizzBuzzMultithreaded {
    private int n;
    private int currentNumber = 1;
    private Object lock = new Object();

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (currentNumber <= n && (currentNumber % 3 != 0 || currentNumber % 5 == 0)) {
                    lock.wait();
                }
                if (currentNumber > n) {
                    break;
                }
                printFizz.run();
                currentNumber++;
                lock.notifyAll();
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (currentNumber <= n && (currentNumber % 5 != 0 || currentNumber % 3 == 0)) {
                    lock.wait();
                }
                if (currentNumber > n) {
                    break;
                }
                printBuzz.run();
                currentNumber++;
                lock.notifyAll();
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (currentNumber <= n && (currentNumber % 3 != 0 || currentNumber % 5 != 0)) {
                    lock.wait();
                }
                if (currentNumber > n) {
                    break;
                }
                printFizzBuzz.run();
                currentNumber++;
                lock.notifyAll();
            }
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (currentNumber <= n && (currentNumber % 3 == 0 || currentNumber % 5 == 0)) {
                    lock.wait();
                }
                if (currentNumber > n) {
                    break;
                }
                printNumber.accept(currentNumber);
                currentNumber++;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Runnable printFizz = () -> System.out.println("fizz");
        Runnable printBuzz = () -> System.out.println("buzz");
        Runnable printFizzBuzz = () -> System.out.println("fizzbuzz");
        IntConsumer printNumber = num -> System.out.println(num);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(printFizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    interface IntConsumer {
        void accept(int num);
    }
}

