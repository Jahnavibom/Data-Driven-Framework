package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class FunctionLibrary extends AppUtil {
// method for login
	public static boolean adminLogin(String user, String pass)
	{
		driver.get(conpro.getProperty("Url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(conpro.getProperty("objreset"))).click();
		driver.findElement(By.xpath(conpro.getProperty("objuser"))).sendKeys(user);
		driver.findElement(By.xpath(conpro.getProperty("objpass"))).sendKeys(pass);
		driver.findElement(By.xpath(conpro.getProperty("objsudmit"))).click();
		String Expected = "dashboard";
		String Actual = driver.getCurrentUrl();
		if(Actual.contains(Expected))
		{
			Reporter.log("Login Sucessful :      "+ Expected + "   "+ Actual,true );
			//click logout link
			driver.findElement(By.xpath(conpro.getProperty("objLogout"))).click();
			return true;
		}
		else 
		{
			// capture error message
			String errorMsg = driver.findElement(By.xpath(conpro.getProperty("objError"))).getText();
			Reporter.log("Login not sucessful "+ errorMsg + "  "+ Expected +  "  "+ Actual,true);
			driver.findElement(By.xpath(conpro.getProperty("objokbtn"))).click();
			return false;
		}
				
	}
}
