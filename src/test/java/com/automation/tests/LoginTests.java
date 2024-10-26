package com.automation.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.base.BaseSalesForce;
import com.automation.utility.Constants;
import com.automation.utility.ExtentReportsUtility;
import com.automation.utility.PropertiesUtility;

@Listeners(com.automation.utility.SalesforceListenerUtility.class)

public class LoginTests extends BaseSalesForce{
	
	@Test
	public void TC1SalesforceLogin_BlankPwd() throws InterruptedException {
		
		WebElement usernameEle = driver.findElement(By.id("username"));
		enterText(usernameEle, username, "username");

		WebElement pwdElement = driver.findElement(By.id("password"));
		enterText(pwdElement, "", "password");

		WebElement loginElement = driver.findElement(By.id("Login"));
		loginElement.click();
		mylog.info("login blank pwd test completed");
		extentReportUtility.reportTestInfo("login blank pwd test completed");		
	}
	
	@Test
	public void TC2SalesforceLoginValid() throws InterruptedException {
	
		
		loginToSalesForce();
		mylog.info("valid login test completed");
		extentReportUtility.reportTestInfo("valid login test completed");
	}
	
	@Test
	public void TC3ValidateRememberMe() throws InterruptedException {
		
		String username = "realtesting@sharklasers.com";
		
		WebElement usernameEle = driver.findElement(By.id("username"));
		enterText(usernameEle, username, "username");
				
		WebElement pwdElement = driver.findElement(By.id("password"));
		enterText(pwdElement, "SParum08", "password");
		
		WebElement rememberMe = driver.findElement(By.xpath("//*[@id=\"rememberUn\"]"));
		clickElement(rememberMe, "Remember Me checkbox ");
		
		WebElement loginElement = driver.findElement(By.id("Login"));
	
		clickElement(loginElement, "Login button ");
		
		Thread.sleep(5000);
		
		// click UserMenu
		WebElement userMenu = driver.findElement(By.xpath("//*[@id=\"userNav\"]"));		
		waitForElementToBeClickable(userMenu, 30, "User Menu");		
		clickElement(userMenu, "User Menu dropdown");
		
		//click Logout
		WebElement logoutLink = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[5]"));
		clickElement(logoutLink, "Logout ");
		Thread.sleep(5000);
		
		//Extract remembered username and validate
		WebElement uNameRemembered = driver.findElement(By.xpath("//*[@id=\"idcard-identity\"]"));
		
		waitForViTextToBePresentInElement(uNameRemembered, 30, username, "username");
		
		System.out.println("username is "+uNameRemembered.getText());

		Assert.assertEquals(username, uNameRemembered.getText());		
	}

	@Test
	public void TC4A_ForgotPassword() throws InterruptedException {
		
		String msg = "We’ve sent you an email with a link to finish resetting your password.";
		
		WebElement forgotPwdEle = driver.findElement(By.id("forgot_password_link"));
		
		waitForVisibility(forgotPwdEle, 30, "Forgot your password");
		
		clickElement(forgotPwdEle, "Forgot your password");
		
			
		WebElement unameForgotPwdEle = driver.findElement(By.xpath("//*[@id=\"un\"]"));
		enterText(unameForgotPwdEle, username, "username email");
		
		WebElement conTinueEle = driver.findElement(By.name("continue"));
		clickElement(conTinueEle, "Continue");
		
		WebElement msgElement = driver.findElement(By.xpath("//*[@id=\"forgotPassForm\"]/div/p[1]"));
		mylog.info(msgElement.getText());
		
		if (msg.equals(msgElement.getText())) {
			mylog.info("Test case passed");
			extentReportUtility.reportTestPassed("Test case passed");
		}
		else {
			mylog.error("Test case failed");
			extentReportUtility.reportTestFailed("Test case failed");
		}		
	
	}
	
	@Test
	public void TC4B_ValidateLoginErrorMsg() throws InterruptedException {
		
		String expectedError = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		WebElement usernameEle = driver.findElement(By.id("username"));
		enterText(usernameEle, "123", "username email");
				
		WebElement pwdElement = driver.findElement(By.id("password"));
		enterText(pwdElement, "22131", "password");
		
		WebElement loginElement = driver.findElement(By.id("Login"));
		clickElement(loginElement, "Login button");
		
		WebElement errorMsgEle = driver.findElement(By.xpath("//*[@id=\"error\"]"));
		
		waitForVisibility(errorMsgEle, 30, "Wrong password error message");
		
		mylog.info("Error message is : "+errorMsgEle.getText());
		extentReportUtility.reportTestInfo("Error message is : "+errorMsgEle.getText());

		Assert.assertEquals(expectedError, errorMsgEle.getText());
	}
	
	

	
	@Test
	public void TC5_UserMenuDropDown () throws InterruptedException {
		
		loginToSalesForce();
		
		clickUserMenuNavigation();

		Thread.sleep(2000);
		
		String expMenuItems[]= {"My Profile","My Settings","Developer Console","Switch to Lightning Experience","Logout"};
		
		List<WebElement> userMenuListEle = driver.findElements(By.xpath("//*[@id=\"userNav-menuItems\"]/a"));
				
		verifyDropDownListValues (userMenuListEle, expMenuItems);

	}
			
}

	
