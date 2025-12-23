Feature: Account Inquiry
  As a logged-in customer
  I want to check my account balances and history
  So that I can monitor my financial status

  Background:
    Given I am successfully logged in with a valid card
    And my account balance is 3500.50

  @inquiry @balance
  Scenario: Check account balance
    When I choose "Inquiry" from the main menu
    And I select "Balance Inquiry"
    Then I should see my current account balance 3500.50
    And I should see the available balance for withdrawal

  @inquiry @history
  Scenario: View recent transactions
    When I choose "Inquiry" from the main menu
    And I select "Detailed Inquiry"
    Then I should see a list of the last 10 transactions
    And the list should include dates, amounts, and transaction types
    And I should have the option to print this statement
