package example;

import org.apache.logging.log4j.Level;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.logging.log4j.LogManager;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class LogTests {
	
	ExtentReports extent = null;
	ExtentSparkReporter spark = null;
	
	Logger mylog;
	@BeforeClass
	public void setUp() {
		mylog=LogManager.getLogger(LogTests.class);
		extent = new ExtentReports();
		spark = new ExtentSparkReporter("results/Spark.html");
		
		extent.attachReporter(spark);
	}

	@AfterClass
	public void tearDown() {
		extent.flush();
	}
	
	
	@Test
	public void test1() {
		
		ExtentTest test = extent.createTest("test1");
		try {
			int a = 10 / 0;
			mylog.info("test1 passed");
			test.pass("test1 passed from Extent Report");
			
		} catch (ArithmeticException e) {
			mylog.error("test1 failure");
			test.fail("test1 failure from Extent Report");
		}

	}
	@Test 
	public void test2() {
		
		ExtentTest test = extent.createTest("test2");
		mylog.info("test2 started");
		test.pass("test2 started from Extent Report");
	}

	@Test
	public void test3() {
		
		ExtentTest test = extent.createTest("test3");
		mylog.info("test3 started");
		test.pass("test3 started from Extent Report");
	}

}
