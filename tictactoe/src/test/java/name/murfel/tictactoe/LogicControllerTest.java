package name.murfel.tictactoe;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static name.murfel.tictactoe.Mark.*;
import static org.junit.Assert.*;

public class LogicControllerTest {
    @Test
    public void checkWinningRowCross() {
        Mark[][] board = {{Nought, Nought, None},
                {Cross, Cross, Cross},
                {None, None, None}};

        assertEquals(Cross, LogicController.checkWinning(board));
    }

    @Test
    public void checkWinningNone3() {
        Mark[][] board = {{Cross, Nought, Cross},
                {Cross, Cross, Nought},
                {Nought, Cross, Nought}};

        assertEquals(None, LogicController.checkWinning(board));
    }

    @Test
    public void checkWinningColNought() {
        Mark[][] board = {{Nought, Nought, Cross},
                {Cross, Nought, Cross},
                {Cross, Nought, None}};

        assertEquals(Nought, LogicController.checkWinning(board));
    }

    @Test
    public void checkWinningDiagonalNought() {
        Mark[][] board = {{Nought, Nought, Cross},
                {Cross, Nought, Cross},
                {Cross, Cross, Nought}};

        assertEquals(Nought, LogicController.checkWinning(board));
    }

    @Test
    public void checkWinningNone() {
        Mark[][] board = {{Cross, Nought, Cross},
                {Nought, Cross, Cross},
                {Nought, Cross, Nought}};

        assertEquals(None, LogicController.checkWinning(board));
    }

    @Test
    public void checkWinningNone2() {
        Mark[][] board = {{Nought, None, None},
                {Nought, Cross, Nought},
                {Cross, None, None}};

        assertEquals(None, LogicController.checkWinning(board));
    }

    private void testMakeRandomMove(@NotNull Mark[][] board) {
        Mark[][] new_board = new Mark[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            new_board[i] = board[i].clone();
        }

        LogicController.makeRandomMove(board, Nought);

        ArrayList<Mark> vals = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (new_board[i][j] == None) {
                    vals.add(board[i][j]);
                } else {
                    assertEquals(new_board[i][j], board[i][j]);
                }
            }
        }

        boolean already_found = false;
        for (Mark val : vals) {
            if (val == None) {
                continue;
            }
            assertEquals(Nought, val);
            if (!already_found) {
                already_found = true;
            } else {
                fail("Random made two or more moves");
            }
        }
        assertTrue("Random didn't make any move", already_found);
    }

    @Test
    public void makeRandomMoveSimple() {
        Mark[][] board = {{None, None, None},
                {None, None, None},
                {None, None, None}};
        testMakeRandomMove(board);
    }

    @Test
    public void makeRandomMove1() {
        Mark[][] board = {{Cross, Cross, Cross},
                {Cross, None, Cross},
                {None, None, None}};
        testMakeRandomMove(board);
    }

    @Test
    public void makeRandomMove2() {
        Mark[][] board = {{None, None, Cross},
                {Cross, None, None},
                {None, None, None}};
        testMakeRandomMove(board);
    }

    @Test
    public void scoreBoardCrossWinningNoEmptyCells() {
        Mark[][] board = {{Cross, Nought, Cross},
                {Cross, Nought, Cross},
                {Nought, Cross, Cross}};
        int score = LogicController.scoreBoard(board, Cross);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardNoughtWinningNoEmptyCells() {
        Mark[][] board = {{Cross, Nought, Cross},
                {Cross, Nought, Nought},
                {Nought, Nought, Cross}};
        int score = LogicController.scoreBoard(board, Nought);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardCrossWinning() {
        Mark[][] board = {{Cross, Cross, Cross},
                {Cross, None, Cross},
                {None, None, None}};
        int score = LogicController.scoreBoard(board, Cross);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardNoughtWinning() {
        Mark[][] board = {{Cross, None, Cross},
                {Cross, Nought, Cross},
                {Nought, Nought, Nought}};
        int score = LogicController.scoreBoard(board, Nought);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardCrossAboutToWin() {
        Mark[][] board = {{Cross, None, Cross},
                {Cross, Nought, Nought},
                {Nought, Nought, Cross}};
        int score = LogicController.scoreBoard(board, Cross);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardNoughtAboutToWin() {
        Mark[][] board = {{Cross, None, Cross},
                {Cross, Nought, Nought},
                {Nought, Nought, Cross}};
        int score = LogicController.scoreBoard(board, Nought);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardCrossAboutToWin2() {
        Mark[][] board = {{Cross, None, Cross},
                {Cross, Nought, None},
                {Nought, Nought, Cross}};
        int score = LogicController.scoreBoard(board, Cross);
        assertEquals(200, score);
    }

    @Test
    public void scoreBoardOneMoveLeft() {
        Mark[][] board = {{Nought, Cross, Cross},
                {Cross, Nought, Cross},
                {Nought, None, Nought}};
        int score = LogicController.scoreBoard(board, Nought);
        assertEquals(200, score);
    }

    @Test
    public void makeSmartMoveOneMoveLeft() {
        Mark[][] board = {{Nought, Cross, Cross},
                {Cross, Nought, Cross},
                {Nought, None, Nought}};
        Mark[][] expected = {{Nought, Cross, Cross},
                {Cross, Nought, Cross},
                {Nought, Nought, Nought}};
        LogicController.makeSmartMove(board, Nought);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                assertEquals(expected[i][j], board[i][j]);
            }
        }
    }

    @Test
    public void makeSmartMoveCrossAboutToWin2() {
        Mark[][] board = {{Cross, None, Cross},
                {None, Nought, None},
                {Nought, Nought, Cross}};
        LogicController.makeSmartMove(board, Cross);
        assertTrue(board[0][1] == Cross || board[1][2] == Cross);
    }

    @Test
    public void makeSmartMoveNougthAboutToWin() {
        Mark[][] board = {{Cross, None, Cross},
                {Cross, Nought, Nought},
                {Nought, Nought, Cross}};
        LogicController.makeSmartMove(board, Nought);
    }

    @Test
    public void countEmptyCellsZero() {
        Mark[][] board = {
                {Cross, Nought, Cross},
                {Cross, Cross, Nought},
                {Nought, Nought, Cross}
        };
        assertEquals(0, LogicController.countEmptyCells(board));
    }

    @Test
    public void countEmptyCellsOne() {
        Mark[][] board = {
                {Cross, Nought, Cross},
                {Cross, Cross, None},
                {Nought, Nought, Cross}
        };
        assertEquals(1, LogicController.countEmptyCells(board));
    }

    @Test
    public void countEmptyCellsFew() {
        Mark[][] board = {
                {Cross, Nought, Cross},
                {None, Cross, None},
                {Nought, None, None}
        };
        assertEquals(4, LogicController.countEmptyCells(board));
    }

    @Test
    public void countEmptyCellsAll() {
        Mark[][] board = {
                {None, None, None},
                {None, None, None},
                {None, None, None}
        };
        assertEquals(9, LogicController.countEmptyCells(board));
    }

    @Test
    public void getRotatedBoard() {
        Integer[][] board = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        Integer[][] expectedBoard = {
                {6, 3, 0},
                {7, 4, 1},
                {8, 5, 2}
        };

        Integer[][] actualBoard = LogicController.getRotatedBoard(board);
        assertTrue(Arrays.deepEquals(expectedBoard, actualBoard));
    }

    @Test
    public void nextMarkAfterCross() {
        assertEquals(Nought, LogicController.nextMarkAfter(Cross));
    }

    @Test
    public void nextMarkAfterNought() {
        assertEquals(Cross, LogicController.nextMarkAfter(Nought));
    }
}