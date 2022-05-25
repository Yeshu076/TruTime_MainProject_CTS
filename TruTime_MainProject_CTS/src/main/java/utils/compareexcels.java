package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import Base.Base;

public class compareexcels extends Base {

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

	


