package orion.orion_location;

import static io.restassured.RestAssured.given;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class LocationStepDefinition {
	
	private RequestSpecification request;
	private Response response;
	String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";
	
	
	@Given("^I have the (.*) PostalCode$")
	public void i_have_the_PostalCode(String postalCode){
		request = given().param("address", postalCode);		
	}

	@When("^I retrieve location details$")
	public void i_retrieve_location_details(){
		response = request.when().get(baseURL);
		System.out.println("response: " + response.prettyPrint());
		response.then().statusCode(200);
		response.then().body("status", equalTo("OK"));
	}

	@Then("^I should see (.*) city name$")
	public void i_should_see_Punggol_city_name(String city) throws Throwable {
		response.then().body("results[0].address_components[1].long_name", equalTo(city));
	}

	@Then("^I should see (.*) country name$")
	public void i_should_see_Singapore_country_name(String country) throws Throwable {
		response.then().body("results[0].address_components[2].long_name", equalTo(country));
	}
}

