Feature: Cash Withdrawal
  As a logged-in customer
  I want to withdraw cash from my account
  So that I can have physical money for spending

  Background:
    Given I am successfully logged in with a valid card
    And my account balance is 1000.00
    And the specific ATM has 50000.00 cash available
    And the maximum daily withdrawal limit is 2000.00
    And the maximum per-transaction withdrawal limit is 1000.00

  @withdraw @success
  Scenario: Successful cash withdrawal
    When I choose "Withdrawal" from the main menu
    And I enter withdrawal amount 200.00
    And I confirm the transaction
    Then the system should dispense 200.00 cash
    And my account balance should be updated to 800.00
    And the ATM available cash should be 49800.00
    And I should see a "Transaction Successful" message
    And the system should print a receipt with the transaction details

  @withdraw @insufficient_funds
  Scenario: Withdrawal amount exceeds account balance
    When I choose "Withdrawal" from the main menu
    And I enter withdrawal amount 1200.00
    And I confirm the transaction
    Then the system should reject the transaction
    And I should see an error message "Insufficient funds"
    And my account balance should remain 1000.00

  @withdraw @limit_exceeded_transaction
  Scenario: Withdrawal amount exceeds per-transaction limit
    When I choose "Withdrawal" from the main menu
    And I enter withdrawal amount 1500.00
    Then the system should reject the transaction
    And I should see an error message "Amount exceeds per-transaction limit"
    And I should be prompted to enter a smaller amount

  @withdraw @limit_exceeded_daily
  Scenario: Withdrawal amount exceeds daily limit
    Given I have already withdrawn 1900.00 today
    When I choose "Withdrawal" from the main menu
    And I enter withdrawal amount 200.00
    Then the system should reject the transaction
    And I should see an error message "Daily withdrawal limit reached"
