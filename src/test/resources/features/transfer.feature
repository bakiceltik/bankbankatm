Feature: Money Transfer
  As a logged-in customer
  I want to transfer money to another account
  So that I can send funds to friends or pay bills

  Background:
    Given I am successfully logged in with a valid card
    And my account balance is 2000.00

  @transfer @success
  Scenario: Successful money transfer to valid account
    When I choose "Transfer" from the main menu
    And I enter the target account number "9876543210"
    And I enter transfer amount 500.00
    And I confirm the transfer details
    Then the system should process the transfer
    And my account balance should be updated to 1500.00
    And I should see a "Transfer Successful" message

  @transfer @insufficient_funds
  Scenario: Transfer amount exceeds account balance
    When I choose "Transfer" from the main menu
    And I enter the target account number "9876543210"
    And I enter transfer amount 2500.00
    Then the system should reject the transfer
    And I should see an error message "Insufficient funds"

  @transfer @invalid_account
  Scenario: Transfer to invalid or non-existent account
    When I choose "Transfer" from the main menu
    And I enter an invalid target account number "0000000000"
    And I enter transfer amount 100.00
    Then the system should validate the account existence
    And I should see an error message "Invalid target account"
    And the transfer should not be processed
