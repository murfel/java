package name.murfel.java.hw10;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Reflector {
    /**
     * Print structure of the given class.
     *
     * The result is a valid Java source code with the same 'interface' as someClass but with a dull implementation.
     *
     * @param someClass a class to print structure of
     */
    public static void printStructure(Class<?> someClass) {
        ClassVisitorUtils.Visitor visitor = new ClassVisitorUtils.Visitor();
        List<String> list = visitor.visit(someClass, true);
        for (String s : list) {
            System.out.println(s);
        }
    }

    /**
     * Compute class difference and print it to System.out.
     *
     * @param c1 the first class to compare to
     * @param c2 the second class to compare to
     */
    public static void diffClasses(Class<?> c1, Class<?> c2) {
        diffClasses(c1, c2, System.out);
    }

    /**
     * Compare only fields and methods in classes c1 and c2. Print to the printStream the difference in form of
     * a string starting with "+ " for a field or method that is present in c1 but absent from c2,
     * or starting with "- " for vice versa.
     *
     * @param c1          the first class to compare to
     * @param c2          the second class to compare to
     * @param printStream a stream to print class difference to
     */
    public static void diffClasses(Class<?> c1, Class<?> c2, PrintStream printStream) {
        DiffVisitor visitor = new DiffVisitor();
        List<String> list1 = visitor.visit(c1, false);
        List<String> list2 = visitor.visit(c2, false);

        SymmetricDifference<String> diff = twoPointerDiff(list1, list2);

        for (String s : diff.firstMinusSecond) {
            printStream.print("+ ");
            printStream.println(s);
        }
        for (String s : diff.secondMinusFirst) {
            printStream.print("- ");
            printStream.println(s);
        }
    }

    /**
     * Traverse two lists with a two pointers algorithm and calculate their symmetric difference.
     * <p>
     * Two pointer algorithm relies on some ordering. In this implementation, natural ordering is used.
     * <p>
     * In addition, two elements e1, e2 are considered equal if {@code e1.compareTo(e2) == 0} with
     * disregard to the Object equals method (so even if natural ordering is not consistent with equals).
     *
     * @param l1 the first list
     * @param l2 the second list
     * @return symmetric difference of the elements in lists
     */
    public static <T extends Comparable<T>> SymmetricDifference<T> twoPointerDiff(List<T> l1, List<T> l2) {
        l1.sort(Comparator.naturalOrder());
        l2.sort(Comparator.naturalOrder());

        int p1 = 0;
        int p2 = 0;

        SymmetricDifference<T> diff = new SymmetricDifference<>();

        while (p1 < l1.size() && p2 < l2.size()) {
            int compare = l1.get(p1).compareTo(l2.get(p2));
            if (compare == 0) {
                p1++;
                p2++;
            } else if (compare < 0) {
                diff.firstMinusSecond.add(l1.get(p1));
                p1++;
            } else {
                diff.secondMinusFirst.add(l2.get(p2));
                p2++;
            }
        }
        if (p1 < l1.size()) {
            diff.firstMinusSecond.addAll(l1.subList(p1, l1.size()));
        } else if (p2 < l2.size()) {
            diff.secondMinusFirst.addAll(l2.subList(p2, l2.size()));
        }

        return diff;
    }

    /**
     * Stores the result of symmetric difference of two mathematical multisets.
     * <p>
     * A mathematical multiset is an unordered collection of elements with repetitions of the same element allowed.
     * <p>
     * Symmetric difference of a mathematical multiset is an operation ^ defined as A^B = A \ B or B \ A
     * where \ is a set difference operator (i.e. A \ B is all elements from A where each element repeated
     * how many times it was repeated in A minus how many times it was repeated in B); and 'or' is a set union operator
     * (i.e. an element would be repeated how many times it was repeated in A plus how many times it was repeated in B).
     */
    public static class SymmetricDifference<T extends Comparable<T>> {
        public List<T> firstMinusSecond;
        public List<T> secondMinusFirst;

        public SymmetricDifference() {
            firstMinusSecond = new LinkedList<>();
            secondMinusFirst = new LinkedList<>();
        }
    }

    /**
     * DiffVisitor serves for the diffClasses function.
     * <p>
     * It visits a class ignoring everything except for fields and methods, also ignoring names.
     */
    private static class DiffVisitor extends ClassVisitorUtils.Visitor {
        @Override
        protected void visitName(String name) {
        }

        @Override
        protected void visitParameterName() {
        }

        @Override
        protected void visitNameWithSpace(String name) {
        }

        @Override
        protected void startVisitingNewScope() {
        }

        @Override
        protected void finishVisitingNewScope() {
        }

        @Override
        protected void visitMethodScope(Method method) {
        }

        @Override
        protected void visitClassModifiers(int modifiers) {
        }

        @Override
        public void visitExtendedClass(Type genericSuperclass) {
        }

        @Override
        public void visitImplementedInterfaces(Type[] genericInterfaces) {
        }

        @Override
        protected void visitConstructorParameters(Type[] parameterTypes) {
        }

        @Override
        public void visitConstructorModifiers(int modifiers) {
        }

        @Override
        protected void visitClassKeyword() {
        }

        @Override
        protected void visitExtendsKeyword() {
        }

        @Override
        protected void visitImplementsKeyword() {
        }

        @Override
        protected void finishVisitingField(Field field) {
            flushLine();
        }

        @Override
        protected void finishVisitingMethodScope(Method method) {
            flushLine();
        }
    }
}
