import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * A collection of instrument to work with two-dimensional square arrays.
 */
public class TwoDArrayInstruments {
    private static int[] arrayTraversal;
    private static int traversalLength;

    /**
     * A wrapper for working with coordinates on a two-dimensional square array.
     */
    private static class Coordinates {
        public int row, col;
        public Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }
        public void moveRight() {
            col++;
        }
        public void moveLeft() {
            col--;
        }
        public void moveUp() {
            row--;
        }
        public void moveDown() {
            row++;
        }
    }

    /**
     * Add an element at the position specified by coords in array to the current arrayTraversal.
     * @param array  a two-dimensional square array currently being traversed
     * @param coords  coordinates of an element in array
     */
    private static void addToTraversal(int[][] array, Coordinates coords) {
        arrayTraversal[traversalLength++] = array[coords.row][coords.col];
    }

    /**
     * Transpose the array as a rectangular matrix.
     * <p>
     * A transpose of a matrix A is a matrix B for which holds B[i][j] == A[j][i].
     *
     * @param array  a two-dimensional rectangualar array
     * @return  the transpose of the input array
     */
    private static int[][] transpose(int[][] array) {
        if (array.length == 0 || array[0].length == 0)
            return array;
        int[][] transposed = new int[array[0].length][];
        for (int row = 0; row < array[0].length; row++)
            transposed[row] = new int[array.length];
        for (int row = 0; row < array[0].length; row++)
            for (int col = 0; col < array.length; col++)
                transposed[row][col] = array[col][row];
        return transposed;
    }

    /**
     * Compare two int arrays.
     */
    private static class ArrayComparator implements Comparator<int[]> {
        /**
         * Compare two int arrays by their first elements. If any of the arrays is empty, the arrays are considered equal.
         *
         * @param t1  the element to compare to
         * @param t2  the element being compared
         * @return  a number greater than 0 if t1[0] is greater than t2[0],
         * 0 if they are equal and a negative number otherwise
         */
        public int compare(int[] t1, int[] t2) {
            if (t1.length == 0 || t2.length == 0)
                return 0;
            return t1[0] - t2[0];
        }
    }

    /**
     * Print the current content of arrayTraversal to System.out.
     */
    private static void printArrayTraversal() {
        for (int i = 0; i < traversalLength; i++)
            System.out.print(arrayTraversal[i] + " ");
    }

    /**
     * Print to System.out a two-dimensional square array of ints of odd length by traversing it spirally starting from
     * the center (row array.length / 2, column array.length / 2). The spiral traversing starts by going right and then down.
     *
     * @param array  a two-dimensional square array of ints for printing
     * @return a one-dimensional array of elements in order of the spiral traversal
     */
    public static int[] printSpirally(int[][] array) {
        if (array.length == 0)
            return new int[0];
        arrayTraversal = new int[array.length * array.length];
        traversalLength = 0;
        Coordinates coords = new Coordinates(array.length / 2, array.length / 2);
        addToTraversal(array, coords);
        if (array.length == 1) {
            printArrayTraversal();
            return arrayTraversal;
        }
        for (int i = 0; i < array.length / 2; i++) {
            coords.moveRight();
            addToTraversal(array, coords);
            int sideLength = (i + 1) * 2;
            for (int j = 0; j < sideLength - 1; j++) {
                coords.moveDown();
                addToTraversal(array, coords);
            }
            for (int j = 0; j < sideLength; j++) {
                coords.moveLeft();
                addToTraversal(array, coords);
            }
            for (int j = 0; j < sideLength; j++) {
                coords.moveUp();
                addToTraversal(array, coords);
            }
            for (int j = 0; j < sideLength; j++) {
                coords.moveRight();
                addToTraversal(array, coords);
            }
        }
        printArrayTraversal();
        return arrayTraversal;
    }

    /**
     * Sort a two-dimensional array by the first elements in columns in increasing order.
     *
     * @param array  a two-dimensional array
     * @return  the sorted by first elements in columns in increasing order version of the input array
     */
    public static int[][] sortColumnsByFirstElement(int[][] array) {
        int[][] newArray = transpose(array);
        Collections.sort(Arrays.asList(newArray), new ArrayComparator());
        return transpose(newArray);
    }
}
