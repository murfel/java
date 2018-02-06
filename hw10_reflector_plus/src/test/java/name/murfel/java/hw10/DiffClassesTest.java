package name.murfel.java.hw10;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DiffClassesTest {

    @org.junit.Test
    public void twoPointerDiff() {
        List<Integer> l1 = Arrays.asList(1, 2, 2, 3, 3, 4, 5);
        List<Integer> l2 = Arrays.asList(2, 3, 3, 4, 6, 7);
        Reflector.SymmetricDifference<Integer> expected = new Reflector.SymmetricDifference<>();
        expected.firstMinusSecond = Arrays.asList(1, 2, 5);
        expected.secondMinusFirst = Arrays.asList(6, 7);
        Reflector.SymmetricDifference<Integer> actual = Reflector.twoPointerDiff(l1, l2);
        assertArrayEquals(expected.firstMinusSecond.toArray(), actual.firstMinusSecond.toArray());
        assertArrayEquals(expected.secondMinusFirst.toArray(), actual.secondMinusFirst.toArray());

    }

    private void testDiffClasses(Class<?> c1, Class<?> c2, String expected) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        Reflector.diffClasses(c1, c2, ps);
        assertEquals(expected, os.toString());
    }

    @org.junit.Test
    public void emptyClassesOk() {
        testDiffClasses(TestClasses.EmptyClass.class, TestClasses.AnotherEmptyClass.class, "");
    }

    @org.junit.Test
    public void ignoreModifiersOk() {
        testDiffClasses(TestClasses.EmptyClass.class, TestClasses.PrivateEmptyClass.class, "");
    }

    @org.junit.Test
    public void hasFieldOk() {
        testDiffClasses(TestClasses.HasFieldA.class, TestClasses.HasFieldB.class, "");
    }

    @org.junit.Test
    public void countsNumberOfFields() {
        testDiffClasses(TestClasses.HasFieldA.class, TestClasses.HasTwoSameFields.class, "- int \n");
    }

    @org.junit.Test
    public void hasComplicatedFieldOk() {
        testDiffClasses(TestClasses.HasComplicatedFieldA.class, TestClasses.HasComplicatedFieldB.class, "");
    }

    @org.junit.Test
    public void complicatedFieldWithoutModifier() {
        testDiffClasses(TestClasses.HasComplicatedFieldA.class, TestClasses.HasComplicatedFieldWithoutModifier.class,
                "+ protected final java.util.List<java.lang.String> \n" +
                        "- protected java.util.List<java.lang.String> \n");
    }

    @org.junit.Test
    public void complicatedFieldWithAnotherType() {
        testDiffClasses(TestClasses.HasComplicatedFieldA.class, TestClasses.HasComplicatedFieldWithOtherType.class,
                "+ protected final java.util.List<java.lang.String> \n" +
                        "- protected final java.util.List<java.lang.Integer> \n");
    }

    @org.junit.Test
    public void ignoreConstructorsOk() {
        testDiffClasses(TestClasses.HasOneConstructor.class, TestClasses.HasTwoConstructors.class,
                "");
    }


    @org.junit.Test
    public void hasMethodOk() {
        testDiffClasses(TestClasses.HasMethodA.class, TestClasses.HasMethodB.class,
                "");
    }

    @org.junit.Test
    public void countsNumberOfMethods() {
        testDiffClasses(TestClasses.HasMethodA.class, TestClasses.HasTwoSameMethods.class,
                "- void () \n");
    }

    @org.junit.Test
    public void looksOnParam() {
        testDiffClasses(TestClasses.HasMethodA.class, TestClasses.HasMethodWithParamA.class,
                "+ void () \n" +
                        "- void (int ) \n");
    }

    @org.junit.Test
    public void hasMethodWithParamOk() {
        testDiffClasses(TestClasses.HasMethodWithParamA.class, TestClasses.HasMethodWithParamB.class,
                "");
    }

    @org.junit.Test
    public void looksOnParamType() {
        testDiffClasses(TestClasses.HasMethodWithParamA.class, TestClasses.HasMethodWithAnotherParam.class,
                "+ void (int ) \n" +
                        "- void (boolean ) \n");
    }

    @org.junit.Test
    public void looksOnModifier() {
        testDiffClasses(TestClasses.HasMethodA.class, TestClasses.HasMethodWithAnotherModifier.class,
                "+ void () \n" +
                        "- public void () \n");
    }

    @org.junit.Test
    public void looksOnReturnType() {
        testDiffClasses(TestClasses.HasMethodA.class, TestClasses.HasMethodWithAnotherReturnType.class,
                "+ void () \n" +
                        "- int () \n");
    }

    @org.junit.Test
    public void looksOnObjectParam() {
        testDiffClasses(TestClasses.HasMethodWithObjectParam.class, TestClasses.HasMethodWithAnotherObjectParam.class,
                "+ void (java.lang.Integer ) \n" +
                        "- void (java.lang.Boolean ) \n");
    }

    @org.junit.Test
    public void hasMethodWithObjectReturnTypeOk() {
        testDiffClasses(TestClasses.HasMethodWithObjectReturnTypeA.class, TestClasses.HasMethodWithObjectReturnTypeB.class,
                "");
    }

    @org.junit.Test
    public void looksOnObjectReturnType() {
        testDiffClasses(TestClasses.HasMethodWithObjectReturnTypeA.class, TestClasses.HasMethodWithAnotherObjectReturnType.class,
                "+ java.lang.Integer () \n" +
                        "- java.lang.Boolean () \n");
    }

    @org.junit.Test
    public void hasComplesMethodOk() {
        testDiffClasses(TestClasses.HasComplexMethodsA.class, TestClasses.HasComplexMethodsB.class,
                "");
    }

    @org.junit.Test
    public void looksOnComplexMethods() {
        testDiffClasses(TestClasses.HasComplexMethodsA.class, TestClasses.HasOtherComplexMethods.class,
                "- public java.util.List<java.lang.Integer> (int , java.lang.Integer , boolean ) \n");
    }


    @org.junit.Test
    public void looksOnMethodsAndField() {
        testDiffClasses(TestClasses.HasOtherComplexMethods.class, TestClasses.HasComplexMethodsAndField.class,
                "+ public java.util.List<java.lang.Integer> (int , java.lang.Integer , boolean ) \n" +
                        "- int \n");
    }



    public static class TestClasses {
        class EmptyClass {
        }

        class AnotherEmptyClass {
        }

        private class PrivateEmptyClass {
        }

        class HasFieldA {
            int a;
        }

        class HasFieldB {
            int b;
        }

        class HasTwoSameFields {
            int a, b;
        }

        class HasComplicatedFieldA {
            protected final List<String> a = null;
        }

        class HasComplicatedFieldB {
            protected final List<String> b = null;
        }

        class HasComplicatedFieldWithoutModifier {
            protected List<String> a;
        }

        class HasComplicatedFieldWithOtherType {
            protected final List<Integer> a = null;
        }

        class HasOneConstructor {
            HasOneConstructor() {}
        }

        class HasTwoConstructors {
            HasTwoConstructors() {}
            HasTwoConstructors(int a) {}
        }

        class HasMethodA {
            void a() {}
        }

        class HasMethodB {
            void b() {}
        }

        class HasTwoSameMethods {
            void a() {}
            void b() {}
        }

        class HasMethodWithParamA {
            void a(int a) {}
        }

        class HasMethodWithParamB {
            void b(int b) {}
        }

        class HasMethodWithAnotherParam {
            void a(boolean a) {}
        }

        class HasMethodWithAnotherModifier {
            public void a() {}
        }

        class HasMethodWithAnotherReturnType {
            int a() { return 0; }
        }

        class HasMethodWithObjectParam {
            void a(Integer a) {}
        }

        class HasMethodWithAnotherObjectParam {
            void a(Boolean a) {}
        }

        class HasMethodWithObjectReturnTypeA {
            Integer a() { return null; }
        }

        class HasMethodWithObjectReturnTypeB {
            Integer a() { return null; }
        }

        class HasMethodWithAnotherObjectReturnType {
            Boolean a() { return null; }
        }

        class HasComplexMethodsA {
            private int a() { return 0; }
            public final List<Integer> b(int a, Integer b, boolean c) { return null; }
        }

        class HasComplexMethodsB {
            private int b() { return 0; }
            public final List<Integer> a(int a, Integer b, boolean c) { return null; }
        }

        class HasOtherComplexMethods {
            private int a() { return 0; }
            public final List<Integer> b(int a, Integer b, boolean c) { return null; }
            public List<Integer> a(int a, Integer b, boolean c) { return null; }
        }

        class HasComplexMethodsAndField {
            int foo;
            private int b() { return 0; }
            public final List<Integer> a(int a, Integer b, boolean c) { return null; }
        }
    }
}