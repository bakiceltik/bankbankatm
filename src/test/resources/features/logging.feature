Feature: Transaction Logging
  As a bank
  I want all ATM transactions to be properly logged
  So that I can audit and track all financial activities

  # FR6 - Serial number logging
  # FR15 - Dispensed amount logging

  Background:
    Given the ATM system is initialized and has sufficient funds
    And the logging system is operational

  @logging @serial
  Scenario: Log card serial number on insertion (FR6)
    When I insert a valid card with serial "1234567890"
    Then the log should record the card insertion
    And the log entry should contain serial number "1234567890"
    And the log entry should contain a timestamp

  @logging @withdrawal
  Scenario: Log dispensed amount with serial number (FR15)
    Given I am successfully logged in with a valid card
    And my account balance is 1000.00
    When I complete a withdrawal of 200.00
    Then the log should record the dispensed amount 200.00
    And the log should contain the card serial number
    And the log should contain the transaction timestamp
    And a response should be sent to the bank for money dispensed

  @logging @transaction_complete
  Scenario: Complete transaction logging cycle
    Given I am successfully logged in with a valid card
    When I perform a transaction
    Then the log should contain:
      | Field           | Value             |
      | Serial Number   | card serial       |
      | Transaction Type| withdrawal        |
      | Amount          | transaction amount|
      | Timestamp       | current time      |
      | Status          | success/failure   |

  @logging @failed_transaction
  Scenario: Log failed transaction attempts
    Given I am successfully logged in with a valid card
    And my account balance is 100.00
    When I attempt a withdrawal of 500.00
    And the transaction fails due to insufficient funds
    Then the log should record the failed attempt
    And the log should contain the reason "Insufficient funds"
