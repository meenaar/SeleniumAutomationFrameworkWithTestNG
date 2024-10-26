package com.automation.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.base.BaseTest;

public class SalesforceListenerUtility extends BaseTest implements ITestListener{
	
	private Logger mylog = LogManager.getLogger(SalesforceListenerUtility.class);
	private ExtentReportsUtility extentReportUtility = ExtentReportsUtility.getInstance();
	
	@Override
	public void onStart(ITestContext context) {
		
		mylog.info(context.getName()+" started...............");
		extentReportUtility.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		mylog.info(context.getName()+" ended.................");
		extentReportUtility.endExtentReport();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		
		mylog.info(result.getMethod().getMethodName()+" started..................");
		extentReportUtility.createSingleTestReportForMethod(result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		mylog.info(result.getMethod().getMethodName()+" ended with success......................");
		extentReportUtility.reportTestPassed(result.getMethod().getMethodName());		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		mylog.error(result.getMethod().getMethodName()+" ended with failure.....................");
			
		extentReportUtility.reportTestFailedWithException(result.getThrowable());
		String filename = CommonUtils.getStringDateAndTimeStamp();
		String path = Constants.SCREENSHOTS_DIRECTORY_PATH+filename+".png";
		takeScreenshot(path);
		extentReportUtility.logTestWithscreenshot(path);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		mylog.warn(result.getMethod().getMethodName()+" skiped...........................");
		extentReportUtility.reportTestFailed(result.getMethod().getMethodName());
		
	}
	
}
