package edu.smcm.mathcs.banking;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Application {

	private static final int starting_balance;
	private static final int number_of_accounts;
	private static final int number_of_clients;
	private static final int number_of_transactions;
	
	static {
		starting_balance = 1000;
		number_of_accounts = 1000;
		number_of_clients = 1000;
		number_of_transactions = 1000;
	}
	
	public static void main(String[] args) {
		Bank bank;
		Thread[] clients;        // clients are threads
		Thread auditor;          // auditors is a thread
		CyclicBarrier barrier;
		
		bank = new Bank(number_of_accounts, starting_balance);								
		barrier = new CyclicBarrier(number_of_clients + 1);									// barrier waits at Application end and inside client threads							
		auditor = new Thread(new Auditor(bank, number_of_accounts * starting_balance));		// auditor thread, waits 100 milliseconds until final audit
		clients = new Thread[number_of_clients];											// [client threads], an array of client threads
		
		for (int count = 0; count < number_of_clients; count++) {							// loop to initialize [client threads]
			clients[count] = new Thread(new Client(bank, number_of_transactions, barrier));
		}
		
		
		// begin auditor thread
		auditor.start();													// auditor starts with 1,000,000 expected capital
																			// auditor calls [bank balance method] in loop 0-1000 for [account objects]
																			// accumulates balances of accounts calling [bank balance method]
																			// [bank balance method] has to call [account balance method] ***** critical
		
		// begin client threads
		for (int count = 0; count < number_of_clients; count++) {
			clients[count].start();
		}
		
		// final barrier
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		auditor.interrupt();
	}
}