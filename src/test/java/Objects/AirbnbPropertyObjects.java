package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AirbnbPropertyObjects {
	WebDriver driver;
	

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
	
	

	
	
	
	
	
	
	
	
	
	public AirbnbPropertyObjects(WebDriver driver){

	        this.driver = driver;

	        //This initElements method will create all WebElements

	        PageFactory.initElements(driver, this);
	        

	    }
}
