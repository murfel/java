import jdk.nashorn.internal.objects.annotations.Function;

import java.util.*;
import java.util.function.BinaryOperator;

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

    public enum Type {
        NUMBER,
        PARENTHESIS,
        OPERATION
    }

    public class Token {
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

        public Type type;
        public String token;
        public Integer number;
    }

    public Calculator(Stack<Integer> stack) {
        this.stack = stack;
    }

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

    public ArrayList<Token> infixToPostfix(ArrayList<Token> expr) {
        ArrayList<Token> intermediateStack = new ArrayList<>();
        ArrayList<Token> outputQueue = new ArrayList<>();
        for (Token token : expr) {
            if (token.type == Type.NUMBER) {
                outputQueue.add(token);
            }
            else if (token.type == Type.OPERATION) {
                while (!intermediateStack.isEmpty()) {
                    Token stackedToken = intermediateStack.get(intermediateStack.size() - 1);
                    if (stackedToken.type == Type.OPERATION &&
                            priority.get(stackedToken.token) >= priority.get(token.token)) {
                        outputQueue.add(stackedToken);
                        intermediateStack.remove(intermediateStack.size() - 1);
                    }
                    else {
                        break;
                    }
                }
                intermediateStack.add(token);
            }
            else if (token.token.equals("(")) {
                intermediateStack.add(token);
            }
            else if (token.token.equals(")")) {
                while (!intermediateStack.isEmpty()) {
                    Token stackedToken = intermediateStack.get(intermediateStack.size() - 1);
                    if (stackedToken.token.equals("(")) {
                        intermediateStack.remove(intermediateStack.size() - 1);
                        break;
                    }
                    outputQueue.add(stackedToken);
                    intermediateStack.remove(intermediateStack.size() - 1);
                }
            }
            else {
                throw new IllegalArgumentException("Unknown token: " + token.token);
            }
        }
        outputQueue.addAll(intermediateStack);
        return outputQueue;
    }

    public Integer calculate(ArrayList<Token> expr) {
        stack.clear();
        for (Token token : expr) {
            if (token.type == Type.NUMBER) {
                stack.push(token.number);
            }
            else {
                int o2 = stack.pop();
                int o1 = stack.pop();
                stack.push(operation.get(token.token).apply(o1, o2));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String input1 = "2 + 3";  // 5
        String input2 = "( 1 + 2 )";  // 3
        String input3 = "1 + 2 * 3 + 1 / 2 - 1";  // 6

        Calculator calc = new Calculator(new Stack<>());

        ArrayList<Token> l = calc.getInfixExpression(input3);
        for (Token t : l) {
            if (t.type == Type.NUMBER)
                System.out.println(t.number);
            else
                System.out.println(t.token);
        }
        System.out.println("~~~");
        ArrayList<Token> p = calc.infixToPostfix(l);
        for (Token t : p) {
            if (t.type == Type.NUMBER)
                System.out.println(t.number);
            else
                System.out.println(t.token);
        }
        System.out.println("~~~");
        System.out.println(calc.calculate(p));
    }

    private Stack<Integer> stack;
}
