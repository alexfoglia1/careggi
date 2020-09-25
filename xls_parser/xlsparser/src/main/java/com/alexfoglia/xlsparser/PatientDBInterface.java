package com.alexfoglia.xlsparser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class PatientDBInterface
{
	protected List<PatientModel> database;
	
	public final List<PatientModel> searchByStatus(int status)
	{
		List<PatientModel> result = new ArrayList<>();
		Predicate<PatientModel> predicate = p -> p.getStatus() == status;
		database.forEach((p) -> {if(predicate.test(p)) result.add(p);});
		
		return result;
	}
	
	public final int[] searchAges(boolean now)
	{
		int[] ages = new int[database.size()];
		for(int i = 0; i < ages.length; i++)
		{
			ages[i] = Integer.parseInt(database.get(i).getAge(now));
		}
		
		return ages;
	}

}
