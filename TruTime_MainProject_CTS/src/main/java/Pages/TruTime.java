package Pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Base.Base;

public class TruTime extends Base {
	By email = By.xpath("//input[@type='email']");
	By next = By.xpath("//input[@type='submit']");
	By pass = By.name("passwd");
	By acc = By.id("user-name");
	By vald = By.className("table-row");
	By Verify = By.xpath("//input[@Value='Verify']");
	By yes = By.xpath("//input[@value='Yes']");
	By search = By.id("searchbox");
	By icon = By.className("icomoon-search2");
	By trutime = By.xpath("(//span[text()='TruTime'])[1]");
	By date = By.xpath("//div[@class='dayDetail ng-scope']/div[1]");

	public void login() {
		logger = report.createTest("Login into Becognizant.");
		try {
			wait(20, email);
			driver.findElement(email).sendKeys(prop.getProperty("email"));
			driver.findElement(next).click();
			wait(20, pass);
			driver.findElement(pass).sendKeys(prop.getProperty("password"));
			driver.findElement(next).click();
			reportPass("Email and Password are verified sucessfully");
			driver.findElement(vald).click();
			// Enter the authenticator key within 15 Seconds
			Thread.sleep(15000);
			wait(20, Verify);
			driver.findElement(Verify).click();
			wait(20, yes);
			driver.findElement(yes).click();
			// Verify Title
			if (driver.getTitle().contains("Be.Cognizant"))
				// Pass
				System.out.println("Page title contains Be.Cognizant");
			else
				// Fail
				System.out.println("Page title doesn't contains Be.Cognizant");
			String name = driver.findElement(acc).getText();
			System.out.println("The User Name of the Account Holder is: " + name);
			Screenshot("Account");
			reportPass("Be.Cognizant Page is reached sucessfully");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void getData() {
		logger = report.createTest("Obtain the Week from trutime.");
		try {
			driver.findElement(search).sendKeys(prop.getProperty("search2"));
			driver.findElement(icon).click();
			wait(20, trutime);
			String currentHandle = driver.getWindowHandle();
			driver.findElement(trutime).click();
			reportPass("TruTime Page is reached sucessfully");
			Set<String> handle1 = driver.getWindowHandles();
			for (String actual : handle1) {
				if (!actual.equalsIgnoreCase(currentHandle)) {
					driver.switchTo().window(actual);
				}
			}
			driver.switchTo().frame("appFrame");
			file = new File(System.getProperty("user.dir") + "\\Data.xlsx");
			workbook = new XSSFWorkbook();
			sh = workbook.createSheet("Dates");
			Screenshot("Trutime");
			List<WebElement> dates = driver.findElements(date);
			Date date = new Date();
			System.out.println("Today's Date is: " + date.toString());
			System.out.println("************************************************");
			System.out.println("The Dates for this week from trutime are:");
			System.out.println("************************************************");
			for (int i = 0; i < dates.size(); i++) {
				sh.createRow(i).createCell(1).setCellValue(dates.get(i).getText());
				System.out.println(dates.get(i).getText());
				if (dates.get(i).getText().contains("Sun")) {
					System.out.println("This Weeks Sunday is: " + dates.get(i).getText());
				}
				if (dates.get(i).getText().contains("Sat")) {
					System.out.println("This Weeks Saturday is: " + dates.get(i).getText());
				}
			}
			reportPass("The dates are obtained sucessfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public void getDates() {
		Calendar now = Calendar.getInstance();
		logger = report.createTest("Getting data from calender class");
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM");

		String[] dates = new String[7];
		int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
		now.add(Calendar.DAY_OF_MONTH, delta);
		for (int i = 0; i < 7; i++) {
			dates[i] = format.format(now.getTime());
			now.add(Calendar.DAY_OF_MONTH, 1);
		}
		System.out.println("\n The Dates for this week from calender class are:");
		System.out.println("************************************************");
		file = new File(System.getProperty("user.dir") + "\\Data1.xlsx");
		workbook = new XSSFWorkbook();
		sh = workbook.createSheet("Dates");
		for (int i = 0; i < dates.length; i++) {
			sh.createRow(i).createCell(1).setCellValue(dates[i]);
			System.out.println(dates[i]);
		}
		reportPass("Sucessfully Got data from calender class");
	}

	public void comparedates() throws IOException {
		System.out.println("\n Comparing Dates from both excel:");
		System.out.println("************************************************");
		logger = report.createTest("Comparing data from both excels");
		try {
			File infile1 = new File("Data.xlsx");
			FileInputStream fileInputStream1 = new FileInputStream(infile1);
			XSSFWorkbook workbook1 = new XSSFWorkbook(fileInputStream1);
			XSSFSheet worksheet1 = workbook1.getSheet("Dates");
			int rowCount1 = worksheet1.getPhysicalNumberOfRows();

			File infile2 = new File("Data1.xlsx");
			FileInputStream fileInputStream2 = new FileInputStream(infile2);
			XSSFWorkbook workbook2 = new XSSFWorkbook(fileInputStream2);
			XSSFSheet worksheet2 = workbook2.getSheet("Dates");
			int rowCount2 = worksheet2.getPhysicalNumberOfRows();

			if (rowCount1 == rowCount2) {
				for (int i = 1; i < rowCount1; i++) {
					XSSFRow row1 = worksheet1.getRow(i);
					XSSFRow row2 = worksheet2.getRow(i);

					String str1 = "";
					XSSFCell date1 = row1.getCell(1);
					if (date1 != null) {
						date1.setCellType(CellType.STRING);
						str1 = date1.getStringCellValue();
					}
					String str2 = "";
					XSSFCell date2 = row2.getCell(1);
					if (date2 != null) {
						date2.setCellType(CellType.STRING);
						str2 = date2.getStringCellValue();
					}
					if (str1.equals(str2)) {
						System.out.println("| trutimedates =" +str1+ " | calenderdates= " +str2+ "| Match correctly");
						reportPass("| trutimedates =" +str1+ " | calenderdates= " +str2+ "| Match correctly");
					} else {
						System.out.println("[ERROR]: | trutimedates =" +str1+ " | calenderdates= " +str2+ " | Doesn't Match Correctly");
						reportFail("[ERROR]: | trutimedates =" +str1+ " | calenderdates= " +str2+ " | Doesn't Match correctly");
		
					}

				}
			}

		}

		catch (FileNotFoundException ex) {
			ex.printStackTrace();

		}

	}

}
