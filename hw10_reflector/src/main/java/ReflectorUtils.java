import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectorUtils {
    /**
     * Create a .java file with the Java source code of the given class. The source code should not contain any
     * implementation. The compiled class should be isomorphic to the given one, that is the Class objects of someClass
     * and compiled class should not differ according to some isomorphism criteria described below.
     *
     * TODO isomorphism
     *
     * @param someClass a class to repeat structure
     */
    public static void printStructure(Class<?> someClass) {
        try {
            OutputStream os = new FileOutputStream(someClass.getCanonicalName() + ".java");
            try (Writer writer = new OutputStreamWriter(os)) {
                writer.write("class ");
                writer.write(someClass.getCanonicalName());










            } catch (IOException e) {
                System.out.println("Encountered IOException. Aborting.");
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Encountered FileNotFoundException. Aborting.");
            return;
        }
    }

    /**
     * Print the symmetric difference of fields and methods of the given classes.
     *
     * @param class1 first class to compare
     * @param class2 second class to compare
     */
    public static void diffClasses(Class<?> class1, Class<?> class2) {
    }

    /**
     * Compare the two classes and return symmetric difference of fields and methods of class1 without class2
     * and class2 without class1 according to some isomorphism criteria.
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
