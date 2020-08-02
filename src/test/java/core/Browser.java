package core;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Level;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class Browser {
	
	final static Logger log = Logger.getLogger(Browser.class);
	
	WebDriver driver = null;
	String browserName = null;
	int timeout = 60;
	String browserLabel = null;
	
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.properties");
	}
	
	public WebDriver initBrowser(String browser) {
		log.setLevel(Level.DEBUG);
		setBrowserLabel(browser);
		log.info(getBrowserLabel() + "Browser request for : " + browser);
		if(browser.equalsIgnoreCase("chrome")) {
			log.info(getBrowserLabel() + "Initializing browser : " + browser);
			WebDriverManager.chromedriver().setup();
			ChromeOptions option = new ChromeOptions();
		    option.addArguments("--test-type");
		    option.addArguments("--disable-popup-bloacking");
		    option.addArguments("--disable-notifications");
		    DesiredCapabilities chrome = DesiredCapabilities.chrome();
		    chrome.setJavascriptEnabled(true);
		    option.setCapability(ChromeOptions.CAPABILITY, option);
		    driver = new ChromeDriver(option);
		    driver.manage().window().maximize();
		    log.info(getBrowserLabel() + "Maximizing the browser window");
		}
		else if(browser.equalsIgnoreCase("safari")) {
			log.info(getBrowserLabel() + "Initializing browser : " + browser);
			DriverManagerType safari = DriverManagerType.SAFARI;
			WebDriverManager.getInstance(safari).setup();
			try {
				Class<?> safariClass = Class.forName(safari.browserClass());
				driver = (WebDriver) safariClass.getDeclaredConstructor().newInstance();
				driver.manage().window().maximize();
				log.info(getBrowserLabel() + "Browser window maximized");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				
				e.printStackTrace();
			} catch (SecurityException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return driver;
			
	}
	
	public void freezForSeconds(int seconds) {
		try {
			for(int i=seconds;i>=1;i--) {
				Thread.sleep(1000);
				log.info(getBrowserLabel() + "Resuming execution in : " +i+ " seconds");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setBrowserLabel(String browserLabel) {
		this.browserLabel=browserLabel;
	}
	
	public String getBrowserLabel() {
		return "[" + browserLabel + "] : ";
	}
	
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
		log.info(getBrowserLabel() + "Browser name is set as : "+ browserName);
	}
	
	public String getBrowserName() {
		log.info(getBrowserLabel() + "Returning browser name as : "  + browserName);
		return browserName;
	}
	
	public WebDriver getDriver() {
		log.info(getBrowserLabel() + "Returning webdriver");
		return driver;
	}
	
	public WebElement waitForWebElementToBeClickable(int timeout, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		log.info(getBrowserLabel() + "Waiting for element : "  + String.valueOf(by));
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public WebElement waitForWebElementToBeClickable(By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		log.info(getBrowserLabel() + "Waiting for element : "  + String.valueOf(by));
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public void waitAndEnterTextOnWebElement(int timeout, By by, String text) {
		log.info(getBrowserLabel() + "Entering text as : "  + text);
		waitForWebElementToBeClickable(timeout, by).sendKeys(text);
	}
	
	public void waitAndEnterTextOnWebElement(By by, String text) {
		log.info(getBrowserLabel() + "Entering text as : "  + text);
		waitForWebElementToBeClickable(timeout, by).sendKeys(text);
	}
	
	public void enterTextWithKeyEvents(By by, Keys key) {
		log.info(getBrowserLabel() + "Entering key events");
		driver.findElement(by).sendKeys(key);
	}
	
	public void clearAndEnterText(By by, String text) {
		log.info(getBrowserLabel() + "Clearing text field : "  + String.valueOf(by));
		driver.findElement(by).clear();
		log.info(getBrowserLabel() + "Entering text as : "  + text);
		driver.findElement(by).sendKeys(text);
	}
	
	public void clickOnElement(By by) {
		waitForWebElementToBeClickable(by).click();
		log.info(getBrowserLabel() + "Clicked on web element : "  + String.valueOf(by));
	}
	
	public String getTextFromWebElement(By by) {
		log.info(getBrowserLabel() + "Get text from : " + String.valueOf(by) + " as : " + waitForWebElementToBeClickable(by).getText());
		return waitForWebElementToBeClickable(by).getText();
	}

	public String getTextFromWebElementWithAttribute(By by, String attribute) {
		log.info(getBrowserLabel() + "Get text from : " + String.valueOf(by) + " with attribute "+attribute+" as : " + waitForWebElementToBeClickable(by).getAttribute(attribute));
		return waitForWebElementToBeClickable(by).getAttribute(attribute);
	}

}
