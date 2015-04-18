package com.roo;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello world");

        Life life = new Life();

        while(true) {
            try {
                life.tick();
                life.printBoardState();
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
