Feature: Location Details From PostalCode

	Scenario: Retrieve Location Details of a PostalCode
		Given I have the 828721 PostalCode
		When I retrieve location details
		Then I should see Punggol city name
		And I should see Singapore country name