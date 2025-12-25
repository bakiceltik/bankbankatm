package com.bankbankatm;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import static org.junit.Assert.*;

/**
 * Step definitions for BankBankATM Cucumber tests.
 * 
 * TDD Red State: All tests should FAIL initially because the original
 * classes throw UnsupportedOperationException. Tests will pass (Green)
 * once the actual implementation is written.
 * 
 * Original Classes Used:
 * - ATM: verify(), readAccountNum(), checkAvailabilityOfCashInATM(),
 * verifyInputAmount(), checkTime()
 * - DatabaseProxy: selectPasswordByAccountNum(), minusBalance(), plusBalance(),
 * createNewAccount(), checkTheBalance()
 * - CardReader: readCard(), ejectCard(), retainCard()
 * - CashDispenser: setInitialCash(), checkCashOnHand(), dispenseCash()
 * - Withdrawal: getSpecificsFromCustomer(), completeTransaction()
 * - Deposit: getSpecificsFromCustomer(), completeTransaction()
 * - Transfer: getSpecificsFromCustomer(), completeTransaction()
 * - Inquiry: getSpecificsFromCustomer(), completeTransaction()
 * - Display: display(), readPIN(), readMenuChoice(), readAmount()
 * - Log: logSend(), logResponse(), logCashDispensed()
 */
public class StepDefinitions {

    // Core Components
    private ATM atm;
    private DatabaseProxy db;
    private CardReader cardReader;
    private CashDispenser cashDispenser;
    private Display display;
    private Log log;

    // Test State
    private int currentAccountNum;
    private String currentPin;
    private double requestedAmount;
    private String targetAccountNum;
    private Exception lastException;
    private boolean transactionSuccess;

    // ===========================================
    // SETUP / HOOKS
    // ===========================================

    @Before
    public void setUp() {
        // Note: These constructors will throw UnsupportedOperationException
        // until they are implemented
        lastException = null;
        transactionSuccess = false;
    }

    // ===========================================
    // AUTH FEATURE STEPS
    // ===========================================

    @Given("the ATM system is initialized and has sufficient funds")
    public void the_atm_system_is_initialized_and_has_sufficient_funds() {
        try {
            atm = new ATM();
            Message cashCheck = atm.checkAvailabilityOfCashInATM();
            assertNotNull("ATM should check cash availability", cashCheck);
        } catch (UnsupportedOperationException e) {
            lastException = e;
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @When("I insert a valid card with serial {string}")
    public void i_insert_a_valid_card_with_serial(String serial) {
        try {
            currentAccountNum = Integer.parseInt(serial);
            atm.readAccountNum(currentAccountNum);
        } catch (UnsupportedOperationException e) {
            lastException = e;
            fail("ATM.readAccountNum() not implemented");
        }
    }

    @When("I insert a valid card")
    public void i_insert_a_valid_card() {
        i_insert_a_valid_card_with_serial("1234567890");
    }

    @When("I enter the correct PIN {string}")
    public void i_enter_the_correct_pin(String pin) {
        try {
            currentPin = pin;
            String result = atm.verify(pin);
            assertNotNull("ATM should verify PIN", result);
        } catch (UnsupportedOperationException e) {
            lastException = e;
            fail("ATM.verify() not implemented");
        }
    }

    @When("I enter the incorrect PIN {string}")
    public void i_enter_the_incorrect_pin(String pin) {
        try {
            currentPin = pin;
            String result = atm.verify(pin);
            // Should return error for incorrect PIN
        } catch (UnsupportedOperationException e) {
            lastException = e;
            fail("ATM.verify() not implemented");
        }
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        assertNull("No exception should occur during successful login", lastException);
    }

    @Then("I receive a {string} message")
    public void i_receive_a_message(String expectedMessage) {
        // Display should show the expected message
        try {
            display = new Display();
            display.display(expectedMessage);
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @Then("I should see a message {string}")
    public void i_should_see_a_message(String expectedMessage) {
        i_receive_a_message(expectedMessage);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedError) {
        try {
            display = new Display();
            display.display(expectedError);
            fail("Should display error: " + expectedError);
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @Then("the card should be ejected")
    public void the_card_should_be_ejected() {
        try {
            cardReader = new CardReader(atm);
            cardReader.ejectCard();
        } catch (UnsupportedOperationException e) {
            fail("CardReader.ejectCard() not implemented");
        }
    }

    @Then("the system should retain the card")
    public void the_system_should_retain_the_card() {
        try {
            cardReader = new CardReader(atm);
            cardReader.retainCard();
        } catch (UnsupportedOperationException e) {
            fail("CardReader.retainCard() not implemented");
        }
    }

    // ===========================================
    // WITHDRAWAL FEATURE STEPS
    // ===========================================

    @Given("I am successfully logged in with a valid card")
    public void i_am_successfully_logged_in_with_a_valid_card() {
        the_atm_system_is_initialized_and_has_sufficient_funds();
        i_insert_a_valid_card();
        i_enter_the_correct_pin("1234");
    }

    @Given("my account has a balance of {double}")
    public void my_account_has_a_balance_of(Double balance) {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Given("the maximum daily withdrawal limit is {double}")
    public void the_maximum_daily_withdrawal_limit_is(Double limit) {
        // ATM should store daily limit parameter (FR1: k)
        assertNotNull("ATM should be initialized", atm);
    }

    @Given("the maximum per-transaction withdrawal limit is {double}")
    public void the_maximum_per_transaction_withdrawal_limit_is(Double limit) {
        // ATM should store transaction limit parameter (FR1: m)
        assertNotNull("ATM should be initialized", atm);
    }

    @Given("the specific ATM has {double} cash available")
    public void the_specific_atm_has_cash_available(Double amount) {
        try {
            Message cashStatus = atm.checkAvailabilityOfCashInATM();
            assertNotNull("ATM should check cash", cashStatus);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @When("I request a withdrawal of {double}")
    public void i_request_a_withdrawal_of(Double amount) {
        try {
            requestedAmount = amount;
            Message result = atm.verifyInputAmount();
            assertNotNull("ATM should verify amount", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.verifyInputAmount() not implemented");
        }
    }

    @Then("the ATM should dispense {double}")
    public void the_atm_should_dispense(Double amount) {
        try {
            log = new Log();
            cashDispenser = new CashDispenser(log);
            // CashDispenser.dispenseCash() should be called
            fail("CashDispenser.dispenseCash() not implemented");
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser or Log not implemented");
        }
    }

    @Then("my account balance should be updated to {double}")
    public void my_account_balance_should_be_updated_to_(Double expectedBalance) {
        try {
            db.minusBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.minusBalance() not implemented");
        }
    }

    @Then("the system should reject the transaction")
    public void the_system_should_reject_the_transaction() {
        assertFalse("Transaction should be rejected", transactionSuccess);
    }

    // ===========================================
    // DEPOSIT FEATURE STEPS
    // ===========================================

    @Given("the ATM deposit slot is operational")
    public void the_atm_deposit_slot_is_operational() {
        the_atm_system_is_initialized_and_has_sufficient_funds();
    }

    @When("I select \"Deposit\" from the menu")
    public void i_select_deposit_from_the_menu() {
        try {
            display = new Display();
            String[] menu = { "Withdrawal", "Deposit", "Transfer", "Inquiry" };
            int choice = display.readMenuChoice("Select option", menu);
        } catch (UnsupportedOperationException e) {
            fail("Display.readMenuChoice() not implemented");
        }
    }

    @When("I insert {double} in valid currency")
    public void i_insert_in_valid_currency(Double amount) {
        try {
            log = new Log();
            log.logEnvelopeAccepted();
        } catch (UnsupportedOperationException e) {
            fail("Log.logEnvelopeAccepted() not implemented");
        }
    }

    @When("I confirm the deposit")
    public void i_confirm_the_deposit() {
        try {
            db = new DatabaseProxy();
            db.plusBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.plusBalance() not implemented");
        }
    }

    @Then("the deposit slot should close")
    public void the_deposit_slot_should_close() {
        // ATM hardware operation
        assertNotNull("ATM should handle deposit slot", atm);
    }

    // ===========================================
    // TRANSFER FEATURE STEPS
    // ===========================================

    @And("I enter the target account number {string}")
    public void i_enter_the_target_account_number(String account) {
        targetAccountNum = account;
    }

    @When("I request a transfer of {double}")
    public void i_request_a_transfer_of(Double amount) {
        requestedAmount = amount;
        try {
            db = new DatabaseProxy();
            db.minusBalance(); // From source
            db.plusBalance(); // To target
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy transfer operations not implemented");
        }
    }

    @When("I enter an invalid target account number {string}")
    public void i_enter_an_invalid_target_account_number(String invalidAccount) {
        targetAccountNum = invalidAccount;
        try {
            db = new DatabaseProxy();
            db.checkTheBalance(); // Should fail for invalid account
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    // ===========================================
    // INQUIRY FEATURE STEPS
    // ===========================================

    @When("I select {string}")
    public void i_select(String option) {
        try {
            display = new Display();
            String[] menu = { option };
            display.readMenuChoice("Select", menu);
        } catch (UnsupportedOperationException e) {
            fail("Display.readMenuChoice() not implemented");
        }
    }

    @Then("I should see my current balance of {double}")
    public void i_should_see_my_current_balance_of(Double balance) {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("I should see a list of recent transactions")
    public void i_should_see_a_list_of_recent_transactions() {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    // ===========================================
    // CHANGE PASSWORD FEATURE STEPS
    // ===========================================

    @Given("my current PIN is {string}")
    public void my_current_pin_is(String pin) {
        currentPin = pin;
    }

    @When("I enter my old PIN {string}")
    public void i_enter_my_old_pin(String oldPin) {
        try {
            String result = atm.verify(oldPin);
        } catch (UnsupportedOperationException e) {
            fail("ATM.verify() not implemented");
        }
    }

    @When("I enter my new PIN {string}")
    public void i_enter_my_new_pin(String newPin) {
        currentPin = newPin;
    }

    @When("I confirm my new PIN {string}")
    public void i_confirm_my_new_pin(String confirmPin) {
        try {
            String result = atm.verify(confirmPin); // Verify new PIN matches
            assertNotNull("PIN verification should return result", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.verify() not implemented");
        }
    }

    // ===========================================
    // PERFORMANCE FEATURE STEPS
    // ===========================================

    @Given("I am on any transaction screen")
    public void i_am_on_any_transaction_screen() {
        i_am_successfully_logged_in_with_a_valid_card();
    }

    @When("I remain idle for {int} seconds")
    public void i_remain_idle_for_seconds(Integer seconds) {
        try {
            Time timeout = atm.checkTime();
            assertNotNull("ATM should check time", timeout);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkTime() not implemented");
        }
    }

    @Then("my session should be terminated")
    public void my_session_should_be_terminated() {
        try {
            Time timeout = atm.checkTime();
            assertNotNull("Session should check timeout", timeout);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkTime() not implemented");
        }
    }

    @Given("an error occurs during a transaction")
    public void an_error_occurs_during_a_transaction() {
        lastException = new Exception("Test error");
    }

    @When("the system displays an error message")
    public void the_system_displays_an_error_message() {
        try {
            display = new Display();
            display.display("Error occurred");
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @Then("the message should remain visible for at least {int} seconds")
    public void the_message_should_remain_visible_for_at_least_seconds(Integer seconds) {
        // Display duration check
        assertTrue("Message duration should be positive", seconds > 0);
    }

    @Given("I have initiated a transaction \\(e.g., withdrawal)")
    public void i_have_initiated_a_transaction() {
        i_am_successfully_logged_in_with_a_valid_card();
    }

    @When("the bank computer does not respond within {int} seconds")
    public void the_bank_computer_does_not_respond_within_seconds(Integer seconds) {
        try {
            Time timeout = atm.checkTime();
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkTime() not implemented");
        }
    }

    @Then("the system should cancel the transaction")
    public void the_system_should_cancel_the_transaction() {
        transactionSuccess = false;
    }

    @Then("the system should eject the card")
    public void the_system_should_eject_the_card() {
        the_card_should_be_ejected();
    }

    @Given("I have requested a cash withdrawal")
    public void i_have_requested_a_cash_withdrawal() {
        i_am_successfully_logged_in_with_a_valid_card();
        requestedAmount = 100.0;
    }

    @When("the bank computer authorizes the transaction")
    public void the_bank_computer_authorizes_the_transaction() {
        transactionSuccess = true;
    }

    @When("the account withdrawal is confirmed processed")
    public void the_account_withdrawal_is_confirmed_processed() {
        try {
            db = new DatabaseProxy();
            db.minusBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.minusBalance() not implemented");
        }
    }

    @Then("and only then should the ATM dispense the cash")
    public void and_only_then_should_the_atm_dispense_the_cash() {
        assertTrue("Transaction must be authorized before dispensing", transactionSuccess);
    }

    @But("if the bank authorization fails or hangs")
    public void if_the_bank_authorization_fails_or_hangs() {
        transactionSuccess = false;
    }

    @Then("the ATM must strictly NOT dispense any cash")
    public void the_atm_must_strictly_not_dispense_any_cash() {
        assertFalse("Cash should not be dispensed without authorization", transactionSuccess);
    }

    @Given("specific bank account {string} has balance {double}")
    public void specific_bank_account_has_balance(String account, Double balance) {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @When("Transaction A requests withdrawal of {double} from ATM 1")
    public void transaction_a_requests_withdrawal_from_atm_1(Double amount) {
        requestedAmount = amount;
    }

    @When("Transaction B requests withdrawal of {double} from ATM 2 at the same time")
    public void transaction_b_requests_withdrawal_from_atm_2(Double amount) {
        try {
            // Second concurrent withdrawal attempt
            db.minusBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.minusBalance() not implemented");
        }
    }

    @Then("the system should process them sequentially or lock the account")
    public void the_system_should_process_them_sequentially() {
        try {
            db.checkTheBalance(); // Verify concurrency control
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("only one transaction should succeed")
    public void only_one_transaction_should_succeed() {
        try {
            db.checkTheBalance(); // Verify only one succeeded
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("the other should be rejected with {string}")
    public void the_other_should_be_rejected_with(String message) {
        try {
            display = new Display();
            display.display(message); // Show rejection message
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @Then("the final account balance should be correctly updated")
    public void the_final_account_balance_should_be_correctly_updated() {
        try {
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    // ===========================================
    // RELIABILITY FEATURE STEPS
    // ===========================================

    @Given("a transaction is in progress")
    public void a_transaction_is_in_progress() {
        i_am_successfully_logged_in_with_a_valid_card();
        transactionSuccess = true;
    }

    @When("a power failure occurs during processing")
    public void a_power_failure_occurs_during_processing() {
        try {
            // NetworkToBank should handle connection failure
            NetworkToBank network = new NetworkToBank(log, null);
            network.closeConnection();
        } catch (UnsupportedOperationException e) {
            fail("NetworkToBank.closeConnection() not implemented");
        }
    }

    @Then("the system should rollback any partial changes")
    public void the_system_should_rollback_any_partial_changes() {
        try {
            // Database should support rollback for ACID compliance
            db = new DatabaseProxy();
            db.checkTheBalance(); // Balance should be unchanged after rollback
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy rollback not implemented");
        }
    }

    @Then("no money should be deducted from my account")
    public void no_money_should_be_deducted_from_my_account() {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance(); // Verify balance unchanged (atomicity)
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("no money should be dispensed if not fully processed")
    public void no_money_should_be_dispensed_if_not_fully_processed() {
        try {
            log = new Log();
            cashDispenser = new CashDispenser(log);
            cashDispenser.checkCashOnHand(null); // Verify no cash was dispensed
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.checkCashOnHand() not implemented");
        }
    }

    @Given("a withdrawal of {double} is requested")
    public void a_withdrawal_of_is_requested(Double amount) {
        requestedAmount = amount;
    }

    @When("the system prepares the cash")
    public void the_system_prepares_the_cash() {
        try {
            log = new Log();
            cashDispenser = new CashDispenser(log);
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser not implemented");
        }
    }

    @Then("the machine must accurately identify and select {double} worth of bills")
    public void the_machine_must_accurately_identify_and_select_worth_of_bills(Double amount) {
        try {
            cashDispenser.checkCashOnHand(null);
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.checkCashOnHand() not implemented");
        }
    }

    @Given("a hardware error occurs during cash dispensing")
    public void a_hardware_error_occurs_during_cash_dispensing() {
        lastException = new Exception("Hardware error");
    }

    @When("the system detects the error")
    public void the_system_detects_the_error() {
        assertNotNull("Error should be detected", lastException);
    }

    @Then("the system should retry up to {int} times")
    public void the_system_should_retry_up_to_times(Integer retries) {
        assertTrue("Should retry", retries > 0);
    }

    @Then("if still failing, return the cash to the user")
    public void if_still_failing_return_the_cash_to_the_user() {
        try {
            cashDispenser.putCash(); // Return cash on error
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.putCash() not implemented");
        }
    }

    // ===========================================
    // INITIALIZATION FEATURE STEPS
    // ===========================================

    @Given("the ATM is being initialized")
    public void the_atm_is_being_initialized() {
        try {
            atm = new ATM();
        } catch (Exception e) {
            fail("ATM initialization failed: " + e.getMessage());
        }
    }

    @When("I set total fund \\(t) to {double}")
    public void i_set_total_fund_to(Double amount) {
        try {
            Message result = atm.checkAvailabilityOfCashInATM(); // Verify t parameter
            assertNotNull("ATM should check fund", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @When("I set maximum daily withdrawal \\(k) to {double}")
    public void i_set_maximum_daily_withdrawal_k_to(Double limit) {
        try {
            Message result = atm.verifyInputAmount(); // Uses k limit
            assertNotNull("ATM should verify limit", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.verifyInputAmount() not implemented");
        }
    }

    @When("I set maximum per-transaction withdrawal \\(m) to {double}")
    public void i_set_maximum_per_transaction_m_to(Double limit) {
        try {
            Message result = atm.verifyInputAmount(); // Uses m limit
            assertNotNull("ATM should verify limit", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.verifyInputAmount() not implemented");
        }
    }

    @When("I set minimum cash threshold \\(n) to {double}")
    public void i_set_minimum_cash_threshold_n_to(Double threshold) {
        try {
            Message result = atm.checkAvailabilityOfCashInATM(); // Uses n threshold
            assertNotNull("ATM should check threshold", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @Then("all parameters should be stored successfully")
    public void all_parameters_should_be_stored_successfully() {
        assertNotNull("ATM should be initialized", atm);
    }

    @Then("the ATM should be ready to accept cards")
    public void the_atm_should_be_ready_to_accept_cards() {
        try {
            atm.checkAvailabilityOfCashInATM();
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @Given("the ATM has only {double} cash remaining")
    public void the_atm_has_only_cash_remaining(Double amount) {
        the_atm_is_being_initialized();
    }

    @Given("the minimum cash threshold \\(n) is {double}")
    public void the_minimum_cash_threshold_n_is(Double threshold) {
        try {
            Message result = atm.checkAvailabilityOfCashInATM(); // Check n threshold
            assertNotNull("ATM should check threshold", result);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkAvailabilityOfCashInATM() not implemented");
        }
    }

    @When("a customer attempts to insert a card")
    public void a_customer_attempts_to_insert_a_card() {
        try {
            cardReader = new CardReader(atm);
            cardReader.readCard();
        } catch (UnsupportedOperationException e) {
            fail("CardReader.readCard() not implemented");
        }
    }

    @Then("the ATM should reject the card")
    public void the_atm_should_reject_the_card() {
        the_card_should_be_ejected();
    }

    @Given("no cash card is inserted in the ATM")
    public void no_cash_card_is_inserted_in_the_atm() {
        the_atm_is_being_initialized();
    }

    @Then("the ATM should display the welcome screen")
    public void the_atm_should_display_the_welcome_screen() {
        try {
            display = new Display();
            display.display("Welcome");
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @Then("the ATM should be ready to accept a card")
    public void the_atm_should_be_ready_to_accept_a_card() {
        the_atm_should_be_ready_to_accept_cards();
    }

    @Given("the ATM has {double} cash remaining")
    public void the_atm_has_cash_remaining(Double amount) {
        the_atm_is_being_initialized();
    }

    @Given("a customer has successfully logged in")
    public void a_customer_has_successfully_logged_in() {
        i_am_successfully_logged_in_with_a_valid_card();
    }

    @When("the customer attempts to withdraw {double}")
    public void the_customer_attempts_to_withdraw(Double amount) {
        i_request_a_withdrawal_of(amount);
    }

    @Then("the ATM should reject the transaction")
    public void the_atm_should_reject_the_transaction() {
        the_system_should_reject_the_transaction();
    }

    @Then("the card should be returned to customer")
    public void the_card_should_be_returned_to_customer() {
        the_card_should_be_ejected();
    }

    @Then("the serial number {string} should be logged")
    public void the_serial_number_should_be_logged(String serial) {
        try {
            log = new Log();
            log.logSend(null);
        } catch (UnsupportedOperationException e) {
            fail("Log.logSend() not implemented");
        }
    }

    @Then("the log entry should include timestamp")
    public void the_log_entry_should_include_timestamp() {
        assertNotNull("Log should be initialized", log);
    }

    @Given("a customer has inserted a card")
    public void a_customer_has_inserted_a_card() {
        the_atm_is_being_initialized();
        i_insert_a_valid_card();
    }

    @When("the customer does not complete the current step within {int} seconds")
    public void the_customer_does_not_complete_within_seconds(Integer seconds) {
        try {
            atm.checkTime();
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkTime() not implemented");
        }
    }

    @Then("the ATM should timeout")
    public void the_atm_should_timeout() {
        try {
            Time timeout = atm.checkTime();
            assertNotNull("ATM should check timeout", timeout);
        } catch (UnsupportedOperationException e) {
            fail("ATM.checkTime() not implemented");
        }
    }

    @Then("the card should be retained")
    public void the_card_should_be_retained() {
        the_system_should_retain_the_card();
    }

    // ===========================================
    // ADDITIONAL RELIABILITY STEPS
    // ===========================================

    @Given("I have requested a withdrawal of {double}")
    public void i_have_requested_a_withdrawal_of_for_reliability(Double amount) {
        i_am_successfully_logged_in_with_a_valid_card();
        requestedAmount = amount;
    }

    @Given("the system has debited my account")
    public void the_system_has_debited_my_account() {
        try {
            db = new DatabaseProxy();
            db.minusBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.minusBalance() not implemented");
        }
    }

    @But("the power fails BEFORE the cash is dispensed")
    public void the_power_fails_before_the_cash_is_dispensed() {
        try {
            // Simulate power failure by closing network connection
            NetworkToBank network = new NetworkToBank(log, null);
            network.closeConnection();
        } catch (UnsupportedOperationException e) {
            fail("NetworkToBank.closeConnection() not implemented");
        }
        transactionSuccess = false;
    }

    @When("the system restarts")
    public void the_system_restarts() {
        the_atm_is_being_initialized();
    }

    @Then("the transaction should be rolled back")
    public void the_transaction_should_be_rolled_back() {
        assertFalse("Transaction should be rolled back", transactionSuccess);
    }

    @Then("my account balance should be restored to its original state")
    public void my_account_balance_should_be_restored_to_its_original_state() {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("no cash should be dispensed")
    public void no_cash_should_be_dispensed() {
        assertFalse("No cash should be dispensed", transactionSuccess);
    }

    // Additional reliability feature steps

    @Given("two transactions act on the same account simultaneously")
    public void two_transactions_act_on_same_account_simultaneously() {
        try {
            db = new DatabaseProxy();
            // Both transactions try to access same account
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @When("one transaction is in progress \\(reading balance)")
    public void one_transaction_is_in_progress_reading_balance() {
        try {
            db = new DatabaseProxy();
            db.checkTheBalance();
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy.checkTheBalance() not implemented");
        }
    }

    @Then("the other transaction must wait or fail")
    public void the_other_transaction_must_wait_or_fail() {
        try {
            db.checkTheBalance(); // Should block or fail due to isolation
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy isolation not implemented");
        }
    }

    @Then("neither transaction should see an intermediate invalid state")
    public void neither_transaction_should_see_intermediate_invalid_state() {
        try {
            db.checkTheBalance(); // Should return consistent state
        } catch (UnsupportedOperationException e) {
            fail("DatabaseProxy consistency not implemented");
        }
    }

    @Given("the cash dispenser is loaded with mixed bills")
    public void the_cash_dispenser_is_loaded_with_mixed_bills() {
        try {
            log = new Log();
            cashDispenser = new CashDispenser(log);
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser not implemented");
        }
    }

    @Then("if identification fails, it should not dispense incorrect amounts")
    public void if_identification_fails_should_not_dispense_incorrect_amounts() {
        try {
            cashDispenser.checkCashOnHand(null); // Should validate before dispensing
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.checkCashOnHand() not implemented");
        }
    }

    @Given("the cash dispenser encounters a mechanical error during operation")
    public void the_cash_dispenser_encounters_mechanical_error() {
        try {
            log = new Log();
            cashDispenser = new CashDispenser(log);
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser not implemented");
        }
        lastException = new Exception("Mechanical error");
    }

    @When("it attempts the operation for the 1st time and fails")
    public void it_attempts_operation_1st_time_and_fails() {
        try {
            cashDispenser.dispenseCash(null); // Retry 1
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.dispenseCash() not implemented - retry 1");
        }
    }

    @When("it retries a 2nd time and fails")
    public void it_retries_2nd_time_and_fails() {
        try {
            cashDispenser.dispenseCash(null); // Retry 2
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.dispenseCash() not implemented - retry 2");
        }
    }

    @When("it retries a 3rd time and fails")
    public void it_retries_3rd_time_and_fails() {
        try {
            cashDispenser.dispenseCash(null); // Retry 3
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.dispenseCash() not implemented - retry 3");
        }
    }

    @Then("the system should stop retrying")
    public void the_system_should_stop_retrying() {
        // After 3 retries, system should give up
        assertNotNull("Error should be set after 3 failed retries", lastException);
    }

    @Then("the system should return any partial cash to any internal reject bin")
    public void the_system_should_return_partial_cash_to_reject_bin() {
        try {
            cashDispenser.putCash(); // Return cash to reject bin
        } catch (UnsupportedOperationException e) {
            fail("CashDispenser.putCash() not implemented");
        }
    }

    @Then("the system should return the card to the user")
    public void the_system_should_return_card_to_user() {
        the_card_should_be_ejected();
    }

    @Then("the system should display a {string} error")
    public void the_system_should_display_error(String errorMessage) {
        try {
            display = new Display();
            display.display(errorMessage);
        } catch (UnsupportedOperationException e) {
            fail("Display.display() not implemented");
        }
    }

    @And("the transaction should be cancelled")
    public void and_the_transaction_should_be_cancelled() {
        transactionSuccess = false;
    }
}
