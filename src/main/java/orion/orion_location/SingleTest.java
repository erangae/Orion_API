package orion.orion_location;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.json.simple.parser.ParseException;
import java.io.*;
import javax.json.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;

public class SingleTest {

	@Test
	public void singleLine() {
		given().get("https://maps.googleapis.com/maps/api/geocode/json").then().statusCode(400).log().all();
	}
	
	@Test
	public void formatted_HeaderValidations() {
		given().
			baseUri("https://maps.googleapis.com").
		when().
			get("/maps/api/geocode/json").
		then().
			statusCode(400).
			statusLine(containsString("Bad Request")).
			contentType("application/json").
			header("Cache-Control", "no-cache, must-revalidate");
	}
	
	@Test
	public void parametersAndBodyValidation() {
		given().
			baseUri("https://maps.googleapis.com").
			param("address", "NZ-1010").
		when().
			get("/maps/api/geocode/json").
		then().
			statusCode(200).
			body("status", equalTo("OK")).
			body("results[0].address_components[1].long_name", equalTo("Auckland"));
	}
	
	@Test
	public void post() {
		given().
			headers("Header","123").
			param("City", "Auckland").
			param("Country", "New Zealand").
		when().
			post("https://maps.googleapis.com/maps/api/geocode/json").
		then().
			statusCode(400).log().all().
			body("status", equalTo("OK")).
			body("results[0].address_components[1].long_name", equalTo("Auckland"));
	}
	
	@Test
	public void multipleValuesValidation() {
		given().
			baseUri("https://maps.googleapis.com").
			param("address", "NZ-1010").
		when().
			get("/maps/api/geocode/json").
		then().
			statusCode(200).
			body(
					"status", equalTo("OK"),
					"results[0].address_components[1].long_name", equalTo("Auckland"),
					"results[0].address_components.long_name", hasItems("1010","Auckland","New Zealand")
				);
	}
	
	@Test
	public void dataDriven() throws IOException {
		
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader("data.csv"));

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
	        	given().
	        		baseUri("https://maps.googleapis.com").
	    			param("address", data[0]).
	    		when().
	    			get("/maps/api/geocode/json").
	    		then().
	    			statusCode(200).
	    			body("status", equalTo("OK")).
	    			body("results[0].address_components[1].long_name", equalTo(data[1])).
	        		body("results[0].address_components[2].long_name", equalTo(data[2]));
            }
	}
	
	@Test
	public void assertions() {
		
		//Getting Response
		Response response = 
		given().
			baseUri("https://maps.googleapis.com").
			param("address", "NZ-1010").
		when().
			get("/maps/api/geocode/json");
		
		//Hard Assertion
		response.
		then().
			body("status", equalTo("OK")).
			body("results[0].address_components[1].long_name", equalTo("Auckland"));
		
		//Soft Assertion
				response.
				then().
					body("status", equalTo("OK"),"results[0].address_components[1].long_name", equalTo("Auckland"));
		
		//Path Assertions
				assertTrue("Status is incorrect", response.path("status").equals("OK"));
				assertTrue("long_name is incorrect", response.path("results[0].address_components[1].long_name").equals("Auckland"));
				
	}
	
	@Test
	public void assertFullJson() throws JsonParseException, JsonMappingException, IOException {
		
		//Getting Response
		Response response = 
		given().
			baseUri("https://maps.googleapis.com").
			param("address", "NZ-1010").
		when().
			get("/maps/api/geocode/json");
		
        String actualjson = response.asString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(actualjson, JsonNode.class);
        
        JsonReader reader =
        		   Json.createReader(new FileReader("result.json"));
        		   JsonObject obj = reader.readObject();

        		   assertTrue("Actual response json content is not same as expected json content",jsonNode.toString().equals(obj.toString()));
	}
}
