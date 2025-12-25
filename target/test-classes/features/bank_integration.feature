Feature: Bank Computer Integration
  As a bank
  I want the ATM to properly communicate with the bank computer
  So that all transactions are correctly processed and accounts updated

  # Bank FR7, FR8, FR9 - Bank computer transaction processing

  Background:
    Given the ATM is connected to the bank network
    And the bank computer is operational

  @bank @transaction_processing
  Scenario: Bank processes transaction and updates daily limit (Bank FR7)
    Given customer with account "1234567890" has balance 5000.00
    And maximum daily withdrawal limit (k) is 2000.00
    And customer has already withdrawn 1500.00 today
    When the customer requests withdrawal of 400.00
    Then the bank computer should process the transaction
    And the bank should update the daily withdrawal counter to 1900.00
    And the bank should send "transaction succeeded" to ATM

  @bank @daily_limit_exceeded
  Scenario: Bank rejects transaction exceeding daily limit (Bank FR9)
    Given customer with account "1234567890" has balance 5000.00
    And maximum daily withdrawal limit (k) is 2000.00
    And customer has already withdrawn 1800.00 today
    When the customer requests withdrawal of 500.00
    Then the bank computer should check against daily limit
    And the bank should send "transaction failed" to ATM
    And I should see error "Daily withdrawal limit exceeded"

  @bank @account_update_confirmation
  Scenario: Bank updates account after money dispensed (Bank FR8)
    Given customer with account "1234567890" has balance 1000.00
    And the bank has authorized a withdrawal of 200.00
    When the ATM dispenses 200.00 in cash
    Then the ATM should send confirmation to bank computer
    And the bank should permanently update the account balance to 800.00
    And the bank should create a new account record

  @bank @dispense_confirmation
  Scenario: ATM confirms cash dispensed to bank
    Given a withdrawal of 300.00 has been authorized
    When the cash dispenser successfully dispenses 300.00
    Then the ATM should send "money dispensed" response to bank
    And the bank should finalize the transaction
    And the account should reflect the new balance

  @bank @dispense_failure
  Scenario: ATM reports dispense failure to bank
    Given a withdrawal of 300.00 has been authorized
    When the cash dispenser fails to dispense cash
    Then the ATM should send "dispense failed" response to bank
    And the bank should rollback the transaction
    And the account balance should remain unchanged
