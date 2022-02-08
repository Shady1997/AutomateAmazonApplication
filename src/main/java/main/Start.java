package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.LoginPage;
import utility.ExcelUtility;
import utility.Utility;

public class Start extends ClassProperties {
	//npm install -g newman      -   newman run <collection name> --data <file name> -n <no of iterations> -d <delay time> -e <environment name>

	@Parameters("browser")
	@BeforeTest
	private void prepareClassProperties(String browser) throws IOException {
		readProperty = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\properties\\generalProperties.properties");
		Properties prop = new Properties();
		prop.load(readProperty);

		if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + prop.getProperty("firefoxdriver"));
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + prop.getProperty("chromedriver"));
			driver = new ChromeDriver();
		} else {
			throw new IllegalArgumentException("Invalid browser value!!");
			// Change thread count 1 for sequential , 2 for parallel 3 ..browser..
		}

		js = (JavascriptExecutor) driver;
		loginPage = new LoginPage(driver);
	}

	@Test(priority = 1, groups = "smoke")
	private void startApplication() throws InterruptedException {
		// Mazimize current window
		driver.manage().window().maximize();
		// navigate to website
		driver.get("https://www.amazon.com/");
		// take screenshot to login page
		Utility.captureScreenshot(driver, "LoginPage");
	}

	@Test(priority = 2, groups = "smoke")
	private void login() throws InterruptedException {
		// click sign button
		loginPage.signinButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// add username
		loginPage.userName.sendKeys(ExcelUtility.getUserName());
		// click continue button
		loginPage.continueButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// add password
		loginPage.password.sendKeys(ExcelUtility.getPassword());
		// click login button
		loginPage.loginButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// take screenshot to login page
		Utility.captureScreenshot(driver, "verifyLogin");
		// wait for 5 sec
		Thread.sleep(5000);
		// verify login successfully
		Assert.assertEquals(driver.getPageSource().contains("Hello, Shady"), true);
	}

	@AfterTest
	private void closeApplication() {
		driver.quit();
	}

	public static void getScreenshotOnFailure() {
		Utility.captureScreenshot(driver, "fail");
	}

}
