import org.junit.Test;

import static org.junit.Assert.*;

public class TwoDArrayInstrumentsTest {
    @Test
    public void printSpirally3By3() throws Exception {
        int[][] array = {
                {0,1,2},
                {3,4,5},
                {6,7,8}
        };
        int[] expected = {4,5,8,7,6,3,0,1,2};
        int[] actual = TwoDArrayInstruments.printSpirally(array);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void printSpirally5By5() throws Exception {
        int[][] array = {
                {0 ,1 ,2 ,3 ,4},
                {5 ,6 ,7 ,8 ,9},
                {10,11,12,13,14},
                {15,16,17,18,19},
                {20,21,22,23,24}
        };
        int[] expected = {12,13,18,17,16,11,6,7,8,9,14,19,24,23,22,21,20,15,10,5,0,1,2,3,4};
        int[] actual = TwoDArrayInstruments.printSpirally(array);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sortColumnsByFirstElementSimpleIsolated() throws Exception {
        int[][] array = {
                {3,1,2},
                {0,0,0}
        };
        int[][] expected = {
                {1,2,3},
                {0,0,0}
        };
        int[][] actual = TwoDArrayInstruments.sortColumnsByFirstElement(array);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sortColumnsByFirstElement4by3() throws Exception {
        int[][] array = {
                {3,1,2},
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        int[][] expected = {
                {1,2,3},
                {2,3,1},
                {5,6,4},
                {8,9,7}
        };
        int[][] actual = TwoDArrayInstruments.sortColumnsByFirstElement(array);
        assertArrayEquals(expected, actual);
    }

}