package com.bankbankatm;

public class Account {

	private int account_number;
	private String password;
	private double balance;
	private int accountType;

	public Account(int accountNum, String password, double balance) {
		this.account_number = accountNum;
		this.password = password;
		this.balance = balance;
	}

	public int getAccountNumber() {
		return account_number;
	}

	public boolean verifyPassword(String inputPassword) {
		return this.password.equals(inputPassword);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}