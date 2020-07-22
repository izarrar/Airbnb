package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AirbnbObjects {
	WebDriver driver;
	
	@FindBy(xpath="//div[text()=\"Location\"]/following-sibling::input")
    public WebElement LocationFld;
	
	public WebElement CheckInDateXpath(String Date) {
    	
    	WebElement CheckInDateXpath=driver.findElement(By.xpath("//div[@data-testid=\"datepicker-day-"+Date+"\"]"));   
    	
    	return CheckInDateXpath;
    }
	
	public WebElement CheckOutDateXpath(String Date) {
    	
    	WebElement CheckOutDateXpath=driver.findElement(By.xpath("//div[@data-testid=\"datepicker-day-"+Date+"\"]"));   
    	
    	return CheckOutDateXpath;
    }
	
	@FindBy(xpath="//div[text()=\"Check in\"]/following-sibling::div")
	public WebElement CheckInVal;
	
	@FindBy(xpath="//div[text()=\"Check out\"]/following-sibling::div")
	public WebElement CheckOutVal;
	
	
	@FindBy(xpath="//div[text()=\"Guests\"]/following-sibling::div")
    public WebElement GuestsBtn;
	
	@FindBy(xpath="//span[text()=\"Places to stay\"]")
    public WebElement mainPanel;
	
	
	@FindBy(xpath="//div[@id=\"stepper-adults\"]/button[@aria-label=\"increase value\"]")
    public WebElement adultsIncreaseBtn;
	
	@FindBy(xpath="//div[@id=\"stepper-children\"]/button[@aria-label=\"increase value\"]")
    public WebElement childsIncreaseBtn;
	
	@FindBy(xpath="//button[@type=\"submit\"]/span")
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
	
	
	public WebElement guestsLst(int num) {
    	
    	WebElement guestsLst=driver.findElement(By.xpath("(//div[contains(text(),\"guests\")])["+num+"]"));   
    	
    	return guestsLst;
    }

	@FindBy(xpath="//span[contains(text(),\"More filters\")]/parent::button")
	public WebElement moreFltrBtn;
	
	@FindBy(xpath="//button[contains(@aria-describedby,\"bedrooms\") and contains(@aria-label,\"increase value\")]")
	public WebElement bedroomsIncreaseValBtn;

	
	@FindBy(xpath="//input[@name=\"Pool\"]/following-sibling::span")
	public WebElement poolChkBx;
	
	@FindBy(xpath="//button[contains(text(),\"Show\")]")
	public WebElement showStaysBtn;
	
	@FindBy(xpath="//a[contains(text(),\"amenities\")]")
	public WebElement amnetiesLnk;
	
	@FindBy(xpath="//h3[contains(text(),\"Facilities\")]/parent::div/parent::div//div[text()=\"Pool\"]")
	public WebElement poolLbl;

	@FindBy(xpath="//h2[text()=\"Amenities\"]")
	public WebElement amnetiesLbl;
	
	@FindBy(xpath="//button[text()=\"Clear all\"]")
	public WebElement clearAllLnk;
	
	@FindBy(xpath="(//div[@class=\"_1c2n35az\"])[1]")
	public WebElement firstPropName;
	
	public WebElement firstPropOnMap(String PropertyName) {
    	
    	WebElement firstPropOnMap=driver.findElement(By.xpath("//span[@class=\"_1nq36y92\"]/parent::div/parent::div/parent::button[contains(@aria-label,\""+PropertyName+"\")]"));   
    	
    	return firstPropOnMap;
    }
	

	@FindBy(xpath="//a[@class=\"_i24ijs\"]")
	public WebElement firstPropOnMapPopUp;
	
	

	
	
	
	
	
	
	
	
	
	public AirbnbObjects(WebDriver driver){

	        this.driver = driver;

	        //This initElements method will create all WebElements

	        PageFactory.initElements(driver, this);
	        

	    }
}
