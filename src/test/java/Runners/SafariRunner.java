package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = { "src/test/resources/Features/safari_flight_booking.feature" },
        glue = {"StepsDefinitions" }
)
public class SafariRunner extends AbstractTestNGCucumberTests {
	
}
