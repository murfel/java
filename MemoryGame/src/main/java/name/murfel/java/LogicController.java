package name.murfel.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Logic controller decides what to do.
 *
 * It receives user actions by its methods being called by the UI controller.
 *
 * In response, it calls appropriate methods on the UI controller.
 *
 * This class (and the {@code UIController}) should is instantiated once on the application start up and its instance is kept as
 * in a static field. The Logic and UI controller communicate with each other by referencing each other's static fields
 * of the {@code Main} class.
 */
public class LogicController {
    private int[][] board;
    private boolean[][] opened;
    private int prevRow = -1;
    private int prevCol = -1;

    /**
     * Create a game board. The game board is a square matrix with side length equal to boardSize (an even number).
     * The matrix entries are numbers from 1 to boardSize^2 / 2 inclusively, each number is repeated twice in the matrix.
     * These numbers are randomly shuffled and assigned to the matrix cells.
     *
     * Each matrix entry also has a property of being open (the matrix entry number is shown) or closed (not shown).
     * Initially all cells are closed.
     *
     * The board is also printed out to System.out for convenience.
     *
     * @param boardSize the length of a side of a square board, should be a positive even number
     */
    public LogicController(int boardSize) {
        assert boardSize % 2 == 0;

        board = new int[boardSize][boardSize];
        opened = new boolean[boardSize][boardSize];

        int cellsTotal = boardSize * boardSize;
        ArrayList<Integer> values = new ArrayList<>(cellsTotal);
        for (int i = 0; i < 2; i++) {
            values.addAll(IntStream.rangeClosed(1, cellsTotal / 2).boxed().collect(Collectors.toCollection(ArrayList::new)));
        }
        Collections.shuffle(values);

        int p = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = values.get(p++);
            }
        }

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * Calculate and forward to UI the response to the user action clicking a cell.
     *
     * If in the previous turn no active cell was opened, this cell opens.
     * If a user clicks on a cell which was clicked in a previous turn, nothing happens.
     * If a user clicks on a cell with number not equal to the number of cell clicked in the previous turn, the new cell
     * is opened and both cells will be closed after a waiting period.
     * Otherwise (if the two numbers are equal), both cells will remain open but become inactive.
     *
     * @param row the row of the cell a user has clicked
     * @param col the col of the cell a user has clicked
     */
    public void goToCell(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
                || opened[row][col]) {
            return;
        }

        if (prevRow == -1 || prevCol == -1) {
            // open a first cell
            Main.uiController.openCell(row, col, board[row][col]);
            prevRow = row;
            prevCol = col;
        } else if (prevRow == row && prevCol == col) {
            // do nothing
        } else if (board[row][col] != board[prevRow][prevCol]) {
            // open the new cell, wait and close both cells
            Main.uiController.openCell(row, col, board[row][col]);
            Main.uiController.waitAndCloseCell(prevRow, prevCol);
            Main.uiController.waitAndCloseCell(row, col);
            prevRow = -1;
            prevCol = -1;
        } else {
            // open both cells forever
            Main.uiController.openCellForever(prevRow, prevCol, board[row][col]);
            Main.uiController.openCellForever(row, col, board[row][col]);
            opened[prevRow][prevCol] = true;
            opened[row][col] = true;
            prevRow = prevCol = -1;
        }
    }
}
