package com.build.qa.build.selenium.pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.framework.BaseFramework;

public abstract class BasePage extends BaseFramework {
	
	public BasePage(WebDriver driver, Wait<WebDriver> wait) { 
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}
	
	public void closeSpalshScreenIfDisplayed(){
		
		try
		{
		WebElement closeButton=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#email-subscribe-splash button")));
		if(closeButton!=null)
			{
				closeButton.click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#email-subscribe-splash")));
			}
		}
		catch(org.openqa.selenium.TimeoutException ex)
		{
			
		}
		
	}
}
