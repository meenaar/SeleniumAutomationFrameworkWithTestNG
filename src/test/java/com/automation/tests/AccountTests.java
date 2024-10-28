package com.automation.tests;

import java.util.List;

import org.openqa.selenium.Alert;
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

public class AccountTests extends BaseSalesForce{

	protected String Account_Name =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEACCOUNT_PROPERTIES,"Account_Name");
	protected String Type =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEACCOUNT_PROPERTIES,"Type");
	protected String Customer_Priority =PropertiesUtility.readDataFromPropertyFile(Constants.CREATEACCOUNT_PROPERTIES,"Customer_Priority");


	@Test
	public void TC10_CreateAccount() throws InterruptedException {
		
		loginToSalesForce();
		
		clickTabLink ("Account_Tab", "Accounts Tab");
			
		WebElement newElement = driver.findElement(By.name("new"));
		clickElement(newElement, "New Button");
		
		WebElement accNameEle = driver.findElement(By.id("acc2"));
		enterText(accNameEle, Account_Name, "Accounts Name text ");

		WebElement typeDropDownEle = driver.findElement(By.xpath("//*[@id=\"acc6\"]"));
		selectByValue (typeDropDownEle, Type);
					
		WebElement customerPriorityEle = driver.findElement(By.xpath("//*[@id=\"00Nak000005IRi2\"]"));
		selectByValue (customerPriorityEle, Customer_Priority);
	
		//Click Save to save new account details
		WebElement saveElement = driver.findElement(By.name("save"));
		
		waitForElementToBeClickable(saveElement, 30, "Save Button");
		
		clickElement(saveElement, "Save Button");
		
		//Thread.sleep(3000);
		
		//verification
		
		//extract displayed new Account Name to verify
		WebElement accName_Saved = driver.findElement(By.className("topName"));
		mylog.info("New Account Name is :" + accName_Saved.getText());
		
		//extract displayed new Account Type to verify
		WebElement typeSaved = driver.findElement(By.xpath("//*[@id=\"acc6_ileinner\"]"));
		mylog.info("Type is  : "+typeSaved.getText()); 
		
		//extract displayed Customer Priority to verify
		WebElement custPrioritySaved = driver.findElement(By.xpath("//*[@id=\"00Nak000005IRi2_ileinner\"]"));
		mylog.info("Customer Priority is  : "+custPrioritySaved.getText()); 
				
		// verify if the newly entered account details are saved and displayed correctly
		Assert.assertEquals(accName_Saved.getText(), Account_Name);		
		Assert.assertEquals(typeSaved.getText(), Type);		
		Assert.assertEquals(custPrioritySaved.getText(), Customer_Priority);
		
	}
	
	@Test
	public void TC11Accounts_CreateNewView() {
		
		
		try {
			loginToSalesForce();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clickTabLink ("Account_Tab", "Accounts Tab");
				
		findAndClickCreateNewViewLink();
		
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		enterText(viewNameEle,"Test10" , "View Name ");
		
		//Test_SimpleViewTest UniqueView
		WebElement viewUniNameEle = driver.findElement(By.id("devname"));
		enterText(viewUniNameEle,"uni10" , "View Unique Name ");
		
		findAndClickSaveButton();		

		WebElement viewListDropDownEle = driver.findElement(By.xpath("//select[@name='fcf']"));
		Select select = new Select(viewListDropDownEle);
	
		String data = select.getFirstSelectedOption().getText();
		mylog.info("-----"+ data);
		
	}
	
	@Test
	public void TC12Accounts_EditView() throws InterruptedException {
		
		loginToSalesForce();
		
		//String viewNameToSelect = "Platinum and Gold SLA Customers";
		//	String viewNameToUpdate = "Platinum and Gold SLA Customersv1";
		//String viewUniNameToUpdate = "PlatinumandGoldSLACustomersv1unique";
		
		String viewNameToSelect = "New This Week";
		String viewNameToUpdate = "New This Week1";
		String viewUniNameToUpdate = "NewThisWeek1";
		String tabToAdd = "Last Activity";
		
		clickTabLink ("Account_Tab", "Accounts Tab");
		
		WebElement viewListDropDownEle = driver.findElement(By.xpath("//select[@name='fcf']"));
		Select select = new Select(viewListDropDownEle);
		select.selectByVisibleText(viewNameToSelect);
		
		WebElement EditLinkEle = driver.findElement(By.xpath("//a[text()='Edit']"));
		clickElement(EditLinkEle, "Edit ");
		
		WebElement pageHeader = driver.findElement(By.xpath("//*[@class='pageDescription']"));
		String actualpageHeader = pageHeader.getText();
		
		System.out.println(actualpageHeader);
		
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		viewNameEle.clear();
		enterText(viewNameEle,viewNameToUpdate, "View Name ");
		
		WebElement viewUniNameEle = driver.findElement(By.id("devname"));
		viewUniNameEle.clear();
		enterText(viewUniNameEle,viewUniNameToUpdate, "View Unique Name ");
		
		WebElement fieldDropdown = driver.findElement(By.id("fcol1"));
		Select selectfield = new Select(fieldDropdown);
		selectfield.selectByVisibleText("Account Name");
	
		WebElement operatorDropdown = driver.findElement(By.id("fop1"));
		Select selectOperator = new Select(operatorDropdown);
		selectOperator.selectByVisibleText("contains");
		
		WebElement valueElement = driver.findElement(By.id("fval1"));
		enterText(valueElement,"<rand>" , " ");
		
		WebElement availFields = driver.findElement(By.xpath("//select[@id='colselector_select_0']"));
		Select selectAvailFields = new Select(availFields);
		selectAvailFields.selectByVisibleText("Last Activity");
		
		WebElement rightArrowAddEle = driver.findElement(By.className("rightArrowIcon"));
		clickElement(rightArrowAddEle, tabToAdd);
		
		findAndClickSaveButton();	
		
		Thread.sleep(4000);
		
		WebElement actualViewDd = driver.findElement(By.xpath("//select[@name='fcf']"));
		Select selectactualViewDd = new Select(actualViewDd);
	
		String data = selectactualViewDd.getFirstSelectedOption().getText();
		mylog.info("-----"+ data);
		Assert.assertEquals(data, viewNameToUpdate);
		
		WebElement lastActivityTab = driver.findElement(By.xpath("//div[@title='Last Activity']"));
		String tabName = lastActivityTab.getText();
		
		Assert.assertEquals(tabName, tabToAdd);
		
		String expMatch = "Grand New";
		
	    List<WebElement> matchedAccNames = driver.findElements(By.xpath("//a/span[contains(text(),'rand')]"));
	    for (WebElement accName : matchedAccNames)
	    {
	        	Assert.assertEquals(accName.getText(), expMatch);
	    	    System.out.println(accName.getText());
	         
	    }

	}
	
	@Test	
	public void TC13_Merge_Accounts() throws InterruptedException {
		
		loginToSalesForce();

		clickTabLink ("Account_Tab", "Accounts Tab");
		
		WebElement mergeAccountsLink = driver.findElement(By.xpath("//a[(text()='Merge Accounts')]"));
		clickElement(mergeAccountsLink, "Merge Accounts Link ");
		
		
		WebElement searchAccTextBox = driver.findElement(By.xpath("//input[@id='srch']"));
		enterText(searchAccTextBox,"NewAccount1", "Search Accounts contains 'NewAccount1' ");
		
		WebElement findAccounts = driver.findElement(By.xpath("//input[@value='Find Accounts' and @type ='submit']"));
		clickElement(findAccounts, "Find Accounts ");
	

		List <WebElement> searchedAccChkbox = driver.findElements(By.xpath("//input[starts-with(@id,'cid')]"));
		
		for (int i=0; i<2; i++)
		{
			for (WebElement options: searchedAccChkbox) {
					
					selectElement(options, "select account to merge");				
			}
		}
		
		WebElement nextBtn = driver.findElements(By.xpath("//input[@name='goNext' and @type='submit']")).get(0);
		clickElement(nextBtn, "Next");
		
		Thread.sleep(4000);
		
		WebElement mergeBtn = driver.findElement(By.xpath("//input[@value=' Merge ' and @type='submit']"));
		clickElement(mergeBtn, "Merge");		
		
		Alert alert=switchToAlert();		
		String data=getAlertText(alert,"merge warning alertbox");		
		mylog.info(data);		
		acceptAlert(alert);
		mylog.info("merge successful");
	}
	
	
	@Test	
	public void TC14_Create_AccountReport() throws InterruptedException {
	

		loginToSalesForce();

		clickTabLink ("Account_Tab", "Accounts Tab");
		
		String reportName = "AllTimeReport10";
		
		String reportUniName = "Unique";
		
		WebElement accLastActivity = driver.findElement(By.xpath("//a[contains(text(),'Accounts with last activity')]"));
		clickElement(accLastActivity, "Accounts with last activity > 30 days");


		WebElement pageHeader = driver.findElement(By.className("pageDescription"));
		
		System.out.println("ppp : "+pageHeader);
		
		WebElement range = driver.findElement(By.xpath("//*[@id='ext-gen150']"));
		clickElement(range, "range");
		
		Thread.sleep(3000);
		
		WebElement rangeAllTime = driver.findElement(By.xpath("//*[@class='x-combo-list-inner']/div[1]"));
		clickElement(rangeAllTime, "All Time");

		WebElement saveBtn = driver.findElement(By.xpath("//button[@type='button' and text()='Save']"));
		clickElement(saveBtn, "Save report");
		
		WebElement reportNamesElme = driver.findElement(By.name("reportName"));
		enterText(reportNamesElme, reportName , "Report Name");
		
		WebElement reportUniqueName = driver.findElement(By.name("reportDevName"));
		reportUniqueName.clear();
		enterText(reportUniqueName, reportUniName, "Report Unique Name");
		
		Thread.sleep(3000);
		WebElement saveAndRunReport = driver.findElement(By.xpath("//*[@id ='dlgSaveAndRun']"));
		clickElement(saveAndRunReport, "Save and Run Report");
		
		Thread.sleep(3000);
		WebElement reportPageHeader = driver.findElement(By.xpath("//h1[@class ='noSecondHeader pageType']"));
		
		Assert.assertEquals(reportPageHeader.getText(), reportName);
	}
	
	
}
