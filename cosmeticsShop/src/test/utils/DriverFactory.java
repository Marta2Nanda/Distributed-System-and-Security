package test.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.logging.Logger;

public class DriverFactory {

	private final static Logger log = Logger.getLogger(DriverFactory.class.getName());
	
	public static WebDriver open (String browserType){
		if(browserType.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\marta\\Desktop\\Programowanie\\chromedriver.exe");
			return new ChromeDriver();
		} else {
			log.info("line15 ------ no driver found!--------");
			return null;
			
		}
			
	}
}
