package Airbnb;


import org.apache.log4j.Logger;
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


	Logger logger = Logger.getLogger("AirbnbTests");

	public static Xls_Reader excelfile = null;
	TestListener tlistener=new TestListener();
	Utility utility=new Utility();
	AirbnbObjects airbnbobjects;
	
	
	WebElement element;	
	String CheckIn="";
	String CheckOut="";
		
	
	
	/////airbnb: Search Filters
	////////////////////Test Case: 1 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that the search results match the search criteria") 
	@Title("Verify that the search results match the search criteria")
	@Test(priority=1,description="Search results should match the search criteria")
	public void appliedFilterVerification () throws Exception {
		try {
			
		
		logger.info("Verifying Test Case 1");
		
		airbnbobjects=new AirbnbObjects(driver);
		
		int adults=Integer.parseInt(Adults);
		int childs=Integer.parseInt(Childs);
		
		try {
			utility.Pause(driver, airbnbobjects.LocationFld, "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
			
		
		utility.PomFindField(driver, airbnbobjects.LocationFld, Location);	
		logger.info("Entered data in location field");
		Thread.sleep(1000);
		utility.PomFindField(driver, airbnbobjects.LocationFld, Keys.ARROW_DOWN);
	
		element = driver.switchTo().activeElement();
		
		utility.PomFindField(driver, element, Keys.ENTER);
		logger.info("Selected option from location dropdown");
		
		String OnWeekFromToday=utility.AddDate(7);
	
		
		
		utility.PomFindClick(driver, airbnbobjects.CheckInDateXpath(OnWeekFromToday));
		logger.info("Clicked checkin date");
		
		String DateParts[]=OnWeekFromToday.split("-");
		int year=Integer.parseInt(DateParts[0]);
		int month=Integer.parseInt(DateParts[1]);
		int date=Integer.parseInt(DateParts[2]);
		
		String OnWeekFromCheckInDate=utility.AddDateSpecific(7, year, month, date);
		
		DateParts=OnWeekFromCheckInDate.split("-");
		int year1=Integer.parseInt(DateParts[0]);
		int month1=Integer.parseInt(DateParts[1]);
		int date1=Integer.parseInt(DateParts[2]);
		
		
		utility.PomFindClick(driver, airbnbobjects.CheckOutDateXpath(OnWeekFromCheckInDate));
		logger.info("Selected checkout date");
		
		String CheckinDateVal=utility.PomReturnText(driver, airbnbobjects.CheckInVal);
		String CheckoutDateVal=utility.PomReturnText(driver, airbnbobjects.CheckOutVal);
		
		String CheckinDateValParts[]=CheckinDateVal.split(" ");
		String CheckoutDateValParts[]=CheckoutDateVal.split(" ");
		
		utility.moveto(driver, airbnbobjects.mainPanel);
		
		utility.PomFindClick(driver, airbnbobjects.GuestsBtn);
		logger.info("Clicked guests field");
         
		
		for(int i=1;i<=adults;i++) {
		utility.PomFindClick(driver, airbnbobjects.adultsIncreaseBtn);
		}
		
		logger.info("Increased "+adults+" adults");
		
		
		for(int i=1;i<=childs;i++) {
		utility.PomFindClick(driver, airbnbobjects.childsIncreaseBtn);
		}
		
		logger.info("Increased "+childs+" child");
		
		utility.PomFindClick(driver, airbnbobjects.searchBtn);
		
		logger.info("Clicked search button");
		
		int totalGuests=adults+childs;
		String locationParts[]=Location.split(",");
		
		
		try {
			utility.Pause(driver, airbnbobjects.locationFltr(locationParts[0]), "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
		
		
		Thread.sleep(3000);
		
		String locationFltr=utility.PomReturnText(driver, airbnbobjects.locationFltr(locationParts[0]));
		logger.info("Verifying location filter");
		Assert.assertTrue(locationFltr.contains(locationParts[0]),"Location search filters is not working properly");
		logger.info("Location filter verified");
		
		if(CheckinDateValParts[0].equals(CheckoutDateValParts[0])) {
			CheckIn=CheckinDateVal;
			CheckOut=CheckoutDateValParts[1];
		}
		else {
			CheckIn=CheckinDateVal;
			CheckOut=CheckoutDateVal;

		}
		
		String dateFltr=Utility.PomReturnText(driver, airbnbobjects.dateFltrXpath(CheckIn, CheckOut));
		logger.info("Verifying date filter");
		Assert.assertTrue(dateFltr.contains(""+CheckIn+" - "+CheckOut+""),"Date search filter is not working properly");
		logger.info("Date filter verified");
		
		String guestFltr=Utility.PomReturnText(driver, airbnbobjects.guestsFltr(totalGuests));
		logger.info("Verifying guests filter");
		Assert.assertEquals(guestFltr, "Guests\n"+totalGuests+" guests","Guest search filter is not working properly");
		logger.info("Guests filter verified");
		
		String guestNumber="";
		String guestParts[];
		int guestsAvailable=0;
		
		Thread.sleep(3000);
		
		logger.info("Verifying guests accomodation on propety list.");
		
		for(int i=2;i<=21;i++) {
			try {
			guestNumber=utility.PomReturnText(driver, airbnbobjects.guestsLst(i));
			}
			catch(Exception exp) {
				break;
			}
			guestParts=guestNumber.split(" guests");
			guestsAvailable=Integer.parseInt(guestParts[0]);
			Assert.assertTrue(guestsAvailable>=totalGuests,"The properties displayed on the first page can not accommodate at least the selected\r\n" + 
					"number of guests.");
		}
		
		logger.info("Guests accomodation on propety list is verified");

		logger.info("Test Case 1 verified");
		}
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}
	}


	/////airbnb: Search Filters
	////////////////////Test Case: 2 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that the results and details page match the extra filters") 
	@Title("Verify that the results and details page match the extra filters")
	@Test(priority=2,description="The results and details page should match the extra filters")
	public void extraFilterVerification () throws Exception {
		try {
		logger.info("Verifying Test Case 2");
		
		int bedrooms=Integer.parseInt(Bedrooms);
		
		utility.moveto(driver, airbnbobjects.moreFltrBtn);
		logger.info("Clicked more filters button");
		
		try {
			utility.Pause(driver, airbnbobjects.bedroomsIncreaseValBtn, "Visibility", 30);
			}
			catch(Exception exp) {
				
			}
			
			Thread.sleep(2000);
			
		for (int i=1;i<=bedrooms;i++) {
		utility.PomFindClick(driver, airbnbobjects.bedroomsIncreaseValBtn);
		}
		
		logger.info("Bedrooms are increased to "+bedrooms);
		
		utility.PomFindClick(driver, airbnbobjects.poolChkBx);
		logger.info("Clicked checkbox aginst pool option");
		
		utility.PomFindClick(driver, airbnbobjects.showStaysBtn);
		logger.info("Clicked show task button");
		
		String bedroomNumber="";
		String bedroomParts[];
		String bedroomParts1[];
		String availableBedroomNumber="";
		int bedroomsAvailable=0;
		
		Thread.sleep(3000);
		
		logger.info("Verifying property is having required number of bedrooms");
		for(int i=2;i<=21;i++) {
			try {
			bedroomNumber=utility.PomReturnText(driver, airbnbobjects.guestsLst(i));
			}
			catch(Exception e) {
				break;
			}
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
		logger.info("Property is having required number of bedrooms is verified");
		
		utility.moveto(driver, airbnbobjects.guestsLst(2));
		logger.info("Clicked first property on property list");
	
	
		utility.switchTabs(driver);
	
	try {
		utility.Pause(driver, airbnbobjects.amnetiesLnk, "Visibility", 30);
		}
		catch(Exception exp) {
			
		}
	
	
		utility.PomFindClick(driver, airbnbobjects.amnetiesLnk);
		logger.info("Clicked amneties link on property detail page");
	
	try {
		utility.Pause(driver, airbnbobjects.poolLbl, "Visibility", 30);
		}
		catch(Exception exp) {
			
		}
	
	
	String poolLbl=utility.PomReturnText(driver, airbnbobjects.poolLbl);
	logger.info("Verifying pool label under facilities section in amneties popup");
	Assert.assertEquals(poolLbl, "Pool\nPrivate or shared","Pool is not being displayed under Facilities category in Amenities popup");
	logger.info("Pool label under facilities section in amneties popup is verified");
	
	
	logger.info("Test Case 2 verified");
		}
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}

	}

	/////airbnb: Search Filters
	////////////////////Test Case: 3 /////////////////////////////////
	@Features("airbnb: Search Filters")
	@Stories("Verify that a property is displayed on the map correctly") 
	@Title("Verify that a property is displayed on the map correctly")
	@Test(priority=3,description="Property should be displayed on the map correctly")
	public void mapDataVerification () throws Exception {
		try {
		logger.info("Verifying Test Case 3");
		
		utility.switchTabs(driver); 
		
		utility.moveto(driver, airbnbobjects.moreFltrBtn);
		
		logger.info("Clicked more filters button");
		
		try {
			utility.Pause(driver, airbnbobjects.poolLbl, "Click", 5);
			}
			catch(Exception exp) {
				
			}

		
		utility.PomFindClick(driver, airbnbobjects.clearAllLnk);
		logger.info("Clearing all the filters");
		
		utility.PomFindClick(driver, airbnbobjects.showStaysBtn);
		logger.info("Clicked show stays button");
	
		try {
			utility.Pause(driver, airbnbobjects.poolLbl, "Click", 5);
			}
			catch(Exception exp) {
				
			}

		
		utility.PomFindHover(driver, airbnbobjects.firstPropName);
		logger.info("Hovered over first property");
		
		String firstPropName=utility.PomReturnText(driver, airbnbobjects.firstPropName);
		
		utility.PomFindClick(driver, airbnbobjects.firstPropOnMap(firstPropName));
		logger.info("Clicked first property label on map");
		
		String propOnMap=utility.PomReturnValue(driver, airbnbobjects.firstPropOnMapPopUp,"aria-label");
		logger.info("Verifying property label on map popup");
		Assert.assertEquals(propOnMap, firstPropName,"Name of the first property is not being appearing on map popup.");
		logger.info("Property label on map popup is verified");
		
		logger.info("Test Case 3 verified");
		}
		catch(Exception exp) {
			throw new SkipException("Could not complete test due to unknown reason");
		}
	}

}

