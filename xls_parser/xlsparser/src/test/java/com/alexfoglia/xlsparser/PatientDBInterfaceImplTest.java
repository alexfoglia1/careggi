package com.alexfoglia.xlsparser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PatientDBInterfaceImplTest
{
	private PatientDBInterfaceImpl fixture;
	private List<PatientModel> db;
	private static final char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	@Before
	public void setup()
	{
		this.db = new ArrayList<>();
		this.fixture = new PatientDBInterfaceImpl(db);
	}
	
	@Test
	public void testFindByStateWhenStateIsZeroResultLengthZeroEmptySet()
	{
		assertEquals(0, this.fixture.searchByStatus(0).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsOneResultLengthZeroEmptySet()
	{
		assertEquals(0, this.fixture.searchByStatus(1).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsTwoResultLengthZeroEmptySet()
	{
		assertEquals(0, this.fixture.searchByStatus(2).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsZeroResultLengthZeroSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(1);
		this.db.add(test1);
		
		assertEquals(0, this.fixture.searchByStatus(0).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsOneResultLengthZeroSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		this.db.add(test1);
		
		assertEquals(0, this.fixture.searchByStatus(1).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsTwoResultLengthZeroSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		this.db.add(test1);
		
		assertEquals(0, this.fixture.searchByStatus(2).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsZeroResultLengthZeroSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(1);
		test2.setStatus(2);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(0, this.fixture.searchByStatus(0).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsOneResultLengthZeroSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		test2.setStatus(2);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(0, this.fixture.searchByStatus(1).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsTwoResultLengthZeroSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		test2.setStatus(1);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(0, this.fixture.searchByStatus(2).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsZeroResultLengthOneSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		this.db.add(test1);
		
		assertEquals(1, this.fixture.searchByStatus(0).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsOneResultLengthOneSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(1);
		this.db.add(test1);
		
		assertEquals(1, this.fixture.searchByStatus(1).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsTwoResultLengthOneSetLengthOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		test1.setStatus(2);
		this.db.add(test1);
		
		assertEquals(1, this.fixture.searchByStatus(2).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsZeroResultLengthOneSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		test2.setStatus(1);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(1, this.fixture.searchByStatus(0).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsOneResultLengthOneSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		test2.setStatus(1);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(1, this.fixture.searchByStatus(1).size());
	}
	
	@Test
	public void testFindByStateWhenStateIsTwoResultLengthOneSetLengthMoreThanOne()
	{
		PatientModel test1 = new PatientModel("","","","","","","");
		PatientModel test2 = new PatientModel("","","","","","","");
		test1.setStatus(0);
		test2.setStatus(2);
		this.db.add(test1);
		this.db.add(test2);
		
		assertEquals(1, this.fixture.searchByStatus(2).size());
	}
	
	@Test
	public void testFindByStateGeneralCase()
	{
		int nZeros = 1+(int)(Math.random()*20);
		int nOnes = 1+(int)(Math.random()*20);
		int nTwos = 1+(int)(Math.random()*20);
		
		List<PatientModel> expectedZeros = new ArrayList<>();
		List<PatientModel> expectedOnes = new ArrayList<>();
		List<PatientModel> expectedTwos = new ArrayList<>();
		
		for(int i = 0; i < nZeros; i++)
		{
			PatientModel test = new PatientModel(randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)));
			test.setStatus(0);
			db.add(test);
			expectedZeros.add(test);
		}
		for(int i = 0; i < nOnes; i++)
		{
			PatientModel test = new PatientModel(randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)));
			test.setStatus(1);
			db.add(test);
			expectedOnes.add(test);
		}
		for(int i = 0; i < nTwos; i++)
		{
			PatientModel test = new PatientModel(randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)),randomString(1+(int)(Math.random()*20)));
			test.setStatus(2);
			db.add(test);
			expectedTwos.add(test);
		}
		
		List<PatientModel> actualZeros = this.fixture.searchByStatus(0);
		List<PatientModel> actualOnes = this.fixture.searchByStatus(1);
		List<PatientModel> actualTwos = this.fixture.searchByStatus(2);
		
		assertEquals(nZeros, actualZeros.size());
		assertEquals(nOnes, actualOnes.size());
		assertEquals(nTwos, actualTwos.size());
		
		for(int i = 0; i < nZeros; i++)
		{
			assertEquals(expectedZeros.get(i), actualZeros.get(i));
		}
		for(int i = 0; i < nOnes; i++)
		{
			assertEquals(expectedOnes.get(i), actualOnes.get(i));
		}
		for(int i = 0; i < nTwos; i++)
		{
			assertEquals(expectedTwos.get(i), actualTwos.get(i));
		}
	}
	

	private String randomString(int length)
	{
		char[] v = new char[length];
		for(int i = 0; i < v.length; i++)
		{
			int randomIndex = (int)(Math.random()*alpha.length);
			v[i] = alpha[randomIndex];
		}
		
		return new String(v);
	}

}
