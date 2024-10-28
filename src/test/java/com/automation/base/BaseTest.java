package com.automation.base;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.utility.ExtentReportsUtility;
import com.automation.utility.SalesforceListenerUtility;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver = null;
	private WebDriverWait wait = null;
	
	protected Logger mylog = LogManager.getLogger(BaseTest.class);
	protected ExtentReportsUtility extentReportUtility = ExtentReportsUtility.getInstance();
	
	
	public static void launchBrowser (String browserName) {		
	
		switch (browserName.toLowerCase()) {
		
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
			
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
			
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default: 
			break;
		}
	}
	
	public static void gotoUrl(String url) throws InterruptedException {
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);		
	}
	
	public static void closeDriver() {
		driver.close();
	}
	
	
	public void takeScreenshot(String filepath) {
		
		TakesScreenshot screenCapture=(TakesScreenshot)driver;
		File src= screenCapture.getScreenshotAs(OutputType.FILE);
		File destFile=new File(filepath);
		
		try {
			Files.copy(src, destFile);
			mylog.info("screen captured");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mylog.error("problem occured during screenshot taking");
		}
		
	}

	public void enterText (WebElement ele, String data, String objectName) {
		
		if (ele.isDisplayed()) {			
			ele.clear();
			ele.sendKeys(data);
			mylog.info("data is entered in the "+objectName);
			extentReportUtility.reportTestInfo("data is entered in the "+objectName);
		}
		else{
			mylog.error(objectName+" textbox is not diplayed");
			extentReportUtility.reportTestFailed(objectName+" textbox is not diplayed");
		}		
	}
	
	public void clickElement (WebElement ele, String objectName) {
		if (ele.isEnabled()) {			
		
			ele.click();
			mylog.info(objectName + " is clicked");
			extentReportUtility.reportTestInfo(objectName+ " is clicked");
		}
		else{
			mylog.error(objectName+" is not clicked");
			extentReportUtility.reportTestFailed(objectName+" is not clicked");
		}
		
	}
	public void selectElement(WebElement ele,String objectName) {
		if(!ele.isSelected()) {
			ele.click();
			mylog.info(objectName+" is selected");
			extentReportUtility.reportTestInfo(objectName+" is selected");
		}
		else{
			mylog.info(objectName+" is already selected");
			extentReportUtility.reportTestFailed(objectName+" is already selected");
		}
	}
	public void selectByValue (WebElement ele, String value) {
	
		Select select = new Select(ele);
		select.selectByValue(value);		
	}
	
	public void selectByVisibleText (WebElement ele, String value) {
		
		Select select = new Select(ele);
		select.selectByVisibleText(value);		
	}
	
	public void waitForVisibility(WebElement ele, long timeInSec, String ObjectName) {
		
		mylog.info("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
		
		extentReportUtility.reportTestInfo("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSec);
		
		wait.until(ExpectedConditions.visibilityOf(ele));
		
	}
	
	public void waitForAlertToPresent(long timeInSec, String ObjectName) {
		
		mylog.info("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
		
		extentReportUtility.reportTestInfo("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
	
		WebDriverWait wait = new WebDriverWait(driver, timeInSec);
		
		wait.until(ExpectedConditions.alertIsPresent());		
	}
	
	public void waitForElementToBeClickable(WebElement ele, long timeInSec, String ObjectName) {
		
		mylog.info("Waiting for "+ ObjectName+" to be clickable for maximum of "+ timeInSec+ "sec");
		
		extentReportUtility.reportTestInfo("Waiting for "+ ObjectName+" to be clickable for maximum of "+ timeInSec+ "sec");
		
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSec);
		
		wait.until(ExpectedConditions.elementToBeClickable(ele));		
	}
	
	public void waitForViTextToBePresentInElement(WebElement ele, long timeInSec, String text, String ObjectName) {
		
		mylog.info("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
		
		extentReportUtility.reportTestInfo("Waiting for visibility of "+ ObjectName+" for maximum of "+ timeInSec+ "sec");
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSec);
		
		wait.until(ExpectedConditions.textToBePresentInElement(ele, text));		
	}
	
	
	
}
