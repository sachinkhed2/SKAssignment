package com.build.qa.build.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.build.qa.build.selenium.framework.BaseFramework;
import com.build.qa.build.selenium.pageobjects.cartpage.CartPage;
import com.build.qa.build.selenium.pageobjects.categorydroppage.CategoryDropPage;
import com.build.qa.build.selenium.pageobjects.headerpage.HeaderPage;
import com.build.qa.build.selenium.pageobjects.homepage.HomePage;
import com.build.qa.build.selenium.pageobjects.productpage.ProductPage;

public class BuildTest extends BaseFramework { 
	
	/** 
	 * Extremely basic test that outlines some basic
	 * functionality and page objects as well as assertJ
	 */
	@Test
	public void navigateToHomePage() { 
		driver.get(getConfiguration("HOMEPAGE"));
		HomePage homePage = new HomePage(driver, wait);
		
		softly.assertThat(homePage.onBuildTheme())
			.as("The website should load up with the Build.com desktop theme.")
			.isTrue();
	}
	
	/** 
	 * Search for the Quoizel MY1613 from the search bar
	 * @assert: That the product page we land on is what is expected by checking the product title
	 * @difficulty Easy
	 */
	@Test
	public void searchForProductLandsOnCorrectProduct() { 
		driver.get(getConfiguration("KITCHENSINKPAGE"));
		HeaderPage headerPage=new HeaderPage(driver, wait);
		headerPage.closeSpalshScreenIfDisplayed();
		ProductPage productPage= headerPage.searchProduct("Quoizel MY1613");
		
		//You might want to use Contains or Equals instead of startswith based on data input strategy
		softly.assertThat(productPage.productTitle().startsWith("Quoizel MY1613"))
		.as("The Product Title should match the searched item")
		.isTrue();
	}
	
	/** 
	 * Go to the Bathroom Sinks category directly (https://www.build.com/bathroom-sinks/c108504) 
	 * and add the second product on the search results (Category Drop) page to the cart.
	 * @assert: the product that is added to the cart is what is expected
	 * @difficulty Easy-Medium
	 */
	@Test
	public void addProductToCartFromCategoryDrop() { 
		driver.get(getConfiguration("KITCHENSINKPAGE"));
		CategoryDropPage categoryDropPage =new CategoryDropPage(driver,wait);
		categoryDropPage.closeSpalshScreenIfDisplayed();
		
		//Get the second product displayed and store the name and ID for verification later
		WebElement product=categoryDropPage.getDisplayedProducts().get(1);
		String productID=product.findElement(By.cssSelector("span[itemprop='productID']")).getText();
		String productName=product.findElement(By.cssSelector("span[itemprop='name']")).getText();
		product.click();
		
		//Add to Cart
		ProductPage productPage  =new ProductPage(driver, wait);
		productPage.closeChatScreenIfDisplayed();
		productPage.addItemToCart();
		
		//Verify item exists in cart
		HeaderPage headerPage=new HeaderPage(driver, wait);
		CartPage cartPage=headerPage.openCart();
		softly.assertThat(cartPage.productExistsInCart(productName+" "+productID ))
		.as("The Product added to the cart should m")
		.isTrue();
		
	}
	
	/** 
	 * Add a product to the cart and email the cart to yourself, also to my email address: jgilmore+SeleniumTest@build.com
	 * Include this message in the "message field" of the email form: "This is {yourName}, sending you a cart from my automation!"
	 * @assert that the "Cart Sent" success message is displayed after emailing the cart
	 * @difficulty Medium-Hard
	 */
	@Test
	public void addProductToCartAndEmailIt() { 
		
		driver.get(getConfiguration("KITCHENSINKPAGE"));
		HeaderPage headerPage=new HeaderPage(driver, wait);
		headerPage.closeSpalshScreenIfDisplayed();
		
		ProductPage productPage= headerPage.searchProduct("Quoizel MY1613");
		productPage.closeChatScreenIfDisplayed();
		productPage.addItemToCart();
		CartPage cartPage=productPage.proceedToCart();
		//CartPage cartPage=new CartPage(driver, wait);
		cartPage.emailCart("Sachin Khed", "Sachin.Khed@gmail.com", "Sachin Khed", "Sachin.Khed@gmail.com", "jgilmore+SeleniumTest@build.com", "Hello");
		
		softly.assertThat(cartPage.verifyCartEmailSentMessage())
		.as("Cart successfully Sent Via Email message should be displayed")
		.isTrue();
		
	}
	
	/** 
	 * Go to a category drop page (such as Bathroom Faucets) and narrow by
	 * at least two filters (facets), e.g: Finish=Chromes and Theme=Modern
	 * @assert that the correct filters are being narrowed, and the result count
	 * is correct, such that each facet selection is narrowing the product count.
	 * @difficulty Hard
	 */
	@Test
	public void facetNarrowBysResultInCorrectProductCounts() { 
		//
		driver.get(getConfiguration("KITCHENSINKPAGE"));
		CategoryDropPage categoryDropPage =new CategoryDropPage(driver,wait);
		categoryDropPage.closeSpalshScreenIfDisplayed();
		
		int initialCount=categoryDropPage.getDisplayedProductCount();
		
		categoryDropPage.applyFilter("Sink Shape", "Rectangular");
		
		int countAfterFilter1=categoryDropPage.getDisplayedProductCount();
		categoryDropPage.applyFilter("Installation Type", "Vessel");
		int countAfterFilter2=categoryDropPage.getDisplayedProductCount();
		softly.assertThat(countAfterFilter1>countAfterFilter2)
		.as("Filters should reduce the displayed product count")
		.isTrue();
		
		
	}
}