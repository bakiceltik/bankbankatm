Feature: ATM Initialization and Configuration
  As a bank maintainer
  I want to initialize the ATM with proper parameters
  So that it operates within defined limits

  # Functional Requirement 1: Initialize parameters t,k,m,n
  # t = Total fund in the ATM at start of day
  # k = Maximum withdrawal per day and account
  # m = Maximum withdrawal per transaction
  # n = Minimum cash in the ATM to permit a transaction

  @init @parameters
  Scenario: ATM initialization with parameters
    Given the ATM is being initialized
    When I set total fund (t) to 100000.00
    And I set maximum daily withdrawal (k) to 2000.00
    And I set maximum per-transaction withdrawal (m) to 500.00
    And I set minimum cash threshold (n) to 1000.00
    Then all parameters should be stored successfully
    And the ATM should be ready to accept cards

  @init @minimum_cash
  Scenario: ATM rejects card when cash below minimum threshold
    Given the ATM has only 500.00 cash remaining
    And the minimum cash threshold (n) is 1000.00
    When a customer attempts to insert a card
    Then the ATM should reject the card
    And I should see an error message "ATM out of service"

  # Functional Requirement 2: Initial display when no card
  @init @idle
  Scenario: ATM displays welcome screen when idle
    Given no cash card is inserted in the ATM
    Then the ATM should display the welcome screen
    And the ATM should be ready to accept a card

  # Functional Requirement 3: ATM running out of money
  @init @out_of_money
  Scenario: ATM running out of money rejects transactions
    Given the ATM has 200.00 cash remaining
    And the minimum cash threshold (n) is 500.00
    And a customer has successfully logged in
    When the customer attempts to withdraw 100.00
    Then the ATM should reject the transaction
    And I should see an error message "Insufficient ATM funds"
    And the card should be returned to customer

  # Functional Requirement 6: Serial number logging
  @auth @logging
  Scenario: Card serial number is logged on insertion
    Given the ATM system is initialized and has sufficient funds
    When I insert a valid card with serial "1234567890"
    Then the serial number "1234567890" should be logged
    And the log entry should include timestamp

  # Section 3.3 Scenario 1: 60 second timeout per step
  @timeout @step
  Scenario: Each step must be completed within 60 seconds
    Given a customer has inserted a card
    When the customer does not complete the current step within 60 seconds
    Then the ATM should timeout
    And the card should be retained
    And I should see a message "Session timeout - card retained"
