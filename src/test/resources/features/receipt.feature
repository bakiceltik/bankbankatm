Feature: Receipt Printing
  As a bank customer
  I want to receive a printed receipt after transactions
  So that I have a record of my banking activities

  # FR14 - ATM prints receipt after successful transaction
  # Hardware Interface 4.2.1 - various printers support

  Background:
    Given I am successfully logged in with a valid card
    And the receipt printer is operational

  @receipt @withdrawal
  Scenario: Print receipt after successful withdrawal (FR14)
    Given my account balance is 1000.00
    When I complete a withdrawal of 200.00
    Then the ATM should print a receipt
    And the receipt should contain:
      | Field              | Value                    |
      | Transaction Type   | Withdrawal               |
      | Amount             | 200.00                   |
      | Date/Time          | current timestamp        |
      | Card Serial        | card serial number       |
      | New Balance        | 800.00                   |
    And the card should be ejected

  @receipt @deposit
  Scenario: Print receipt after successful deposit
    When I complete a deposit of 500.00
    Then the ATM should print a receipt
    And the receipt should contain:
      | Field              | Value                    |
      | Transaction Type   | Deposit                  |
      | Amount             | 500.00                   |
      | Date/Time          | current timestamp        |

  @receipt @transfer
  Scenario: Print receipt after successful transfer
    When I complete a transfer of 300.00 to account "9876543210"
    Then the ATM should print a receipt
    And the receipt should contain:
      | Field              | Value                    |
      | Transaction Type   | Transfer                 |
      | Amount             | 300.00                   |
      | Target Account     | 9876543210               |
      | Date/Time          | current timestamp        |

  @receipt @inquiry
  Scenario: Print receipt after balance inquiry
    When I request a balance inquiry
    And I choose to print the balance
    Then the ATM should print a receipt
    And the receipt should contain the current balance

  @receipt @printer_error
  Scenario: Handle receipt printer error
    Given the receipt printer has a paper jam
    When I complete a withdrawal of 100.00
    Then the transaction should still complete successfully
    And I should see a message "Receipt could not be printed"
    And the cash should still be dispensed

  @receipt @optional
  Scenario: Customer opts out of receipt
    When I complete a withdrawal of 200.00
    And I choose not to print a receipt
    Then no receipt should be printed
    And the transaction should complete normally
