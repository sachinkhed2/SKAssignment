package com.build.qa.build.selenium.pageobjects.headerpage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;
import com.build.qa.build.selenium.pageobjects.cartpage.CartPage;
import com.build.qa.build.selenium.pageobjects.productpage.ProductPage;

public class HeaderPage extends BasePage {
	
	
	private By searchBar;
	private By cart;
	

	public HeaderPage(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
		
		searchBar=By.cssSelector("#search_txt");
		cart=By.cssSelector("a[class*=header-cart]");
	}
	
	
	public ProductPage searchProduct(String searchValue)
	{
		WebElement search=wait.until(ExpectedConditions.presenceOfElementLocated(searchBar));
		search.sendKeys(searchValue);
		search.sendKeys(Keys.RETURN);
		return new ProductPage(driver, wait);
	}
	
	public CartPage openCart()
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(cart)).click();
		return new CartPage(driver, wait);
	}
	
	
}
