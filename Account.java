package edu.smcm.mathcs.banking;

/**
 * A model of a simple bank account.
 */
public class Account {
	
	/**
	 * The current balance of the account
	 */
	private int balance;
	
	/**
	 * Some Constructor.
	 * 
	 * @param opening_balance starting balance of account
	 */
	public Account(int opening_balance) {
		balance = opening_balance;
	}
	
	/**
	 * Obtain current balance of account.
	 * 
	 * @return current balance
	 */
	
	//CRITICAL REGION?????????????????????????????????????????????
	
	public int balance() {								// return [balance] from [account object 0-1000]
		return balance;									// referenced by [bank object] [balance method]
	}
	
	/**
	 * Withdraw an amount from the account.
	 * 
	 * @param amount amount to withdraw
	 */
	
	//CRITICAL REGION?????????????????????????????????????????????
	
	public synchronized void withdraw(int amount) {		// return [balance - amount passed] from [account object 0-1000]
														// referenced by [bank object] [withdraw method]
	
	//public void withdraw(int amount) {
		balance = balance - amount;
	}
	
	/**
	 * Deposit an amount in the account.
	 * 
	 * @param amount amount to deposit
	 */
	
	//CRITICAL REGION?????????????????????????????????????????????

	public synchronized void deposit(int amount) {		// return [balance + amount passed] from [account object 0-1000]
														// referenced by [bank object] [deposit method]
	
	//public void deposit(int amount) {
		balance = balance + amount;
	}
}
