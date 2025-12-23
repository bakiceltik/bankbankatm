Feature: System Reliability and Hardware Safety
  As a bank
  I want the ATM to handle hardware failures and data errors reliably
  So that financial data is never corrupt and hardware operates correctly

  # Requirement 4.5.1: Database ACID properties (Atomicity, Consistency, Isolation, Durability)
  @reliability @database @acid
  Scenario: Transaction Atomicity on Power Failure
    Given I have requested a withdrawal of 100.00
    And the system has debited my account
    But the power fails BEFORE the cash is dispensed
    When the system restarts
    Then the transaction should be rolled back
    And my account balance should be restored to its original state
    And no cash should be dispensed

  @reliability @database @isolation
  Scenario: Data Isolation
    Given two transactions act on the same account simultaneously
    When one transaction is in progress (reading balance)
    Then the other transaction must wait or fail
    And neither transaction should see an intermediate invalid state

  # Requirement 4.5.2: Cash Machine Denomination & Retry Logic
  @reliability @hardware @cash_dispenser
  Scenario: Cash Denomination Recognition
    Given the cash dispenser is loaded with mixed bills
    When I request a withdrawal of 100.00
    Then the machine must accurately identify and select 100.00 worth of bills
    And if identification fails, it should not dispense incorrect amounts

  @reliability @hardware @retry
  Scenario: Hardware Retry Logic (3 attempts)
    Given the cash dispenser encounters a mechanical error during operation
    When it attempts the operation for the 1st time and fails
    And it retries a 2nd time and fails
    And it retries a 3rd time and fails
    Then the system should stop retrying
    And the system should return any partial cash to any internal reject bin
    And the system should return the card to the user
    And the system should display a "Hardware Malfunction" error
    And the transaction should be cancelled
