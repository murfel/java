public interface Operation {
    abstract void evaluate(Stack<Operation> expression, Stack<Integer> operands);
}
