Feature: Rest Assured API Automation

@createUserPOST
Scenario : Create User
  Given I create a new user for Book store
  Then I verify user is Created