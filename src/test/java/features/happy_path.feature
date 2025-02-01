@browser:chrome
Feature: Happy Path Scenarios

  Background:
    # Navigate to login page and log in
    * Navigate to "https://catchylabs-webclient.testinium.com/" url
    * Enter text "batihan.kota" into element "usernameInput"
    * Enter text "@Zomboy135790" into element "passwordInput"
    * Click on element "loginButton"
    # Verify and open Money Transfer App
    * Wait until element "openMoneyTransferAppButton" is clickable
    * Verify presence of element "openMoneyTransferAppButton"
    * Click on element "openMoneyTransferAppButton"

  @HappyPath
  @AccountDetailsAndHomepage
  Scenario: Homepage control
    # Verify the appearance of the elements
    * Wait until element "accountNameText" is visible
    * Wait until element "accountTypeText" is visible
    * Wait until element "creationTimeText" is visible
    * Wait until element "amountText" is visible
    * Wait until element "backButton" is clickable
    # User checks account information and balance (name, type, creation time, amount)
    * Verify text of element "accountNameText" is "batihan.kota"
    * Verify text of element "accountTypeText" is "CHECKING"
    * Verify text of element "creationTimeText" should match "[0-9]{2}-[0-9]{2}-[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"
    * Verify numeric value of element "amountText"
    # User clicks the Back button
    * Click on element with JavaScript "backButton"
    * Wait until element "openMoneyTransferAppButton" is visible
    # User logs out
    * Wait until element "logOutButton" is clickable
    * Click on element with JavaScript "logOutButton"
    * Wait until element "loginButton" is visible

  @HappyPath
  @TransferMoney
  Scenario: Transfer an amount and verify updated balance
    # User checks the balance
    * Wait until element "amountText" is visible
    * Store numeric value from element "amountText" into variable "initialBalance"
    # User navigates to the Transfer Money screen
    * Wait until element "transferMoney_ModalButton" is visible
    * Click on element "transferMoney_ModalButton"
    # User verifies that the transfer screen has opened
    * Wait until element "lastTransactionSender" is visible
    * Verify text of element "lastTransactionSender" should match "batihan\.kota"
    * Verify presence of element "transferMoney_PageContainer"
    # User selects the sender account
    * Select value "batihan.kota" in element "transferMoney_SenderAccountDropdown"
    # User selects the receiver account
    * Wait until element "transferMoney_ReceiverAccountDropdown" is visible
    * Click on element "transferMoney_ReceiverAccountDropdown_Option1"
    # User enters the transfer amount
    * Enter text "100" into element "transferMoney_AmountInput"
    * Store numeric value from element "transferMoney_AmountInput" into variable "transferAmount"
    # User clicks the "Send" button and completes the transfer
    * Click on element "transferMoney_SendButton"
    # User verifies that the balance has been updated
    * Wait until element "amountText" is visible
    * Store numeric value from element "amountText" into variable "updatedBalance"
    * Verify that sum of variable "initialBalance" minus "transferAmount" equals "updatedBalance"
    # User verifies that the transfer transaction appears in the transaction history
    * Wait until element "lastTransactionAmount" is visible
    * Verify text of element "lastTransactionAmount" should match "100"
    * Verify text of element "lastTransactionSender" should match "batihan.kota"

    @HappyPath
    @AddMoney
    Scenario: Add money and verify updated balance
    # User checks the current balance
      * Wait until element "amountText" is visible
      * Store numeric value from element "amountText" into variable "initialBalance"
    # User navigates to the Add Money screen
      * Wait until element "addMoney_ModalButton" is clickable
      * Click on element "addMoney_ModalButton"
    # User verifies that the Add Money screen has opened
      * Wait until element "addMoney_PageContainer" is visible
      * Verify presence of element "addMoney_PageContainer"
    # User enters valid information (Card number, Card holder, Expiry date, CVV, etc.)
      * Enter text "1234123412341234" into element "addMoney_CardNumberInput"
      * Enter text "Batıhan Kota" into element "addMoney_CardHolderInput"
      * Enter text "10/26" into element "addMoney_ExpiryDateInput"
      * Enter text "110" into element "addMoney_CvvInput"
    # User enters the deposit amount
      * Enter text "500" into element "addMoney_AmountInput"
      * Store numeric value from element "addMoney_AmountInput" into variable "depositAmount"
    # User confirms the deposit
      * Wait until element "addMoney_AddButton" is clickable
      * Click on element "addMoney_AddButton"
    # User verifies the updated balance
      * Wait for 5 seconds
      * Wait until element "amountText" is visible
      * Store numeric value from element "amountText" into variable "updatedBalance"
      * Print value of variable "initialBalance"
      * Print value of variable "depositAmount"
      * Print value of variable "updatedBalance"
      * Verify that sum of variable "initialBalance" plus "depositAmount" equals "updatedBalance"
    # User verifies the transaction in history
      * Wait until element "lastTransactionAmount" is visible
      * Verify text of element "lastTransactionAmount" should match "500"

  @HappyPath
  @EditAccount
  Scenario: Edit account name and verify update
    # User navigates to the Edit Account page
    * Wait until element "editAccount_ModalButton" is clickable
    * Click on element "editAccount_ModalButton"
    # User verifies that the Edit Account screen has opened
    * Wait until element "editAccount_PageHeader" is visible
    * Verify presence of element "editAccount_PageHeader"
    # User modifies the account name
    * Wait until element "editAccount_NameInput" is visible
    * Store value from element "editAccount_NameInput" into variable "originalAccountName"
    * Clear element "editAccount_NameInput" and type text "Batihan-Updated"
    * Click on element "editAccount_UpdateButton"
    # User verifies the updated account name
    * Wait until element "accountNameText" is visible
    * Wait for 2 seconds
    * Verify text of element "accountNameText" should match "Batihan-Updated"
    # User reverts the account name to its original state
    * Wait until element "editAccount_ModalButton" is clickable
    * Click on element "editAccount_ModalButton"
    * Wait until element "editAccount_PageHeader" is visible
    * Clear text in element "editAccount_NameInput"
    * Enter text in element "editAccount_NameInput" from variable "originalAccountName"
    * Click on element "editAccount_UpdateButton"
    # User verifies that the account name has been restored
    * Wait until element "accountNameText" is visible
    * Wait for 2 seconds
    * Verify text of element "accountNameText" should match variable "originalAccountName"
