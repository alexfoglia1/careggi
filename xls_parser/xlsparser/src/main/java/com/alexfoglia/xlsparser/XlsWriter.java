package com.alexfoglia.xlsparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsWriter
{

	public static final int NAME_INDEX = 0;
	public static final int BIRTH_INDEX = 1;
	public static final int AGE_INDEX = 2;
	public static final int DIAG_INDEX = 3;
	public static final int ICD9CM_INDEX = 4;
	public static final int SURGERY_INDEX = 5;
	public static final int NOTE_INDEX = 6;
	
	public static final String NOTE_STATUS_AM = "Il campo ATTOCHIRURGICO contiene sia la parola minitoracotomia che la parola sternotomia";
	public static final String NOTE_STATUS_OT = "Non sono rispettati i vincoli di integrità tra i campi CODICEDIAGNOSI e CODICEICD9CM ma il campo ATTOCHIRURGICO contiene la parola minitoracotomia";
	public static final String NOTE_STATUS_OK = "Nessuna";
	
	public void write(List<PatientModel> patients, String filename)
	{
		Workbook wb = new HSSFWorkbook();
		//Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow((short)0);
		// Create a cell and put a value in it.
		row.createCell(NAME_INDEX).setCellValue("NOME");
		row.createCell(BIRTH_INDEX).setCellValue("DATANASCITA");
		row.createCell(AGE_INDEX).setCellValue("ETA");
		row.createCell(DIAG_INDEX).setCellValue("CODICEDIAGNOSI");
		row.createCell(ICD9CM_INDEX).setCellValue("CODICEICD9CM");
		row.createCell(SURGERY_INDEX).setCellValue("DATAINTERVENTO");
		row.createCell(NOTE_INDEX).setCellValue("NOTE");
		
		int size = patients.size();
		for(int i = 0; i < size; i++)
		{
			PatientModel pat = patients.get(i);
			
			row = sheet.createRow(i + 1);
			row.createCell(NAME_INDEX).setCellValue(pat.getName());
			row.createCell(BIRTH_INDEX).setCellValue(pat.getBirthdate());
			row.createCell(AGE_INDEX).setCellValue(pat.getAge(true));
			row.createCell(DIAG_INDEX).setCellValue(pat.getDiag_code());
			row.createCell(ICD9CM_INDEX).setCellValue(pat.getIcd9cm_code());
			row.createCell(SURGERY_INDEX).setCellValue(pat.getSurgery_date());
			
			int state = pat.getStatus();
			row.createCell(NOTE_INDEX).setCellValue
			(
				state == PatientModel.STATUS_AM ?
						 NOTE_STATUS_AM
						 :
				state == PatientModel.STATUS_OT ?
						 NOTE_STATUS_OT
						 :
					     NOTE_STATUS_OK
			);
		}
		// Write the output to a file
		FileOutputStream fileOut;
		try
		{
			fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			wb.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
