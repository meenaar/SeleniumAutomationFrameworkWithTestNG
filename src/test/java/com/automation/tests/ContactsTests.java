package com.automation.tests;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.base.BaseSalesForce;
import com.automation.utility.Constants;
import com.automation.utility.PropertiesUtility;

@Listeners(com.automation.utility.SalesforceListenerUtility.class)

public class ContactsTests extends BaseSalesForce{
	
	protected String lastNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CREATECONTACTS_PROPERTIES,"lastNameData");
	protected String accNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CREATECONTACTS_PROPERTIES,"accNameData");

	protected String viewNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CONTACTSCREATENEWVIEW_PROPERTIES,"viewNameData");
	protected String viewUniNameData =PropertiesUtility.readDataFromPropertyFile(Constants.CONTACTSCREATENEWVIEW_PROPERTIES,"viewUniNameData");

	@Test
	public void TC25_CreateNewContact() throws InterruptedException {
		
		loginToSalesForce();
	
		clickTabLink ("Contact_Tab", "Contacts Tab");
			
		WebElement newElement = driver.findElement(By.xpath("//input[@type='button' and @name='new']"));
		clickElement(newElement, "New");
		
		WebElement lastName = driver.findElement(By.id("name_lastcon2"));
		enterText(lastName, lastNameData, "Last Name");
				
		WebElement accName = driver.findElement(By.id("con4"));
		enterText(accName, accNameData, "Account Name");
		
		findAndClickSaveButton();
		
		WebElement topName = driver.findElement(By.xpath("//h2[@class='topName']"));		
		
		WebElement actualLastName = driver.findElement(By.id("con2_ileinner"));
		
		WebElement actualAccName = driver.findElement(By.id("con4_ileinner"));
		
		Assert.assertEquals(topName.getText(), lastNameData);
		
		Assert.assertEquals(actualLastName.getText(), lastNameData);
		
		Assert.assertEquals(actualAccName.getText(), accNameData);
	
	}
	
	@Test
	public void TC26_Contacts_CreateNewView() throws InterruptedException {
		
		loginToSalesForce();
	
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		findAndClickCreateNewViewLink();
		
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		enterText(viewNameEle,viewNameData , "View Name ");
		
		//Test_SimpleViewTest UniqueView
		WebElement viewUniNameEle = driver.findElement(By.id("devname"));
		viewUniNameEle.clear();
		enterText(viewUniNameEle,viewUniNameData , "View Unique Name ");
		
		findAndClickSaveButton();
		
		WebElement viewListDropDownEle = driver.findElement(By.xpath("//select[@name='fcf']"));
		Select select = new Select(viewListDropDownEle);
		
		WebElement data = select.getFirstSelectedOption();
		mylog.info("-----"+ data.getText());
		extentReportUtility.reportTestInfo("-----"+ data.getText());
		
		Assert.assertEquals(data.getText(), viewNameData);
		
	}
	
	@Test
	public void TC27_CheckRecentlyCreatedContact() throws InterruptedException {
		
		loginToSalesForce();
	
		String expectedHeader = "Recent Contacts";
		String valueToSelect = "Recently Created";
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		WebElement recentDropDown = driver.findElement(By.id("hotlist_mode"));
		selectByVisibleText(recentDropDown, "Recently Created");
		
		WebElement headerTitle = driver.findElement(By.xpath("//h3[text() = 'Recent Contacts']"));
		mylog.info(headerTitle.getText());
		
		String actualHeader = headerTitle.getText();
		
		Select select = new Select(recentDropDown);
		
		WebElement data = select.getFirstSelectedOption();
		mylog.info("Selected dropdown value-----"+ data.getText());
		
		Assert.assertEquals(data.getText(), valueToSelect);
		
		Assert.assertEquals(actualHeader, expectedHeader);
		
	}
	
	@Test
	public void TC28_MyContactsView() throws InterruptedException {
		
		loginToSalesForce();
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		String valueToSelect = "My Contacts";
		
		WebElement viewDropdown = driver.findElement(By.xpath("//*[@id='fcf']"));
		
		selectByVisibleText(viewDropdown, "My Contacts");
				
		//WebElement goButton = driver.findElement(By.xpath("//input[@type='button' and @value= ' Go! ']"));
		//clickElement(goButton, "Go");
		
		WebElement actualViewDropdown = driver.findElement(By.xpath("//select[starts-with(@id,'00Bak00000HJI')]"));
		
		Select select = new Select(actualViewDropdown);
		
		WebElement data = select.getFirstSelectedOption();
		
		mylog.info("Selected dropdown value-----"+ data.getText());
		
		Assert.assertEquals(data.getText(), valueToSelect);
		
	}
	
	@Test
	//not completed yet
	
	public void TC29_ViewAContact() throws InterruptedException {
		
		
		loginToSalesForce();
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		WebElement goButton = driver.findElement(By.xpath("//input[@type='button' and @value= ' Go! ']"));
		clickElement(goButton, "Go");
		
		WebElement table = driver.findElement(By.xpath("//*[@id=\"ext-gen12\"]/div[1]/table"));
		
		
		System.out.println(table.getText());

			//	WebElement row = table.findElement(By.xpath(“//tr[contains(@class, ‘myRow’)]”)); 
		WebElement element = table.findElement(By.xpath("//td[contains( @class, 'td-FULL_NAME Hidden')]"));
		
		System.out.println(element.getText());
		
	}
	
	
	@Test
	public void TC30_Unsuccessful_CreateNewView() throws InterruptedException {
		
		String viewUniNameData = "EFGH";
		
		String expectedData = "Error: You must enter a value";
		
		loginToSalesForce();
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		findAndClickCreateNewViewLink();
		
		WebElement viewUniNameEle = driver.findElement(By.id("devname"));
		viewUniNameEle.clear();
		enterText(viewUniNameEle,viewUniNameData , "View Unique Name ");
		
		findAndClickSaveButton();
		
		WebElement ViewNameErrorMsg = driver.findElement(By.xpath("//div[@class='requiredInput']/div[@class='errorMsg']"));
		String actualData = ViewNameErrorMsg.getText();
		
		Assert.assertEquals(actualData, expectedData);
		
	}
	
	@Test
	public void TC31_Cancel_CreateNewView() throws InterruptedException {
		
		String viewNameData = "ABCD";
		String viewUniNameData = "ABCDEFGH";
		String pageHeader1 = "Contacts";
		String pageHeader2 = "Home";
			
		loginToSalesForce();
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
		
		findAndClickCreateNewViewLink();
		
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		enterText(viewNameEle,viewNameData , "View Name ");
		
		WebElement viewUniNameEle = driver.findElement(By.id("devname"));
		viewUniNameEle.clear();
		enterText(viewUniNameEle,viewUniNameData , "View Unique Name ");
		
		WebElement cancelElement = driver.findElement(By.xpath("//input[@value='Cancel']"));
		clickElement(cancelElement, "Cancel create new view");
		
		WebElement header1Ele = driver.findElement(By.xpath("//h1[text()='Contacts']"));
		
		WebElement header2Ele = driver.findElement(By.xpath("//h2[text()=' Home']"));
		
		Assert.assertEquals(header1Ele.getText(), pageHeader1);
		
		Assert.assertEquals(header2Ele.getText(), pageHeader2);		
	}
	
	@Test
	public void TC32_SaveAndNew_NewContact() throws InterruptedException {
	
		String pageHeader1 = "Contact Edit";
		String pageHeader2 = "New Contact";
		
		loginToSalesForce();
		
		clickTabLink ("Contact_Tab", "Contacts Tab");
			
		WebElement newElement = driver.findElement(By.xpath("//input[@type='button' and @name='new']"));
		clickElement(newElement, "New");
		
		WebElement lastName = driver.findElement(By.id("name_lastcon2"));
		enterText(lastName, lastNameData, "Last Name");
				
		WebElement accName = driver.findElement(By.id("con4"));
		enterText(accName, accNameData, "Account Name");
		
		
		WebElement saveElement = driver.findElements(By.xpath("//input[@type='submit' and @value='Save & New']")).get(0);
		clickElement(saveElement, "Save & New");
		
		Thread.sleep(3000);
		
		WebElement header1Ele = driver.findElement(By.xpath("//h1[text()='Contact Edit']"));
		
		WebElement header2Ele = driver.findElement(By.xpath("//h2[text()=' New Contact']"));
		
		Assert.assertEquals(header1Ele.getText(), pageHeader1);
		
		Assert.assertEquals(header2Ele.getText(), pageHeader2);	
		
	}
	
	
	@Test
	public void TC33_Verify_LoggedInUser() throws InterruptedException {
		
		loginToSalesForce();
		
		clickTabLink ("home_Tab", "Home");
		
		WebElement loggedUserLink = driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a"));
			
		String actualUsername = loggedUserLink.getText();
		
		System.out.println("actual username : "+actualUsername);
		
		clickElement(loggedUserLink, "logged Firstname & Lastname");	
		
		// get user's first name and last name		
		WebElement userMenuEle = driver.findElement(By.xpath("//*[@id=\"userNav\"]"));
		
		waitForVisibility(userMenuEle, 30, "User Menu dropdown");
		
		clickElement(userMenuEle, "User Menu dropdown");
		
		WebElement myProfileEle = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[1]"));
		clickElement(myProfileEle, "My Profile");
		
		WebElement myProfileUserLink = driver.findElement(By.xpath("//*[@id=\"tailBreadcrumbNode\"]"));
		String myProfileUserName = myProfileUserLink.getText();
		
		System.out.println("Username from My Profile: "+ myProfileUserName);
		
		Assert.assertEquals(actualUsername, myProfileUserName.trim());
	
	}
	
	
	@Test
	public void TC34_Edit_UserLastName() throws InterruptedException {
	
		loginToSalesForce();
		
		clickTabLink ("home_Tab", "Home");
		String lastNameToUpdate = "Ar";
		
		WebElement loggedUserLink = driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a"));
			
		String actualUsername = loggedUserLink.getText();
		
		System.out.println("actual username : "+actualUsername);
		
		clickElement(loggedUserLink, "logged Firstname & Lastname");	
		
		WebElement contactEditPen = driver.findElements(By.xpath("//img[@title='Edit Profile']")).get(0);
		clickElement(contactEditPen, "Edit Contact");
		
		
		driver.switchTo().frame("contactInfoContentId");
		
		System.out.println("\n***Control in contact frame***");
		Thread.sleep(3000);
		
		
		WebElement aboutTab=driver.findElement(By.xpath("//li[@id='aboutTab']/a"));
		clickElement(aboutTab, "About Tab");
		
		WebElement lastName=driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
		lastName.clear();
		enterText(lastName, lastNameToUpdate, "last name");
		
		System.out.println("last name is :"+lastName.getText());
		
		WebElement saveAll=driver.findElement(By.xpath("//input[@value='Save All']"));
		clickElement(saveAll, "Save All");

		driver.switchTo().parentFrame();
		
		//verify updated last name in user page title where it shows first name and last name
		WebElement topUserName=driver.findElement(By.xpath("//span[@id='tailBreadcrumbNode']"));
		System.out.println(topUserName.getText());
		
		String expName = topUserName.getText();
				
		String userPagelastName = splitNames(expName);
		
		Assert.assertEquals(userPagelastName, lastNameToUpdate);
		
		//verify updated last name in user menu navigation
		WebElement userMenuEle = driver.findElement(By.xpath("//*[@id=\"userNav\"]"));
		System.out.println("User menu   : "+userMenuEle.getText());
		
		String userMenulastName = splitNames(userMenuEle.getText());
		
		Assert.assertEquals(userMenulastName, lastNameToUpdate);
		
	}
	
}
