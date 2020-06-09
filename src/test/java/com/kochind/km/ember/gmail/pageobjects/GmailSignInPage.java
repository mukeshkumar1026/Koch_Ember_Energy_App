package com.kochind.km.ember.gmail.pageobjects;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kochind.km.ember.gmail.utilities.Utilities;

public class GmailSignInPage {

	private WebDriver driver;
	private WebDriverWait wait;
	public By emailTextBox = By.id("identifierId");
	public By nextBtn = By.cssSelector("[class=\"RveJvd snByac\"]");
	public By passwordTextBox = By.cssSelector("[class=\"whsOnd zHQkBf\"]");
	public By loginBtn = By.cssSelector("[class=\"RveJvd snByac\"]");
	public By forgotPassword = By.cssSelector("#forgotPassword > span > span");
	public By submitForgotPassword = By.cssSelector("[class=\"RveJvd snByac\"]");
	public By error = By.cssSelector("[class=\"stUf5b qpSchb\"]");
	private By forgotEmailMessage = By.cssSelector("[id=\"Forgot email?\"]");

	public GmailSignInPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean verifySignInPageTitle() {
		return driver.getTitle().contains("Sign in – Google accounts");
	}

	public void signInToGmail(String userName, String password) {
		Utilities.sendText(driver, emailTextBox, userName);
		Utilities.click(driver, nextBtn);
		Utilities.sendText(driver, passwordTextBox, password);
		Utilities.click(driver, nextBtn);
	}

	public boolean verifyNoPasswodTextBox() {
		try {
			WebElement passwordTxtBox = driver.findElement(passwordTextBox);
		} catch (NoSuchElementException e) {
			return true;
		}
		return false;
	}

	public String errorMessage() {
		Utilities.waitForPageLoad(driver);
		return driver.findElement(error).getText();
	}

	public String emailMessage() {
		Utilities.waitForPageLoad(driver);
		return driver.findElement(forgotEmailMessage).getText();
	}

	public String getpasswordFeedBack() {
		// Utilities.waitForPageLoad(driver);
		return driver.findElement(error).getText();
	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public void clickOnSendEmail() {
		WebElement submitforgotPasswordBtn = driver.findElement(submitForgotPassword);
		if (submitforgotPasswordBtn.isDisplayed())
			submitforgotPasswordBtn.click();
	}

	public void changePasswordAtLogin() {
		driver.findElement(By.cssSelector("button#user-icon")).click();
		Utilities.waitForPageLoad(driver);
		WebElement element = driver.findElement(By.id("changePassword"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
}
