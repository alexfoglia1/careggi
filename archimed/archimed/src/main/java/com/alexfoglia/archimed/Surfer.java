package com.alexfoglia.archimed;

import java.awt.AWTException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Surfer
{
	public static final String AOUC_URL = "https://portaleaouc.aou-careggi.toscana.it/dana-na/auth/url_0/welcome.cgi";
	private ChromeDriver driver;
	private Keyboard k;
	
	public Surfer()
	{
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		
		driver = new ChromeDriver(options);
		try
		{
			k = new Keyboard();
		} catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean login(String username, String password)
	{
		System.out.println("Navigating to home page");
		driver.get(AOUC_URL);

		System.out.println("Inserting credentials");
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnSubmit")).click();

		System.out.println("Logging in");
		WebElement btnContinue;
		try
		{
			btnContinue =driver.findElement(By.name("btnContinue"));
		}
		catch (Exception e)
		{
			btnContinue = null;
		}
		
		if (btnContinue != null)
		{
			btnContinue.click();
		}

		String title = driver.getTitle();
		while (title.equals("Please wait..."))
		{
			System.out.println("Waiting for page charge");
			safeSleep(1000);
			title = driver.getTitle();
		}

		System.out.println("Entering intranet");
		WebElement intranetBtn;
		try
		{
			intranetBtn = driver.findElement(By.linkText("Intranet Aziendale"));
		}
		catch(Exception e)
		{
			intranetBtn = null;
		}
		if (intranetBtn == null)
		{
			System.out.println("LOGIN FAILED");
			return false;
		}
		else
		{
			System.out.println("LOGIN SUCCESS");
			intranetBtn.click();
			return true;
		}
	}

	private void safeSleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean archimedLogin(String username, String password)
	{
		WebElement archimedBtn = driver.findElement(By.linkText("ArchiMed"));
		archimedBtn.click();
		WebElement goToArchimed = driver.findElement(By.linkText("Vai ad ArchiMed"));
		goToArchimed.click();
		for(int i = 0; i < 10; i++)
		{
			System.out.println("Wait "+(10-i)+ " seconds ...");
			safeSleep(1000);
		}
		k.type(username);
		k.type("\t");
		k.type(password);
		k.type("\n");
		
		List<WebElement> el = driver.findElements(By.cssSelector("*"));

		for(WebElement e:el)
		{
			System.out.println(e.getText());
		}
		return false;
	}

}
