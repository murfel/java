package name.murfel.java.hw08;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest {
    private static <T> Stack<T> getMockedMyStack() {
        @SuppressWarnings("unchecked")
        Stack<T> mockedStack = mock(Stack.class);
        java.util.Stack<T> behindStack = new java.util.Stack<>();

        when(mockedStack.push(any())).thenAnswer(invocationOnMock -> {
            @SuppressWarnings("unchecked")
            T element = (T) invocationOnMock.getArguments()[0];
            return behindStack.push(element);
        });

        when(mockedStack.pop()).thenAnswer(invocationOnMock -> behindStack.pop());

        return mockedStack;
    }

    @Test
    public void getInfixExpressionSimple() {
        Calculator calc = new Calculator(new Stack<>());
        String input = "6 / 2";
        ArrayList<Calculator.Token> actual = calc.getInfixExpression(input);
        ArrayList<Calculator.Token> expected = new ArrayList<>(Arrays.asList(
                new Calculator.Token(6),
                new Calculator.Token("/"),
                new Calculator.Token(2)
        ));
        assertEquals(expected, actual);
    }

    @Test
    public void getInfixExpressionComplex() {
        Calculator calc = new Calculator(new Stack<>());
        String input = "( 1 + 2 ) * 3 - ( 8 / 4 )";
        ArrayList<Calculator.Token> expected = new ArrayList<>(Arrays.asList(
                new Calculator.Token("("),
                new Calculator.Token(1),
                new Calculator.Token("+"),
                new Calculator.Token(2),
                new Calculator.Token(")"),
                new Calculator.Token("*"),
                new Calculator.Token(3),
                new Calculator.Token("-"),
                new Calculator.Token("("),
                new Calculator.Token(8),
                new Calculator.Token("/"),
                new Calculator.Token(4),
                new Calculator.Token(")")
        ));
        ArrayList<Calculator.Token> actual = calc.getInfixExpression(input);
        assertEquals(expected, actual);
    }

    @Test
    public void infixToPostfixSimple() {
        Calculator calc = new Calculator(new Stack<>());
        ArrayList<Calculator.Token> input = new ArrayList<>(Arrays.asList(
                new Calculator.Token(1),
                new Calculator.Token("+"),
                new Calculator.Token(2)
        ));
        ArrayList<Calculator.Token> expected = new ArrayList<>(Arrays.asList(
                new Calculator.Token(1),
                new Calculator.Token(2),
                new Calculator.Token("+")
        ));
        ArrayList<Calculator.Token> actual = calc.infixToPostfix(input);
        assertEquals(expected, actual);
    }

    @Test
    public void infixToPostfixComplex() {
        // (1 + 2) * 3 - (8 / 4) →
        // 1 2 + 3 * 8 4 / -
        Calculator calc = new Calculator(new Stack<>());
        ArrayList<Calculator.Token> input = new ArrayList<>(Arrays.asList(
                new Calculator.Token("("),
                new Calculator.Token(1),
                new Calculator.Token("+"),
                new Calculator.Token(2),
                new Calculator.Token(")"),
                new Calculator.Token("*"),
                new Calculator.Token(3),
                new Calculator.Token("-"),
                new Calculator.Token("("),
                new Calculator.Token(8),
                new Calculator.Token("/"),
                new Calculator.Token(4),
                new Calculator.Token(")")
        ));
        ArrayList<Calculator.Token> expected = new ArrayList<>(Arrays.asList(
                new Calculator.Token(1),
                new Calculator.Token(2),
                new Calculator.Token("+"),
                new Calculator.Token(3),
                new Calculator.Token("*"),
                new Calculator.Token(8),
                new Calculator.Token(4),
                new Calculator.Token("/"),
                new Calculator.Token("-")
        ));
        ArrayList<Calculator.Token> actual = calc.infixToPostfix(input);
        assertEquals(expected, actual);
    }

    @Test
    public void calculateWithMockSubstituteSingleValue() {
        @SuppressWarnings("unchecked")
        Stack<Integer> mockStack = mock(Stack.class);
        when(mockStack.pop()).thenReturn(555);
        Calculator calc = new Calculator(mockStack);
        ArrayList<Calculator.Token> expr = new ArrayList<>(Collections.singletonList(
                new Calculator.Token(555)
        ));
        assertEquals(new Integer(555), calc.calculate(expr));
    }

    @Test
    public void calculateWithMockSubstituteValuesForZeros() {
        @SuppressWarnings("unchecked")
        Stack<Integer> mockStack = mock(Stack.class);
        when(mockStack.pop()).thenReturn(0);
        Calculator calc = new Calculator(mockStack);
        ArrayList<Calculator.Token> expr = new ArrayList<>(Arrays.asList(
                new Calculator.Token(0),
                new Calculator.Token(0),
                new Calculator.Token("+")
        ));
        assertEquals(new Integer(0), calc.calculate(expr));
    }

    @Test
    public void calculateWithMockSimple() {
        Calculator calculator = new Calculator(getMockedMyStack());
        // 6 2 / = 6 / 2 = 3
        ArrayList<Calculator.Token> expr = new ArrayList<>(Arrays.asList(
                new Calculator.Token(6),
                new Calculator.Token(2),
                new Calculator.Token("/")
        ));
        assertEquals(new Integer(3), calculator.calculate(expr));
    }

    @Test
    public void calculateWithMockComplex() {
        Calculator calculator = new Calculator(getMockedMyStack());
        // (1 + 2) * 3 - (8 / 4) = 7
        // 1 2 + 3 * 8 4 / - = 7
        ArrayList<Calculator.Token> expr = new ArrayList<>(Arrays.asList(
                new Calculator.Token(1),
                new Calculator.Token(2),
                new Calculator.Token("+"),
                new Calculator.Token(3),
                new Calculator.Token("*"),
                new Calculator.Token(8),
                new Calculator.Token(4),
                new Calculator.Token("/"),
                new Calculator.Token("-")
        ));
        assertEquals(new Integer(7), calculator.calculate(expr));
    }


}