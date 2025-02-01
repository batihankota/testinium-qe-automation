@browser:chrome
Feature: Edit Account Scenarios

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
    # Verify main page and open Edit Account
    * Wait until element "editAccount_ModalButton" is clickable
    * Click on element "editAccount_ModalButton"
    # Clear the existing account name
    * Wait until element "editAccount_NameInput" is visible
    * Click and send backspace until element "editAccount_NameInput" is empty

  @EditAccount
  @EditAccountEmptyName
  Scenario: Prevent empty account name update
    # User verifies that the Update button is not clickable
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountOnlySpaces
  Scenario: Prevent username with only space characters
    # User enters a username containing only space characters
    * Enter text "   " into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username cannot be empty"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountExcessiveSpaces
  Scenario: Prevent excessive space usage in account name
    # User enters a username with excessive spaces
    * Enter text "   Batihan   Kota   " into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username cannot contain excessive spaces"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountNumericOnly
  Scenario: Prevent account name from being only numbers
    # User enters a username that consists of only numeric characters
    * Enter text "123456789" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username cannot be only numbers"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountMinLength
  Scenario: Prevent account name shorter than minimum limit
    # User enters a username that is shorter than the minimum allowed character limit
    * Enter text "ab" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username must be at least 3 characters long"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountMaxLength
  Scenario: Prevent account name longer than maximum limit
    # User enters a username that exceeds the maximum allowed character limit
    * Enter text "ThisUsernameIsWayTooLong1234567890" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username cannot exceed 20 characters"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountSpecialCharacters
  Scenario: Prevent special characters in username
    # User enters a username with special characters
    * Enter text "$bati@!han'\"(kota)*" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Special characters are not allowed"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountUnicodeCharacters
  Scenario: Prevent unicode characters in username
    # User enters a username with Unicode characters
    * Enter text "𝔅𝔞𝔱𝔦𝔥𝔞𝔫" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Invalid characters in username"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountEmoji
  Scenario: Prevent emoji in username
    # User enters a username with emoji
    * Enter text "Batihan 😀" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Emojis are not allowed"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable

  @EditAccount
  @EditAccountTurkishCharacters
  Scenario: Prevent Turkish characters in username
    # User enters a username with Turkish characters
    * Enter text "Batıhan Çota" into element "editAccount_NameInput"
    # User verifies the error message (if applicable)
    * Verify text of element "editAccountPageErrorMessage" should match "Username cannot contain special characters or Turkish letters"
    # User verifies that the Update button is disabled
    * Verify element "editAccount_UpdateButton" is disabled
    * Verify element "editAccount_UpdateButton" is not clickable