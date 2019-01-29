package edu.ncsu.csc.coffeemaker.http;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import junit.framework.TestCase;

public class AddRecipeHTTPTest extends TestCase {
	
	/** The URL for CoffeeMaker - change as needed */
	public static final String ADDRESS = "http://localhost:8080/CoffeeMaker_WebTest";
	
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMenuPage() {
		try {
			WebConversation webConversation = new WebConversation();
			WebResponse menuResponse = webConversation.getResponse(ADDRESS);
			assertEquals("CoffeeMaker", menuResponse.getTitle());
		} catch (SAXException e) {
			fail("SAXException: " + e.getMessage());
		} catch (IOException e) {
			fail("IOException: " + e.getMessage());
		}
	}
	
	public void testAddRecipe1() throws Exception {
		//Go to add_recipe.jsp
		WebConversation webConversation = new WebConversation();
		WebResponse menuResponse = webConversation.getResponse(ADDRESS);
		WebResponse addRecipePage = menuResponse.getLinkWith("Add a recipe").click();
		
		//Fill out form and submit
		addRecipePage.getForms()[0].setParameter("name", "Coffee");
		addRecipePage.getForms()[0].setParameter("price", "50");
		addRecipePage.getForms()[0].setParameter("amtCoffee", "3");
		addRecipePage.getForms()[0].setParameter("amtMilk", "1");
		addRecipePage.getForms()[0].setParameter("amtSugar", "1");
		addRecipePage.getForms()[0].setParameter("amtChocolate", "0");
		addRecipePage = addRecipePage.getForms()[0].submit();
		
		//Test that Recipe added
		assertTrue(addRecipePage.getText().contains("Coffee successfully added."));
		
		//Go to main menu
		menuResponse = addRecipePage.getLinkWith("Back to CoffeeMaker Menu").click();
	}

}
