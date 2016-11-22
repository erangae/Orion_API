package orion.orion_location;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin={"pretty", "html:target/html/"},
		features="src/test")

public class TestRunner {

}

