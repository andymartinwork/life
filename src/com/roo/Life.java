package com.roo;

import java.util.Random;

/**
 * Created by andy on 14/04/15.
 */

// TODO: possibly add food? Look at research proposal
public class Life {

    private int gridSize = 15;
    public Object[][] board = new Object[gridSize][gridSize];
    private final int numberOfCells = gridSize;

    public Life() {
        setupBoard();
    }

    public void setupBoard() {

        // Randomly distribute Cells over the board
        Random rand = new Random();

        for (int i = 0; i < numberOfCells; i++) {
            int xPosition = rand.nextInt(gridSize);
            int yPosition = rand.nextInt(gridSize);

            Cell cell = new Cell(board, xPosition, yPosition, gridSize);

            board[xPosition][yPosition] = cell;
        }
    }

    // Dump the state of the board out to the command line
    public void printBoardState() {

        System.out.println();

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Object boardObject = board[x][y];
                if (boardObject != null) {
                    System.out.print("C ");
                } else {
                    System.out.print("- ");
                }
            }

            System.out.println();
        }
        System.out.println();
        System.out.println("===============");
        System.out.println();
    }

    // Loop through each of the elements and run the tick method on each Cell
    public void tick() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Object boardObject = board[x][y];
                if (boardObject != null) {
                    if (boardObject instanceof Cell) {
                        ((Cell) boardObject).tick();
                    }
                }
            }
        }
    }
}
