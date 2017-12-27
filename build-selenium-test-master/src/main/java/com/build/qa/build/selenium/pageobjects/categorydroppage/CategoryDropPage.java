package com.build.qa.build.selenium.pageobjects.categorydroppage;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class CategoryDropPage extends BasePage{
	
	
private By products;
	
	public CategoryDropPage(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
		products = By.cssSelector("ul#category-product-drop li.product-tile-container");
	}
	
	public void applyFilter(String facetGroup, String facet)
	{
		expandFacetGroupIfNeeded(facetGroup);
		String cssText="label[data-facet-group='"+facetGroup+"'][data-facet-value='"+facet+"']";
		WebElement filter=getElement(By.cssSelector(cssText));
		filter.click();
		
	}
	
	private void expandFacetGroupIfNeeded(String facetGroup)
	{
		String cssText="li[data-groupname='"+facetGroup+"']";
		WebElement filter=getElement(By.cssSelector(cssText));
		String text=filter.getAttribute("class");
		if(!text.endsWith("active"))
			filter.click();
		
	}
	
	
	public List<WebElement> getDisplayedProducts()
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(products));
		List<WebElement> mylist=driver.findElements(products);
		return mylist;
	
	}
	
	
	public int getDisplayedProductCount()
	{
		String productCount="";
		Number i=-1;
		By prodCount=By.cssSelector("div#category-content span.total span.js-num-results");
		
		WebElement results=getElement(prodCount);
		
		if (results==null)
			return -1;
		
		productCount=results.getText();	
		try {
			 i=  NumberFormat.getNumberInstance(java.util.Locale.US).parse(productCount);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i.intValue();
	}

	

}
