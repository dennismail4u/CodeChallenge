package TestGoCanvas;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
	private static String userName = "qa+628@gocanvas.com";
	private static String password = "canvas";
	
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
		
		System.out.println("Driver got initialized successfully.");
	}

	public static void login() {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		clickByLinkText(driver, "Log In");
		enterTextById(driver, "login", userName);
		enterTextById(driver, "password", password);
		clickByXpath(driver, "//button[@id='btn_Log In' and @value='Log In']");
		
		Assert.assertTrue("Login failed!! Please check the Login", 
				driver.findElements(By.linkText("Create App")).size() != 0);
		
		System.out.println("Logged In successfully.");
	}

	public static void createApp() {
		clickByLinkText(driver, "Create App");
		clickByXpath(driver, "//img[contains(@src,'/images/template_options/T---Blank.png')]");
		clickById(driver, "start-tamplate");
		
		Assert.assertTrue("Something Went Wrong while creating app. Couldnt reach the form screen. Please check !!", 
				driver.findElements(By.id("formName")).size() != 0);
		
		System.out.println("Selected the blank form successfully.");
	}
	
	public static void fillFormPage() {
		enterTextById(driver, "formName", "Dennis Thomas");
		enterTextByXpath(driver, "//div[contains(@data-placeholder,'New Screen')]", "SDET");
		clickByXpath(driver, "//li[contains(@title,'Fields')]");
		clickByXpath(driver,
				"//li[contains(@title,'A Short Text field allows the user to enter short text as a response to a prompt.')]");
		enterTextByXpath(driver, "//textarea[contains(@placeholder,'New Short Text')]", "that was easy!");
		
		System.out.println("Entered the required details on the form successfully.");
	}
	
	public static void saveAndPublish() {
		clickByXpath(driver, "//a[contains(@title,'Save')]");
		System.out.println("Saved the form successfully.");
		
		clickByJavaScript(driver, "//a[@title='Publish to device']");

		//Note: Below are for canceling from 'Next' screen and retry. While clicking on Next button, it was not landing on Publish screen
		//but keep spinning - Worked when I tried cancel and tapped again. Not sure what was the issue.
		clickByXpath(driver, "//button[@class='btn cvs-def-btn']");
		clickByJavaScript(driver, "//a[@title='Publish to device']");
		clickByJavaScript(driver, "//button[@class='btn ng-binding cvs-prim-btn']");
		
		clickBycssSelector(driver, "[ng-class='\\{disabled\\: validations\\.email \\=\\= false\\}']");

		Assert.assertTrue("Something Went Wrong while tapping on the Next button while publishing the form. Please check !!", 
				driver.findElements(By.xpath("//body/div[@role='dialog']//publish/section[@class='ng-scope']//button[@class='btn cvs-prim-btn']")).size() != 0);
		
		clickByXpath(driver, "//body/div[@role='dialog']//publish/section[@class='ng-scope']//button[@class='btn cvs-prim-btn']");
		
		System.out.println("Published the form successfully.");
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
