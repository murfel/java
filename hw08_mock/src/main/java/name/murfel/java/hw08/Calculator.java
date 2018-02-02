import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BinaryOperator;

/**
 * This class provides an ability to compute an infix expression with +-*\/() and natural numbers.
 * It does so by providing an interface to parse an infix expression from string, to convert from
 * an infix expression to a postfix notation, and to compute an expression in postfix notation.
 * <p>
 * Incorrect arguments cause undefined behaviour.
 */
public class Calculator {
    private static HashMap<String, Integer> priority = new HashMap<>();
    private static HashMap<String, BinaryOperator<Integer>> operation = new HashMap<>();

    static {
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("*", 2);
        priority.put("/", 2);

        operation.put("+", (x, y) -> x + y);
        operation.put("-", (x, y) -> x - y);
        operation.put("*", (x, y) -> x * y);
        operation.put("/", (x, y) -> x / y);
    }

    private Stack<Integer> stack;

    /**
     * The instance argument stack is only used inside of calculate method.
     * It does not affect any other method in any way.
     *
     * @param stack a stack which will be used for every calculate call
     */
    public Calculator(Stack<Integer> stack) {
        this.stack = stack;
    }

    /**
     * Read one line from System.in which should represent an arithmetic expression in infix notation
     * with spaces between every token (a token is either a number, or a character from "+-*\/()")
     * and print out the result of this computation to System.out.
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        Calculator calc = new Calculator(new Stack<>());
        ArrayList<Token> l = calc.getInfixExpression(input);
        ArrayList<Token> p = calc.infixToPostfix(l);
        System.out.println(calc.calculate(p));
    }

    /**
     * Split string into tokens and build a list out of them.
     *
     * @param expr an arithmetic expression in infix notation with space between every token
     * @return ArrayList with tokens from expr
     */
    public ArrayList<Token> getInfixExpression(String expr) {
        ArrayList<Token> list = new ArrayList<Token>();
        Scanner scanner = new Scanner(expr);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt())
                list.add(new Token(scanner.nextInt()));
            else
                list.add(new Token(scanner.next()));
        }
        return list;
    }

    /**
     * Convert an expression in infix notation to the same expression in postfix notation.
     * Implemented as the shunting-yard algorithm.
     *
     * @param expr an expresssion in infix notation
     * @return an expression equivalent to expr but in prefix notation
     */
    public ArrayList<Token> infixToPostfix(ArrayList<Token> expr) {
        ArrayList<Token> intermediateStack = new ArrayList<>();
        ArrayList<Token> outputQueue = new ArrayList<>();
        for (Token token : expr) {
            if (token.type == Type.NUMBER) {
                outputQueue.add(token);
            } else if (token.type == Type.OPERATION) {
                while (!intermediateStack.isEmpty()) {
                    Token stackedToken = intermediateStack.get(intermediateStack.size() - 1);
                    if (stackedToken.type == Type.OPERATION &&
                            priority.get(stackedToken.token) >= priority.get(token.token)) {
                        outputQueue.add(stackedToken);
                        intermediateStack.remove(intermediateStack.size() - 1);
                    } else {
                        break;
                    }
                }
                intermediateStack.add(token);
            } else if (token.token.equals("(")) {
                intermediateStack.add(token);
            } else if (token.token.equals(")")) {
                while (!intermediateStack.isEmpty()) {
                    Token stackedToken = intermediateStack.get(intermediateStack.size() - 1);
                    if (stackedToken.token.equals("(")) {
                        intermediateStack.remove(intermediateStack.size() - 1);
                        break;
                    }
                    outputQueue.add(stackedToken);
                    intermediateStack.remove(intermediateStack.size() - 1);
                }
            } else {
                throw new IllegalArgumentException("Unknown token: " + token.token);
            }
        }
        outputQueue.addAll(intermediateStack);
        return outputQueue;
    }

    /**
     * Calculate the result of an expression in postfix notation.
     * Calculation uses the stack provided at the time this object was instantiated.
     * One instance of Calculator can calculate multiple expressions, every time it will use the same stack.
     * <p>
     * The expression is not checked for correctness
     * and an incorrect expression still can be evaluated to some fakish result.
     *
     * @param expr an expression in postfix notation
     * @return the result of the computation
     */
    public Integer calculate(ArrayList<Token> expr) {
        for (Token token : expr) {
            if (token.type == Type.NUMBER) {
                stack.push(token.number);
            } else {
                int o2 = stack.pop();
                int o1 = stack.pop();
                stack.push(operation.get(token.token).apply(o1, o2));
            }
        }
        return stack.pop();
    }


    public enum Type {
        NUMBER,
        PARENTHESIS,
        OPERATION
    }

    /**
     * A class for representing either an Integer number, an operator (+-*\/), or parenthesis.
     */
    public static class Token {
        private Type type;
        private String token;
        private Integer number;

        Token(Integer number) {
            type = Type.NUMBER;
            this.number = number;
        }

        Token(String token) {
            if (token.equals("(") || token.equals(")"))
                type = Type.PARENTHESIS;
            else
                type = Type.OPERATION;
            this.token = token;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) return false;
            if (other == this) return true;
            if (!(other instanceof Token)) return false;
            Token otherToken = (Token) other;
            if (type == otherToken.type) {
                if (type == Type.NUMBER) {
                    return number.equals(otherToken.number);
                }
                return token.equals(otherToken.token);
            }
            return false;
        }
    }
}
