import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassSymmetricDifference {
    public ArrayList<Field> fieldsIn1Without2;
    public ArrayList<Field> fieldsIn2Without1;
    public ArrayList<Method> methodsIn1Without2;
    public ArrayList<Method> methodsIn2Without1;
}
