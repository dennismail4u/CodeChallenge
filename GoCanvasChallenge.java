package TestGoCanvas;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoCanvasChallenge {

	private static WebDriver driver = null;
	private static WebDriverWait wait = null;
	private static String driverPathChrome = "/Users/DennisThomas/Documents/workspace/Drivers/chromedriver";
	private static String url = "https://www.gocanvas.com/";

	public static void main(String[] args) {
		initialize();
		login();
		createApp();
		fillFormPage();
		saveAndPublish();
		quiteBrowser();
	}

	public static void initialize() {
		System.setProperty("webdriver.chrome.driver", driverPathChrome);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-extensions");
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 20);
	}

	public static void login() {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		clickByLinkText(driver, "Log In");
		enterTextById(driver, "login", "qa+628@gocanvas.com");
		enterTextById(driver, "password", "canvas");
		clickByXpath(driver, "//button[@id='btn_Log In' and @value='Log In']");
	}

	public static void createApp() {
		clickByLinkText(driver, "Create App");
		clickByXpath(driver, "//img[contains(@src,'/images/template_options/T---Blank.png')]");
		clickById(driver, "start-tamplate");
	}
	
	public static void fillFormPage() {
		enterTextById(driver, "formName", "Dennis Thomas");
		enterTextByXpath(driver, "//div[contains(@data-placeholder,'New Screen')]", "SDET");
		clickByXpath(driver, "//li[contains(@title,'Fields')]");
		clickByXpath(driver,
				"//li[contains(@title,'A Short Text field allows the user to enter short text as a response to a prompt.')]");
		enterTextByXpath(driver, "//textarea[contains(@placeholder,'New Short Text')]", "that was easy!");
	}
	
	public static void saveAndPublish() {
		clickByXpath(driver, "//a[contains(@title,'Save')]");
		clickByJavaScript(driver, "//a[@title='Publish to device']");

		//Note: Below are for cancelling from 'Next' screen and retry. Couldn't locate the the buttons Next & Publish at the first try but keep spinning - Worked when I tried cancel and tapped
		//again. Not sure what was the issue.
		clickByXpath(driver, "//button[@class='btn cvs-def-btn']");
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn cvs-prim-btn']")));
//		driver.findElement(By.xpath("//button[@class='btn cvs-def-btn']")).click();

		clickByJavaScript(driver, "//a[@title='Publish to device']");
		clickByJavaScript(driver, "//button[@class='btn ng-binding cvs-prim-btn']");
		
		clickBycssSelector(driver, "[ng-class='\\{disabled\\: validations\\.email \\=\\= false\\}']");

		clickByXpath(driver, "//body/div[@role='dialog']//publish/section[@class='ng-scope']//button[@class='btn cvs-prim-btn']");
	}
	
	public static void quiteBrowser(){
		driver.quit();
	}
	
	
	//Help Methods
	public static void clickById(WebDriver driver, String id) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		WebElement ele = driver.findElement(By.id(id));
		ele.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static void clickByLinkText(WebDriver driver, String linkText) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
		WebElement ele = driver.findElement(By.linkText(linkText));
		ele.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public static void clickByXpath(WebDriver driver, String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	public static void clickBycssSelector(WebDriver driver, String cssSele) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSele)));

		WebElement ele = driver.findElement(By.cssSelector(cssSele));
		ele.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static void clickByJavaScript(WebDriver driver, String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		WebElement ele = driver.findElement(By.xpath(xpath));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", ele);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public static void enterTextById(WebDriver driver, String id, String txt) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		WebElement ele = driver.findElement(By.id(id));
		ele.click();
		ele.sendKeys(txt);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void enterTextByXpath(WebDriver driver, String xpath, String txt) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
		ele.sendKeys(txt);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
