import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest {
    @org.junit.Test
    public void getInfixExpression() throws Exception {
        Calculator calc = new Calculator(new Stack<>());
    }

    @org.junit.Test
    public void infixToPostfix() throws Exception {
    }

    @org.junit.Test
    public void calculate() throws Exception {
        Stack<Integer> mockStack = mock(Stack.class);
        Calculator calc = new Calculator(mockStack);
        when(mockStack.pop()).thenReturn(0);
//        ArrayList<Calculator.Token> expr = Arrays.asList({
//                new Calculator.Token(6),
//                new Calculator.Token(2),
//                new Calculator.Token("/")
//        });
        ArrayList<Calculator.Token> expr = new ArrayList<>();
        expr.add(new Calculator.Token(0));
        expr.add(new Calculator.Token(0));
        expr.add(new Calculator.Token("+"));
        assertEquals(new Integer(0), calc.calculate(expr));
    }
}