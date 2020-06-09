package com.kochind.km.ember.gmail.test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kochind.km.ember.gmail.base.TestBaseSetup;
import com.kochind.km.ember.gmail.pageobjects.GmailHomePage;
import com.kochind.km.ember.gmail.pageobjects.GmailSignInPage;
import com.kochind.km.ember.gmail.pageobjects.SignInPage;
import com.kochind.km.ember.gmail.utilities.TestProperties;
import com.kochind.km.ember.gmail.utilities.Utilities;

public class GmailHomePageTest extends TestBaseSetup{
	private Properties testProperties;
	private static WebDriver driver;
	private String useremail;
	private String currentpassword;
	private WebDriverWait webDriverWait;
	private static String gmailBaseUrl;
	private GmailHomePage gmailPage;
	private Utilities utilities;
	private GmailSignInPage signInPage;
	@Before
	 public void setUp(){
		
//	initializeTestBaseSetupGmail();
	testProperties=loadTestProperties();
	driver=getDriver();
//	webDriverWait = new WebDriverWait(driver, 180);
	gmailBaseUrl = testProperties.getProperty(TestProperties.GmailBaseUrl.toString());
	useremail = testProperties.getProperty(TestProperties.Useremail1.toString());
	currentpassword = testProperties.getProperty(TestProperties.Currentpassword.toString());
//	driver.get(gmailBaseUrl);
//	webDriverWait = new WebDriverWait(driver, 30);
	signInPage = new GmailSignInPage(driver);
	gmailPage = new GmailHomePage(driver);
	utilities = new Utilities(driver);
	
	}
	
//	@After
	public void tearDown(){
		driver.quit();
		
	}
	
//	@Test
	//Verify the Gmail Home Page Launch
	
	public void verifyGmailHomePage() throws Exception{
		try{
			String composeMail = "Compose";
			signInPage.signInToGmail(useremail, currentpassword);
			gmailPage.selectComposeMail(composeMail);
			gmailPage.clickCompseMail();
			gmailPage.verifyComposeMailTitle();
			gmailPage.clickDropDownAndSignOut();
			
			
		}catch (Exception e){
			String userOutput = System.getProperty("user.dir") + "\\target\\screenshots\\SVR\\"
					+ new Exception().getStackTrace()[0].getMethodName() + ".png";
			File sceenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sceenshotFile, new File(userOutput));
			throw (e);
		}
	}
	
//	@Test
	
	public static void main(String args[]){// verifyLoginGmail(){
		driver.get("https:\\www.gmail.com\\");
		
	}
}