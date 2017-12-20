import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectorUtils {
    public static void printStructure(Class<?> someClass) {
    }

    public static void diffClasses(Class<?> class1, Class<?> class2) {
    }


    /**
     * Compare the two classes and return symmetric difference of fields and methods of class1 without class2
     * and class2 without class1.
     *
     * It is assumed that neither of the classes contains enums or annotations.
     *
     * @param class1 first class to compare
     * @param class2 second class to compare
     * @return two-element array with two symmetric differences
     */
    public static ClassSymmetricDifference getDiffClasses(Class<?> class1, Class<?> class2) {
        System.out.println(class1.equals(class2));
        System.out.println(class1.isAssignableFrom(class2));
        System.out.println(class2.isAssignableFrom(class1));
        return null;
    }
}
