import java.util.concurrent.*;

public class MultiThreadedTimeDisplayApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        int[] elapsedSeconds = {0};


        Runnable timeDisplayTask = () -> {
            while (true) {
                System.out.println("Прошло времени: " + elapsedSeconds[0] + " секунд");
                elapsedSeconds[0]++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable messageDisplayTask = () -> System.out.println("Прошло 5 секунд");

        executorService.submit(timeDisplayTask);
        scheduledExecutorService.scheduleAtFixedRate(messageDisplayTask, 5, 5, TimeUnit.SECONDS);

        try {

            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();
        scheduledExecutorService.shutdownNow();
    }
}
