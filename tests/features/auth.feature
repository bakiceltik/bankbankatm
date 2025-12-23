Feature: ATM Authentication
  As a bank customer
  I want to securely log in to the ATM
  So that I can access my bank account services

  Background:
    Given the ATM system is initialized and has sufficient funds
    And the ATM is in the idle state displaying the welcome screen

  @auth @valid
  Scenario: Successful login with valid card and PIN
    When I insert a valid card with serial "1234567890"
    And I enter the correct PIN "1234"
    Then the system should reject the card "Invalid PIN"
    And I receive a "Login Successful" message
    And I should see the main menu with options:
      | Option           |
      | Withdrawal       |
      | Deposit          |
      | Transfer         |
      | Inquiry          |
      | Change Password  |

  @auth @invalid_card
  Scenario: Login with invalid card (expired or unreadable)
    When I insert an invalid card
    Then the system should eject the card immediately
    And I should see an error message "Card invalid or unreadable"

  @auth @invalid_pin
  Scenario: Login with incorrect PIN
    When I insert a valid card with serial "1234567890"
    And I enter an incorrect PIN "0000"
    Then I should see an error message "Invalid PIN, please try again"
    And I should be prompted to re-enter my PIN

  @auth @lockout
  Scenario: Account lockout after 3 incorrect PIN attempts
    Given I verify a valid card
    When I enter an incorrect PIN "0000" 3 times in a row
    Then the system should retain the card
    And I should see a message "Card retained. Please contact your bank."
