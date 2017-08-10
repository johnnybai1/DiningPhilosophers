public class PhilosopherMonitor {

    enum State {THINK, HUNGRY, EAT}

    private Object canEat[]; // We synchronize on an Object associated with each philosopher
    private State[] philosopher; // Keep track of what each philosopher is doing
    private int count; // How many philosophers are involved?

    public PhilosopherMonitor(int n) {
        count = n;
        canEat = new Object[n];
        philosopher = new State[n];
        // Start with all philosophers thinking
        for (int i = 0; i < n; i ++) {
            philosopher[i] = State.THINK;
            canEat[i] = new Object();
        }
    }

    /**
     *
     * @param x is the philosopher number that wishes to eat
     */
    public void pickUp(int x) throws InterruptedException {
        synchronized(canEat[x]) { // Protect access!
            philosopher[x] = State.HUNGRY; // Express x's interest to eat
            System.out.println("Philosopher " + x + " is hungry!");
            testNeighbors(x); // Can x eat?
            if (philosopher[x] != State.EAT) {
                System.out.println("Philosopher " + x + " is waiting!");
                canEat[x].wait();
            }
        }
    }

    /**
     * @param x is the philosopher number that has finished eating
     */
    public void putDown(int x) throws InterruptedException {
        philosopher[x] = State.THINK; // express x is done eating
        testNeighbors(left(x));
        testNeighbors(right(x));
    }


    /**
     * If philosopher x can eat, we set his state to eating.
     * Note: Race condition exists when philosopher x uses canEat[x], and when philosopher left(x) or right(x)
     * calls putDown(). This is why we must use synchronized(canEat[x])
     * @param x is the philosopher we are testing
     */
    public void testNeighbors(int x) {
        if (philosopher[left(x)] != State.EAT && philosopher[right(x)] != State.EAT
                && philosopher[x] == State.HUNGRY) {
            philosopher[x] = State.EAT;
            synchronized(canEat[x]) {
                canEat[x].notifyAll(); // This line is used only when a philosopher puts down his tool
            }
        }
    }

    // Left neighbor of x
    private int left(int x) {
        return (x + count - 1) % count;
    }

    // Right neighbor of x
    private int right(int x) {
        return (x + 1) % count;
    }


}
