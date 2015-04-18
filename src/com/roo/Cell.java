package com.roo;

import java.util.Random;

/**
 * Created by andy on 14/04/15.
 */
public class Cell {

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    // TODO: Add MAX_LIFE to the dna String
    private final int MAX_LIFE = 15;
    // TODO: Convert dna to JSON
    private String dna = "URRLD";
    private int dnaPosition = 0;
    private int age = 0;
    private int deathDate;
    private Object[][] board;
    private int xPosition;
    private int yPosition;
    private int gridSize;

    // TODO: Initialise instructions randomly
    public Cell(Object[][] board, int xPosition, int yPosition, int gridSize) {
        this.board = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.gridSize = gridSize;

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

    }

    private void reproduce() {

    }

    private void age() {
        age++;
        if (age > deathDate) {
            // Die by removing self from board.
            System.out.println("Cell dying. DNA: " + dna);
            board[yPosition][xPosition] = null;
        }
    }

    public void tick() {
        decodeDna();
        age();
    }

//    public class Location {
//        public int x;
//        public int y;
//
//        public Location() {
//            this.x = 0;
//            this.y = 0;
//        }
//
//        public Location(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
}
