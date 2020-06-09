package com.kochind.km.ember.gmail.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class Utilities {

	private WebDriverWait webDriverWait;
	private Properties testProperties;
	private WebDriver driver;
	private String gmailBaseUrl;
	private String useremail;
	private String currentpassword;
	private String logout;

	public Utilities(WebDriver driver) {
		this.driver = driver;
	}

	public final static String TEST_PROPERTIES_LOCATION = String.format("resources%stest.properties",
			System.getProperty("file.separator", "/"));

	public void setUp() {
		testProperties = loadTestProperties();
		System.setProperty("webdriver.chrome.driver",
				testProperties.getProperty(TestProperties.ChromeDriverLocation.toString()));
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1600, 900));
		gmailBaseUrl = testProperties.getProperty(TestProperties.GmailBaseUrl.toString());
		useremail = testProperties.getProperty(TestProperties.Useremail1.toString());
		currentpassword = testProperties.getProperty(TestProperties.Currentpassword.toString());
		 if(testProperties.getProperty(TestProperties.WaitForZscaler.toString()).equalsIgnoreCase("TRUE")){
			 driver.get(gmailBaseUrl);
			 webDriverWait.until(ExpectedConditions.titleContains("Zscaler authentication in progress...."));
		 }
		 webDriverWait=new WebDriverWait(driver, 60);
	}

	// webDriverWait = new WebDriverWait(driver, 60);

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

	public static void sendText(WebDriver driver, By element, String text) {
		driver.findElement(element).clear();
		WebElement txtBox = driver.findElement(element);
		if (txtBox.isDisplayed())
			txtBox.sendKeys(text);

	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(String.format("Error during sleep"), e);
		}
	}

	public void waitForSignIn() {
		webDriverWait = new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("user-icon")));
	}

	public void waitForSignInPortal() {
		webDriverWait = new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("class=\"\"]")));

	}

	public static void waitForPageLoad(WebDriver driver) {
		waitForPageLoad(driver, 30, 1000L);
	}

	public static void waitForPageLoad(WebDriver driver, int timeout, long initialDelay) {
		try {
			Thread.sleep(initialDelay);
			new WebDriverWait(driver, timeout).until((WebDriver f) -> {
				return ((JavascriptExecutor) f).executeScript("return document.readyState").toString()
						.equals("complete");

			});
		} catch (Exception e) {
			Assert.fail("Timeout waiting for page to load");
		}
	}

	public void clickElementInDom(WebElement element, WebDriver webdriver) {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		js.executeScript("arguments[0].scrollIntoView(true, arg1);", element);
	}

	// logout
	public void testLogout() throws Exception {
		webDriverWait = new WebDriverWait(driver, 60);
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#user-icon")));
		waitForPageLoad(driver);
		driver.findElement(By.cssSelector("button#user-icon")).click();
		WebElement element = driver.findElement(By.id("logout"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("argument[0].click();", element);
		waitForPageLoad(driver);
		sleep(2000);
		driver.quit();
	}

	public static void click(WebDriver driver, By Button) {
		Utilities.waitForPageLoad(driver);
		WebElement btn = driver.findElement(Button);
		if (btn.isDisplayed())
			btn.click();
		Utilities.waitForPageLoad(driver);

	}
}
