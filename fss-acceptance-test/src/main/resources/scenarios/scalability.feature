@scalability
Feature: jobs search should be fast

  @createJobs
  Scenario Outline: create many jobs
    Given user 'testUser1' is logged in
    When current user can create <numJobs> jobs in less than <creationTime> seconds


    Examples:
      | numJobs | creationTime |
      | 100     | 10           |
      | 300     | 20           |

  @searchJobs
  Scenario Outline: search jobs
    Given user 'testUser1' is logged in
    When current user searches with "<criteria>" it can see page <pageNum> with <pageSize> in less than <searchTime> milliseconds

    Examples:
      | criteria        | pageNum | pageSize | searchTime |
      | title like '%a%' | 1       | 20       | 50         |
      | title like '%a%' | 2       | 20       | 60         |
