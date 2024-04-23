Feature: Create/Delete User to Bookstore

  @createUserAndDelete
  Scenario: Create User
    Given I create a new user for Book store
    And I verify user is Created

    When I delete the user without authorisation
    And I verify user is not authorised

    And I generate authorisation token for the user
    And I delete the user with authorisation
    Then I verify user is deleted
