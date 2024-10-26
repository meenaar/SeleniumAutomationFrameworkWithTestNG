package com.automation.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.base.BaseSalesForce;

@Listeners(com.automation.utility.SalesforceListenerUtility.class)


public class UserMenuOptionsTests extends BaseSalesForce{
	
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
		
		WebElement dispLayoutEle1= driver.findElement(By.id("DisplayAndLayout_font"));
		clickElement(dispLayoutEle1,"Display & Layout");
		
		WebElement customizeTabEle1= driver.findElement(By.id("CustomizeTabs_font"));
		clickElement(customizeTabEle1,"Customize My Tabs");
	
		
		// executed fine till above code
		WebElement customAppDDEle1= driver.findElement(By.id("p4"));
		
		Select select2= new Select(customAppDDEle1);
		select2.selectByVisibleText("Salesforce Chatter");
		
		System.out.println("After selecting dropdown");
		
		List<WebElement> selectedTabsEle = driver.findElements(By.xpath("//*[@id=\"duel_select_1\"]/option"));
		
		ArrayList<String> TabsLists = new ArrayList<>();
	      
		for (WebElement options :  selectedTabsEle) {
			
			TabsLists.add(options.getText());
			
			mylog.info(TabsLists);
			
			 if(TabsLists.contains("Reports")){  //check if value exist in list above
				 	mylog.info("Reports tab is there.");  
			 }
			 //else{
			//    	mylog.info("Reports tab not found.");			      
			// }   
		}
		
				
		//EmailSetup_font
		
		WebElement emailEle	= driver.findElement(By.id("EmailSetup_font"));
		clickElement(emailEle,"Email");
		
		WebElement myEmailSettingsEle	= driver.findElement(By.linkText("My Email Settings"));
		clickElement(myEmailSettingsEle,"My Email Settings");
		
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
		
		System.out.println("First window "+driver.getTitle());
		
		System.out.println("..........."+driver.getWindowHandle());

		  //fetch handles of all windows, there will be two, [0]- default, [1] - new window
		
        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        
        //assert on title of new window
        String title=driver.getTitle();
        
        System.out.println("second window "+title);
        Assert.assertEquals("Developer Console",title);
        
		System.out.println("..........."+driver.getWindowHandle());
		
		//closing current window
        driver.close();
        //Switch back to the old tab or window
        driver.switchTo().window((String) windowHandles[0]);
	

	}
	
	
	@Test
	public void TC9_LogoutSalesForce() throws InterruptedException {
		
		loginToSalesForce();
		clickUserMenuNavigation();
		
		List<WebElement> userMenuListEle = driver.findElements(By.xpath("//*[@id=\"userNav-menuItems\"]/a"));
		
		System.out.println("User Menu dropdown is displayed with below menu options: ");
		for (WebElement options :  userMenuListEle) {
			System.out.println(options.getText());
		}
		
		logoutFromSalesForce();

	}
}
