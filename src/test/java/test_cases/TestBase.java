package test_cases;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.PageBase;

public class TestBase {
	//npm install -g newman      -   newman run <collection name> --data <file name> -n <no of iterations> -d <delay time> -e <environment name>
	
	// define main properties
	public static WebDriver driver;
	FileInputStream readProperty;
	public static Properties prop;
	public static ChromeOptions options;
	JavascriptExecutor js;
	LoginPage loginPage;

	@Parameters("browser")
	@BeforeTest
	public void prepareClassProperties(String browser) throws IOException, AWTException {
		readProperty = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\generalProperties.properties");
		prop = new Properties();
		prop.load(readProperty);
		options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-web-security");
		options.addArguments("--no-proxy-server");

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		options.setExperimentalOption("prefs", prefs);
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });

		if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + prop.getProperty("firefoxdriver"));
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + prop.getProperty("chromedriver"));
			driver = new ChromeDriver(options);
		} else {
			throw new IllegalArgumentException("Invalid browser value!!");
			// Change thread count 1 for sequential , 2 for parallel 3 ..browser..
		}

		js = (JavascriptExecutor) driver;
		loginPage = new LoginPage(driver);
	}

	@Test(priority = 1, groups = "smoke", description = "Start Amazon Web Application")
	private void startApplication() throws InterruptedException {
		// Mazimize current window
		driver.manage().window().maximize();
		// navigate to website
		driver.get("https://www.amazon.com/");
		// take screenshot to login page
		PageBase.captureScreenshot(driver, "HomePage");
	}

	@Test(priority = 2, groups = "smoke", description = "Login to Amazon Web Application")
	private void login() throws InterruptedException {
		// click sign button
		loginPage.signinButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// add username
		loginPage.userName.sendKeys(PageBase.getExcelData(1, 0, "Sheet1"));
		// click continue button
		loginPage.continueButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// add password
		loginPage.password.sendKeys(PageBase.getExcelData(1, 1, "Sheet1"));
		// click login button
		loginPage.loginButton.click();
		// wait for 5 sec
		Thread.sleep(5000);
		// take screenshot to login page
		PageBase.captureScreenshot(driver, "verifyLogin");
		// wait for 5 sec
		Thread.sleep(5000);
		// verify login successfully
		Assert.assertEquals(driver.getPageSource().contains("Hello, Shady"), true);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
//		String report = System.getProperty("user.dir") + "\\test-output\\index.html";
//		driver.get(report);
	}

	public static void getScreenshotOnFailure() {
		PageBase.captureScreenshot(driver, "fail" + java.time.LocalTime.now());
	}

}
