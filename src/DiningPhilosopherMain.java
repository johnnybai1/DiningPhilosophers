import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DiningPhilosopherMain {

    public static void main(String[] args) {
        int NUM = 5;
        Philosopher[] p = new Philosopher[NUM];
        PhilosopherMonitor m = new PhilosopherMonitor(NUM);
        for (int i = 0; i < NUM; i++) {
            p[i] = new Philosopher(i, m);
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i < NUM; i++) {
            executor.execute(p[i]);
        }


    }
}
