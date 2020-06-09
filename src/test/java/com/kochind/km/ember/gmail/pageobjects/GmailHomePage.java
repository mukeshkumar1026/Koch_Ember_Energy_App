package com.kochind.km.ember.gmail.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kochind.km.ember.gmail.utilities.Utilities;

public class GmailHomePage {

	private Utilities utilities;
	private WebDriver driver;
	private WebDriverWait wait;
	private By composeMail = By.cssSelector("[class=\"T-I J-J5-Ji T-I-KE L3\"]");
	private By composeTitle = By.xpath("//*[contains(text(),'Compose')]");
	private By profileMenu = By.cssSelector("[class=\"gb_Ia gbii\"]");
	private By profileMenuDropDown = By.cssSelector("[class=\"gb_6a gb_F gb_l gb_7a gb_na\"]");
	private By gmailSignOut = By.id("gb_71");

	public GmailHomePage(WebDriver driver) {
		this.driver = driver;
		utilities = new Utilities(driver);
	}

	public void clickCompseMail() {
		driver.findElement(composeMail).click();
	}

	public String verifyComposeMailTitle() {
		return driver.findElement(composeTitle).getText();
	}

	public void clickDropDownAndSignOut() {
		WebElement profileMenuClick = driver.findElement(profileMenu);
		utilities.sleep(2000);
		WebElement dropdown = driver.findElement(profileMenuDropDown);
		if (dropdown.isDisplayed())
			dropdown.click();
		WebElement signOut = driver.findElement(gmailSignOut);
		if (signOut.isDisplayed())
			signOut.clear();
	}
	
	public void selectComposeMail(String composeMail){
		List<WebElement> compose=driver.findElements(composeTitle);
		for(WebElement compose1:compose){
			if(compose1.getText().contains(composeMail)){
				compose1.click();
			}
		}
	}
}