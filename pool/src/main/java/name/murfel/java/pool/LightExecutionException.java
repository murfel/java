package name.murfel.java.pool;

/**
 * Represents inability to get the result of a LightFuture task.
 * Thrown when LightFuture.get() called but the execution couldn't be finished due to internal reasons.
 */
public class LightExecutionException extends Exception {
    public LightExecutionException(String message) {
        super(message);
    }

    public LightExecutionException(Exception e) {
        super(e);
    }
}
