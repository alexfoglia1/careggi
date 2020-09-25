package com.alexfoglia.xlsparser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class UtilsTest
{
	@Test
	public void testCount()
	{
		int[] v = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,20,23,27,28,29,30,31,33,37,39,39,39,40};
		double c_1_17 = Utils.count(v, 1, 17); 
		double c_17_19 = Utils.count(v, 17, 19); 
		double c_19_25 = Utils.count(v, 19, 25); 
		double c_26_37 = Utils.count(v, 26, 37); 
		double c_1_37 = Utils.count(v, 1, 37);
		double c_1_38 = Utils.count(v, 1, 38);
		double c_7_7 = Utils.count(v, 7, 7); 
		double c_7_8 = Utils.count(v, 7, 8);
		double c_39_39 = Utils.count(v, 39, 39);
		double c_1_40 = Utils.count(v, 1, 40);
		
		assertTrue(16.5 == c_1_17);
		assertTrue(0.5 == c_17_19);
		assertTrue(2 == c_19_25);
		assertTrue(6.5 == c_26_37);
		assertTrue(25.5 == c_1_37);
		assertTrue(26 == c_1_38);
		assertTrue(1 == c_7_7);
		assertTrue(1 == c_7_8);
		assertTrue(3 == c_39_39);
		assertTrue(v.length == c_1_40);
	}
}
