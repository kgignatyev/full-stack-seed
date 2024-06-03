@test-jobs
Feature: Jobs management

  Scenario Outline: Create a new account
    Given user "<userName>" is logged in
    And current user has account "<accountName>"
    Then current user can create a new job "<jobName>"

    Examples:
      | userName  | accountName | jobName    |
      | testUser1 | Test1       | TestJob1-1 |
      | testUser2 | Test2       | TestJob1-2 |

  Scenario Outline: Update job
    Given user "<userName>" is logged in
    And current user can find "<jobName>"
    Then current user can update current job notes

    Examples:
      | userName  | jobName    |
      | testUser1 | TestJob1-1 |
      | testUser2 | TestJob1-2 |

  Scenario Outline: Record job history
    Given user "testUser1" is logged in
    And current user can find "TestJob1-1"
    Then current user can add event "<jobEventType>" happened on "<date>" with notes "<notes>"
    And check that job has status: "<jobStatus>"

    Examples:
      | jobEventType        | date       | notes                             | jobStatus           |
      | APPLIED             | 2024-05-01 | Applied for the job               | APPLIED             |
      | INTERVIEW_SCHEDULED | 2024-05-01 | initial call with recruiter       | INTERVIEWING        |
      | INTERVIEWED         | 2024-05-01 | Technical interview with John Dow | INTERVIEWING        |
      | REJECTED            | 2024-05-20 | Rejection note                    | REJECTED_BY_COMPANY |
