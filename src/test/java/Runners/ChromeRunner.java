package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = { "src/test/resources/Features/chrome_flight_booking.feature" },
        glue = {"StepsDefinitions" }
)
public class ChromeRunner extends AbstractTestNGCucumberTests {
	
}
