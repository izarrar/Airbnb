package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AirbnbSearchResultsObjects {
	WebDriver driver;
	

	@FindBy(xpath="//span[contains(text(),\"More filters\")]/parent::button")
	public WebElement moreFltrBtn;
	
	@FindBy(className ="_12p9ain")
	public WebElement moreFiltersDialog;
	
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
	
	
	
	public AirbnbSearchResultsObjects(WebDriver driver){

	        this.driver = driver;

	        //This initElements method will create all WebElements

	        PageFactory.initElements(driver, this);
	        

	    }
}
