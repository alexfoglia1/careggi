package com.alexfoglia.xlsparser;

import java.util.List;

public class PatientDBInterfaceImpl extends PatientDBInterface
{
	public PatientDBInterfaceImpl(List<PatientModel> database)
	{
		this.database = database;
	}

}
