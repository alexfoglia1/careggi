package com.alexfoglia.xlsparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class XlsParser
{
	public static final String NAME = "PAZIENTE";
	public static final String BIRTHDATE = "DATANASCITAPAZIENTE";
	public static final String DIAGCODE = "CODICEDIAGNOSI";
	public static final String ICD9CM = "CODICEICD9CM";
	public static final String SURGERYDATE = "DATAINTERVENTO";
	public static final String SURG_DESC = "ATTOCHIRURGICO";
	public static final String VALVEDISEASE = "424.0";

	public static final Set<String> VALID_ICD9CM = buildValidIcd9cmCodes();

	private static final Set<String> buildValidIcd9cmCodes()
	{
		Set<String> validIcd9cmCodes = new HashSet<>();
		validIcd9cmCodes.add("35.24");
		validIcd9cmCodes.add("35.23");
		validIcd9cmCodes.add("35.12");
		return validIcd9cmCodes;
	}

	private int maxrowindex;
	private int name_index;
	private int birth_index;
	private int diag_index;
	private int icd9cm_index;
	private int surgery_index;
	private int desc_index;

	public XlsParser()
	{
		this.maxrowindex = -1;
		this.name_index = -1;
		this.birth_index = -1;
		this.diag_index = -1;
		this.icd9cm_index = -1;
		this.surgery_index = -1;
		this.desc_index = -1;
	}
	
	public List<PatientModel> parseCustom(String filepath, String wdiagcode, String wicd9cm, String wtxtPresent, String wtxtNotPresent) throws IllegalArgumentException
	{
		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = 0;
			int tmp = 0;

			for (int i = 0; i < 10 || i < rows; i++)
			{
				row = sheet.getRow(i);
				if (row != null)
				{
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			row = sheet.getRow(0);
			this.maxrowindex = row.getPhysicalNumberOfCells();

			for (int c = 0; c < cols; c++)
			{
				cell = row.getCell((short) c);
				String cell_text = cell.toString();

				switch (cell_text)
				{
				case NAME:
					this.name_index = c;
					break;
				case BIRTHDATE:
					this.birth_index = c;
					break;
				case DIAGCODE:
					this.diag_index = c;
					break;
				case ICD9CM:
					this.icd9cm_index = c;
					break;
				case SURGERYDATE:
					this.surgery_index = c;
					break;
				case SURG_DESC:
					this.desc_index = c;
					break;
				default:
					break;

				}
			}
			
			if(this.name_index == -1 || this.birth_index == -1 || this.diag_index == -1 || this.icd9cm_index == -1 || this.surgery_index == -1 || this.desc_index == -1)
			{
				wb.close();
				throw new IllegalArgumentException("Missing field(s) in given xls");
			}
			
			this.maxrowindex = Utils.max(
					new int[] { name_index, birth_index, diag_index, icd9cm_index, surgery_index, desc_index });

			List<PatientModel> patients = new ArrayList<>();
			List<PatientModel> suspicious = new ArrayList<>();
			List<PatientModel> onlyTorac = new ArrayList<>();
			for (int r = 1; r < rows; r++)
			{
				row = sheet.getRow(r);

				if (row != null && isValid(row))
				{
					String name = row.getCell(name_index).toString();
					String birthdate = row.getCell(birth_index).toString().replace(" ", "").replace("00:00:00", "");
					String age = "" + Utils.getAge(birthdate, null);
					String diagcode = row.getCell(diag_index).toString();
					String icd9cm = row.getCell(icd9cm_index).toString();
					String surgerydate = row.getCell(surgery_index).toString().replace(" ", "").replace("00:00:00", "");
					String agesurg = "" + Utils.getAge(birthdate, surgerydate);
					PatientModel patient = new PatientModel(name, age.replace("ANNI", ""),
							birthdate, diagcode, icd9cm, surgerydate, agesurg);
					patient.setStatus(PatientModel.STATUS_CU);
					boolean addPatient = true;
					if(wdiagcode != null && !wdiagcode.equals(diagcode))
					{
						addPatient = false;
					}
					if(wicd9cm != null && !wicd9cm.equals(icd9cm))
					{
						addPatient = false; 
					}
					
				}
			wb.close();
			}
			return patients;
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
	}

	public List<PatientModel> parse(String filepath) throws IllegalArgumentException
	{
		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = 0;
			int tmp = 0;

			for (int i = 0; i < 10 || i < rows; i++)
			{
				row = sheet.getRow(i);
				if (row != null)
				{
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			row = sheet.getRow(0);
			this.maxrowindex = row.getPhysicalNumberOfCells();

			for (int c = 0; c < cols; c++)
			{
				cell = row.getCell((short) c);
				String cell_text = cell.toString();

				switch (cell_text)
				{
				case NAME:
					this.name_index = c;
					break;
				case BIRTHDATE:
					this.birth_index = c;
					break;
				case DIAGCODE:
					this.diag_index = c;
					break;
				case ICD9CM:
					this.icd9cm_index = c;
					break;
				case SURGERYDATE:
					this.surgery_index = c;
					break;
				case SURG_DESC:
					this.desc_index = c;
					break;
				default:
					break;

				}
			}
			
			if(this.name_index == -1 || this.birth_index == -1 || this.diag_index == -1 || this.icd9cm_index == -1 || this.surgery_index == -1 || this.desc_index == -1)
			{
				wb.close();
				throw new IllegalArgumentException("Missing field(s) in given xls");
			}
			
			this.maxrowindex = Utils.max(
					new int[] { name_index, birth_index, diag_index, icd9cm_index, surgery_index, desc_index });

			List<PatientModel> patients = new ArrayList<>();
			List<PatientModel> suspicious = new ArrayList<>();
			List<PatientModel> onlyTorac = new ArrayList<>();
			for (int r = 1; r < rows; r++)
			{
				row = sheet.getRow(r);

				if (row != null && isValid(row))
				{
					String name = row.getCell(name_index).toString();
					String birthdate = row.getCell(birth_index).toString().replace(" ", "").replace("00:00:00", "");
					String age = "" + Utils.getAge(birthdate, null);
					String diagcode = row.getCell(diag_index).toString();
					String icd9cm = row.getCell(icd9cm_index).toString();
					String surgerydate = row.getCell(surgery_index).toString().replace(" ", "").replace("00:00:00", "");
					String agesurg = "" + Utils.getAge(birthdate, surgerydate);
					
					PatientModel patient = new PatientModel(name, age.replace("ANNI", ""),
							birthdate, diagcode, icd9cm, surgerydate, agesurg);
					if (icd9cmMeetsDiagCode(row))
					{
						if (miniTorachAndNotChest(row))
						{
							patient.setStatus(PatientModel.STATUS_OK);
							patients.add(patient);
						} else if (miniTorachAndChest(row))
						{
							patient.setStatus(PatientModel.STATUS_AM);
							suspicious.add(patient);
						}
					} else if (onlyTorach(row))
					{
						patient.setStatus(PatientModel.STATUS_OT);
						onlyTorac.add(patient);
					}
				}
			}
			patients.addAll(suspicious);
			patients.addAll(onlyTorac);

			wb.close();

			return patients;
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
	}

	
	private boolean onlyTorach(HSSFRow row)
	{
		String desc = row.getCell(desc_index).toString().toLowerCase();

		return desc.contains("minitoracotomia");
	}

	private boolean icd9cmMeetsDiagCode(HSSFRow row)
	{
		return row.getCell(diag_index).toString().equals(VALVEDISEASE)
				&& VALID_ICD9CM.contains(row.getCell(icd9cm_index).toString());
	}

	private boolean miniTorachAndNotChest(HSSFRow row)
	{
		String desc = row.getCell(desc_index).toString().toLowerCase();

		return desc.contains("minitoracotomia") && !desc.contains("sternotomia");
	}

	private boolean miniTorachAndChest(HSSFRow row)
	{
		String desc = row.getCell(desc_index).toString().toLowerCase();

		return desc.contains("minitoracotomia") && desc.contains("sternotomia");
	}

	private boolean isValid(HSSFRow row)
	{
		return row.getPhysicalNumberOfCells() >= maxrowindex;
	}
	
	
}
