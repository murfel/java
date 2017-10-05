package name.murfel.hw01;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyElementTest {
    @Test
    public void getKey() throws Exception {
        MyElement element = new MyElement("meow", "miu");
        assertEquals("meow", element.getKey());
    }

    @Test
    public void getValue() throws Exception {
        MyElement element = new MyElement("meow", "miu");
        assertEquals("miu", element.getValue());
    }

}