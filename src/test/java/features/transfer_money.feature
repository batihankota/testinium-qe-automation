@browser:chrome
Feature: Transfer Money Scenarios

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
    # Store the current balance
    * Wait until element "amountText" is visible
    * Store numeric value from element "amountText" into variable "initialBalance"
    * Print value of variable "initialBalance"
    # Verify main page and open Transfer Money
    * Wait until element "transferMoney_ModalButton" is clickable
    * Verify presence of element "transferMoney_ModalButton"
    * Click on element "transferMoney_ModalButton"

  @TransferMoney
  @TransferMoneyMaximumValue
  Scenario: Transfer money with the maximum allowed value
    # User enters the maximum transfer amount and confirms
    * Enter text "99999999" into element "transferMoney_AmountInput"
    * Store value from element "transferMoney_AmountInput" into variable "enteredAmount"
    * Click on element "transferMoney_SendButton"
    # User verifies that the account balance is updated correctly
    * Wait until element "amountText" is visible
    * Store numeric value from element "amountText" into variable "updatedBalance"
    * Compare difference of variables "initialBalance" and "enteredAmount" with element "amountText"

  @TransferMoney
  @TransferMoneyNegativeValue
  Scenario: Attempt to transfer a negative amount and verify the error
    # User enters a negative amount and attempts to transfer
    * Enter text "-100" into element "transferMoney_AmountInput"
    * Click on element "transferMoney_SendButton"
    # User verifies that an error message is displayed
    * Verify text of element "amountError" should match "Invalid amount format."

  @TransferMoney
  @TransferMoneyExceedingBalance
  Scenario: Attempt to transfer an amount exceeding the balance and veriy the error
    # User enters an amount greater than their current balance
    * Enter text "999999999999" into element "transferMoney_AmountInput"
    # User verifies that an error message is displayed
    * Verify presence of element "balanceError"
    * Verify text of element "balanceError" should match "Insufficient funds."

  @TransferMoney
  @TransferMoneyZeroAmount
  Scenario: Attempt to transfer 0 TL and verify the error
    # User enters 0 TL as the transfer amount and confirms
    * Enter text "0" into element "transferMoney_AmountInput"
    * Click on element "transferMoney_SendButton"
    # User verifies that an error message is displayed
    * Verify presence of element "amountError"
    * Verify text of element "amountError" should match "Amount must be greater than 0."


  @TransferMoney
  @TransferMoneyDifferentReceivers
  Scenario Outline: Transfer money to different receivers and verify the transaction
    # User selects a receiver account
    * Click on element "transferMoney_ReceiverAccountDropdown"
    * Click on element "<receiverOption>"
    # User enters the transfer amount
    * Enter text "<amount>" into element "transferMoney_AmountInput"
    # User confirms the transaction
    * Click on element "transferMoney_SendButton"
    # User verifies that the correct receiver account was credited
    * Wait until element "lastTransaction" is visible
    * Verify text of element "lastTransactionReceiver" should match "<expectedReceiver>"

    Examples:
      | receiverOption                                 | amount | expectedReceiver |
      | transferMoney_ReceiverAccountDropdown_Option1  | 50.00  | Testinium-1      |
      | transferMoney_ReceiverAccountDropdown_Option2  | 100.00 | Testinium-2      |
      | transferMoney_ReceiverAccountDropdown_Option3  | 250.00 | Testinium-3      |
      | transferMoney_ReceiverAccountDropdown_Option4  | 500.00 | Testinium-4      |
      | transferMoney_ReceiverAccountDropdown_Option5  | 1000.00| Testinium-5      |

  @TransferMoney
  @TransferMoneyInvalidCharactersInAmount
  Scenario Outline: Attempt to enter invalid characters in the transfer Amount field and verify it remains empty
    # User enters an invalid transfer amount and verifies that the field remains empty
    * Enter text "<invalidInput>" into element "transferMoney_AmountInput"
    * Verify input field "transferMoney_AmountInput" is empty

    Examples:
      | invalidInput  |
      | abcdef        |
      | !@#$%^&*()    |
      | 12AB34        |
      | 5+5           |
      | xyz123        |

  @TransferMoney
  @TransferMoneyDecimalValue
  Scenario Outline: Verify decimal value handling in transfer transactions
    # User enters a decimal transfer amount and confirms
    * Enter text "<amount>" into element "transferMoney_AmountInput"
    * Verify value of element "transferMoney_AmountInput" should match "<expectedValue>"

    Examples:
      | amount     | expectedValue |
      | 10.0       | 10            |
      | 0.9999     | 0.99          |
      | 1234.567   | 1234.56       |
      | 1234,56    | 1234.56       |
