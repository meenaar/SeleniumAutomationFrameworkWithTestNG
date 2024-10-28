package com.automation.base;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.utility.PropertiesUtility;

import example.LogTests;

import com.automation.utility.Constants;

public class BaseSalesForce extends BaseTest {
	
	Alert alert;
	protected String username = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,
			"username");
	protected String password = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES,
			"password");

	protected Logger mylog;

	@BeforeClass
	public void setUp() {
		mylog = LogManager.getLogger(LogTests.class);
	}

	@BeforeMethod
	@Parameters("browsername")
	public void setUpBeforeMethod(@Optional("chrome") String name) throws InterruptedException {

		mylog.info("*******************setUpBeforeMethod*********************");
		launchBrowser(name);
		String url = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "url");
		gotoUrl(url);
	}

	@AfterMethod
	public void tearDownAfterMethod() {

		// closeDriver();
		mylog.info("*******************tearDownAfterTestMethod*********************");

	}

	public void loginToSalesForce() throws InterruptedException {

		WebElement usernameEle = driver.findElement(By.id("username"));
		enterText(usernameEle, username, "username");

		WebElement pwdElement = driver.findElement(By.id("password"));
		enterText(pwdElement, password, "password");

		WebElement loginElement = driver.findElement(By.id("Login"));
		loginElement.click();

		Thread.sleep(4000);
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	public void clickUserMenuNavigation() {

		WebElement userMenuEle = driver.findElement(By.xpath("//*[@id=\"userNav\"]"));

		waitForVisibility(userMenuEle, 30, "User Menu dropdown");

		clickElement(userMenuEle, "User Menu dropdown");
	}

	public void logoutFromSalesForce() {

		WebElement logoutLink = driver.findElement(By.xpath("//*[@id='userNav-menuItems']/a[text()='Logout']"));

		clickElement(logoutLink, "Logout ");
	}

	public void clickTabLink(String tabName, String objectName) {

		WebElement tabLinkEle = driver.findElement(By.xpath("//*[@id='" + tabName + "']/a"));

		waitForVisibility(tabLinkEle, 30, objectName);

		clickElement(tabLinkEle, objectName);

	}

	public void clickUserMenu() {

		WebElement userMenu = driver.findElement(By.xpath("//*[@id=\"userNav\"]"));
		clickElement(userMenu, "User Menu dropdown");

	}

	public void clickLinkUnderReports(String linkName, String objectName) {

		WebElement oppLinkEle = driver.findElement(By.xpath("//a[text()='" + linkName + "']"));
		clickElement(oppLinkEle, objectName);
	}

	public String getPageHeaderText(String headerText) {

		WebElement pageHeaderEle = driver.findElement(By.xpath("//h1[text()='" + headerText + "']"));

		waitForVisibility(pageHeaderEle, 30, headerText);

		String actualHeader = pageHeaderEle.getText();

		return actualHeader;
	}

	public void findAndClickCreateNewViewLink() {

		WebElement createNewViewEle = driver.findElement(By.xpath("//a[text()='Create New View']"));
		clickElement(createNewViewEle, "Create New View link ");

	}

	public void findAndClickSaveButton() {

		WebElement saveElement = driver.findElements(By.xpath("//input[@type='submit' and @name='save']")).get(0);
		clickElement(saveElement, "Save");
	}

	public void verifyDropDownListValues(List<WebElement> ele, String[] expectedList) {

		int matchCount = 0;
		if (ele.size() == expectedList.length) {
			for (WebElement options : ele) {

				mylog.info("dropdown values :" + options.getText());
				extentReportUtility.reportTestInfo("dropdown values :" + options.getText());

				for (int i = 0; i < expectedList.length; i++) {
					if (expectedList[i].equals(options.getText())) {
						matchCount += 1;
					}
				}
			}
		}
		Assert.assertEquals(matchCount, expectedList.length);
	}

	public Alert switchToAlert() {

		waitForAlertToPresent(30, "error alert box");

		Alert alert = driver.switchTo().alert();
		mylog.info("switched to an alert");
		
		return alert;
	}

	public String getAlertText(Alert alert, String objectname) {

		mylog.info("extracting text in the " + objectname + " alert");
		String text = alert.getText();
		mylog.info("text is extracted from alert box is ==" + text);
		return text;
	}

	public void acceptAlert(Alert alert) {

		alert.accept();
		mylog.info("Alert accepted");
	}

	public void cancelAlert(Alert alert) {

		alert.dismiss();
		mylog.info("Alert cancelled");
	}
	
	public String splitNames(String expName) {
		
		String[] nameArr = expName.split("\\s+");   
	    return nameArr[1];
	}
}
