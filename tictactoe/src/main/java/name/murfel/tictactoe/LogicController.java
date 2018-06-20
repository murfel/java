package name.murfel.tictactoe;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Logic controller decides what to do.
 * <p>
 * It receives user actions by its methods being called by the UI controller.
 * <p>
 * In response, it calls appropriate methods on the UI controller.
 * <p>
 * This class (and the {@code UIController}) should is instantiated once on the application start up and its instance is kept as
 * in a static field. The Logic and UI controller communicate with each other by referencing each other's static fields
 * of the {@code Main} class.
 */
public class LogicController {
    public int games_tied = 0;
    public int games_won_by_computer = 0;
    public int games_won_by_player = 0;
    private int boardSize;
    private Mark[][] board;
    private Mark nextMark = Mark.Cross;
    private boolean playingWithPC;
    private boolean easyDifficulty;
    private Mark computerMark = Mark.Nought;


    /**
     * Initialize logic controller.
     *
     * @param boardSize the size of one side of a square board
     */
    public LogicController(int boardSize) {
        this.boardSize = boardSize;
        board = new Mark[boardSize][boardSize];
    }

    /**
     * If mark is cross return Nought, else return Cross. If mark is None, nothing is guaranteed.
     *
     * @param mark a mark to ask a next from
     * @return next mark
     */
    public static Mark nextMarkAfter(Mark mark) {
        if (mark == Mark.None) {
            return Mark.None;
        }
        return mark == Mark.Cross ? Mark.Nought : Mark.Cross;
    }

    /**
     * Rotate the board 90 degrees to the right. It creates a new board thus leaving the old board intact.
     *
     * @param board a square board to rotate
     * @param <T>   type of elements in the board
     * @return a new rotated board
     */
    public static <T> T[][] getRotatedBoard(T[][] board) {
        T[][] rotatedBoard = (T[][]) Array.newInstance(board[0][0].getClass(), board.length, board.length);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                rotatedBoard[j][board.length - 1 - i] = board[i][j];
            }
        }
        return rotatedBoard;
    }

    /**
     * Check if there is a horizontal row strike or a diagonal strike from top left to down right.
     * <p>
     * If the board is not correct, i.e. cannot be achieved by playing correct tic-tac-toe, nothing is guaranteed.
     *
     * @param board a board to check winning on
     * @return mark if there is a winning combination of such a mark, else None
     */
    private static Mark checkHalfWinning(Mark[][] board) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][0] == Mark.None) {
                continue;
            }
            boolean strike = true;
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] != board[row][0]) {
                    strike = false;
                    break;
                }
            }
            if (strike) {
                return board[row][0];
            }
        }

        if (board[0][0] != Mark.None) {
            boolean strike = true;
            for (int i = 0; i < board.length; i++) {
                if (board[i][i] != board[0][0]) {
                    strike = false;
                    break;
                }
            }
            if (strike) {
                return board[0][0];
            }
        }

        return Mark.None;
    }

    /**
     * Check if there is any winning combination for any mark (Cross or Nought).
     * <p>
     * Winning combinations are horizontal, vertical, and diagonal strikes of three marks of the same type.
     * <p>
     * If the board is not correct, i.e. cannot be achieved by playing correct tic-tac-toe, nothing is guaranteed.
     *
     * @param board a board to check winning on
     * @return mark if there is a winning combination of such a mark, else None
     */
    @Contract(pure = true)
    public static Mark checkWinning(Mark[][] board) {
        Mark mark = checkHalfWinning(board);
        if (mark != Mark.None) {
            return mark;
        }
        return checkHalfWinning(getRotatedBoard(board));
    }

    /**
     * Makes a uniformly random move of the {@code mark} to any free cell (marked with None).
     *
     * @param board a board where to put mark to
     * @param mark  a mark to put
     */
    public static void makeRandomMove(Mark[][] board, Mark mark) {
        Random random = new Random();
        int move = random.nextInt(countEmptyCells(board));
        out:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != Mark.None) {
                    continue;
                }
                if (move == 0) {
                    board[i][j] = mark;
                    break out;
                } else {
                    move--;
                }
            }
        }
    }

    /**
     * Makes a smart move of the {@code mark} to any free cell (marked with None).
     * <p>
     * To estimate a smart move it uses the Monte Carlo method.
     *
     * @param board a board where to put mark to
     * @param mark  a mark to put
     */
    public static void makeSmartMove(Mark[][] board, Mark mark) {
        if (countEmptyCells(board) == 0) {
            return;
        }

        int[][] scores = new int[board.length][board.length];

        int maxScore = -1;
        int maxI = 0;
        int maxJ = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != Mark.None) {
                    scores[i][j] = -1;
                    continue;
                }
                board[i][j] = mark;
                // their score is from -200 to 200, our score is from 400 to 0
                scores[i][j] = 200 - scoreBoard(board, nextMarkAfter(mark));
                board[i][j] = Mark.None;

                if (scores[i][j] > maxScore) {
                    maxI = i;
                    maxJ = j;
                    maxScore = scores[i][j];
                }
            }
        }

        if (maxScore == -1) {
            System.err.println("Incorrect board");
            return;
        }
        board[maxI][maxJ] = mark;
    }

    /**
     * Counts how many empty cells are currently on the board.
     * <p>
     * A cell is empty if there is no Cross or Nought, i.e. if there is the None mark.
     *
     * @param board a board to count empty cells on
     * @return the number of empty cells
     */
    public static int countEmptyCells(Mark[][] board) {
        int count = 0;
        for (Mark[] row : board) {
            for (Mark mark : row) {
                if (mark == Mark.None) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Run a hundred of trials and pick the best cell to put the {@code mark}
     * (very likely corresponding to the winning position)
     *
     * @param board board to score
     * @param mark  the mark to choose the best position for
     * @return
     */
    public static int scoreBoard(Mark[][] board, final Mark mark) {
        int score = 0;
        for (int trialNumber = 0; trialNumber < 100; trialNumber++) {
            Mark[][] customBoard = new Mark[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                customBoard[i] = board[i].clone();
            }

            Mark nextMark = mark;
            while (countEmptyCells(customBoard) > 0) {
                makeRandomMove(customBoard, nextMark);
                nextMark = (nextMark == Mark.Cross ? Mark.Nought : Mark.Cross);
                if (checkWinning(customBoard) != Mark.None) {
                    break;
                }
            }

            Mark winner = checkWinning(customBoard);
            if (winner == mark) {
                score += 2;
            } else if (winner == Mark.None) {
                score++;
            } else {
                score -= 2;
            }
        }

        return score;
    }

    /**
     * Set a hot seat flag.
     */
    public void startNewHotSeatGame() {
        playingWithPC = false;
        startNewRound();
    }

    /**
     * Set a playing with PC flag and set difficulty.
     *
     * @param easyDifficulty if true then easy difficulty is applied, else the hard one
     */
    public void startNewGameWithDifficulty(boolean easyDifficulty) {
        this.easyDifficulty = easyDifficulty;
        playingWithPC = true;
        startNewRound();
    }

    /**
     * Check if someone won or there are no more moves left and let the UI know.
     *
     * @return true if game has finished, else false
     */
    private boolean finishIfWinning() {
        Mark winner = checkWinning();
        if (winner != Mark.None) {
            nextMark = Mark.None;
            Main.uiController.declareWinner(winner);
            if (playingWithPC) {
                if (winner == computerMark) {
                    games_won_by_computer++;
                } else {
                    games_won_by_player++;
                }
            }
            return true;
        } else if (countEmptyCells(board) == 0) {
            nextMark = Mark.None;
            Main.uiController.declareTie();
            if (playingWithPC) {
                games_tied++;
            }
            return true;
        }
        return false;
    }

    /**
     * Respond to a player making a move to the cell.
     *
     * @param row a row where move was made
     * @param col a col where move was made
     */
    public void goToCell(int row, int col) {
        if (board[row][col] != Mark.None) {
            return;
        }

        board[row][col] = nextMark;
        Main.uiController.drawBoard(board);

        if (finishIfWinning()) {
            return;
        }

        nextMark = nextMarkAfter(nextMark);

        if (playingWithPC) {
            if (easyDifficulty) {
                makeRandomMove();
            } else {
                makeSmartMove();
            }
            Main.uiController.drawBoard(board);
            nextMark = nextMarkAfter(nextMark);
            finishIfWinning();
        }
    }

    @Contract(pure = true)
    private Mark checkWinning() {
        return checkWinning(board);
    }

    private void makeRandomMove() {
        makeRandomMove(board, computerMark);
    }

    private void makeSmartMove() {
        makeSmartMove(board, nextMark);
    }

    /**
     * Prepare for the next game round with the same settings.
     */
    public void startNewRound() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Mark.None;
            }
        }
        Main.uiController.drawBoard(board);
        Main.uiController.declareGameInProgress();
        nextMark = Mark.Cross;
    }
}
