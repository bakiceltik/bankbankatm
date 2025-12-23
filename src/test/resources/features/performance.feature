Feature: System Performance and Timing Constraints
  As a bank
  I want the ATM to meet specific performance and timing requirements
  So that the system is secure, user-friendly, and reliable

  # Requirement from Section 3.3: User must finish each step within 60 seconds
  @performance @timeout @session
  Scenario: Session timeout due to user inactivity
    Given I am on any transaction screen
    When I do not perform any action for 60 seconds
    Then the system should time out
    And the system should retain the card
    And I should see a message "Session timed out"

  # Requirement 4.3.1: Error message display constraints
  @performance @ui
  Scenario: Error message display duration
    Given an error occurs during a transaction
    When the system displays an error message
    Then the message should remain visible for at least 30 seconds
    So that I have enough time to read and understand the error

  # Requirement 4.3.2: Bank response timeout
  @performance @network @timeout
  Scenario: Bank server response timeout
    Given I have initiated a transaction (e.g., withdrawal)
    When the bank computer does not respond within 120 seconds
    Then the system should cancel the transaction
    And the system should eject the card
    And I should see an error message "System unavailable, please try again later"

  # Requirement 4.3.3: Conditional dispensing
  @performance @reliability
  Scenario: Cash dispensing synchronization
    Given I have requested a cash withdrawal
    When the bank computer authorizes the transaction
    And the account withdrawal is confirmed processed
    Then and only then should the ATM dispense the cash
    But if the bank authorization fails or hangs
    Then the ATM must strictly NOT dispense any cash

  # Requirement 4.3.4: Concurrent processing
  @performance @concurrency
  Scenario: Concurrent transaction handling
    Given specific bank account "12345" has balance 1000.00
    When Transaction A requests withdrawal of 800.00 from ATM 1
    And Transaction B requests withdrawal of 500.00 from ATM 2 at the same time
    Then the system should process them sequentially or lock the account
    And only one transaction should succeed
    And the other should be rejected with "Insufficient funds"
    And the final account balance should be correctly updated
