package com.kochind.km.ember.gmail.test;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.*; 
import java.io.*; 
import java.util.*;
import java.lang.StackTraceElement;

import com.kochind.km.ember.gmail.base.TestBaseSetup;
import com.kochind.km.ember.gmail.pageobjects.GmailSignInPage;
import com.kochind.km.ember.gmail.pageobjects.SignInPage;
import com.kochind.km.ember.gmail.utilities.TestProperties;
import com.kochind.km.ember.gmail.utilities.Utilities;

public class LoginTest extends TestBaseSetup {
	private static Properties testProperties;
	private WebDriver driver;
	private static String useremail;
	private static String currentpassword;
	private WebDriverWait webDriverWait;
	private GmailSignInPage signInPage;
	private static String gmailBaseUrl;
	private Utilities utilities;

	@Before

	public void setUp() {

		testProperties = Utilities.loadTestProperties();
		initializeTestBaseSetupGmail();
		driver=getDriver();
		webDriverWait = new WebDriverWait(driver, 60);
		gmailBaseUrl = testProperties.getProperty(TestProperties.GmailBaseUrl.toString());
		useremail = testProperties.getProperty(TestProperties.Useremail1.toString());
		currentpassword = testProperties.getProperty(TestProperties.Currentpassword.toString());
		driver.get(gmailBaseUrl);
		webDriverWait.until(ExpectedConditions.titleContains("Sign in – Google accounts"));
		webDriverWait=new WebDriverWait(driver, 30);
		signInPage = new GmailSignInPage(driver);
		utilities = new Utilities(driver);
		
	}

//	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	// verify login successful with valid Email and valid password
	public void verifyLogIn() throws Exception {
		try {
			signInPage.signInToGmail(useremail, currentpassword);
			utilities.waitForSignIn();
			Assert.assertTrue(signInPage.verifySignInPageTitle());
		} catch (Exception e) {
			String userOutput = System.getProperty("user.dir") + "\\target\\screenshots\\SVR\\"
					+ new Exception().getStackTrace()[0].getMethodName() + ".png";
			File sceenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sceenshotFile, new File(userOutput));
			throw (e);
		} finally {
			driver.quit();

		}
	}
}
