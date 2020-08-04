
package Objects;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

@Listeners({ TestListener.class })
public class BaseClass {

	
	Utility utility=new Utility();
	
	public static WebDriver driver;
	Logger logger=Logger.getLogger("BaseClass");
	
	TestListener tlistener=new TestListener();
	
	ArrayList<String> userData = new ArrayList<String>();
	
	public static String URL="";
	public static String Location="";
	public static String Adults="";
	public static String Childs="";
	public static String Bedrooms="";
	
@BeforeSuite
public void BaseMethod() throws Throwable {
	
	
		

	WebDriverManager.chromedriver().setup();
	

	String downloadFilepath = System.getProperty("user.dir");	
	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	chromePrefs.put("profile.default_content_settings.popups", 0);
	chromePrefs.put("download.default_directory", downloadFilepath);
	ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);	
	options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	options.setCapability(ChromeOptions.CAPABILITY, options);
	options.setExperimentalOption("useAutomationExtension", false);
    driver= new ChromeDriver(options);
	
	PropertyConfigurator.configure("Log4j.properties");
	
	//Get Data from data file.
try{
	userData=utility.getDataFromXML();
	
	URL=userData.get(0);
	Location=userData.get(1);
	Adults=userData.get(2);
	Childs=userData.get(3);
	Bedrooms=userData.get(4);
	}
catch(Exception ex)
{
	System.out.println("Data not found:"+ex.getMessage());
}	

try {
	driver.get(URL);
	driver.manage().window().maximize();
}
catch(Exception excep) {
	Assert.fail("URL not accessable");
}
}


        
        




/*    
//It will execute after every test Suite       
        
@Title("Closing Browser")
@AfterSuite
public void ClosingBrowser() {

	logger.info("driver quit");
	driver.quit();
	
    
}
   */ 
        
        //It will execute after every test execution 

        	@AfterMethod
        	public void onTestFaliure(ITestResult iTestResult){

        		
        			
        	if(ITestResult.FAILURE==iTestResult.getStatus()){	
        		
        	Utility.captureScreenshot(driver,iTestResult.getName());
        		
        	if(driver instanceof WebDriver){
        		System.out.println("Screenshot Captured for test case:"+TestListener.getTestMethodName(iTestResult));
        		tlistener.saveScreenshotPNG(driver);
        	}
        	
        	
        		}
        	

        }	


		
 }

	


	        
	    

	   
	
	
	

