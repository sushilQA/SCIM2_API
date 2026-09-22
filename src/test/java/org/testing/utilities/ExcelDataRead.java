package org.testing.utilities;

import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;

public class ExcelDataRead {

	public static String readACell(int rown, int column) throws BiffException, IOException {

		File file = new File("../SCIM2_API/src/test/java/org/testing/resources/Credentials.xls");
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			Cell cell = sheet.getCell(column, rown);
			return cell.getContents();

		} catch (BiffException | IOException e) {
			System.out.println("readACell failed for row=" + rown + ", column=" + column + ": " + e.getMessage());
			throw e;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("readACell - invalid row/column index: row=" + rown + ", column=" + column);
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	public static int getNumberOfRows(String sheetName) throws BiffException, IOException {

		File file = new File("../SCIM2_API/src/test/java/org/testing/resources/DynamicData.xls");
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			return sheet.getRows();

		} catch (BiffException | IOException e) {
			System.out.println("getNumberOfRows failed for sheet '" + sheetName + "': " + e.getMessage());
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	public static int getNumberOfColumns(String sheetName) throws BiffException, IOException {

		File file = new File("../SCIM2_API/src/test/java/org/testing/resources/DynamicData.xls");
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			return sheet.getColumns();

		} catch (BiffException | IOException e) {
			System.out.println("getNumberOfColumns failed for sheet '" + sheetName + "': " + e.getMessage());
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

}