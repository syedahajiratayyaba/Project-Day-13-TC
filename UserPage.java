package com.ibm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.test.BaseTest;

public class UserPage extends BaseTest{
	
		@FindBy(xpath="//*[@id=\"pnum2\"]")
		WebElement userEle;

		@FindBy(id="pword2")
		WebElement passEle;
		
		@FindBy(id="mem_login")
		WebElement loginEle;
		WebDriverWait wait;
		WebDriver driver;
		
		public UserPage(WebDriver driver,WebDriverWait wait) {
			PageFactory.initElements(driver, this);
			this.driver=driver;
			this.wait=wait;
		}

		public void userName(String user1)
		{
			userEle.sendKeys(user1);
		}
		public void enterPassword(String password1)
		{
			passEle.sendKeys(password1);
		}
		public void clickOnLogin()
		{
			loginEle.click();
		}
		
	/*	@FindBy(xpath="//*cvdcg")
		WebElement tabNotPresentEle;

		public boolean getTabNotPresent()
		{
		boolean deletedTab=tabNotPresentEle.isDisplayed();
		return deletedTab;
		}*/

}
