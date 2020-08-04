package Objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AirbnbSearchCriteriaObjects {
	WebDriver driver;
	Utility utility=new Utility();
	
	@FindBy(xpath="//div[text()=\"Location\"]/following-sibling::input")
    public WebElement LocationFld;
	
	
	public String getOneWeekFromCheckInDate(String CheckInDate) {

		
		String DateParts[]=CheckInDate.split("-");
		int year=Integer.parseInt(DateParts[0]);
		int month=Integer.parseInt(DateParts[1]);
		int date=Integer.parseInt(DateParts[2]);
			
		String oneWeekFromCheckInDate=utility.AddDateSpecific(7, year, month, date);
		
	return oneWeekFromCheckInDate;
	}

	
	public WebElement CheckInDateXpath(String Date) {
    	
    	WebElement CheckInDateXpath=driver.findElement(By.xpath("//div[@data-testid=\"datepicker-day-"+Date+"\"]"));   
    	
    	return CheckInDateXpath;
    }
	
	public WebElement CheckOutDateXpath(String Date) {
    	
    	WebElement CheckOutDateXpath=driver.findElement(By.xpath("//div[@data-testid=\"datepicker-day-"+Date+"\"]"));   
    	
    	return CheckOutDateXpath;
    }
	
	@FindBy(className = "_1cot5uz")
	public WebElement smallSearch;
	
	
	
	@FindBy(xpath="//div[text()=\"Check in\"]/following-sibling::div")
	public WebElement CheckInVal;
	
	@FindBy(xpath="//div[text()=\"Check out\"]/following-sibling::div")
	public WebElement CheckOutVal;
	
	
	@FindBy(xpath="//div[text()=\"Guests\"]/following-sibling::div")
    public WebElement GuestsBtn;
	
	@FindBy(xpath="//form[@role=\"search\"]/parent::div")
    public WebElement mainPanel;
	
	
	@FindBy(xpath="//div[@id=\"stepper-adults\"]/button[@aria-label=\"increase value\"]")
    public WebElement adultsIncreaseBtn;
	
	@FindBy(xpath="//div[@id=\"stepper-children\"]/button[@aria-label=\"increase value\"]")
    public WebElement childsIncreaseBtn;
	
	@FindBy(className ="_1mzhry13")
    public WebElement searchBtn;
	
	public WebElement locationFltr(String location) {
    	
    	WebElement locationFltr=driver.findElement(By.xpath("//span[contains(text(),\"Location\")]/parent::button[text()=\""+location+"\"]"));   
    	
    	return locationFltr;
    }
	
	public WebElement dateFltrXpath(String D1, String D2) {
    	
    	WebElement dateFltrXpath=driver.findElement(By.xpath("//span[contains(text(),\"Check in\")]/parent::button[text()=\""+D1+" - "+D2+"\"]"));   
    	
    	return dateFltrXpath;
    }

	
public WebElement guestsFltr(int guestNumber) {
    	
    	WebElement guestsFltr=driver.findElement(By.xpath("//span[contains(text(),\"Guests\")]/parent::button[text()=\""+guestNumber+" guests\"]"));   
    	
    	return guestsFltr;
    }
	
	
	public WebElement propertyLst(int num) {
    	
    	WebElement propertyLst=driver.findElement(By.xpath("(//div[contains(text(),\"guests\")])["+num+"]"));   
    	
    	return propertyLst;
    }
	
	
	@FindBy(id ="site-content")
    public WebElement siteContent;
	
	

	@FindBy(xpath="//div[contains(text(),\"guests\")]")
    public WebElement propertyLst;
	
	
public int getTotalPropertyNo(WebDriver driver) {

		
		List<WebElement> AllValues = driver.findElements(By.xpath("//div[contains(text(),\"guests\")]"));
		
		int totapPropertNo=AllValues.size();
		
		return totapPropertNo;
	}

	
	
	
	
	
	
	
	
	
	public AirbnbSearchCriteriaObjects(WebDriver driver){

	        this.driver = driver;

	        //This initElements method will create all WebElements

	        PageFactory.initElements(driver, this);
	        

	    }
}
