package com.lihui.share.util;


import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyExcelUtil
{
	//根据模板文件获取XSSFWorkbook对象
	public static XSSFWorkbook getWorkbookByTemplate(String templateFileName) throws InvalidFormatException, IOException
	{
		File templateFile = new File(templateFileName);
		XSSFWorkbook workbook = new XSSFWorkbook(templateFile);
		return workbook;
	}
	
	public static XSSFWorkbook getWorkbook()
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		return workbook;
	}
	
	
}
