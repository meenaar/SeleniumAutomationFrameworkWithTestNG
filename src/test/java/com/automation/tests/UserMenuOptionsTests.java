package com.automation.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.base.BaseSalesForce;

@Listeners(com.automation.utility.SalesforceListenerUtility.class)

public class UserMenuOptionsTests extends BaseSalesForce {

	
	@Test
	public void TC6_MyProfileOption() throws InterruptedException {

		loginToSalesForce();

		clickUserMenuNavigation();

		Thread.sleep(2000);
		
		String lastNameToUpdate = "Arun";
		
		WebElement myProfileEle = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[1]"));
		clickElement(myProfileEle, "My Profile");
		
		System.out.println("First window " + driver.getTitle());
		System.out.println("..........." + driver.getWindowHandle());
		
		WebElement contactEditPen = driver.findElements(By.xpath("//img[@title='Edit Profile']")).get(0);
		clickElement(contactEditPen, "Edit Contact");
		
		driver.switchTo().frame("contactInfoContentId");
		
		System.out.println("\n***Control in contact frame***");
		Thread.sleep(6000);
		
		
		WebElement aboutTab=driver.findElement(By.xpath("//li[@id='aboutTab']/a"));
		aboutTab.click();
		
		WebElement lastName=driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
		lastName.clear();
		enterText(lastName, lastNameToUpdate, "last name");
		
		
		System.out.println("last name is :"+lastName.getText());
		
		WebElement saveAll=driver.findElement(By.xpath("//input[@value='Save All']"));
		
		clickElement(saveAll, "Save All");
		
		driver.switchTo().parentFrame();
		
		WebElement postEle =driver.findElement(By.xpath("//span[text()='Post']"));
		clickElement(postEle, "Post");
		
		/* Have to complete rest
		 * 
		 * WebElement childFrame = driver.findElement(By.cssSelector("cke_43_contents > iframe"));
		
		driver.switchTo().frame(childFrame);
		
		WebElement postTextArea =driver.findElement(By.xpath("//body/p"));
		enterText(postTextArea, "Hello entered", "Post text area");
		
		*/
	}
	
	@Test
	public void TC7_MySettings_UserNameMenu() throws InterruptedException {

		loginToSalesForce();

		clickUserMenuNavigation();

		Thread.sleep(2000);

		WebElement mySettingsEle = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[2]"));
		clickElement(mySettingsEle, "My Settings");

		WebElement dispLayoutEle = driver.findElement(By.id("DisplayAndLayout_font"));
		clickElement(dispLayoutEle, "Display & Layout");

		WebElement customizeTabEle = driver.findElement(By.id("CustomizeTabs_font"));
		clickElement(customizeTabEle, "Customize My Tabs");

		WebElement customAppDDEle = driver.findElement(By.id("p4"));

		Select select = new Select(customAppDDEle);
		select.selectByVisibleText("Salesforce Chatter");

		WebElement availTabsEle = driver.findElement(By.xpath("//*[@id=\"duel_select_0\"]"));
		String tab_data = "Reports";

		Select select1 = new Select(availTabsEle);
		select1.selectByVisibleText(tab_data);

		WebElement rightArrowAddEle = driver.findElement(By.className("rightArrowIcon"));
		clickElement(rightArrowAddEle, "Add Tab");

		WebElement saveElement = driver.findElement(By.name("save"));
		clickElement(saveElement, "Save Customize Tab");

		Thread.sleep(4000);

		WebElement dispLayoutEle1 = driver.findElement(By.id("DisplayAndLayout_font"));
		clickElement(dispLayoutEle1, "Display & Layout");

		WebElement customizeTabEle1 = driver.findElement(By.id("CustomizeTabs_font"));
		clickElement(customizeTabEle1, "Customize My Tabs");

		
		WebElement customAppDDEle1 = driver.findElement(By.id("p4"));

		Select select2 = new Select(customAppDDEle1);
		select2.selectByVisibleText("Salesforce Chatter");

		System.out.println("After selecting dropdown");

		List<WebElement> selectedTabsEle = driver.findElements(By.xpath("//*[@id=\"duel_select_1\"]/option"));

		ArrayList<String> TabsLists = new ArrayList<>();

		for (WebElement options : selectedTabsEle) {

			TabsLists.add(options.getText());

			mylog.info(TabsLists);

			if (TabsLists.contains("Reports")) { // check if value exist in list above
				mylog.info("Reports tab is there.");
			}			
		}

		// EmailSetup_font

		WebElement emailEle = driver.findElement(By.id("EmailSetup_font"));
		clickElement(emailEle, "Email");

		WebElement myEmailSettingsEle = driver.findElement(By.linkText("My Email Settings"));
		clickElement(myEmailSettingsEle, "My Email Settings");

		WebElement emailNameEle = driver.findElement(By.xpath("//*[@id=\"sender_name\"]"));
		emailNameEle.clear();
		enterText(emailNameEle, "Meena A", "Email Name");

		WebElement emailAddrEle = driver.findElement(By.xpath("//*[@id=\"sender_email\"]"));
		emailAddrEle.clear();
		enterText(emailAddrEle, "meenakshiaru@gmail.com", "Email Address");

		WebElement saveEmailEle = driver.findElement(By.name("save"));
		clickElement(saveEmailEle, "Save Email Settings");

		Thread.sleep(2000);

		WebElement headerEle = driver.findElement(By.xpath("//h1[text()='My Email Settings']"));
		System.out.println(headerEle.getText());

		Assert.assertEquals(headerEle.getText(), "My Email Settings");

	}

	@Test
	public void TC8_DeveloperConsole_UserNameMenu() throws InterruptedException {

		loginToSalesForce();

		clickUserMenuNavigation();

		Thread.sleep(2000);

		WebElement devConsoleEle = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[3]"));
		clickElement(devConsoleEle, "Developer Console");

				
		System.out.println("First window " + driver.getTitle());

		System.out.println("..........." + driver.getWindowHandle());

		// fetch handles of all windows, there will be two, [0]- default, [1] - new window

		Object[] windowHandles = driver.getWindowHandles().toArray();
		driver.switchTo().window((String) windowHandles[1]);

		// assert on title of new window
		String title = driver.getTitle();

		System.out.println("second window " + title);
		Assert.assertEquals("Developer Console", title);

		System.out.println("..........." + driver.getWindowHandle());

		// closing current window
		driver.close();
		// Switch back to the old tab or window
		driver.switchTo().window((String) windowHandles[0]);
	}

	
	@Test
	public void TC9_LogoutSalesForce() throws InterruptedException {

		loginToSalesForce();
		clickUserMenuNavigation();

		List<WebElement> userMenuListEle = driver.findElements(By.xpath("//*[@id=\"userNav-menuItems\"]/a"));

		System.out.println("User Menu dropdown is displayed with below menu options: ");
		for (WebElement options : userMenuListEle) {
			System.out.println(options.getText());
		}

		logoutFromSalesForce();
	}
	

	@Test
	public void TC35_VerifyTabCustomization() throws InterruptedException {

		loginToSalesForce();

		clickTabLink("AllTab_Tab", "Home");
		
		String tabToRemove ="Chatter";

		WebElement customizeTabButton = driver.findElement(By.xpath("//*[@name='customize']"));
		clickElement(customizeTabButton, "Customize My Tabs");

		WebElement selectedTabsEle = driver.findElement(By.xpath("//*[@id=\"duel_select_1\"]"));

		mylog.info(selectedTabsEle);

		Select select = new Select(selectedTabsEle);
		select.selectByVisibleText(tabToRemove);

		WebElement lefttArrowAddEle = driver.findElement(By.className("leftArrowIcon"));
		clickElement(lefttArrowAddEle, "Remove Tab");

		WebElement saveElement = driver.findElement(By.name("save"));
		clickElement(saveElement, "Save Customize Tab");

		Thread.sleep(4000);

		WebElement userMenuEle = driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]"));
		waitForVisibility(userMenuEle, 30, "User Menu dropdown");
		clickElement(userMenuEle, "User Menu dropdown");

		logoutFromSalesForce();

		Thread.sleep(4000);

		WebElement uName = driver.findElement(By.id("username"));
		uName.clear();
		enterText(uName, username, "username");

		WebElement pwdElement = driver.findElement(By.id("password"));
		enterText(pwdElement, password, "password");

		WebElement loginElement = driver.findElement(By.id("Login"));
		clickElement(loginElement, "Login");

		mylog.info("After logged in again");

		Thread.sleep(4000);

		List<WebElement> AllTabsList = driver.findElements(By.xpath("//*[@id='tabBar']/li"));

		for (WebElement options : AllTabsList) {

			System.out.println("tabs are  :" + options.getText());

			// Use Assert.assertFalse to check if the list does NOT contain a specific
			// element
			Assert.assertFalse(AllTabsList.stream().anyMatch(e -> options.getText().equals(tabToRemove)),
					"The Tabs should not contain "+tabToRemove);

		}

	}

}
