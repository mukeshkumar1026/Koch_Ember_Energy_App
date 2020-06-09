package com.kochind.km.ember.gmail.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.MalformedInputException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kochind.km.ember.gmail.pageobjects.BasePage;
import com.kochind.km.ember.gmail.utilities.TestProperties;

public class TestBaseSetup {

	private static Properties testProperties;
	private static BasePage homePage;
	private static WebDriver driver;
	private static WebDriver driverRemote;
	private static String baseUrl;
	private static String gmailBaseUrl;

	public final static String TEST_PROPERTIES_LOCATION = String.format("resources%stest.properties",
			System.getProperty("file.separator", "/"));

	public WebDriver getDriver() {
		return driver;
	}

	public static WebDriver getRemoteDriver() {
		return driverRemote;
	}

	// public WebDriver getDriver(){
	//
	// }
	//
	private static void setDriver(String browserType, String appURL) throws MalformedInputException {

		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType + "is invalid, Launching Firefox as browser of choice..");
			WebDriver driver = initFirefoxDriver(appURL);
		}
	}

	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver",
				testProperties.getProperty(TestProperties.ChromeDriverLocation.toString()));
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		WebDriver driver = new ChromeDriver(dc);
		driver.manage().window().setSize(new Dimension(1600, 900));
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1600, 900));
		driver.navigate().to(appURL);
		return driver;

	}

	public static Properties loadTestProperties() {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(TEST_PROPERTIES_LOCATION);
			Properties retVal = new Properties();
			retVal.load(inputStream);
			return retVal;
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Could not find prperties file at \"%s\"", TEST_PROPERTIES_LOCATION));
		} finally {
			cleanUpInputStream(inputStream);
		}
	}

	public static void cleanUpInputStream(InputStream input) {
		try {
			input.close();
		} catch (Exception e) {
		}
	}

	public void initializeTestBaseSetupGmail() {
		try {
			testProperties = loadTestProperties();
			gmailBaseUrl = testProperties.getProperty(TestProperties.GmailBaseUrl.toString());
			setDriver("chrome", gmailBaseUrl);
		} catch (Exception e) {
			System.out.println("Launch Browser");
//			System.out.println("Error........." + e.getStackTrace());
		}
	}
}