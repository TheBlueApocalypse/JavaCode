package edu.smcm.mathcs.banking;

/**
 * A model of a regular bank.
 */
public class Bank {

	/**
	 * The bank accounts.
	 */
	private Account[] accounts; // initialize array of type account

	/**
	 * The amount of money in the bank.
	 */
	private int capitalisation;
	
	/**
	 * All arguments constructor
	 * 
	 * @param number_of_accounts number of accounts to create
	 * @param starting_balance amount of money in each account at the beginning
	 */
	
	// Loop to initialize accounts in the accounts array 0-1000
	// with starting balance 1000
	// capitalization is 1000 accounts * 1000 starting balance = 1,000,000
	public Bank(int number_of_accounts, int starting_balance) {
		this.accounts = new Account[number_of_accounts];						// array of [type account] initialized with 1000 from num accts
		this.capitalisation = starting_balance * number_of_accounts;            // [bank instance] [variable capitalization] is initialized with
																				// 1,000,000 from start bal*num accts
		
	// populate array of accounts with type account and starting balance
		for (int account = 0; account < number_of_accounts; account++) {
			this.accounts[account] = new Account(starting_balance);				// each [account instance] is initialized in loop with starting balance 1000
		}	
	}
	
	/**
	 * Accessor for the number of bank accounts.
	 * 
	 * @return number of bank accounts in the bank
	 */
	public int numberOfAccounts() {
		return accounts.length;
	}
	
	/**
	 * Accessor for expected amount of money in the bank.
	 * 
	 * @return expected amount of money in the bank
	 */
	public int capitalisation() {
		return capitalisation;
	}
	
	/**
	 * Get balance in a particular account.
	 * 
	 * @param account account number
	 * @return balance of account
	 */
	
	//CRITICAL REGION?????????????????????????????????????????????

	public int balance(int account) {
		return accounts[account].balance();						// return [account balance] of [account 0 - 1000] in account array
																// references [account object] [balance method]
	}
	
	/**
	 * Withdraw money from an account.
	 * 
	 * @param account account number to withdraw from
	 * @param amount withdrawal amount
	 */
	
	// Bank references account object, within account array, calls withdraw method of account object
	//CRITICAL REGION?????????????????????????????????????????????

	public synchronized void withdraw(int account, int amount) {
		accounts[account].withdraw(amount);						// withdraw [account balance] - [amount] from [account 0 - 1000]
	}															// references [account object] [withdraw method]

	
	/**
	 * Deposit money to account.
	 * 
	 * @param account account number to deposit to
	 * @param amount deposit amount
	 */
	
	//CRITICAL REGION?????????????????????????????????????????????

	public synchronized void deposit(int account, int amount) {
		accounts[account].deposit(amount);						// deposit [account balance] + [amount] to [account 0 - 1000]
	}															// references [account object] [deposit method]

	
}
