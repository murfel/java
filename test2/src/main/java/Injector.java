import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {
    public static Object initialize(Class rootClassName, Collection<Class> classes)
            throws AmbiguousImplementationException, InjectionCycleException, ImplementationNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        classes.add(rootClassName);
        HashMap<Class, HashSet<Class>> matrix = buildDependencyMatrix(classes);
        HashMap<Class, Object> objects = new HashMap<>();
        dfs(rootClassName, matrix, new HashMap<Class, Color>(), objects);
        return objects.get(rootClassName);
    }

    private static HashMap<Class, HashSet<Class>> buildDependencyMatrix(Collection<Class> classes)
            throws AmbiguousImplementationException, InjectionCycleException,  ImplementationNotFoundException {
        // build adjacency list
        // a -> b means a depends on b
        HashMap<Class, HashSet<Class>> matrix = new HashMap<>();
        for (Class c : classes) {
            matrix.put(c, new HashSet<>());
            for (Class param : c.getConstructors()[0].getParameterTypes()) {
                matrix.get(c).add(param);
            }
            // for super class and implemented interfaces make an alias to this class
            HashSet<Class> originalClassHashSet = matrix.get(c);
            Class superclass = c.getSuperclass();
            if (matrix.containsKey(superclass)) {
                throw new AmbiguousImplementationException();
            }
            matrix.put(superclass, originalClassHashSet);
            for (Class inter : c.getInterfaces()) {
                if (matrix.containsKey(inter)) {
                    throw new AmbiguousImplementationException();
                }
                matrix.put(inter, originalClassHashSet);
            }
        }
        return matrix;
    }

    private static enum Color {
        NOT_VISITED,  // white
        ON_STACK,  // green
        VISITED  // gray
    }

    private static void dfs(Class currentClass, HashMap<Class, HashSet<Class>> matrix,
                            HashMap<Class, Color> color, HashMap<Class, Object> objects)
            throws AmbiguousImplementationException, InjectionCycleException, ImplementationNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Color currentColor = color.getOrDefault(currentClass, Color.NOT_VISITED);
        if (currentColor.equals(Color.VISITED)) {
            return;
        }
        if (currentColor.equals(Color.ON_STACK)) {
            throw new InjectionCycleException();  // we only dfs from root component
        }

        color.put(currentClass, Color.ON_STACK);
        for (Class dependencyClass : matrix.get(currentClass)) {
            dfs(dependencyClass, matrix, color, objects);
        }
        color.put(currentClass, Color.VISITED);

        Object[] args = getArrayOfArgs(currentClass, objects);
        Object currentClassObject = currentClass.getConstructors()[0].newInstance(args);
        objects.put(currentClass, currentClassObject);
    }

    private static Object[] getArrayOfArgs(Class aClass, HashMap<Class, Object> objects) {
        Constructor ctor = aClass.getConstructors()[0];
        Object[] args = new Object[ctor.getParameterCount()];
        int i = 0;
        for (Class param : ctor.getParameterTypes()) {
            args[i] = objects.get(param);
        }
        return args;
    }
}
