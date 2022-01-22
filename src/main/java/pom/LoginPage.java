package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[@id='nav-link-accountList-nav-line-1']")
	public WebElement signinButton;

	@FindBy(xpath = "//input[@id='ap_email']")
	public WebElement userName;

	@FindBy(xpath = "//input[@id='continue']")
	public WebElement continueButton;

	@FindBy(xpath = "//input[@id='ap_password']")
	public WebElement password;

	@FindBy(xpath = "//input[@name='rememberMe']")
	public WebElement rememberMe;

	@FindBy(xpath = "//input[@id='signInSubmit']")
	public WebElement loginButton;

}
