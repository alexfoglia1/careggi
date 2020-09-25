package com.alexfoglia.xlsparser;

public class PatientModel
{
	public static final int STATUS_OK = 0;
	public static final int STATUS_AM = 1;
	public static final int STATUS_OT = 2;
	public static final int STATUS_CU = 3;
	
	private String name, age, age_surg, birthdate, diag_code, icd9cm_code, surgery_date;
	private int status;
	
	public PatientModel(String name, String age, String birthdate, String diag_code, String icd9cm_code, String surgery_date, String age_surg)
	{
		this.name = name;
		this.age = age;
		this.birthdate = birthdate;
		this.diag_code = diag_code;
		this.icd9cm_code = icd9cm_code;
		this.surgery_date = surgery_date;
		this.age_surg = age_surg;
		this.status = 0;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((age_surg == null) ? 0 : age_surg.hashCode());
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((diag_code == null) ? 0 : diag_code.hashCode());
		result = prime * result + ((icd9cm_code == null) ? 0 : icd9cm_code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + status;
		result = prime * result + ((surgery_date == null) ? 0 : surgery_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientModel other = (PatientModel) obj;
		if (age == null)
		{
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (age_surg == null)
		{
			if (other.age_surg != null)
				return false;
		} else if (!age_surg.equals(other.age_surg))
			return false;
		if (birthdate == null)
		{
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (diag_code == null)
		{
			if (other.diag_code != null)
				return false;
		} else if (!diag_code.equals(other.diag_code))
			return false;
		if (icd9cm_code == null)
		{
			if (other.icd9cm_code != null)
				return false;
		} else if (!icd9cm_code.equals(other.icd9cm_code))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		if (surgery_date == null)
		{
			if (other.surgery_date != null)
				return false;
		} else if (!surgery_date.equals(other.surgery_date))
			return false;
		return true;
	}

	public String getName()
	{
		return name;
	}

	public String getAge(boolean now)
	{
		return now ? age : age_surg;
	}

	public String getBirthdate()
	{
		return birthdate;
	}

	public String getDiag_code()
	{
		return diag_code;
	}

	public String getIcd9cm_code()
	{
		return icd9cm_code;
	}

	public String getSurgery_date()
	{
		return surgery_date;
	}

	@Override
	public String toString()
	{
		return "PatientModel [name=" + name + ", age=" + age + ", birthdate=" + birthdate + ", diag_code=" + diag_code
				+ ", icd9cm_code=" + icd9cm_code + ", surgery_date=" + surgery_date + "]";
	}
	
	public int getStatus()
	{
		return this.status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	

}
