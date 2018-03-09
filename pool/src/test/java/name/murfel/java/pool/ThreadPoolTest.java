package name.murfel.java.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class ThreadPoolTest {
    @Test
    public void zeroThreadsNeverReady() {
        ThreadPool threadPool = new ThreadPool(0);
        LightFuture<Integer> task = threadPool.addTask(() -> 1 + 1);
        assertFalse(task.isReady());
    }

    private void nThreadsMTasks(int numberOfTasks, int numberOfThreads) throws LightExecutionException {
        ThreadPool threadPool = new ThreadPool(numberOfThreads);
        LightFuture[] lightFutures = new LightFuture[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            int k = i;
            lightFutures[i] = threadPool.addTask(() -> k);
        }
        for (int i = 0; i < numberOfTasks; i++) {
            assertEquals(i, lightFutures[i].get());
            assertTrue(lightFutures[i].isReady());
        }
    }

    @Test
    public void oneThreadOneTask() throws LightExecutionException {
        nThreadsMTasks(1, 1);
    }

    @Test
    public void tasksLessThanThreads() throws LightExecutionException {
        nThreadsMTasks(10, 120);
    }

    @Test
    public void threadsLessThanTasks() throws LightExecutionException {
        nThreadsMTasks(123123, 10);
    }

    @Test
    public void oneThreadOneTaskThenApply() throws LightExecutionException {
        ThreadPool threadPool = new ThreadPool(1);
        LightFuture<Integer> task = threadPool.addTask(() -> 2);
        LightFuture<Integer> newTask = task.thenApply(x -> x + 1);
        assertEquals(new Integer(3), newTask.get());
        assertTrue(newTask.isReady());
        assertTrue(task.isReady());
        assertEquals(new Integer(2), task.get());
    }

    @Test
    public void severalThreadsSeveralTasksAndChildTasks() throws LightExecutionException {
        ThreadPool threadPool = new ThreadPool(5);
        ArrayList<LightFuture> lightFutures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int k = i;
            lightFutures.add(threadPool.addTask(() -> k));
        }
        Random random = new Random(42);
        for (int i = 0; i < 1000; i++) {
            int index = lightFutures.size();
            lightFutures.add(lightFutures.get(random.nextInt(index)).thenApply(x -> index));
        }
        for (int i = 0; i < lightFutures.size(); i++) {
            assertEquals(i, lightFutures.get(i).get());
            assertTrue(lightFutures.get(i).isReady());
        }
    }

    @Test
    public void manyTasksShutdown() throws LightExecutionException, InterruptedException {
        ThreadPool threadPool = new ThreadPool(3);
        ArrayList<LightFuture> lightFutures = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            int k = i;
            lightFutures.add(threadPool.addTask(() -> k));
        }
        Thread.sleep(20);
        threadPool.shutdown();
        for (int i = 0; i < lightFutures.size(); i++) {
            if (lightFutures.get(i).isReady()) {
                assertEquals(i, lightFutures.get(i).get());
            }
        }
    }
}