package com.automation.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.base.BaseSalesForce;
import com.automation.utility.Constants;
import com.automation.utility.PropertiesUtility;

@Listeners(com.automation.utility.SalesforceListenerUtility.class)

public class LeadsTest extends BaseSalesForce {

	protected String lastNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CREATELEADS_PROPERTIES,"lastNameData");
	protected String compNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CREATELEADS_PROPERTIES,"compNameData");

	@Test
	public void TC20_CheckLeadsTabLink() throws InterruptedException {
		
		loginToSalesForce();	
		clickTabLink ("Lead_Tab", "Leads Tab");		
	}
	
	@Test
	public void TC21_LeadsSelectViewValidate() throws InterruptedException {
			
		loginToSalesForce();
		
		clickTabLink ("Lead_Tab", "Leads Tab");
		
		String[] expectedOppList = {"All Open Leads" ,"My Unread Leads" ,"Recently Viewed Leads" ,"Today's Leads", "View - Custom 1", "View - Custom 2"};
		
		WebElement viewLeadsDropdown = driver.findElement(By.xpath("//*[@id='fcf']"));
		
		clickElement(viewLeadsDropdown, "View Leads Dropdown");
		
		List <WebElement> LeadsListOfItems = driver.findElements(By.xpath("//*[@id='fcf']/option"));
		
		verifyDropDownListValues(LeadsListOfItems, expectedOppList);
			
	}
	
	@Test	
	public void TC22_LeadsDefaultView() throws InterruptedException {
		
		loginToSalesForce();
	
		clickTabLink ("Lead_Tab", "Leads Tab");
		
		String valueToSelect ="My Unread Leads";
		
		WebElement viewDropdown = driver.findElement(By.xpath("//*[@id='fcf']"));		
		
		waitForVisibility(viewDropdown, 30, "Leads view dropdown");
		
		selectByVisibleText(viewDropdown, valueToSelect);
		
		clickUserMenuNavigation();
		
		logoutFromSalesForce();

		launchBrowser ("chrome");
		gotoUrl("https://login.salesforce.com");
		
		Thread.sleep(4000);
		
		loginToSalesForce();
		
		clickTabLink ("Lead_Tab", "Leads Tab");
		
		WebElement selectedViewDropdown = driver.findElement(By.xpath("//*[@id='fcf']"));
		
		Select select = new Select(selectedViewDropdown);
		
		String actualValue = select.getFirstSelectedOption().getText();
		
		mylog.info("Actual selected value is : "+actualValue);
		extentReportUtility.reportTestInfo("Actual selected value is : "+actualValue);
	
		Assert.assertEquals(actualValue, valueToSelect);	
	}
	
	@Test
	public void TC23_TodaysLeadsView() throws InterruptedException {
		
		loginToSalesForce();
	
		clickTabLink ("Lead_Tab", "Leads Tab");
		
		String valueToSelect ="Today's Leads";
		WebElement viewDropdown = driver.findElement(By.xpath("//*[@id='fcf']"));
		
		selectByVisibleText(viewDropdown, valueToSelect);
		
		WebElement goElement = driver.findElement(By.xpath("//input[@name='go' and @type='button']"));
		clickElement(goElement, "Go!");
		
		WebElement actualViewDropdown = driver.findElement(By.xpath("//select[starts-with(@id,'00Bak00000HJIiA')]"));
				
		Select select = new Select(actualViewDropdown);
		String actualValue = select.getFirstSelectedOption().getText();
		
		Assert.assertEquals(actualValue, valueToSelect);	
		
	}
	
	@Test
	public void TC24CreateNewLeads() throws InterruptedException {
		
		loginToSalesForce();
	
		clickTabLink ("Lead_Tab", "Leads Tab");
		
		WebElement newElement = driver.findElement(By.xpath("//input[@type='button' and @name='new']"));
		clickElement(newElement, "New");
				
		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_lastlea2']"));
		enterText(lastName, lastNameData, "Last Name");
		
		WebElement compName = driver.findElement(By.xpath("//input[@id='lea3']"));
		enterText(compName, compNameData, "Company Name");
		
		WebElement saveElement = driver.findElements(By.xpath("//input[@type='submit' and @name='save']")).get(0);
		clickElement(saveElement, "Save");
		
		WebElement topName = driver.findElement(By.xpath("//h2[@class='topName']"));		
		
		WebElement actualLastName = driver.findElement(By.id("lea2_ileinner"));
		
		WebElement actualCompName = driver.findElement(By.id("lea3_ilecell"));
		
		Assert.assertEquals(topName.getText(), lastNameData);	
		
		Assert.assertEquals(actualLastName.getText(), lastNameData);	
		
		Assert.assertEquals(actualCompName.getText(), compNameData);	
		
	}
}
