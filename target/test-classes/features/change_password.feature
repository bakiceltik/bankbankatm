Feature: Change Password
  As a logged-in customer
  I want to change my PIN
  So that I can maintain the security of my account

  Background:
    Given I am successfully logged in with a valid card
    And my current PIN is "1234"

  @password @success
  Scenario: Successfully change PIN
    When I choose "Change Password" from the main menu
    And I enter my old PIN "1234"
    And I enter a new PIN "5678"
    And I re-enter the new PIN "5678" to confirm
    Then the system should update my PIN to "5678"
    And I should see a "Password Changed Successfully" message

  @password @wrong_old_pin
  Scenario: Change PIN with incorrect old PIN
    When I choose "Change Password" from the main menu
    And I enter an incorrect old PIN "0000"
    Then the system should reject the change request
    And I should see an error message "Incorrect old PIN"
    And my PIN should remain "1234"

  @password @mismatch
  Scenario: New PIN confirmation mismatch
    When I choose "Change Password" from the main menu
    And I enter my old PIN "1234"
    And I enter a new PIN "5678"
    And I re-enter a different PIN "9999" to confirm
    Then the system should reject the change request
    And I should see an error message "PINs do not match"
    And my PIN should remain "1234"
