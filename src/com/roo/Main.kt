package com.roo

object Main {
    @JvmStatic
    fun main(args: Array<String>) { // write your code here
        println("Starting simulation")
        val life = Life()
        var tickCounter = 0
        while (true) {
            try {
                tickCounter++
                // Add food every 10 turns
                if (tickCounter % 10 == 0) {
                    life.addFood()
                }
                life.printBoardState(true)
                life.tick()
                Thread.sleep(1000)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}
