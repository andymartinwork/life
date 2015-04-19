package com.roo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andy on 14/04/15.
 */

public class Life {

    private int gridSize = 15;
    public Object[][] board = new Object[gridSize][gridSize];
    public int[][] foodBoard = new int[gridSize][gridSize];
    private final int NUMBER_OF_CELLS = gridSize;
    private final int NUMBER_OF_FOOD = gridSize;

    public Life() {
        setupBoard();
        addFood();
    }

    public void setupBoard() {

        // Randomly distribute Cells over the board
        Random rand = new Random();

        for (int i = 0; i < NUMBER_OF_CELLS; i++) {
            int xPosition = rand.nextInt(gridSize);
            int yPosition = rand.nextInt(gridSize);

            Cell cell = new Cell(board, foodBoard, xPosition, yPosition, gridSize);

            board[yPosition][xPosition] = cell;
        }
    }

    // Food is currently an integer representing the amount of food in a position
    public void addFood() {
        Random rand = new Random();

        for (int i = 0; i < NUMBER_OF_FOOD; i++) {
            int xPosition = rand.nextInt(gridSize);
            int yPosition = rand.nextInt(gridSize);

            int foodValue = rand.nextInt(10);

            foodBoard[yPosition][xPosition] = foodValue;
        }
    }

    // Dump the state of the board out to the command line
    public void printBoardState(boolean printGrid) {

        int numberOfItems = 0;
        System.out.println();

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Object boardObject = board[y][x];
                if (boardObject != null) {
                    numberOfItems++;
                    if (printGrid) { System.out.print("C "); }
                } else {
                    if (printGrid) { System.out.print("- "); }
                }
            }

            if (printGrid) { System.out.println(); }
        }

        System.out.println();
        System.out.println("Number of items: " + numberOfItems);
        System.out.println("===============");
        System.out.println();
    }

    // Loop through each of the elements and run the tick method on each Cell
    public void tick() {

        ArrayList<Cell> cells = new ArrayList<Cell>();

        // We should read a list of cell positions at the point of the tick
        // and then execute them.
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Object boardObject = board[y][x];
                if (boardObject != null) {
                    if (boardObject instanceof Cell) {
                        cells.add((Cell) boardObject);
                    }
                }
            }
        }

        for (Cell cell : cells) {
            cell.tick();
        }
    }
}
