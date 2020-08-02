package StepsDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.Browser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FlightSearchPage;
import pages.FlightSearchResultsPage;

public class BookFlightSteps {

	WebDriver driver = null;
	WebDriverWait wait = null;
	Browser browser = null;
	FlightSearchPage flightSearchPage = null;
	FlightSearchResultsPage flightSearchResultsPage = null;
	
	public BookFlightSteps() {
		browser = new Browser();
	}
	
	@Given("user opens the flight booking page on {string}")
	public void user_opens_the_flight_booking_page_on(String web_browser) {
		driver = browser.initBrowser(web_browser);
		browser.setBrowserName(web_browser);
		wait = new WebDriverWait(driver, 60);
		driver.get("https://www.cleartrip.com/");
	}
	
	@When("user searches for a flight with {string} and {string}")
	public void user_searches_for_a_flight_with_and(String source, String destination) {
		String[] sources = source.split("=");
		String[] dest = destination.split("=");
		flightSearchPage = new FlightSearchPage(browser);
		flightSearchPage.enterFrom(sources[0], sources[1]);
		flightSearchPage.enterDestination(dest[0], dest[1]);
		flightSearchPage.enterDate("Sat, 31 Oct, 2020");
	}

	@When("user gets the list of available flights")
	public void user_gets_the_list_of_available_flights() {
		flightSearchResultsPage = flightSearchPage.clickOnSearchButton();
		flightSearchResultsPage.clickOnDuration();
	}

	@Then("user selects the best fastest and cheapest flight from the list")
	public void user_selects_the_best_fastest_and_cheapest_flight_from_the_list() {
		flightSearchResultsPage.selectBestFareFlight();
		driver.quit();
	}

}
