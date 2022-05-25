package TestSuites;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.Base;
import Pages.TruTime;
import utils.GetDates;
import utils.compareexcels;

public class trutime extends Base {
	
	WebDriver driver;

	TruTime ha = new TruTime();
	GetDates gd = new GetDates();
	compareexcels ce = new compareexcels();

	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("Executing Test Cases");

		ha.invokeBrowser();
		reportPass("Browser is Invoked");
	}

	@Test(priority = 1)
	public void testCases() throws Exception {
        ha.openURL();
		ha.login();
		ha.getData();
		ha.closeBrowser();
	    reportPass("All steps passed successfuly");
	}
	
	@Test(priority = 2)
	public void testCases1() throws Exception {
		gd.getDates();
		ce.comparedates();
	    reportPass("All steps passed successfuly");
	}
	

	@AfterTest
	public void closeBrowser() throws IOException {
		reportPass("Browser is closed successfuly");
		endReport();

	}
}
