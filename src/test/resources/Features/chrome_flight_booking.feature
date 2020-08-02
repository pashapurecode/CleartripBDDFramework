# @author: Praveen Ashapure
Feature: Book a flight

	@Smoke
  Scenario Outline: Book a fastest and cheapest flight travel
    Given user opens the flight booking page on "<web>"
    When user searches for a flight with <source> and <destination>
    And user gets the list of available flights
    Then user selects the best fastest and cheapest flight from the list

    Examples: 
      |web		|source                                                       | destination                                                        |
      |chrome	|"Hyderabad=Hyderabad, IN - Rajiv Gandhi International (HYD)" | "Delhi=New Delhi, IN - Indira Gandhi Airport (DEL)"                |
