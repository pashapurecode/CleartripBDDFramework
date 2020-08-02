package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import core.Browser;

public class FlightSearchPage {
	
	final static Logger log = Logger.getLogger(FlightSearchPage.class);

	WebDriver driver = null;
	Browser browser = null;
	
	final static By FROM_BY_ID = By.id("FromTag");
	final static By TO_BY_ID = By.id("ToTag");
	final static By DATE_PICKER_BY_ID = By.id("ui-datepicker-div");
	final static By DEPARTURE_DATE_BY_ID = By.id("DepartDate");
	final static By SEARCH_BUTTON_BY_ID = By.id("SearchBtn");
	
	
	public FlightSearchPage(Browser browser) {
		this.browser = browser;
		this.driver = this.browser.getDriver();
		log.info(browser.getBrowserLabel() + "On search flight page");
	}
	
	public void enterFrom(String from, String suggestion) {
		log.info(browser.getBrowserLabel() + "Entering FROM city name");
		browser.waitAndEnterTextOnWebElement(FROM_BY_ID, from);
		log.info(browser.getBrowserLabel() + "Waiting for the FROM city airport names suggestions");
		browser.waitForWebElementToBeClickable(By.xpath("//a[text()='"+suggestion+"']"));
		log.info(browser.getBrowserLabel() + "Selected FROM airport as : " + suggestion);
		browser.enterTextWithKeyEvents(FROM_BY_ID, Keys.ENTER);
	}
	
	public void enterDestination(String to, String suggestion) {
		log.info(browser.getBrowserLabel() + "Entering TO city name");
		browser.waitAndEnterTextOnWebElement(TO_BY_ID, to);
		log.info(browser.getBrowserLabel() + "Waiting for the TO city airport names suggestions");
		browser.waitForWebElementToBeClickable(By.xpath("//a[text()='"+suggestion+"']"));
		log.info(browser.getBrowserLabel() + "Selected TO airport as : " + suggestion);
		browser.enterTextWithKeyEvents(TO_BY_ID, Keys.ENTER);
	}
	
	public void enterDate(String date) {
		log.info(browser.getBrowserLabel() + "Waiting for the date picker");
		browser.waitForWebElementToBeClickable(5, DATE_PICKER_BY_ID);
		browser.enterTextWithKeyEvents(DEPARTURE_DATE_BY_ID, Keys.ENTER);
		browser.clearAndEnterText(DEPARTURE_DATE_BY_ID, date);
		log.info(browser.getBrowserLabel() + "Cleared date field and enetered date of journey as : " + date);
		browser.enterTextWithKeyEvents(DEPARTURE_DATE_BY_ID, Keys.ESCAPE);
		log.info(browser.getBrowserLabel() + "Pressed escape key in case if, date popup is still opened");
	}
	
	public FlightSearchResultsPage clickOnSearchButton() {
		browser.clickOnElement(SEARCH_BUTTON_BY_ID);
		log.info(browser.getBrowserLabel() + "Searching for the available flights lists");
		return new FlightSearchResultsPage(browser);
	}
	
}
