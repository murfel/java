import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {
    /**
     * Instantiate the class and return the instance. It is only allowed to instantiate and use classes
     * provided with the classes variable.
     *
     * Internally, it build a dependency matrix and traverses it with a dfs algorithm.
     *
     * It is guaranteed that every class will be instantiated no more than once.
     *
     * @param rootClassName  class to instantiate
     * @param classes  classes that are allowed to use when instantiating an object
     * @return an instance of rootClassName
     * @throws AmbiguousImplementationException  when there are two or more identical implementations of a dependency
     * @throws InjectionCycleException  when it's impossible to instantiate a dependency class due to a cyclical dependency
     * @throws ImplementationNotFoundException  when a dependency is not included into the allowed classes
     */
    public static Object initialize(Class rootClassName, Collection<Class> classes)
            throws AmbiguousImplementationException, InjectionCycleException, ImplementationNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        classes.add(rootClassName);
        HashMap<Class, HashSet<Class>> matrix = buildDependencyMatrix(classes);
        HashMap<Class, Object> objects = new HashMap<>();
        dfs(rootClassName, matrix, new HashMap<Class, Color>(), objects);
        return objects.get(rootClassName);
    }

    /**
     * Build a dependency matrix for traversing it with dfs later. matrix is actually an adjacency list.
     *
     * If classA depends on classB, then matrix.get(classA).contains(classB);
     *
     * This method also handles every possible ambiguity. If there are two or more classes that implement a required
     * dependency, then AmbiguousImplementationException is thrown.
     *
     * @param classes  the vertices for building an adjacency list (matrix)
     * @return adjacency list (matrix) according to the dependency relation on classes
     */
    private static HashMap<Class, HashSet<Class>> buildDependencyMatrix(Collection<Class> classes)
            throws AmbiguousImplementationException {
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

    /**
     * Traverse given adjacency list (matrix) using the dfs algorithm.
     *
     * This method handles every possible condition for InjectionCycleException. If dfs finds a cycle, it immediately
     * throws a corresponding exception.
     *
     * dfs tries to first meet all dependencies (i.e. traverse dependency classes, that is, children of current vertex)
     * and if all dependecies are met, then it creates an instance of currentClass and puts it into objects hash map.
     *
     * @param currentClass  current vertex
     * @param matrix  adjacency list
     * @param color  types of vertices
     * @param objects  a storage with all currently created objects
     */
    private static void dfs(Class currentClass, HashMap<Class, HashSet<Class>> matrix,
                            HashMap<Class, Color> color, HashMap<Class, Object> objects)
            throws InjectionCycleException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
