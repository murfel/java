import static org.junit.Assert.*;

public class ReflectorUtilsTest {
    @org.junit.Test
    public void printStructure() throws Exception {
    }

    @org.junit.Test
    public void diffClasses() throws Exception {
    }

    @org.junit.Test
    public void getDiffClasses() throws Exception {
    }

    @org.junit.Test
    public void simple() throws Exception {
        ReflectorUtils.getDiffClasses(TestClass1.class, TestClass2.class);
        System.out.println(TestClass1.class.equals(TestClass2.class));
    }
}