#the first annotation (@web) define type execution to call in class runner
#the second annotation (@google) define type data to read in test
@web @google
Feature: Google Searches
  Feature to test automation structure

#the third annotation (@GGL_00001 - example) define id of scenario in execution
  @GGL_00001
  Scenario: Search for google
    Given I navigate to google
    When I search for the word
    Then I validate the outcome
    
  @GGL_00002
  Scenario: Search for google
    Given I navigate to google
    When I search for the word
    Then I validate the outcome