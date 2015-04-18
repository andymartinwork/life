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

    private final int MAX_LIFE = 10000;
    private String dna = "URRLD";
    private int dnaPosition = 0;
    private int age = 0;
    private int deathDate;
    private Object[][] board;
    private int xPosition;
    private int yPosition;
    private int gridSize;

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
    private boolean feel(Direction direction) {
        boolean freePosition = false;

        switch (direction) {
            case UP:
                if (yPosition > 0) {
                    freePosition = (board[xPosition][yPosition - 1] == null);
                }
                break;

            case DOWN:
                if (yPosition < (gridSize - 1)) {
                    freePosition = (board[xPosition][yPosition + 1] == null);
                }
                break;

            case LEFT:
                if (xPosition < 0) {
                    freePosition = (board[xPosition - 1][yPosition] == null);
                }
                break;

            case RIGHT:
                if (xPosition < (gridSize -1)) {
                    freePosition = (board[xPosition + 1][yPosition] == null);
                }
                break;
        }
        return freePosition;
    }

    // This function moves the cell on the grid, provided it's empty and in the world
    private void move(Direction direction) {
        if(feel(direction)) {
            switch (direction) {
                case UP:
                    board[xPosition][yPosition - 1] = this;
                    board[xPosition][yPosition] = null;
                    yPosition = yPosition - 1;
                    break;

                case DOWN:
                    board[xPosition][yPosition + 1] = this;
                    board[xPosition][yPosition] = null;
                    yPosition = yPosition + 1;
                    break;

                case LEFT:
                    board[xPosition - 1][yPosition] = this;
                    board[xPosition][yPosition] = null;
                    xPosition = xPosition - 1;
                    break;

                case RIGHT:
                    board[xPosition + 1][yPosition] = this;
                    board[xPosition][yPosition] = null;
                    xPosition = xPosition + 1;
                    break;
            }
        }
    }

    private void checkIfDead() {
        if (age > deathDate) {
            // Die by removing self from board.
            System.out.println("Cell dying. DNA: " + dna);
            board[xPosition][yPosition] = null;
        }
    }

    public void tick() {
        decodeDna();
        age++;
        checkIfDead();
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
