package smoketests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import test.utils.DriverFactory;

public class RegistrationDemo {
	
	public static void main(String[] args) {
		String browserType = "chrome";
		WebDriver driver;
		driver = DriverFactory.open(browserType);
		
		driver.get("http://localhost:8080/cosmeticsShop/registration.html");
		
		final String clientId= "TestUser";
		
		driver.findElement(By.name("clientId")).sendKeys(clientId);
		driver.findElement(By.name("registerButton")).click();
		
		String confirmation = driver.findElement(By.id("welcome")).getText();
		System.out.println("Conf " + confirmation);

	}
}
