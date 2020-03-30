package com.roo

import java.util.*

/**
 * Created by andy on 14/04/15.
 */
// TODO: Convert dna to a tree data structure and change the logic of each function to actions
// For example, eat might look at other locations and move to them.
// Reproduce might move to an empty bit of the board.
class Cell(
    private val dna: String,
    private val board: Array<Array<Any?>>,
    private val foodBoard: Array<IntArray>,
    private var xPosition: Int,
    private var yPosition: Int,
    private val gridSize: Int) {
    // TODO: Perhaps change this enum to encompass all actions the Cell can perform?
    private enum class Direction(val value: Char) {
        UP('U'),
        DOWN('D'),
        LEFT('L'),
        RIGHT('R'),
        NONE('N')
    }

    // These variables predict how many cells will be on the board.
    // Max life predicts the amount far more than food dropped.
    private val MAX_LIFE = 200 // 200 is around the minimum for a stable population on 15*15
    private val FOOD_DROP_ON_DEATH = 9 // 9 may be minimum for a stable population on 15*15
    private var dnaPosition = 0
    private var age = 0
    private var foodStore = 10 // Start with some food
    private val deathDate: Int

    constructor(board: Array<Array<Any?>>,
                foodBoard: Array<IntArray>,
                xPosition: Int,
                yPosition: Int,
                gridSize: Int) : this(randomDna(), board, foodBoard, xPosition, yPosition, gridSize)

    private fun decodeDna() {
        val instruction = dna.toCharArray()[dnaPosition]
        print(instruction)
        when (instruction) {
            'U' -> move(Direction.UP)
            'D' -> move(Direction.DOWN)
            'L' -> move(Direction.LEFT)
            'R' -> move(Direction.RIGHT)
        }
        dnaPosition++
        if (dnaPosition == dna.toCharArray().size) {
            dnaPosition = 0
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
    private fun move(direction: Direction) {
        when (direction) {
            Direction.UP -> if (yPosition > 0 && board[yPosition - 1][xPosition] == null) {
                board[yPosition - 1][xPosition] = this
                board[yPosition][xPosition] = null
                yPosition -= 1
            }
            Direction.DOWN -> if (yPosition < gridSize - 1 && board[yPosition + 1][xPosition] == null) {
                board[yPosition + 1][xPosition] = this
                board[yPosition][xPosition] = null
                yPosition += 1
            }
            Direction.LEFT -> if (xPosition < 0 && board[yPosition][xPosition - 1] == null) {
                board[yPosition][xPosition - 1] = this
                board[yPosition][xPosition] = null
                xPosition -= 1
            }
            Direction.RIGHT -> if (xPosition < gridSize - 1 && board[yPosition][xPosition + 1] == null) {
                board[yPosition][xPosition + 1] = this
                board[yPosition][xPosition] = null
                xPosition += 1
            }
        }
    }

    private fun eat() {
        foodStore += foodBoard[yPosition][xPosition]
        foodBoard[yPosition][xPosition] = 0
    }

    private fun mutate(dna: String): String {
        val rand = Random()
        val dnaChars = dna.toCharArray()
        dnaChars[rand.nextInt(dnaChars.size - 1)] = Direction.values()[rand.nextInt(Direction.values().size)].value
        return String(dnaChars)
    }

    private fun reproduce() { // There is a random chance of this happening. We can't have all this reproduction going on willy nilly.
// At the moment 50% chance
        if (foodStore > 6 && Math.random() >= 0.5) {
            val mutatedDna = mutate(dna)
            // Choose a random location to reproduce, and make sure it's possible before doing
            if (Math.random() >= 0.5) {
                val newXPosition = chooseReproductionPosition(xPosition)
                if (board[yPosition][newXPosition] == null) {
                    val childCell = Cell(mutatedDna, board, foodBoard, newXPosition, yPosition, gridSize)
                    board[yPosition][newXPosition] = childCell
                    foodStore -= 3
                }
            } else {
                val newYPosition = chooseReproductionPosition(yPosition)
                if (board[newYPosition][xPosition] == null) {
                    val childCell = Cell(mutatedDna, board, foodBoard, xPosition, newYPosition, gridSize)
                    board[newYPosition][xPosition] = childCell
                    foodStore -= 3
                }
            }
        }
    }

    private fun chooseReproductionPosition(position: Int): Int { // If on the edges of the grid, make sure we move in the right direction
// TODO: replace with a call to feel
        var position = position
        if (position >= gridSize - 1) {
            position -= position
        } else if (position == 0) {
            position += position
        } else { // 50% chance of whether we add to a position or subtract
            position += if (Math.random() >= 0.5) 1 else -1
        }
        return position
    }

    private fun age() {
        age++
        foodStore--
        if (age > deathDate || foodStore < 0) { // Die by removing self from board and adding food to the food tile.
            println("Cell dying. DNA: $dna")
            board[yPosition][xPosition] = null
            foodBoard[yPosition][xPosition] = foodBoard[yPosition][xPosition] + foodStore + FOOD_DROP_ON_DEATH
        }
    }

    fun tick() {
        decodeDna()
        age()
        eat()
        reproduce()
    }

    companion object {
        private fun randomDna(): String {
            val rand = Random()

            val dnaArray: ArrayList<Char> = ArrayList()
            repeat(5) {
                // This is a bit WTF and quite inefficient
                dnaArray.add(Direction.values()[rand.nextInt(Direction.values().size)].value)
            }
            return String(dnaArray.toCharArray())
        }
    }

    init {
        val random = Random()
        deathDate = random.nextInt(MAX_LIFE)
    }
}
