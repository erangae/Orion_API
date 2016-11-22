Feature: Location Details From PostalCode

	Scenario: Retrieve Location Details of a PostalCode
		Given I have the 828721 PostalCode
		When I retrieve location details
		Then I should see Punggol city name
		And I should see Singapore country name
		
	Scenario Outline: Retrieve Location Details of a PostalCode
		Given I have the <postalCode> PostalCode
		When I retrieve location details
		Then I should see <city> city name
		And I should see <country> country name

  		Examples:
    		| postalCode | city 		  | country    |
    		|  149598    |  Queenstown    |  Singapore |
    		|  670125    |  Bukit Panjang |  Singapore |