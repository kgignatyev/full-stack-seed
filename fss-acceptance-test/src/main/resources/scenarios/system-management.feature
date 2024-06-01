@test-system-management
Feature: System management

  Scenario Outline: Admin access
    Given user "admin"
    Then current user can search accounts
    And current user can impersonate normal user "<user>"
    Examples:
      | user      |
      | testUser1 |
      | testUser2 |

  Scenario Outline: application metrics are available only to authorized users
    Given user "<user>"
    Then current user has access application metrics "<has access>"
    Examples:
      | user       | has access |
      | testUser1  | no         |
      | testUser2  | no         |
      | admin      | yes        |
      | prometheus | yes        |
