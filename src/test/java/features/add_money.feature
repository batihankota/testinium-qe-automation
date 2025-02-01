@browser:chrome
Feature: Add Money Scenarios

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
    # Verify main page and open Add Money
    * Wait until element "addMoney_ModalButton" is clickable
    * Verify presence of element "addMoney_ModalButton"
    * Click on element "addMoney_ModalButton"

  @AddMoney
    @AddMoneyZeroAmount
  Scenario Outline: Prevent adding zero amount to account
    # User enters valid card details
    * Wait until element "addMoney_PageContainer" is visible
    * Enter text "1234123412341234" into element "addMoney_CardNumberInput"
    * Enter text "Batıhan Kota" into element "addMoney_CardHolderInput"
    * Enter text "12/26" into element "addMoney_ExpiryDateInput"
    * Enter text "123" into element "addMoney_CvvInput"
    # User enters zero amount "<zeroAmount>" and verifies the error message
    * Enter text "<zeroAmount>" into element "addMoney_AmountInput"
    # User verifies the error message for zero amount transaction
    * Verify element "addMoney_AddButton" is disabled
    * Verify text of element "addMoney_AmountError" should match "Invalid amount. Minimum transaction is 1"

    Examples:
      | zeroAmount |
      | 0          |
      | 0.00       |


  @AddMoney
  @AddMoneyEmptyFields
  Scenario: Verify error messages when required fields are empty
    * Click on element "addMoney_AddButton"
    # Verify that the Card Number field displays an error when left empty.
    * Verify presence of element "addMoney_CardNumberError"
    * Verify text of element "addMoney_CardNumberError" should match "Required"
    # Verify that the Card Holder field displays an error when left empty.
    * Verify presence of element "addMoney_CardHolderError"
    * Verify text of element "addMoney_CardHolderError" should match "Required"
    # Verify that the Expiry Date field displays an error when left empty.
    * Verify presence of element "addMoney_ExpiryDateError"
    * Verify text of element "addMoney_ExpiryDateError" should match "Required"
    # Verify that the CVV field displays an error when left empty
    * Verify presence of element "addMoney_CvvError"
    * Verify text of element "addMoney_CvvError" should match "Required"
    # Verify that the Amount field displays an error when left empty
    * Verify presence of element "addMoney_AmountError"
    * Verify text of element "addMoney_AmountError" should match "Required"

  @AddMoney
  @AddMoneyIncompleteFields
  Scenario: Verify error messages for incomplete input fields
    # User enters an incomplete card number
    * Enter text "411111" into element "addMoney_CardNumberInput"
    # User enters an incomplete cardholder name
    * Enter text "Bati123" into element "addMoney_CardHolderInput"
    # User enters an incomplete expiry date
    * Enter text "12" into element "addMoney_ExpiryDateInput"
    # User enters an incomplete CVV
    * Enter text "1" into element "addMoney_CvvInput"
    # User verifies the error messages
    * Click on element "addMoney_AddButton"
    * Verify text of element "addMoney_CardNumberError" should match "Too Short!"
    * Verify text of element "addMoney_ExpiryDateError" should match "Wrong date. Please give a correct date"
    * Verify text of element "addMoney_CvvError" should match "Too short!"
    * Verify text of element "addMoney_CardHolderError" should match "Full name is required."

  @AddMoney
  @AddMoneyExcessiveInput
  Scenario: Verify error messages for excessive input
    # User enters an excessively long card number
    * Enter text "1234123412341234123" into element "addMoney_CardNumberInput"
    # User enters an excessively long cardholder name
    * Enter text "Batıhannnnnn KKKKKKKKKKKKOTTTTTTTAAAAA" into element "addMoney_CardHolderInput"
    # User enters an excessively long CVV
    * Enter text "12345" into element "addMoney_CvvInput"
    # User enters an invalid expiry date with a past year
    * Enter text "01/22" into element "addMoney_ExpiryDateInput"
    * Verify text of element "addMoney_ExpiryDateError" should match "Wrong date. Please give a correct date"
    * Clear text in element "addMoney_ExpiryDateInput"
    # User enters an invalid expiry date with an excessive month
    * Enter text "99/15" into element "addMoney_ExpiryDateInput"
    * Verify text of element "addMoney_ExpiryDateError" should match "Wrong date. Please give a correct date"
    # User verifies the error messages
    * Click on element "addMoney_AddButton"
    * Verify text of element "addMoney_CvvInput" should match "123"
    * Verify text of element "addMoney_CardNumberError" should match "Too Long!"
    * Verify text of element "addMoney_CardHolderError" should match "Too Long!"

  @AddMoney
  @AddMoneyInvalidCharacters
  Scenario: Verify invalid character input restrictions
    # User enters invalid characters in the card number field and verifies that only digits are retained
    * Enter text "abcd5678efgh" into element "addMoney_CardNumberInput"
    * Verify value of element "addMoney_CardNumberInput" should match "5678"
    # User enters an invalid amount using text instead of numbers and verifies the error message
    * Enter text "one hundred" into element "addMoney_AmountInput"
    * Verify text of element "addMoney_AmountError" should match "amount must be a `number` type"
    # User enters letters in the CVV field and verifies that only digits are retained
    * Enter text "a12" into element "addMoney_CvvInput"
    * Verify text of element "addMoney_CvvInput" should match "12"
    # User enters letters in the expiry date field and verifies the error message
    * Enter text "AA/BB" into element "addMoney_ExpiryDateInput"
    * Verify input field "addMoney_ExpiryDateInput" is empty
    * Verify text of element "addMoney_ExpiryDateError" should match "Required"
     # User enters a cardholder name with numbers and verifies the error message
    * Enter text "Bati123han" into element "addMoney_CardHolderInput"
    * Verify text of element "addMoney_CardHolderError" should match "Card holder name cannot contain numbers or special characters."

  @AddMoney
  @AddMoneyDecimalValues
  Scenario Outline: Verify decimal value handling in Amount field
    # User enters valid card details
    * Enter text "1234123412341234" into element "addMoney_CardNumberInput"
    * Enter text "Batıhan Kota" into element "addMoney_CardHolderInput"
    * Enter text "12/26" into element "addMoney_ExpiryDateInput"
    * Enter text "123" into element "addMoney_CvvInput"
    # User enters a decimal amount and verifies the transaction proceeds
    * Enter text "<amount>" into element "addMoney_AmountInput"
    * Click on element "addMoney_AddButton"
    # User verifies that the transaction amount is correct
    * Wait until element "lastTransactionAmount" is visible
    * Verify text of element "lastTransactionAmount" should match "<expectedTransactionAmount>"
    # User verifies that the account balance is updated correctly
    * Wait until element "accountBalance" is visible
    * Compare sum of variables "initialBalance" and "<expectedTransactionAmount>" with element "accountBalance"

    Examples:
      | amount         | expectedTransactionAmount  |
      | 0.5            | 0.5                        |
      | 1234.56        | 1234.56                    |
      | 123456789.12   | 123456789.12               |

  @AddMoney
  @AddMoneyInvalidDecimalInputs
  Scenario: Verify invalid decimal inputs and auto-formatting in Amount field
    # User enters valid card details
    * Enter text "1234123412341234" into element "addMoney_CardNumberInput"
    * Enter text "Batıhan Kota" into element "addMoney_CardHolderInput"
    * Enter text "12/26" into element "addMoney_ExpiryDateInput"
    * Enter text "123" into element "addMoney_CvvInput"
    # User enters zero as amount and verifies the error message
    * Enter text "-0000.00" into element "addMoney_AmountInput"
    * Verify text of element "addMoney_AmountError" should match "amount must be greater than 0"
    * Clear text in element "addMoney_AmountInput"
    # User enters a decimal value using a comma instead of a dot and verifies it is auto-formatted
    * Enter text "1234,56" into element "addMoney_AmountInput"
    * Verify text of element "addMoney_AmountInput" should match "1234.56"

  @AddMoney
  @AddMoneyMaximumValue
  Scenario: Verify maximum allowed value in Amount field
    # User enters the maximum allowed amount and initiates the transaction
    * Enter text "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.99" into element "addMoney_AmountInput"
    * Verify text of element "addMoney_AmountError" should match "amount exceeds the maximum limit"

  @AddMoney
  @AddMoneyNegativeValue
  Scenario: Verify error message when entering a negative amount
    # User enters a negative amount and verifies the error message
    * Enter text "-100.00" into element "addMoney_AmountInput"
    * Verify element "addMoney_AddButton" is disabled
    * Verify text of element "addMoney_AmountError" should match "amount must be a positive number"

  @AddMoney
  @AddMoneySpecialCharacters
  Scenario: Verify error message when entering special characters in Amount field
    # User enters special characters in the amount and verifies the error message
    * Enter text "!@#$%^&*()" into element "addMoney_AmountInput"
    * Verify element "addMoney_AddButton" is disabled
    * Verify text of element "addMoney_AmountError" should match "amount must be a `number` type"

  @AddMoney
  @AddMoneyInvalidCard
  Scenario: Verify error messages for an invalid credit card (all digits are 0)
    # User enters an invalid card number
    * Enter text "0000000000000000" into element "addMoney_CardNumberInput"
    # User enters an invalid expiry date
    * Enter text "01/01" into element "addMoney_ExpiryDateInput"
    # User enters an invalid CVV
    * Enter text "000" into element "addMoney_CvvInput"
    # User enters a zero amount
    * Enter text "999999999" into element "addMoney_AmountInput"
    # User verifies the error messages
    * Click on element "addMoney_AddButton"
    * Verify text of element "addMoney_CardNumberError" should match "Invalid card number"
    * Verify text of element "addMoney_ExpiryDateError" should match "Invalid expiry date"
    * Verify text of element "addMoney_ExpiryDateError" should match "Invalid expiry date"
    * Verify text of element "addMoney_CvvError" should match "Invalid Cvv"
    * Verify text of element "addMoney_AmountError" should match "Invalid amount. Minimum transaction amount is 1"
