package edu.smcm.mathcs.banking;

public class Auditor implements Runnable {

	/**
	 * The time to wait between audits.
	 */
	private static int wait_time;
		
	/**
	 * The bank being audited.
	 */
	private Bank bank;
	
	/**
	 * THe expected amount of money in the bank. 
	 */
	private int expected_capitalisation;
	
	static {
		wait_time = 100;
	}
	
	/**
	 * All arguments Constructor.
	 * 
	 * @param bank the bank being audited
	 * @param expected_capitalisation the expected amount of money in the bank
	 */
	public Auditor(Bank bank, int expected_capitalisation) {
		this.bank = bank;
		this.expected_capitalisation = expected_capitalisation;
	}
	
	/**
	 * Perform an audit of the bank.
	 * 
	 * Sum all the account balances and check them against the expected amount.
	 */
	private void performAudit() {
		int capitalisation;
		capitalisation = 0;
																						// *****Is the BALANCE being returned before transaction is complete????
		for (int account = 0; account < bank.numberOfAccounts(); account++) {			// CRITICAL REGION?????????????????????????
			capitalisation = capitalisation + bank.balance(account);                  	// call to bank object balance method, which calls account balance method
		}
		
		if (capitalisation == expected_capitalisation) {                              	// check after looping through accounts
			System.out.println("Bank checks out.");
		} else {
			System.err.println("Bank should have " + expected_capitalisation +
					" but has " + capitalisation + ".");
		}
	}
	
	// auditor WAIT TIME is 100 milliseconds **********************************
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			performAudit();
			try {
				Thread.sleep(wait_time);                             					// wait 100 milliseconds to perform [audit loop calling balance]
			} catch (InterruptedException e) {
				/* Ignore interruption dealt with in while loop */
				break;
			}
		}
		System.out.println("Final Audit");												// one more [audit loop] executes final audit*******^
		performAudit();
	}
}