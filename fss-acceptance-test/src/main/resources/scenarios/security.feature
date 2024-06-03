@Security
Feature: Security

  Scenario Outline:
    Given user "<userName>" is logged in
    Then user can retrieve own policies
    And user can't retrieve policies of other users

    Examples:
      | userName  |
      | testUser1 |
      | testUser2 |
