package main;

import java.io.FileInputStream;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import pom.LoginPage;

public class ClassProperties {

	public static WebDriver driver;
	FileInputStream readProperty;
	JavascriptExecutor js;
	LoginPage loginPage;
}
