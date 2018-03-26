package name.murfel.hw01;

/**
 * MyElement is a wrapper for two Strings representing key and value.
 */
public class MyElement {
    private String key, value;

    public MyElement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
