package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import Base.Base;

public class GetDates extends Base {

    
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
}
