package com.alexfoglia.xlsparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.math3.special.Erf;

public class Utils
{
	public static int getAge(String bdate, String surg_date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date d;
		Date s;
		try
		{
			d = sdf.parse(bdate);
			s = surg_date == null ? null : sdf.parse(surg_date);
		} catch (ParseException e)
		{
			return 0;
		}
		LocalDate l1 = toLocalDate(d);
		
		LocalDate now1 = s == null ? LocalDate.now() : toLocalDate(s);
		return  Period.between(l1, now1).getYears();
	}

	private static LocalDate toLocalDate(Date d)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		return l1;
	}

	public static int max(int[] v)
	{
		int max = v[0];
		for (int i = 1; i < v.length; i++)
		{
			max = max < v[i] ? v[i] : max;
		}
		return max;
	}
	
	public static double max(double[] v)
	{
		double max = v[0];
		for(int i = 1; i < v.length; i++)
		{
			if(max < v[i])
			{
				max = v[i];
			}
		}
		return max;
	}

	public static int min(int[] v)
	{
		int min = v[0];
		for(int i = 1; i < v.length; i++)
		{
			if(min > v[i])
			{
				min = v[i];
			}
		}
		return min;
	}

	public static double stdDev(int[] v)
	{
		double mean = mean(v);
		double sum = 0;
		for(int i = 0; i < v.length; i++)
		{
			sum += Math.pow(v[i] - mean, 2);
		}
		return Math.sqrt(sum/(v.length - 1));
	}

	public static double mean(int[] v)
	{
		double sum = 0;
		for(int i = 0; i < v.length; i++)
		{
			sum += v[i];
		}
		return sum/v.length;
	}
	
	public static double cnorm(double z)
	{
		return 0.5*(1 + Erf.erf(z/Math.sqrt(2)));
	}
	
	public static double count(int[] v, int leftBound, int rightBound)
	{
		double count = 0;
		int max = max(v);
		int min = min(v);
		if(leftBound == rightBound)
		{
			int x = leftBound;
			for(int i = 0; i < v.length; i++)
			{
				if(v[i] == x)
				{
					count += 1;
				}
			}
			return count;
		}
		for(int i = 0; i < v.length; i++)
		{
			if(v[i] > leftBound && v[i] < rightBound)
			{
				count += 1;
			}
			else if(v[i] == rightBound)
			{
				if(v[i] == max)
				{
					count += 1;
				}
				else
				{
					count += 0.5;
				}
			}
			else if(v[i] == leftBound)
			{
				if(v[i] == min)
				{
					count += 1;
				}
				else
				{
					count += 0.5;
				}
			}
		}
		
		return count;
	}

}
