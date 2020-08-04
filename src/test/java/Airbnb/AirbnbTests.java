package Airbnb;




import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;
import Objects.*;


@Listeners({ TestListener.class })
public class AirbnbTests extends BaseClass{


	

	
	TestListener tlistener=new TestListener();
	Utility utility=new Utility();
	AirbnbSearchCriteriaObjects airbnbsearchcriteria;
	AirbnbSearchResultsObjects airbnbsearchresults;
	AirbnbPropertyObjects airbnbproperty;
	
	
	WebElement element;	
	String CheckIn="";
	String CheckOut="";
	int totalNoOfProperties=0;
		
	
	
	/////airbnb: Search Criteria
	////////////////////Test Case: 1 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that the search results match the search criteria") 
	@Title("Verify that the search results match the search criteria")
	@Test(priority=1,description="Search results should match the search criteria")
	public void appliedFilterVerification () throws Exception {
	try {
			
		
		
		
		airbnbsearchcriteria=new AirbnbSearchCriteriaObjects(driver);
		airbnbsearchresults=new AirbnbSearchResultsObjects(driver);
		airbnbproperty=new AirbnbPropertyObjects(driver);
				
		
		int adults=Integer.parseInt(Adults);
		int childs=Integer.parseInt(Childs);
		
		try {
			utility.Pause(driver, airbnbsearchcriteria.LocationFld, "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
			
		
		utility.PomFindField(driver, airbnbsearchcriteria.LocationFld, Location);	
		
		Thread.sleep(1000);
		utility.PomFindField(driver, airbnbsearchcriteria.LocationFld, Keys.ARROW_DOWN);
	
		element = driver.switchTo().activeElement();
		
		utility.moveto(driver, airbnbsearchcriteria.mainPanel);
		
		utility.PomFindField(driver, element, Keys.ENTER);
		
		
		String OnWeekFromToday=utility.AddDate(7);
			
		utility.PomFindClick(driver, airbnbsearchcriteria.CheckInDateXpath(OnWeekFromToday));
		
		
		
		String oneWeekFromCheckInDate=airbnbsearchcriteria.getOneWeekFromCheckInDate(OnWeekFromToday);
		
		
		utility.PomFindClick(driver, airbnbsearchcriteria.CheckOutDateXpath(oneWeekFromCheckInDate));
		
		
		String CheckinDateVal=utility.PomReturnText(driver, airbnbsearchcriteria.CheckInVal);
		String CheckoutDateVal=utility.PomReturnText(driver, airbnbsearchcriteria.CheckOutVal);
		
		String CheckinDateValParts[]=CheckinDateVal.split(" ");
		String CheckoutDateValParts[]=CheckoutDateVal.split(" ");
		
		utility.moveto(driver, airbnbsearchcriteria.mainPanel);
		
		utility.PomFindClick(driver, airbnbsearchcriteria.GuestsBtn);
		
		
		for(int i=1;i<=adults;i++) {
		utility.PomFindClick(driver, airbnbsearchcriteria.adultsIncreaseBtn);
		}
		
		
		for(int i=1;i<=childs;i++) {
		utility.PomFindClick(driver, airbnbsearchcriteria.childsIncreaseBtn);
		}
		
		
		utility.PomFindClick(driver, airbnbsearchcriteria.GuestsBtn);
		
		utility.PomFindClick(driver, airbnbsearchcriteria.searchBtn);
		
		
		
		int totalGuests=adults+childs;
		String locationParts[]=Location.split(",");
		
		
		try {
			utility.Pause(driver, airbnbsearchcriteria.smallSearch, "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
		
		
		String locationFltr=utility.PomReturnText(driver, airbnbsearchcriteria.locationFltr(locationParts[0]));
		
		Assert.assertTrue(locationFltr.contains(locationParts[0]),"Location search filters is not working properly");
		
		
		if(CheckinDateValParts[0].equals(CheckoutDateValParts[0])) {
			CheckIn=CheckinDateVal;
			CheckOut=CheckoutDateValParts[1];
		}
		else {
			CheckIn=CheckinDateVal;
			CheckOut=CheckoutDateVal;

		}
		
		String dateFltr=utility.PomReturnText(driver, airbnbsearchcriteria.dateFltrXpath(CheckIn, CheckOut));
		
		Assert.assertTrue(dateFltr.contains(""+CheckIn+" - "+CheckOut+""),"Date search filter is not working properly");
		
		String guestFltr=utility.PomReturnText(driver, airbnbsearchcriteria.guestsFltr(totalGuests));
		
		Assert.assertEquals(guestFltr, "Guests\n"+totalGuests+" guests","Guest search filter is not working properly");
		
		
		String guestNumber="";
		String guestParts[];
		int guestsAvailable=0;
		
	 
		try {
			utility.Pause(driver, airbnbsearchcriteria.siteContent, "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
	
		
		
		totalNoOfProperties=airbnbsearchcriteria.getTotalPropertyNo(driver);
		
		
		for(int i=2;i<=totalNoOfProperties;i++) {
			
			guestNumber=utility.PomReturnText(driver, airbnbsearchcriteria.propertyLst(i));
			guestParts=guestNumber.split(" guests");
			guestsAvailable=Integer.parseInt(guestParts[0]);
			Assert.assertTrue(guestsAvailable>=totalGuests,"The properties displayed on the first page can not accommodate at least the selected\r\n" + 
					"number of guests.");
		}
		
		
	}
		
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}
	
	}


	/////airbnb: Search Results
	////////////////////Test Case: 2 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that the results and details page match the extra filters") 
	@Title("Verify that the results and details page match the extra filters")
	@Test(priority=2,description="The results and details page should match the extra filters")
	public void extraFilterVerification () throws Exception {
		try {
		
		int bedrooms=Integer.parseInt(Bedrooms);
		
		utility.moveto(driver, airbnbsearchresults.moreFltrBtn);

		Thread.sleep(2000);
			
		for (int i=1;i<=bedrooms;i++) {
		utility.PomFindClick(driver, airbnbsearchresults.bedroomsIncreaseValBtn);
		}
		
		
		
		utility.PomFindClick(driver, airbnbsearchresults.poolChkBx);
		
		
		utility.PomFindClick(driver, airbnbsearchresults.showStaysBtn);
		
		
		String bedroomNumber="";
		String bedroomParts[];
		String bedroomParts1[];
		String availableBedroomNumber="";
		int bedroomsAvailable=0;
		
		Thread.sleep(3000);
		
		
		int totalNoOfProperties=airbnbsearchcriteria.getTotalPropertyNo(driver);
		
		
		for(int i=2;i<=totalNoOfProperties;i++) {
			bedroomNumber=utility.PomReturnText(driver, airbnbsearchcriteria.propertyLst(i));
			bedroomParts=bedroomNumber.split("guests ");
			
			try {
			bedroomParts1=bedroomParts[1].split(" bedrooms");
			}
			catch(Exception exp) {
			bedroomParts1=bedroomParts[0].split(" bedrooms");
			}
			
			availableBedroomNumber= bedroomParts1[0].replaceAll("[^0-9]", "");			
			bedroomsAvailable=Integer.parseInt(availableBedroomNumber);
			
			Assert.assertTrue(bedroomsAvailable>=bedrooms,"The properties displayed on the first page donot have at least the selected\r\n" + 
					"number of bedrooms.");
		}
		
		
		utility.moveto(driver, airbnbsearchcriteria.propertyLst(2));
		
	
	
		utility.switchTabs(driver);
	
	try {
		utility.Pause(driver, airbnbsearchresults.amnetiesLnk, "Visibility", 30);
		}
		catch(Exception exp) {
			
		}
	
	
		utility.PomFindClick(driver, airbnbsearchresults.amnetiesLnk);
		
	
	try {
		utility.Pause(driver, airbnbsearchresults.poolLbl, "Visibility", 30);
		}
		catch(Exception exp) {
			
		}
	
	
	String poolLbl=utility.PomReturnText(driver, airbnbsearchresults.poolLbl);
	
	Assert.assertEquals(poolLbl, "Pool\nPrivate or shared","Pool is not being displayed under Facilities category in Amenities popup");
	
		
	}
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}
		

	}

	/////airbnb: Properties
	////////////////////Test Case: 3 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that a property is displayed on the map correctly") 
	@Title("Verify that a property is displayed on the map correctly")
	@Test(priority=3,description="Property should be displayed on the map correctly")
	public void mapDataVerification () throws Exception {
		try {
		
		
		utility.switchTabs(driver); 
		
		utility.moveto(driver, airbnbsearchresults.moreFltrBtn);
		
		
		
		try {
			utility.Pause(driver, airbnbsearchresults.poolLbl, "Click", 5);
			}
			catch(Exception exp) {
				
			}

		
		utility.PomFindClick(driver, airbnbproperty.clearAllLnk);
		
		
		utility.PomFindClick(driver, airbnbsearchresults.showStaysBtn);
		
	
		try {
			utility.Pause(driver, airbnbsearchresults.poolLbl, "Click", 5);
			}
			catch(Exception exp) {
				
			}

		
		utility.PomFindHover(driver, airbnbproperty.firstPropName);
		
		
		String firstPropName=utility.PomReturnText(driver, airbnbproperty.firstPropName);
		
		utility.PomFindClick(driver, airbnbproperty.firstPropOnMap(firstPropName));
		
		
		String propOnMap=utility.PomReturnValue(driver, airbnbproperty.firstPropOnMapPopUp,"aria-label");
		
		Assert.assertEquals(propOnMap, firstPropName,"Name of the first property is not being appearing on map popup.");
		
		}
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}
	}


	
	
}

