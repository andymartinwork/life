package com.roo;

import java.util.Random;

/**
 * Created by andy on 14/04/15.
 */
public class Cell {

    // TODO: Perhaps change this enum to encompass all actions the Cell can perfom?
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    // TODO: Add MAX_LIFE to the dna String
    private final int MAX_LIFE = 15;
    // TODO: Convert dna to JSON and have a string of actions for each method
    // For example, eat might look at other locations and move to them.
    // Reproduce might move to an empty bit of the board.
    private String dna;
    private int dnaPosition = 0;
    private int age = 0;
    private int foodStore = 10; // Start with some food
    private int deathDate;
    private Object[][] board;
    private int[][] foodBoard;
    private int xPosition;
    private int yPosition;
    private int gridSize;

    // TODO: Initialise dna randomly if not provided with any
    private static String getRandomDna() {
        return "URRLD";
    }

    public Cell(Object[][] board, int[][]foodBoard, int xPosition, int yPosition, int gridSize) {
        this(getRandomDna(), board, foodBoard, xPosition, yPosition, gridSize);
    }

    public Cell(String dna, Object[][] board, int[][]foodBoard, int xPosition, int yPosition, int gridSize) {
        this.dna = dna;
        this.board = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.gridSize = gridSize;
        this.foodBoard = foodBoard;

        Random random = new Random();
        deathDate = random.nextInt(MAX_LIFE);
    }

    private void decodeDna() {
        char instruction = dna.toCharArray()[dnaPosition];

        System.out.print(instruction);

        // TODO: Make the instructions modifiable
        switch (instruction) {
            case 'U':
                move(Direction.UP);
                break;
            case 'D':
                move(Direction.DOWN);
                break;
            case 'L':
                move(Direction.LEFT);
                break;
            case 'R':
                move(Direction.RIGHT);
                break;
        }

        dnaPosition++;
        if (dnaPosition == dna.toCharArray().length) {
            dnaPosition = 0;
        }
    }

    // This feels the current location, or a location around it
//    private boolean feel(Direction direction) {
//        boolean freePosition = false;
//
//        // TODO: This doesn't seem to be working
//        switch (direction) {
//            case UP:
//                if (yPosition > 0) {
//                    freePosition = (board[yPosition - 1][xPosition] == null);
//                }
//                break;
//
//            case DOWN:
//                if (yPosition < (gridSize - 1)) {
//                    freePosition = (board[yPosition + 1][xPosition] == null);
//                }
//                break;
//
//            case LEFT:
//                if (xPosition < 0) {
//                    freePosition = (board[yPosition][xPosition - 1] == null);
//                }
//                break;
//
//            case RIGHT:
//                if (xPosition < (gridSize -1)) {
//                    freePosition = (board[yPosition][xPosition + 1] == null);
//                }
//                break;
//        }
//        System.out.println("freeposition: " + freePosition);
//        return freePosition;
//    }

    // This function moves the cell on the grid, provided it's empty and in the world
    private void move(Direction direction) {

        switch (direction) {
            case UP:
                if (yPosition > 0 && (board[yPosition - 1][xPosition] == null)) {
                    board[yPosition - 1][xPosition] = this;
                    board[yPosition][xPosition] = null;
                    yPosition = yPosition - 1;
                }
                break;

            case DOWN:
                if (yPosition < (gridSize - 1) && (board[yPosition + 1][xPosition] == null)) {
                    board[yPosition + 1][xPosition] = this;
                    board[yPosition][xPosition] = null;
                    yPosition = yPosition + 1;
                }
                break;

            case LEFT:
                if (xPosition < 0 && (board[yPosition][xPosition - 1] ==null)) {
                    board[yPosition][xPosition - 1] = this;
                    board[yPosition][xPosition] = null;
                    xPosition = xPosition - 1;
                }
                break;

            case RIGHT:
                if (xPosition < (gridSize -1) && (board[yPosition][xPosition + 1] == null)) {
                    board[yPosition][xPosition + 1] = this;
                    board[yPosition][xPosition] = null;
                    xPosition = xPosition + 1;
                }
                break;
        }
    }

    private void eat() {
        foodStore = foodStore + foodBoard[yPosition][xPosition];
        foodBoard[yPosition][xPosition] = 0;
    }

    // TODO: Actually mutate the dna string.
    private String mutate(String dna) {
        return dna;
    }

    private void reproduce() {
        // TODO: Add in a random chance of this happening. We can't have all this reproduction going on willy nilly.
        if (foodStore > 5) {
            String mutatedDna = mutate(dna);
            // TODO: choose a random location to reproduce, and make sure it's possible before doing
            Cell childCell = new Cell(mutatedDna, board, foodBoard, xPosition + 1, yPosition, gridSize);
            board[yPosition][xPosition + 1] = childCell;
        }
    }

    private void age() {
        age++;
        foodStore--;
        if (age > deathDate || foodStore < 0) {
            // Die by removing self from board.
            System.out.println("Cell dying. DNA: " + dna);
            board[yPosition][xPosition] = null;
        }
    }

    public void tick() {
        decodeDna();
        age();
        eat();
        reproduce();
    }
}
