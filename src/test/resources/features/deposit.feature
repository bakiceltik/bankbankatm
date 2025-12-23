Feature: Cash Deposit
  As a logged-in customer
  I want to deposit cash into my account
  So that I can increase my account balance

  Background:
    Given I am successfully logged in with a valid card
    And my account balance is 1000.00
    And the ATM deposit slot is operational

  @deposit @success
  Scenario: Successful cash deposit
    When I choose "Deposit" from the main menu
    And the specific ATM opens the deposit slot
    And I insert 500.00 cash into the deposit slot
    And the system validates the cash
    Then the system should accept the cash
    And my account balance should be updated to 1500.00
    And I should see a "Deposit Successful" message
    And the system should print a receipt with the deposit details

  @deposit @invalid_currency
  Scenario: Deposit with invalid currency or unrecognized bills
    When I choose "Deposit" from the main menu
    And the specific ATM opens the deposit slot
    And I insert invalid paper or foreign currency
    Then the system should reject the invalid items
    And the system should return the rejected items to me
    And my account balance should remain 1000.00
    And I should see a message "Some items were rejected"

  @deposit @cancel
  Scenario: User cancels deposit transaction
    When I choose "Deposit" from the main menu
    And the specific ATM opens the deposit slot
    And I decide to cancel the transaction
    Then the system should close the deposit slot
    And if I had inserted any cash, it should be returned
    And my account balance should remain 1000.00
