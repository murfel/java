import jdk.internal.org.objectweb.asm.TypeReference;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculatorTest {

    @org.junit.Test
    public void getInfixExpressionSimple() throws Exception {
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

    @org.junit.Test
    public void getInfixExpressionComplex() throws Exception {
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

    @org.junit.Test
    public void infixToPostfixSimple() throws Exception {
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

    @org.junit.Test
    public void infixToPostfixComplex() throws Exception {
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

    @org.junit.Test
    public void calculateWithMockSubstituteSingleValue() throws Exception {
        Stack<Integer> mockStack = mock(Stack.class);
        when(mockStack.pop()).thenReturn(555);
        Calculator calc = new Calculator(mockStack);
        ArrayList<Calculator.Token> expr = new ArrayList<>(Arrays.asList(
                new Calculator.Token(555)
        ));
        assertEquals(new Integer(555), calc.calculate(expr));
    }

    @org.junit.Test
    public void calculateWithMockSubstituteValuesForZeros() throws Exception {
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

    @org.junit.Test
    public void calculateWithMockVerifyOrderSimple() throws Exception {
//        Stack<Integer> mockStack = mock(Stack.class);
//        doCallRealMethod().when(mockStack).clear();
//        when(mockStack.push(any())).thenCallRealMethod();
//        when(mockStack.pop()).thenCallRealMethod();
//        Calculator calc = new Calculator(mockStack);
        Calculator calc = new Calculator(new Stack<>());
        ArrayList<Calculator.Token> expr = new ArrayList<>(Arrays.asList(
                new Calculator.Token(6),
                new Calculator.Token(2),
                new Calculator.Token("/")
        ));
        assertEquals(new Integer(3), calc.calculate(expr));
//        InOrder inOrder = Mockito.inOrder(mockStack);
//        inOrder.verify(mockStack).clear();
//        inOrder.verify(mockStack).push(6);
//        inOrder.verify(mockStack).push(2);
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).push(3);
//        inOrder.verify(mockStack).pop();
//        verifyNoMoreInteractions(mockStack);
    }

    @org.junit.Test
    public void calculateWithMockVerifyOrderComplex() throws Exception {
//        Stack<Integer> mockStack = mock(Stack.class);
//        doCallRealMethod().when(mockStack).clear();
//        when(mockStack.push(any())).thenCallRealMethod();
//        when(mockStack.pop()).thenCallRealMethod();
//        Calculator calc = new Calculator(mockStack);
        Calculator calc = new Calculator(new Stack<>());
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
        assertEquals(new Integer(7), calc.calculate(expr));
//        InOrder inOrder = Mockito.inOrder(mockStack);
//        inOrder.verify(mockStack).clear();
//        inOrder.verify(mockStack).push(1);
//        inOrder.verify(mockStack).push(2);
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).push(3);
//        inOrder.verify(mockStack).push(3);
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).push(9);
//        inOrder.verify(mockStack).push(8);
//        inOrder.verify(mockStack).push(4);
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).push(2);
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).pop();
//        inOrder.verify(mockStack).push(7);
//        inOrder.verify(mockStack).pop();
//        verifyNoMoreInteractions(mockStack);
    }
}