package com.bankbankatm;

public class DatabaseProxy {

	/**
	 * 
	 * @param accountNum
	 */
	// private java.util.Map<Integer, Account> accounts = new java.util.HashMap<>();

	public void addAccount(Account account) {
		// accounts.put(account.getAccountNumber(), account);
	}

	public Account getAccount(int accountNum) {
		// return accounts.get(accountNum);
		return null;
	}

	public String selectPasswordByAccountNum(int accountNum) {
		// return "stub-password-check";
		return null;
	}

	public boolean verifyPin(int accountNum, String pin) {
		// return account != null && account.verifyPassword(pin);
		return false;
	}

	public double getBalance(int accountNum) {
		return 0.0;
	}

	public void updateBalance(int accountNum, double newBalance) {
	}

	public boolean accountExists(int accountNum) {
		return false;
	}

	public void updatePin(int accountNum, String newPin) {
	}

	public void minusBalance() {
		// TODO - implement DatabaseProxy.minusBalance
		throw new UnsupportedOperationException();
	}

	public void plusBalance() {
		// TODO - implement DatabaseProxy.plusBalance
		throw new UnsupportedOperationException();
	}

	public int createNewAccount() {
		// TODO - implement DatabaseProxy.createNewAccount
		throw new UnsupportedOperationException();
	}

	public void checkTheBalance() {
		// TODO - implement DatabaseProxy.checkTheBalance
		throw new UnsupportedOperationException();
	}

}