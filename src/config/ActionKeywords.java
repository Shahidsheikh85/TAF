package config;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static executionEngine.ExecuteTestScript.OR;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.openqa.selenium.TakesScreenshot;


import utility.Log;
import executionEngine.ExecuteTestScript;


public class ActionKeywords {

	public static WebDriver driver;
	public static int iTestStep;
	public static String sTestCaseID;
	public static JavascriptExecutor js;
	public static Actions action;


	//Testen nieuwe functies;
	//tweede regels
	//Derde regels

	public static WebDriverWait wait;
//	private static File targetFile;


	//----------------------------------------------------------------??

	public static void openBrowser(String object,String data){	



		Log.info("Opening Browser");
		try{				

			if(data.equals("Mozilla")){
				driver=new FirefoxDriver();
				driver.manage().window().maximize();
				Log.info("Mozilla browser started");
			}
			else if(data.equals("IE")){
				System.setProperty("webdriver.ie.driver", "N:\\Documents\\TAF\\BUILDS\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
				driver.manage().window().maximize();
				Log.info("IE browser started");
				//log(LogStatus.INFO, "IE Started");
			}
			else if(data.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", ".\\TAF\\BUILDS\\chromedriver.exe");
				driver=new ChromeDriver();
				Log.info("Chrome browser started");
			}
			else if (data.equals("Tablet")){
				driver=new FirefoxDriver();
				driver.manage().window().setSize(new Dimension(768, 1080));
			}
			else if (data.equals("Mobile")){
				driver=new FirefoxDriver();
				driver.manage().window().setSize(new Dimension(320, 480));
			}
			else if (data.equals("Mobile2")){
				driver=new FirefoxDriver();
				driver.manage().window().setSize(new Dimension(360, 640));
			}

			else if(data.equals("HeadLess")){
				driver=new HtmlUnitDriver();
				Log.info("HTM-UNIT-Chrome browser started");

			}

			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}

	}



	public static void navigate(String object, String data){
		try{

			if(data.equals("Testomgeving")){
				Log.info("Navigating to test environment");

				// Switch to new window opened
				for(String winHandle : driver.getWindowHandles()){
					driver.switchTo().window(winHandle);
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(Constants.URL);
				Thread.sleep(1000);
				driver.switchTo();				 			 
			}
			else if(!data.equals("Testomgeving")){
				Log.info("Navigating folder: " + data +" on test environment" );
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//After the choosen URL  
				driver.get(Constants.URL +data);
				Thread.sleep(1000);
				driver.switchTo();	
			}

			//driver.get(Constants.Col_URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}




	public static void click(String object, String data) throws AWTException{

		Robot robot = new Robot();
		try{

			waitForElementToBePresent(By.xpath(OR.getProperty(object)),5000);
			System.out.println("Element " + object + " is visible");
			System.out.println("Element is Present" + data);
			if(!data.equals("Javascript")){
				driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
				Log.info("Clicking on Webelement "+ object);
				System.out.println("Clicking on Webelement "+ object);
				String handles= driver.getWindowHandle();
				System.out.println(handles);

				//Pointer (mouse) position 
				Point coordinates = driver.findElement(By.xpath(OR.getProperty(object))).getLocation();
				robot.mouseMove(coordinates.getX()+5, coordinates.getY()+100);

				driver.findElement(By.xpath(OR.getProperty(object))).click();
				Thread.sleep(1000);
			}
			else {
				//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
				WebElement ele = driver.findElement(By.xpath(OR.getProperty(object)));
				Actions action = new Actions(driver);
				action.moveToElement(ele).build().perform();
			}
			/*
				else{
					Log.info("Clicking on Webelement "+ object);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(500);
				}
			 */

		}
		catch (Exception e){
			Log.error("Not able to click --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}



	public static void input(String object, String data){

		try{
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			Log.info("Entering the text in " + object);
			//Point coordinates = driver.findElement(By.xpath(OR.getProperty(object))).getLocation();
			//robot.mouseMove(coordinates.getX(), coordinates.getY());

			WebElement input = driver.findElement(By.xpath(OR.getProperty(object)));
			input.clear();
			input.sendKeys(data);

			Thread.sleep(1500);
		}catch(Exception e){
			Log.error("Not able to Enter input --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}


	public static void wait(String object, String data) throws Exception{
		try{
			Log.info("Wait for 2 seconds");
			Thread.sleep(2000);
		}catch(Exception e){
			Log.error("Not able to Wait --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}

	public static void select_AHref(String object, String data) throws Exception{
		try{
			driver.findElement(By.xpath("//a[contains(.,'"+data+"')]")).click();
			Thread.sleep(500);						
		} catch(Exception e){
			Log.error("Not able to verify ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}
	}


	public boolean verifyText(String object, String data) throws Exception{
		try{	
			wait = new WebDriverWait(driver, 15);
			if(object.equals("")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(.,'" +data+ "')]")));
				WebElement elem = driver.findElement(By.xpath("//*[contains(.,'" +data+ "')]")); 
				if (elem == null){
					System.out.println("The text is not found on the page!");

				}
			}else{

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
				Log.info("Check text on Page: " + data);
				return driver.findElement(By.xpath(OR.getProperty(object))).getText().contains(data);
			}
		} catch (Exception e) {
			Log.error("Not able to verify ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
			return false;
		}
		return false;
	}

	public static void getText(String object, String data) throws Exception{
		try{
			String getText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			Log.info("Text on page is: " + getText);
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;

		}
	}

	public static void selectDropdwn(String object, String data) throws Exception{
		try{
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			Select dropdown= new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			dropdown.selectByVisibleText(data);
			Log.info("Text on dropdown selected is: " + data);
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;

		}
	}



	public static void wysiwyg(String object, String data) throws Exception{
		try{
			String currentWindow = driver.getWindowHandle();


			wait = new WebDriverWait(driver, 15);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cke_wysiwyg_frame")));
			if (object.equals("iFrame_Body")){
				System.out.println("execute iframe body");
				WebElement editorFrame = driver.findElement(By.className("cke_wysiwyg_frame"));
				driver.switchTo().frame(editorFrame);
				String handles= driver.getWindowHandle();
				System.out.println(handles);

				WebElement body = driver.findElement(By.tagName("body"));

				body.clear(); 
				System.out.println("Enter text");
				body.sendKeys(data);

			} else if (!object.equals("iFrame_Body")){

				driver.switchTo().frame(1);
				//System.out.println(editorFrame + " iframe is selected");
				String handles= driver.getWindowHandle();
				System.out.println(handles);

				WebElement body = driver.findElement(By.tagName("body"));

				body.clear(); 
				body.sendKeys(data);

			}
			driver.switchTo().window(currentWindow);

		} catch (Exception e){
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}
	}

	public static void checkbox (String object, String data) throws Exception{

		try{

			WebElement checkbox =driver.findElement(By.xpath("//label[contains(text(), '"+data+"')]/preceding-sibling::input[@type='checkbox']"));
			Log.info("Checkbox found and selected");
			checkbox.click();


		}catch (Exception e){
			Log.error("Not able to click checkbox ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}

	}

	public static void hoover(String object, String data){
		try{
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));
			WebElement searchBtn = driver.findElement(By.xpath(OR.getProperty(object)));

			Actions action = new Actions(driver);
			action.moveToElement(searchBtn);
			action.perform();
			action.click(searchBtn);
			action.perform();;

		}catch (Exception e){
			Log.error("Not able to click Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}

	}


	public static void AutoSelect(String object, String data){
		try{
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ARROW_DOWN);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(2000);

		}catch (Exception e){
			Log.error("Not able to click Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}

	}

	public static void upload(String object, String data){
		try{
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe[id='mediaBrowser']")));
			driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='mediaBrowser']")));
			System.out.println(data);
			driver.findElement(By.name("files[upload]")).clear();
			driver.findElement(By.name("files[upload]")).sendKeys("N:\\Documents\\TAF\\STAAT\\src\\images\\" + data + ".jpg");
		}catch (Exception e){
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}
	}

	public static void tab(String object, String data){
		try{
			Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
		} catch (Exception e) {
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}
	}

	public static void switchScreen(String object, String data){
		try{
			Actions actions = new Actions(driver);
			if (data.equals("")){
				System.out.println("Switch to deafult frame");
				Log.info("switch default frame");
				driver.switchTo().defaultContent();
				actions.keyUp(Keys.CONTROL).sendKeys(Keys.UP).perform();

			}
			else if(data.equals("frame")) {
				System.out.println("Switch to field frame");
				Log.info("Switch frames");
				driver.switchTo().frame("field-frame");
				actions.keyUp(Keys.CONTROL).sendKeys(Keys.UP).perform();
			}
		}catch(Exception e) {
			Log.error("Not able to get Text ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}

	}


	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			driver.close();
		}catch(Exception e){
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}

	public static void Scroll(String object, String data) throws Exception{
		Robot robot = new Robot();
		try{
			if(data.equals("DOWN")){
				//actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			}
			else if(data.equals("UP")){
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);

				//actions.keyUp(Keys.CONTROL).sendKeys(Keys.PAGE_UP).perform();
			}
		}catch(Exception e) {
			Log.error("Not able to get KEY PRESSED ---" + e.getMessage());
			ExecuteTestScript.bResult =false;
		}
	}


	public static File takeScreenshot() throws Exception{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/");
		Calendar cal = Calendar.getInstance();

		File screenshotDir = new File(Constants.Path_ScreenShot + "/"  
				+ dateFormat.format(cal.getTime())
				+ "/screenshots/");
		screenshotDir.mkdirs();

		File targetFile = new File(screenshotDir.getAbsolutePath() + "/" +
				screenshotDir.listFiles().length  + "-iTestStep.png");	

		FileUtils.copyFile(scrFile, targetFile);
		return targetFile;

	}




	public static void loginRIVM(String object, String data) throws Exception{
		try{
			Thread.sleep(1000);
			Log.info("It works");
			Runtime.getRuntime().exec("R://RIO//Projecten//REACH-CLP-topdesk-web-portal//SCRUM-fase//Test//TAF//src//RIVM_Inlog.exe");
			Thread.sleep(3000);
		}catch(Exception e){
			Log.error("Not able to use httpLoginCO --- " + e.getMessage());
			ExecuteTestScript.bResult = false;
		}
	}

	public static boolean waitForElementToBePresent(By by, int waitInMilliSeconds) throws Exception
	{
		int wait = waitInMilliSeconds;
		int iterations  = (wait/250);
		long startmilliSec = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++)
		{
			if((System.currentTimeMillis()-startmilliSec)>wait)
				return false;
			List<WebElement> elements = driver.findElements(by);
			if (elements != null && elements.size() > 0)
				return true;
			Thread.sleep(250);
		}
		return false;
	}

}
