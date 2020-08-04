package Objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Utility {

	public static WebDriverWait wait;
	Logger logger = Logger.getLogger("Utility");

	public String getDropdownFirstSelectedOption(WebDriver driver, WebElement element) {

		Select select = new Select(element);
		String firstSelectedOption = select.getFirstSelectedOption().getText();

		return firstSelectedOption;
	}
	
	
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	

	public List<WebElement> getDropdownValues(WebDriver driver, WebElement element) {

		Select select = new Select(element);
		List<WebElement> options = select.getOptions();

		return options;
	}
	
	
	

	public void checkPageIsReady(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");

		}
		// This loop will rotate for 25 times to check If page Is ready after every 1
		// second.
		// You can replace your value with 25 If you wants to Increase or decrease wait
		// time.
		else {

			for (int i = 0; i < 25; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				// To check page ready state.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					System.out.println("Page Is loaded.");
					break;
				}
			}
		}

	}





//This function will return latest file name from download directory
	public File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

//This function will verify latest downloaded file and return boolean (true/false)
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().contains(fileName))
				return flag = true;
		}

		return flag;
	}


	// This function will handle window change
	public void switchtowindowhandler(WebDriver driver) {

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}

	}


//This function will wait for element according to time(seconds) and condition (Visibility,Click and Frame)
	public static void Pause(WebDriver driver, WebElement element, String KeyWord, int timeInSeconds) {

		wait = new WebDriverWait(driver, timeInSeconds); // time to pause in seconds
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {

			try {

				if (KeyWord.contains("Visibility")) {
					wait.until(ExpectedConditions.visibilityOf(element)); // waiting for the element to be visible

				} else if (KeyWord.contains("Click")) {
					wait.until(ExpectedConditions.elementToBeClickable(element)); // waiting for the element to be
																					// Clickable
				} else if (KeyWord.contains("Frame")) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));// waiting for the frame to
																							// be available and switch
																							// to it
				} else if (KeyWord.contains("Invisibility")) {
					wait.until(ExpectedConditions.invisibilityOf(element));
				}

				result = true;
				break;
			} catch (StaleElementReferenceException e) {

			}
			attempts++;
		}

	}


	//This function will return Credentials in ArrayList from XML file.
	// It will be used to get url, username and password from xml file
	public ArrayList<String> getDataFromXML() throws ParserConfigurationException, SAXException, IOException {
		// XML Chunk Starts Here
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		

		String filePath = System.getProperty("user.dir") + "\\Userdata\\Userdata.xml";

		Document document = builder.parse(new File(filePath));
		org.w3c.dom.Element rootElement = document.getDocumentElement();
		String URL = rootElement.getElementsByTagName("URL").item(0).getTextContent();
		String Location = rootElement.getElementsByTagName("Location").item(0).getTextContent();
		String Adults = rootElement.getElementsByTagName("Adults").item(0).getTextContent();
		String Childs = rootElement.getElementsByTagName("Childs").item(0).getTextContent();
		String Bedrooms = rootElement.getElementsByTagName("Bedrooms").item(0).getTextContent();
		// XML Chunk Ends Here

		ArrayList<String> userData = new ArrayList<String>();
		userData.add(URL);
		userData.add(Location);
		userData.add(Adults);
		userData.add(Childs);
		userData.add(Bedrooms);
		return userData;
	}


	// This function will return salt string of alphabets.
//This function is returning random string alphabet string
	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	// This function will return salt string of numbers.
//This function is returning random string number string
	public String getSaltStringNumbers() {
		String SALTCHARS = "1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 9) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

//This function will capture screenshot
// for capturing screen shot	
	public static void captureScreenshot(WebDriver driver, String screenshotName) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(source, new File("./Screenshots/" + screenshotName + ".png"));

			System.out.println("Screenshot taken");
		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
	}

//This function will scroll the element
	public static void scrollTo(WebDriver driver, WebElement element) {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}


//This function will return the date by adding specified number of days to todays date. 
//This function is returning date after adding specified number of days to current date
	public String AddDate(int number) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, number);

		Date currentDatePlusOne = c.getTime();
		String setDate = dateFormat.format(currentDatePlusOne).toString();
		return setDate;

	}

//This function will return the date by adding specified number of days to specified date.
//This function is returning date after adding specified number of days to specified date
	public String AddDateSpecific(int number, int year, int month, int date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, date);
		c.add(Calendar.DATE, number);

		Date currentDatePlusOne = c.getTime();
		String setDate = dateFormat.format(currentDatePlusOne).toString();
		return setDate;

	}

//This function will return the date by adding specified number of days to specified date.
//This function is returning date of first Monday of the month of specified year & month
//month input starts with zero index  

	public static String getFirstMonday(int year, int month) {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cacheCalendar = Calendar.getInstance();
		cacheCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cacheCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		cacheCalendar.set(Calendar.MONTH, month);
		cacheCalendar.set(Calendar.YEAR, year);
		Date currentDate = cacheCalendar.getTime();
		String CurrentDate = dateFormat.format(currentDate).toString();
		return CurrentDate;

	}

//This function will return the date of first monday of the next month of the specified date.
//This function is returning date of next Monday from specified year,month & date
//month input starts with zero index  
	public String getNextMonday(int year, int month, int date) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar date1 = Calendar.getInstance();
		date1.set(year, month - 1, date);

		while (date1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			date1.add(Calendar.DATE, 1);
		}

		Date currentDate = date1.getTime();
		String CurrentDate = dateFormat.format(currentDate).toString();
		return CurrentDate;
	}

//This function will return the number of  days to next sunday from the specified date.
//This function is returning date of next Monday from specified year,month & date
//month input starts with zero index  
	public int getDaystoSunday(int year, int month, int date) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar date1 = Calendar.getInstance();
		date1.set(year, month - 1, date);
		int i = 1;
		while (date1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			date1.add(Calendar.DATE, 1);
			i++;
		}

		return i;
	}

//This function will return the Todays Date.
//This function is returning todays date
	public static String TodaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date1 = Calendar.getInstance();
		Date currentDate = date1.getTime();
		String CurrentDate = dateFormat.format(currentDate).toString();
		return CurrentDate;

	}

//This function will return true if Today is Saturday or Sunday or else it will return false.
	public boolean DayOfWeek(int number) {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, number);
		boolean flag = false;
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			flag = true;
		}

		return flag;

	}

//This function will return the present month of the present year.
//This function is returning name of the month of specified number of month
	String getMonthForInt(int num) {
		String month = "";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;

	}

//This function will move webdriver to the specified element.
//This function move driver to specified elemenet  
	public static void moveto(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click().build().perform();

	}
	
	

//This function will hover webdriver on to the specified element.
	public static void movetoHover(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();

	}


	public static void PomFindClick(WebDriver driver, WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {
			//	Utility.scrollTo(driver, element);
				element.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindClick1(WebDriver driver, WebElement element) throws InterruptedException {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				Utility.scrollTo(driver, element);
				Thread.sleep(1500);
				element.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindMove(WebDriver driver, WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				Utility.scrollTo(driver, element);
				movetoHover(driver, element);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindScroll(WebDriver driver, WebElement element) {
		boolean result = false;

		int attempts = 0;

		while (attempts < 4) {

			try {
				Utility.scrollTo(driver, element);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindClear(WebDriver driver, WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				Utility.scrollTo(driver, element);
				moveto(driver, element);
				element.clear();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindHover(WebDriver driver, WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				Utility.scrollTo(driver, element);
				movetoHover(driver, element);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}

	public static void retryingFindFieldwithoutClear(WebDriver driver, By by, String text) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {
				WebElement element = driver.findElement(by);
				Utility.scrollTo(driver, element);
				element.sendKeys(text);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}

	public static void PomFindField(WebDriver driver, WebElement element, String text) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				//Utility.scrollTo(driver, element);

				element.clear();
				element.sendKeys(text);

				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomFindField(WebDriver driver, WebElement element, Keys key) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {
			try {

				Utility.scrollTo(driver, element);
				element.sendKeys(key);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static String PomReturnText(WebDriver driver, WebElement element) {
		boolean result = false;
		String val1 = "";
		int attempts = 0;
		while (attempts < 2) {
			try {

				Utility.scrollTo(driver, element);
				val1 = element.getText();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return val1;
	}


	public static String PomReturnValue(WebDriver driver, WebElement element, String Attribute) {
		boolean result = false;
		String val7 = "";
		int attempts = 0;
		while (attempts < 2) {
			try {
				val7 = element.getAttribute(Attribute);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return val7;
	}


	public static void PomReturnSelect(WebDriver driver, WebElement element, String VisibleText) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {

				scrollTo(driver, element);
				Select pcode = new Select(element);
				pcode.selectByVisibleText(VisibleText);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static void PomReturnSelect(WebDriver driver, WebElement element, int visibleIndex) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {

				scrollTo(driver, element);
				Select pcode = new Select(element);
				pcode.selectByIndex(visibleIndex);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

	}


	public static String PomReturnCssValue(WebDriver driver, WebElement element, String CssValue) {
		boolean result = false;
		String val7 = "";
		int attempts = 0;
		while (attempts < 2) {
			try {
				val7 = element.getCssValue(CssValue);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return val7;
	}

//This function will format specified date according to format yyyy-MM-dd
	public String dateformat(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = df.parse(date);

		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

		String finalString = newFormat.format(date1);

		String tim = finalString;

		return tim;

	}


//This function will return specified time in 24 Hrs format
	public String toTwentyFour(String time) throws ParseException {
		// Format of the date defined in the input String
		DateFormat df = new SimpleDateFormat("hh:mm aa");
		// Desired format: 24 hour format: Change the pattern as per the need
		DateFormat outputformat = new SimpleDateFormat("HH:mm");
		Date date = null;
		String output = null;

		// Converting the input String to Date
		date = df.parse(time);
		// Changing the format of date and storing it in String
		output = outputformat.format(date);
		// Displaying the date
		return output;

	}

//This function will return Current Time
	public String getCurrentTime() throws ParseException {

		Calendar cal = Calendar.getInstance();
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		String time1 = sdf.format(cal.getTime());

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss");
		Date date = parseFormat.parse(time1);
		String time = displayFormat.format(date);

		return time1;

	}

//This function will return Current Time (Duplicate)
//Displaying current time in 12 hour format with AM/PM
	public String CurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("h:mm");
		String dateString = dateFormat.format(new Date()).toString();
		return dateString;
	}

//This function will return Data in ArrayList of specified file name & sheet name of Excel file
	public ArrayList<String> readExcel(String fileName, String sheetName) throws IOException {
		String filePath = System.getProperty("user.dir");
		// Create an object of File class to open xlsx file
		ArrayList<String> ExcelData = new ArrayList<String>();
		File file = new File(filePath + "\\" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook guru99Workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			guru99Workbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			guru99Workbook = new HSSFWorkbook(inputStream);

		}

		// Read sheet inside the workbook by its name

		Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

		// Find number of rows in excel file

		int rowCount = guru99Sheet.getLastRowNum() - guru99Sheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it

		for (int i = 1; i < rowCount + 1; i++) {

			Row row = guru99Sheet.getRow(i);

			// Create a loop to print cell values in a row

			for (int j = 0; j < row.getLastCellNum(); j++) {

				// Print Excel data in console

				// System.out.print(row.getCell(j)+"\n");
				ExcelData.add(row.getCell(j).toString());
			}

		}
		return ExcelData;
	}


//This function will return Current Hour
	public String CurrentHour() {
		// Displaying current time in 12 hour format with AM/PM
		DateFormat dateFormat = new SimpleDateFormat("hh");
		String dateString = dateFormat.format(new Date()).toString();
		// System.out.println("Current time in AM/PM: "+dateString);
		return dateString;
	}

//This function will return salt string of alphabets of specified length
	public String getSaltStringCustom(int length) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
		// This function is returning random string alphabet string
	}



	
	public void switchTabs(WebDriver driver) {
		
		String currentHandle= driver.getWindowHandle();
		
	    Set<String> handles=driver.getWindowHandles();
	    for(String actual: handles)
	    {
	        
	     if(!actual.equalsIgnoreCase(currentHandle))
	     {
	         //switching to the opened tab
	         driver.switchTo().window(actual);
	         
	  
	       
	     }
	    }
	}

}