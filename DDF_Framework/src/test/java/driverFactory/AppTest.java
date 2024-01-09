package driverFactory;

import java.io.File;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil {
	String inputPath = "./FileInput/LoginData.xlsx";
	String outputPath = "./FileOutput/DataDrivenResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable
	
	
	{
		report = new ExtentReports("./target/Report/Login.html");
		boolean res = false;
		// create object for ExcelFileUtil file
		ExcelFileUtil xl = new ExcelFileUtil(inputPath);
		//count no of rows in sheet
		int rc = xl.rowCount("Login");
		Reporter.log("No of rows are : "+ rc , true);
		for( int i=1;i<=rc;i++)
		{
			logger = report.startTest("Validate login");
			String username = xl.getCellData("Login", i, 0);
			String Password = xl.getCellData("Login", i, 1);
			//call login method
			res = FunctionLibrary.adminLogin(username, Password);
			if(res)
			{
				logger.log(LogStatus.PASS, "Login Sucess");
				// if res is true write as  login success in results cell
				xl.setCellData("Login", i, 2, "Login success", outputPath);
				// Write as pass in status cell
				xl.setCellData("Login", i, 3, "Pass", outputPath);
			}
			else
			{
				//Take Screen shot and store
				File screen  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
				logger.log(LogStatus.FAIL, "Login Fail");
				// if res is false write as login fail in result cell
				xl.setCellData("Login", i, 2, "Login Failed", outputPath);
				//Write as Fail in status cell
				xl.setCellData("Login", i, 3, "Fail", outputPath);
			}
			report.endTest(logger);
			report.flush();
		}
		
	}

}
