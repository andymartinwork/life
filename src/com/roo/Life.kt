package com.roo

import java.util.*

/**
 * Created by andy on 14/04/15.
 */
class Life {
    private val gridSize = 15
    private val MAX_FOOD_PER_GRID_POSITION = 10
    var board = Array(gridSize) { arrayOfNulls<Any>(gridSize) }
    var foodBoard = Array(gridSize) { IntArray(gridSize) }
    private val NUMBER_OF_CELLS = gridSize
    private val NUMBER_OF_FOOD = gridSize
    private fun setupBoard() { // Randomly distribute Cells over the board
        val rand = Random()
        for (i in 0 until NUMBER_OF_CELLS) {
            val xPosition = rand.nextInt(gridSize)
            val yPosition = rand.nextInt(gridSize)
            val cell = Cell(board, foodBoard, xPosition, yPosition, gridSize)
            board[yPosition][xPosition] = cell
        }
    }

    // Food is currently an integer representing the amount of food in a position
    fun addFood() {
        val rand = Random()
        for (i in 0 until NUMBER_OF_FOOD) {
            val xPosition = rand.nextInt(gridSize)
            val yPosition = rand.nextInt(gridSize)
            val foodValue = rand.nextInt(MAX_FOOD_PER_GRID_POSITION)
            foodBoard[yPosition][xPosition] = foodValue
        }
    }

    // Dump the state of the board out to the command line
    fun printBoardState(printGrid: Boolean) {
        var numberOfItems = 0
        println()
        for (y in 0 until gridSize) {
            for (x in 0 until gridSize) {
                val boardObject = board[y][x]
                if (boardObject != null) {
                    numberOfItems++
                    if (printGrid) {
                        print("C ") // TODO: Print last move, maybe?
                    }
                } else {
                    if (printGrid) {
                        print("- ")
                    }
                }
            }
            if (printGrid) {
                println()
            }
        }
        println()
        println("Number of items: $numberOfItems")
        println("===============")
        println()
    }

    // Loop through each of the elements and run the tick method on each Cell
    fun tick() {
        val cells = ArrayList<Cell>()
        // We should read a list of cell positions at the point of the tick
        // and then execute them. This is to prevent a cell from being run twice.
        for (y in 0 until gridSize) {
            for (x in 0 until gridSize) {
                val boardObject = board[y][x]
                if (boardObject != null) {
                    if (boardObject is Cell) {
                        cells.add(boardObject)
                    }
                }
            }
        }
        for (cell in cells) {
            cell.tick()
        }
    }

    init {
        setupBoard()
        addFood()
    }
}
