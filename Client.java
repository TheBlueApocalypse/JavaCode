package edu.smcm.mathcs.banking;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// TODO Document Client

public class Client implements Runnable {

	private static final int wait_time;
	private static final Random oracle;
	
	private Bank bank;
	private int number_of_transactions;
	private CyclicBarrier barrier;
	
	static {
		wait_time = 10;
		oracle = new Random();
	}
	
	public Client(Bank bank, int number_of_transactions, CyclicBarrier barrier) {
		this.bank = bank;
		this.number_of_transactions = number_of_transactions;
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		int from;
		int to;
		int amount;
		
		for (int count = 0; count < number_of_transactions; count++) {
			
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
			
			from = oracle.nextInt(bank.numberOfAccounts());
			to = oracle.nextInt(bank.numberOfAccounts());
			
			// condition to handle bank.balance(from) -> having 0 or less
			// if from account will be overdrawn, then do not execute transaction
			if (bank.balance(from) > 0) {
				amount = oracle.nextInt(bank.balance(from));
				bank.withdraw(from, amount); // BANK SYNCHRONIZED METHOD
				bank.deposit(to, amount);
			}
			else {
				System.out.println("Balance was below 0:" + bank.balance(from));
				continue;
			}
			

			// client WAIT TIME is 10 milliseconds **********************************

			try {
				Thread.sleep(wait_time);
			} catch (InterruptedException e) {
				/* Ignore interrupt, loop deals with it */
			}
		}
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
	}

}
