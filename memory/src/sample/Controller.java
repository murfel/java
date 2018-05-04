package sample;

import javafx.application.Application;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {
    private int boardSize;
    private ArrayList<ArrayList<Integer>> board;

    public void setSize(int boardSize) {
        assert boardSize % 2 == 0;
        this.boardSize = boardSize;
        init();
    }

    private void init() {
        board = new ArrayList<>(boardSize);
        for (int i = 0; i < boardSize; i++) {
            board.set(i, new ArrayList<>(boardSize));
        }
        ArrayList<Integer> values = new ArrayList(Arrays.asList(IntStream.rangeClosed(1, boardSize * boardSize / 2).boxed().toArray()));
        ArrayList<Integer> more_values = new ArrayList(Arrays.asList(IntStream.rangeClosed(1, boardSize * boardSize / 2).boxed().toArray()));
        values.addAll(more_values);
        Collections.shuffle(values);
        int p = 0;
        for (ArrayList<Integer> row : board) {
            for (Integer cell : row) {
                cell = values.get(p++);
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }


}
