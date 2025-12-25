Feature: ATM Maintainability and Availability
  As a bank operations team
  I want the ATM to be maintainable and available
  So that customers can access banking services reliably

  # Requirement 4.4.1 - Availability
  # Requirement 4.4.3 - Maintainability

  @availability @24_7
  Scenario: ATM available 24 hours a day (4.4.1)
    Given the ATM is properly configured
    When I check the system status at any time of day
    Then the ATM should be operational
    And all core services should be available

  @availability @service_check
  Scenario: All ATM services available
    Given the ATM is in operational state
    Then the following services should be available:
      | Service           |
      | Cash Withdrawal   |
      | Balance Inquiry   |
      | Money Transfer    |
      | PIN Change        |
      | Deposit           |

  @maintainability @connection
  Scenario: Only maintainers can connect new ATMs (4.4.3)
    Given a new ATM device needs to be connected to network
    When an unauthorized person attempts to connect the ATM
    Then the connection should be rejected
    And the system should log the unauthorized attempt

  @maintainability @authorized_connection
  Scenario: Maintainer successfully connects new ATM
    Given a new ATM device needs to be connected to network
    And a maintainer is authenticated with valid credentials
    When the maintainer initiates the ATM connection
    Then the new ATM should be registered in the network
    And the ATM should receive its configuration
    And the connection should be logged with maintainer ID

  @maintainability @diagnostics
  Scenario: ATM self-diagnostics
    Given the ATM is powered on
    When the ATM runs startup diagnostics
    Then it should check all hardware components:
      | Component        | Status |
      | Card Reader      | OK     |
      | Cash Dispenser   | OK     |
      | Display          | OK     |
      | Network          | OK     |
      | Printer          | OK     |
    And report any failures to the maintenance system
