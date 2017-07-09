Feature: Location Details From PostalCode

	@Single
	Scenario: Retrieve Location Details of a PostalCode
		Given I have the NZ-1010 PostalCode
		When I retrieve location details
		Then I should see Auckland city name
		And I should see New Zealand country name
		
	@Loop
	Scenario Outline: Retrieve Location Details of a PostalCode
		Given I have the <postalCode> PostalCode
		When I retrieve location details
		Then I should see <city> city name
		And I should see <country> country name

  		Examples:
    		| postalCode | city 		  | country      |
    		|  NZ-1010   |  Auckland      |  New Zealand |
    		|  670125    |  Bukit Panjang |  Singapore   |