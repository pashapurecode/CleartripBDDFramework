package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import core.Browser;

public class FlightSearchResultsPage {
	
	final static Logger log = Logger.getLogger(FlightSearchResultsPage.class);

	WebDriver driver = null;
	Browser browser = null;
	
	final static By CHEAPEST_FARE_DIV_XPATH = By.xpath("//a[@class='tabsRow__link show   tabsRow__link--cheapest ']");
	final static By DURATION_COLUMN_LINK_BY_XPATH = By.xpath("//a[text()='Duration']");
	final static By LOWEST_DURATION_BY_XPATH = By.xpath("(//th[@class='duration'])[1]");
	final static By LOWEST_SUGGESTED_FARE_BY_XPATH = By.xpath("(//th[@id='BaggageBundlingTemplate']/span)[1]");
	final static By EVENING_FILTER_LEFT_BY_XPATH = By.xpath("//label[contains(text(),'Evening')]");
	final static By BOOK_FLIGHT_BUTTON_BY_XPATH = By.xpath("(//table[@class='resultUnit flightDetailsLink ']/tbody/tr/td//button[@class='booking '])[1]");
	final static String PRICE_ATTRIBUTE = "data-pr";
	
	
	public FlightSearchResultsPage(Browser browser) {
		this.browser = browser;
		this.driver = this.browser.getDriver();
		log.info(browser.getBrowserLabel() + "On available flights results page");
	}
	
	public void clickOnDuration() {
		try {
			log.info(browser.getBrowserLabel() + "Waiting for cheapest flight rates table list to be displayed");
			browser.waitForWebElementToBeClickable(60, CHEAPEST_FARE_DIV_XPATH);
			log.info(browser.getBrowserLabel() + "Waiting for duration link to be clickable");
			browser.waitForWebElementToBeClickable(DURATION_COLUMN_LINK_BY_XPATH);
			browser.freezForSeconds(5);
		}
		catch(org.openqa.selenium.TimeoutException tex) {
			log.info(browser.getBrowserName() + "==================== ERROR =========================");
			log.info(browser.getBrowserName() + " : Site is loading very slow, try again the execution");
			log.info(browser.getBrowserName() + "==================== ERROR =========================");
			Assert.fail(browser.getBrowserName() + " : Site is loading very slow, try again the execution");
		}
	}
	
	public void selectBestFareFlight() {
		String result_duration = browser.getTextFromWebElement(LOWEST_DURATION_BY_XPATH);
		int result_fare = Integer.valueOf(browser.getTextFromWebElementWithAttribute(LOWEST_SUGGESTED_FARE_BY_XPATH, PRICE_ATTRIBUTE));
		browser.clickOnElement(EVENING_FILTER_LEFT_BY_XPATH);
		String evening_duration = browser.getTextFromWebElement(LOWEST_DURATION_BY_XPATH);
		int evening_fare = Integer.valueOf(browser.getTextFromWebElementWithAttribute(LOWEST_SUGGESTED_FARE_BY_XPATH, PRICE_ATTRIBUTE));
		
		if(!isEveningFlightHasCheapestFareWithShortDuration(result_fare, evening_fare, totalTimeInMinutes(result_duration), totalTimeInMinutes(evening_duration))) {
			browser.clickOnElement(EVENING_FILTER_LEFT_BY_XPATH);
			log.info(browser.getBrowserName() + "----------- Clicked On Booking : Lowest Fare Suggested Flight Details -------------");
			log.info(browser.getBrowserName() + "Flight Fare: " + result_fare + " Rs.");
			log.info(browser.getBrowserName() + "Flight Duratiion: " + totalTimeInMinutes(result_duration) + " minutes");
		}
		else {
			log.info(browser.getBrowserName() + "----------- Clicked On Booking : Evening Flight Details -------------");
			log.info(browser.getBrowserName() + "Flight Fare: " + evening_fare + " Rs.");
			log.info(browser.getBrowserName() + "Flight Duratiion: " + totalTimeInMinutes(evening_duration) + " minutes");
		}
		browser.clickOnElement(BOOK_FLIGHT_BUTTON_BY_XPATH);
		browser.freezForSeconds(15);
	}
	

	
	public boolean isEveningFlightHasCheapestFareWithShortDuration(int result_fare, int eve_fare, int result_dur, int eve_dur) {
		log.info(browser.getBrowserName() + "----------- Lowest Fare Suggested Flight Details -------------");
		log.info(browser.getBrowserName() + "Flight Fare: " + result_fare + " Rs.");
		log.info(browser.getBrowserName() + "Flight Duratiion: " + result_dur + " minutes");
		log.info(browser.getBrowserName() + "----------- Evening Flight Details -------------");
		log.info(browser.getBrowserName() + "Flight Fare: " + eve_fare + " Rs.");
		log.info(browser.getBrowserName() + "Flight Duratiion: " + eve_dur + " minutes");
		
		
		if(result_fare == eve_fare) {
			log.info(browser.getBrowserName() + "Fares are equal: " + result_fare + " = " + eve_fare);
			if(result_dur < eve_dur) {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking less time : " + result_dur);
				log.info(browser.getBrowserName() + "Evening flight is taking more time : " + eve_dur + " minutes");
				return false;
			}
			if(result_dur == eve_dur) {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking equal time : " + result_dur);
				return true;
			}
			else {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking less time : " + result_dur + " minutes");
				log.info(browser.getBrowserName() + "Evening flight is taking more time : " + eve_dur + " minutes");
				return false;
			}
		}
		else if(result_fare > eve_fare) {
			log.info(browser.getBrowserName() + "Evening flight has low fare then lowest suggested flight: " + eve_fare + " < " + result_fare);
			if(result_dur < eve_dur) {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking less time : " + result_dur);
				log.info(browser.getBrowserName() + "Evening flight is taking more time : " + eve_dur + " minutes");
			}
			if(result_dur == eve_dur) {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking equal time : " + result_dur);
			}
			else {
				log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking less time : " + result_dur + " minutes");
				log.info(browser.getBrowserName() + "Evening flight is taking more time : " + eve_dur + " minutes");
			}
			return true;
		}
		else {
			log.info(browser.getBrowserName() + "Evening flight has high fare then lowest fare suggested flight : " + eve_fare + " Rs. > " + result_fare + " Rs.");
			log.info(browser.getBrowserName() + "Lowest fare suggested flight is taking less time : " + result_dur + " minutes");
			log.info(browser.getBrowserName() + "Evening flight is taking more time : " + eve_dur + " minutes");
			return false;
		}
		
	}
	
	public int totalTimeInMinutes(String timeStr) {
		String duration = timeStr.trim();
		int totalTime = 0;
		if(duration.endsWith("m")) {
			String[] time = duration.split(" ");
			time[0] = time[0].replace("h", "");
			time[1] = time[1].replace("m", "");
			totalTime = Integer.valueOf(time[0])*60 + Integer.valueOf(time[1]);
		}
		else {
			String time = duration.replace("h", "");
			totalTime = Integer.valueOf(time)*60;
		}
		
		return totalTime;
	}

}
