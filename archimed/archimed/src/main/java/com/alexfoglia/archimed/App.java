package com.alexfoglia.archimed;

public class App 
{
    public static void main( String[] args )
    {
    	System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    	Surfer s = new Surfer();
    	boolean loginOk = s.login("cabruccif", "18051993");
    	if(loginOk)
    	{
    		boolean archimedLogin = s.archimedLogin("cabruccif", "Bb.18051993");
    	}
    }
}
