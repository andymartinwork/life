package com.roo;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Starting simulation");

        Life life = new Life();

        int tickCounter = 0;

        while(true) {
            try {
                // Add food every 10 turns
                if (tickCounter % 10 == 0) {
                    life.addFood();
                }

                life.printBoardState(true);
                life.tick();
                Thread.sleep(2000);
                tickCounter++;
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
