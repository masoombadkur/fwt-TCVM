package com.yash.tcvm.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yash.tcvm.literal.ExcelSheetConstants;

public class ExcelUtil {
	
	private static Logger logger = Logger.getLogger(ExcelUtil.class);

	public static void createExcelSheet(List<String> columns, String sheetName, String fileName, List<Object[]> data) throws IOException {
		logger.info("ExcelUtil's createExcelSheet() method starts");
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < columns.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns.get(i));
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;		
		for (Object[] datum : data) {
			Row row = sheet.createRow(rowNum++);
			for (int i = 0; i < datum.length; i++) {
				row.createCell(i).setCellValue(datum[i].toString());
			}
		}
		for (int i = 0; i < columns.size(); i++) {
			sheet.autoSizeColumn(i);
		}		
		
		FileOutputStream fileOut = new FileOutputStream(ExcelSheetConstants.EXCEL_SHEET_FILE_PATH.concat(fileName));
		workbook.write(fileOut);
		fileOut.close();
		
		workbook.close();

	}
}
