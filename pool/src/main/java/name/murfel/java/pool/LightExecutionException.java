package name.murfel.java.pool;

public class LightExecutionException extends Exception {
    public LightExecutionException(String message) {
        super(message);
    }

    public LightExecutionException(Exception e) {
        super(e);
    }
}
