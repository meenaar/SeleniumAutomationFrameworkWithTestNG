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

public class OpportunityTests extends BaseSalesForce{

	protected String oppName =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"oppName");
	protected String accName =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"accName");
	protected String closeDate =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"closeDate");
	protected String stage =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"stage");
	protected String probability =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"probability");
	protected String pageTitle =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEOPPORTUNITY_PROPERTIES,"pageTitle");

	
	@Test
	public void TC15_VerifyOpportunitiesDropDown() throws InterruptedException {
		
		loginToSalesForce();
		
		String[] expectedOppList = {"All Opportunities", "Closing Next Month", "Closing This Month", "My Opportunities","New Last Week", "New This Week", "Opportunity Pipeline", "Private", "Recently Viewed Opportunities", "Won"};
		
		clickTabLink ("Opportunity_Tab", "Opportunity");
		
		WebElement opportunityDdElem = driver.findElement(By.xpath("//*[@id='fcf']"));
		
		clickElement(opportunityDdElem, "View Opportunity Dropdown");
		
		List<WebElement> oppMenuListEle = driver.findElements(By.xpath("//*[@id='fcf']/option"));
		
		verifyDropDownListValues (oppMenuListEle, expectedOppList);			
	}
	
	
	@Test
	public void TC16CreateNewOpty() throws InterruptedException {
		
		loginToSalesForce();
			
		WebElement opportunityEle = driver.findElement(By.xpath("//*[@id='Opportunity_Tab']/a"));
		clickElement(opportunityEle, "Opportunities");
		
		WebElement newElement = driver.findElement(By.xpath("//*[@name='new']"));
		clickElement(newElement, "New Opportunity");
		
		WebElement oppNameEle = driver.findElement(By.xpath("//*[@id='opp3']"));
		enterText(oppNameEle, oppName , "Opportunity Name");
		
		WebElement accNameEle = driver.findElement(By.xpath("//*[@id='opp4']"));
		enterText(accNameEle, accName , "Account Name");
		
		WebElement closeDateEle = driver.findElement(By.xpath("//*[@id='opp9']"));
		enterText(closeDateEle, closeDate, "Close Date");
		
		WebElement stageEle = driver.findElement(By.xpath("//*[@id='opp11']"));
		selectByValue(stageEle, stage);
		
		WebElement probEle = driver.findElement(By.xpath("//*[@id='opp12']"));
		enterText(probEle, probability, "Probability");
		
	//	WebElement primaryCampnSrc = driver.findElement(By.xpath("//*[@id='opp17']"));
		//enterText(primaryCampnSrc, "ttttt", "Primary Campaign Source");
		
		WebElement save = driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[@type='submit' and @name='save']"));
		clickElement(save, "Save Opportunity");
			
		WebElement oppDetailTitle = driver.findElement(By.xpath("//h2[text()='Opportunity Detail']"));
		Assert.assertEquals(oppDetailTitle.getText(), pageTitle);		
	
		WebElement actualOppName = driver.findElement(By.xpath("//*[@id='opp3_ileinner']"));
		Assert.assertEquals(actualOppName.getText(), oppName);				
		
		WebElement actualAccName = driver.findElement(By.xpath("//*[@id='opp4_ileinner']"));		
		Assert.assertEquals(actualAccName.getText(), accName);
		
		WebElement actualCloseDate = driver.findElement(By.xpath("//*[@id='opp9_ileinner']"));	
		Assert.assertEquals(actualCloseDate.getText(), closeDate);		
			
		WebElement actualStage = driver.findElement(By.xpath("//*[@id='opp11_ileinner']"));		
		Assert.assertEquals(actualStage.getText(), stage);		
		
		WebElement actualProb = driver.findElement(By.xpath("//*[@id='opp12_ileinner']"));		
		
		System.out.println("Expected : "+ probability);
		System.out.println("Actual : "+ actualProb.getText());
		
		Assert.assertEquals(actualProb.getText(), probability);		
	}
	
	@Test
	public void TC17TestOpportunityPipelineReport() throws InterruptedException {
		
		loginToSalesForce();
		String expectedHeader = "Opportunity Pipeline";
		
		clickTabLink ("Opportunity_Tab", "Opportunity");
		
		clickLinkUnderReports("Opportunity Pipeline", "Opportunity Pipeline link");
	
		String actualHeader = getPageHeaderText("Opportunity Pipeline");
				
		WebElement reportStatus = driver.findElement(By.xpath("//div[@id='status']"));
		mylog.info(reportStatus.getText());
		extentReportUtility.reportTestInfo(reportStatus.getText());
		
		//validation
		
		Assert.assertEquals(expectedHeader, actualHeader);
		
		Assert.assertEquals(reportStatus.getText(), "Complete");
		
	}
	
	@Test	
	public void TC18TestStuckOpportunitiesReport() throws InterruptedException {
		
		loginToSalesForce();
		String expectedHeader = "Stuck Opportunities";
		
		clickTabLink ("Opportunity_Tab", "Opportunity");
		
		clickLinkUnderReports("Stuck Opportunities", "Stuck Opportunities link");
		
		String actualHeader = getPageHeaderText("Stuck Opportunities");
		
		Assert.assertEquals(expectedHeader, actualHeader);
		
	}

	@Test	
	public void TC19TestQuarterlySummaryReport() throws InterruptedException {
	
		loginToSalesForce();
	
		String reportName = "Opportunity Report";
		String expInterval = "Current FQ";
		String expShow = "All opportunities";
		
		clickTabLink ("Opportunity_Tab", "Opportunity");
		
		WebElement intervalEle = driver.findElement(By.xpath("//*[@id='quarter_q']"));
		
		Select select = new Select(intervalEle);
		
		select.selectByVisibleText(expInterval);
		
		
		WebElement includeEle = driver.findElement(By.xpath("//*[@id='open']"));
		
		Select selectInclude = new Select(includeEle);
		
		selectInclude.selectByValue("all");
		
		WebElement runReportBtn = driver.findElement(By.xpath("//input[@value='Run Report']"));
		
		clickElement(runReportBtn, "Run Report");
		
		// Verify the Opportunity Report page header and entered search criteria fields
		WebElement reportPageHeader = driver.findElement(By.xpath("//h1[@class ='noSecondHeader pageType']"));
		
		mylog.info("Page header : "+ reportPageHeader.getText());
		
		Assert.assertEquals(reportPageHeader.getText(), reportName);
	
	
		WebElement range = driver.findElement(By.xpath("//select[@id='quarter_q']"));
	
		Select selectRange = new Select(range);
		
		mylog.info("Selected range   : "+selectRange.getFirstSelectedOption().getText());
		
		String actualRange = selectRange.getFirstSelectedOption().getText();
		
		Assert.assertEquals(actualRange, expInterval);
		
	
		WebElement show = driver.findElement(By.xpath("//select[@id='scope']"));
		
		Select selectShow = new Select(show);
		
		mylog.info("Selected show   : "+selectShow.getFirstSelectedOption().getText());
		
		String actualShow = selectShow.getFirstSelectedOption().getText();
		
		Assert.assertEquals(actualShow, expShow);
	}

}
