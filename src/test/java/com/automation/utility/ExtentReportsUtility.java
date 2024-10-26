package com.automation.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsUtility {
	
	
	public static ExtentReports report;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;
	public static ExtentReportsUtility extentObject;
	
	private ExtentReportsUtility() {
		
	}
	
	public static ExtentReportsUtility getInstance() {
		if(extentObject==null) {
			extentObject=new ExtentReportsUtility();
		}

		return extentObject;
	}
	
	public void startExtentReport() {
		report = new ExtentReports();
		spark = new ExtentSparkReporter(Constants.SPARKS_HTML_REPORT_PATH);
			
		report.attachReporter(spark);
	}
	
	public void createSingleTestReportForMethod(String methodName) {
		
		test = report.createTest(methodName);
		
	}
	
	public void endExtentReport() {
		report.flush();
	}
	
	public void reportTestInfo(String text) {
		
		test.info(text);
	}
	
	public void reportTestPassed(String text) {
		
		test.pass(text);
	}
	
	public void reportTestFailed(String text) {
			
		test.fail(text);
	}
	
	public void reportTestFailedWithException(Throwable e) {
		
		test.log(Status.FAIL, e);
	}
	
	public void logTestWithscreenshot(String path) {
		//testLogger.addScreenCaptureFromBase64String(path);
		test.fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		
	}
	
}
