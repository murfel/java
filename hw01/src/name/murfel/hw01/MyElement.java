package name.murfel.hw01;

/**
 * MyElement is a wrapper for two Strings representing key and value.
 */
public class MyElement {
    String key, value;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
