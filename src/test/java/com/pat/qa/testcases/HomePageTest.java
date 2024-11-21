package com.pat.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pat.qa.base.TestBase;
import com.pat.qa.pages.HomePage;

public class HomePageTest extends TestBase {

	HomePage homepage;

	public HomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		homepage = new HomePage();
		sleep(2000);
		homepage.clickSearchOption();
		sleep(2000);
		homepage.acceptDisclimer();
	}

	@Test
	public void testValidateTableAndDates() throws InterruptedException {
		homepage.differentiateDates();
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
