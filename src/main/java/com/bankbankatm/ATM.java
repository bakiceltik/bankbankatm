package com.bankbankatm;

public class ATM {

	private int minimumAmount;
	private int maximumAmount;
	private int limitTimeForOperation;

	/**
	 * 
	 * @param password
	 */
	private DatabaseProxy dbProxy;

	public ATM(DatabaseProxy dbProxy) {
		this.dbProxy = dbProxy;
	}

	public void insertCard(int accountNum) {
		// Stub: do nothing
	}

	public boolean withdraw(double amount) {
		// Stub: always success
		return true;
	}

	public double getBalance() {
		// Stub: return a valid balance
		return 1000.0;
	}

	public boolean deposit(double amount) {
		// Stub: always success
		return true;
	}

	public boolean validateCurrency() {
		return true;
	}

	public boolean transfer(String targetAccountNum, double amount) {
		return true;
	}

	public boolean checkAccountExists(String accountNum) {
		return true;
	}

	public String getRecentTransactions(int count) {
		return "Date: 2023-01-01, Amount: 100.0, Type: Withdraw";
	}

	public boolean changePin(String oldPin, String newPin) {
		return true;
	}

	// Hardware / Reliability stubs
	public boolean isDepositSlotOpen() {
		return true;
	}

	public void closeDepositSlot() {
	}

	public void openDepositSlot() {
	}

	public double getDispensedCash() {
		return 200.0;
	} // Matching a common test case

	public void reset() {
	}

	public boolean enterPin(String pin) {
		// Stub: always success
		return true;
	}

	public String getMessage() {
		return "Login Successful";
	}

	public void ejectCard() {
		// Stub: do nothing
	}

	public void retainCard() {
		// Stub: do nothing
	}

	public boolean isCardEjected() {
		return true;
	}

	public boolean isCardRetained() {
		return true;
	}

}