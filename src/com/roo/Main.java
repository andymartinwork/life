package com.roo;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Starting simulation");

        Life life = new Life();

        while(true) {
            try {
                life.printBoardState(true);
                life.tick();
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
