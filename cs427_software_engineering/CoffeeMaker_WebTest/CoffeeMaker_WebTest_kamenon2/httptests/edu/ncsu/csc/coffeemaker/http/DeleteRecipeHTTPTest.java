package edu.ncsu.csc.coffeemaker.http;

import java.io.IOException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class DeleteRecipeHTTPTest extends TestCase {
	
	public static final String ADDRESS = "http://localhost:8080/CoffeeMaker_WebTest";
	
	public void testDeleteRecipe() {
		try {
			//Go to add_recipe.jsp
			WebConversation webConversation = new WebConversation();
			WebResponse menuResponse = webConversation.getResponse(ADDRESS);
			WebResponse addRecipePage = menuResponse.getLinkWith("Add a recipe").click();
			
			//Fill out form and submit
			addRecipePage.getForms()[0].setParameter("name", "Coffee");
			addRecipePage.getForms()[0].setParameter("price", "50");
			addRecipePage.getForms()[0].setParameter("amtCoffee", "2");
			addRecipePage.getForms()[0].setParameter("amtMilk", "1");
			addRecipePage.getForms()[0].setParameter("amtSugar", "1");
			addRecipePage.getForms()[0].setParameter("amtChocolate", "0");
			addRecipePage = addRecipePage.getForms()[0].submit();
			
			//To to main menu
			menuResponse = addRecipePage.getLinkWith("Back to CoffeeMaker Menu").click();
			
			//To delete recipe
			WebResponse deleteRecipePage = menuResponse.getLinkWith("Delete a Recipe").click();
			deleteRecipePage.getForms()[0].setParameter("recipe", "0");
			deleteRecipePage = deleteRecipePage.getForms()[0].submit();
			assertTrue(deleteRecipePage.getText().contains("Coffee successfully deleted"));
			
		} catch (SAXException e) {
			fail("SAXException: " + e.getMessage());
		} catch (IOException e) {
			fail("IOException: " + e.getMessage());
		}
	}

}
