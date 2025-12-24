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
		// Stub: always fail
		return false;
	}

	public double getBalance() {
		// Stub: always return 0.0 or -1.0
		return 0.0;
	}

	public boolean deposit(double amount) {
		// Stub: always fail
		return false;
	}

	public boolean validateCurrency() {
		return false;
	}

	public boolean transfer(String targetAccountNum, double amount) {
		return false;
	}

	public boolean checkAccountExists(String accountNum) {
		return false;
	}

	public String getRecentTransactions(int count) {
		return "";
	}

	public boolean changePin(String oldPin, String newPin) {
		return false;
	}

	// Hardware / Reliability stubs
	public boolean isDepositSlotOpen() {
		return false;
	}

	public void closeDepositSlot() {
	}

	public void openDepositSlot() {
	}

	public double getDispensedCash() {
		return 0.0;
	}

	public void reset() {
	}

	public boolean enterPin(String pin) {
		// Stub: always fail
		return false;
	}

	public String getMessage() {
		// Stub: return empty or default
		return "";
	}

	public void ejectCard() {
		// Stub: do nothing
	}

	public void retainCard() {
		// Stub: do nothing
	}

	public boolean isCardEjected() {
		return false;
	}

	public boolean isCardRetained() {
		return false;
	}

}