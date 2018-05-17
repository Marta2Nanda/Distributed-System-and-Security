package smoketests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import test.utils.DriverFactory;

public class RegistrationTest {

	private String webURL;
	private WebDriver driver;
	private final String clientId= "TestUser";
	private final String duplicateClientId= "Marta";
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Before 
	public void setUp() {
		webURL = "http://localhost:8080/cosmeticsShop/registration.html";
		driver = DriverFactory.open("chrome");
		driver.get(webURL);
	}
	
	@Test
	public void testCorrectRegistration() throws Exception {
		try {
			driver.get(webURL);
			Thread.sleep(3000);
			driver.findElement(By.name("clientId")).clear();
			driver.findElement(By.name("clientId")).sendKeys(clientId);
			driver.findElement(By.name("registerButton")).click();
			
			// welcome page
			String actualUrl = driver.getCurrentUrl();
			String expectedUrl = "http://localhost:8080/cosmeticsShop/welcome.html";
			assertEquals(actualUrl, expectedUrl);
			//Storing Title name in the String variable
			String title = driver.getTitle();
			assertEquals("Welcome", title);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=SQLException.class)
	public void testDuplicateNameRegistration() throws Exception {
		try {
			driver.get(webURL);
			Thread.sleep(3000);
			driver.findElement(By.name("clientId")).clear();
			driver.findElement(By.name("clientId")).sendKeys(duplicateClientId);
			driver.findElement(By.name("registerButton")).click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	@After
	public void tearDown() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
