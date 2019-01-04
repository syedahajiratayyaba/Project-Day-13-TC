package com.ibm.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ibm.pages.UserPage;
import com.ibm.utilities.DBUtil;
import com.ibm.utilities.ExcelReader;
import com.ibm.utilities.ExcelUtil;
import com.ibm.utilities.PropertiesFileHandler;

public class BaseTest extends ExcelReader{
	WebDriverWait wait;
	WebDriver driver;	
	
    @Test()
    
    public void testcase13() throws InterruptedException, IOException, SQLException{
    	
    	FileInputStream file = new FileInputStream("./TestData/data.properties");
    	Properties prop = new Properties();
    	prop.load(file);
    	String url = prop.getProperty("url");
    	String username = prop.getProperty("user");
    	String password = prop.getProperty("password");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 60);
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Login login = new Login(driver, wait);
		driver.get(url);
		
		login.enterEmailAddress(username);
		login.enterPassword(password);
		login.clickOnLogin();
		
		WebElement systemEle=driver.findElement(By.linkText("System"));
		systemEle.click();
		
		WebElement settingsEle=driver.findElement(By.linkText("Settings"));
		settingsEle.click();

		Thread.sleep(3000);
		
		WebElement addressEle=driver.findElement(By.id("address"));
		addressEle.clear();
		addressEle.sendKeys("Change123");
		
		WebElement emailEle=driver.findElement(By.id("email"));
		emailEle.clear();
		emailEle.sendKeys("test@gmail.com");
		
		WebElement phoneNoEle=driver.findElement(By.id("phonenumber"));
		phoneNoEle.clear();
		phoneNoEle.sendKeys("1234567890");
		
		WebElement goToTopEle=driver.findElement(By.cssSelector("span.ti-arrow-up"));
		goToTopEle.click();
		Thread.sleep(3000);
		WebElement saveEle=driver.findElement(By.cssSelector("i.fa.fa-save"));
		saveEle.click();
		Thread.sleep(2000);
		//Assertion in the admin page
		String successMsg=login.AddWeightClassSuccMsg().trim();
		Assert.assertEquals(successMsg,"Success: You have successfully updated Store!"); 
		
		System.out.println("Updated address, Phone number and email is reflected in the Admin Page");

		
		WebElement logOutEle=driver.findElement(By.cssSelector("i.fa.fa-sign-out"));
		logOutEle.click();
		
		Thread.sleep(3000);
	
		driver.get("https://atozgroceries.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		UserPage user = new UserPage(driver, wait);
		
		WebElement loginEle=driver.findElement(By.linkText("Login"));
		loginEle.click();
		
		user.userName("7675058941");
		user.enterPassword("456789");
		user.clickOnLogin();
		Thread.sleep(3000);
		//Assertion in the user page
		Assert.assertTrue(driver.findElement(By.partialLinkText("test@gmail.com")).isDisplayed());
		System.out.println("Modified settings from admin Panel are reflected in User page");
		
		String exp="1234567890";
		String act= DBUtil.singleDataQuery("SELECT pnum from as_store");
		
		//validation of database
		Assert.assertEquals(act,exp);
		Reporter.log("Assertion on phone number change present in database");
		
		
    }

}