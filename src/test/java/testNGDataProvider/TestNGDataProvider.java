package testNGDataProvider;

import org.testng.annotations.DataProvider;

public class TestNGDataProvider {

	@DataProvider(name = "test-data")
	public static Object[][] dataProvFunc() {
		return new Object[][] { { "Lambda Test" }, { "Automation" } };
	}
}
