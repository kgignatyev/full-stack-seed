@test-init
Feature: Acceptance tests data cleanup

  Scenario Outline: Delete Test data
    Given user "admin"
    Then we delete test account "<account>"
    Examples:
      | account |
      | Test1   |
      | Test2   |

  Scenario Outline: Delete Test users
    Given user "admin"
    Then we delete test user "<user name>"
    Examples:
      | user name |
      | testUser1 |
      | testUser2 |

