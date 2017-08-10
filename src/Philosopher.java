import java.util.Random;

public class Philosopher implements Runnable {

    private PhilosopherMonitor monitor;
    private int number;
    private Random rng = new Random();

    public Philosopher(int number, PhilosopherMonitor monitor) {
        this.number = number;
        this.monitor = monitor;
    }

    /**
     * Philosopher represents a thread that will repeat think(), pickUp(), eat(), and putDown().
     */
    @Override
    public void run() {
        try {
            while (true) {
                think();
                monitor.pickUp(number);
                eat();
                monitor.putDown(number);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void think() throws InterruptedException {
        int time = rng.nextInt(5) + 1;
        System.out.println("Philosopher " + number + " is thinking: " + time*100 + "ms.");
        Thread.sleep(time*100);
    }

    public void eat() throws InterruptedException {
        System.out.println("Philoospher " + number + " is eating: " + 300 + "ms.");
        Thread.sleep(300);
    }
}
