package com.xxx.test;
import static com.xxx.test.DriverScript.APP_LOGS;
import static com.xxx.test.DriverScript.CONFIG;
import static com.xxx.test.DriverScript.OR;
import static com.xxx.test.DriverScript.currentTestCaseName;
import static com.xxx.test.DriverScript.currentTestDataSetID;
import static com.xxx.test.DriverScript.currentTestSuiteXLS;
//import static com.manager.publishthis.test.DriverScript.OR;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.bcel.classfile.Constant;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;
import org.openqa.selenium.safari.SafariDriver;

//import com.manager.publishthis.util.ScrollingBrowser;
import com.xxx.xls.read.Xls_Reader;
public class Keywords {
	
	public WebDriver driver;
	
	
	public String openBrowser(String object,String data){		
		APP_LOGS.debug("Opening browser");
		if(data.equals("Mozilla"))
			driver=new FirefoxDriver();
		else if(data.equals("IE"))
			driver=new InternetExplorerDriver();
		else if(data.equals("Chrome"))
			driver = new ChromeDriver();
		
			
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;

	}
	
	
	public String navigate(String object,String data){		
		APP_LOGS.debug("Navigating to URL");
		try{
		driver.navigate().to(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public void clearInputBox(String object,String data){		
		APP_LOGS.debug("Clearing Input box");
		try{
		driver.findElement(By.xpath(OR.getProperty(object))).clear();
		}catch(Exception e){
			//return Constants.KEYWORD_FAIL+" -- Not able to clear";
		}
		//return Constants.KEYWORD_PASS;
	}
	
	public String clickLink(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        try{
        driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }
     
		return Constants.KEYWORD_PASS;
	}
	public String clickLink_linkText(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        driver.findElement(By.linkText(OR.getProperty(object))).click();
     
		return Constants.KEYWORD_PASS;
	}
	
	public String clickButton_class(String object,String data){
        APP_LOGS.debug("Clicking on Button ");
        driver.findElement(By.className(OR.getProperty(object))).click();
     
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public  String verifyLinkText(String object,String data){
        APP_LOGS.debug("Verifying link Text");
        try{
        	String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
        	String expected=data;
        	
        	if(actual.equals(expected))
        		return Constants.KEYWORD_PASS;
        	else
        		return Constants.KEYWORD_FAIL+" -- Link text not verified";
        	
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

        }
        
	}
	
	
	public  String clickButton(String object,String data){
        APP_LOGS.debug("Clicking on Button");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
            }catch(Exception e){
    			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
            }
        
        
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public  String verifyButtonText(String object,String data){
		APP_LOGS.debug("Verifying the button text");
		try{
		String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
    	String expected=data;

    	if(actual.equals(expected))
    		return Constants.KEYWORD_PASS;
    	else
    		return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			if(!data.equals(Constants.RANDOM_VALUE)){
			  driver.findElement(By.xpath("//*[starts-with(@id, 'boundlist')]")).sendKeys(data);
			}else{
				// logic to find a random value in list
				WebElement droplist= driver.findElement(By.xpath("//*[starts-with(@id, 'boundlist')]")); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				String selectedVal=droplist_cotents.get(index).getText();
				
			  driver.findElement(By.xpath("//*[starts-with(@id, 'boundlist')]")).sendKeys(selectedVal);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}
		
		return Constants.KEYWORD_PASS;	
	}
	
	public String verifyAllListElements(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list");
	try{	
		WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
		List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
		
		// extract the expected values from OR. properties
		String temp=data;
		String allElements[]=temp.split(",");
		// check if size of array == size if list
		if(allElements.length != droplist_cotents.size())
			return Constants.KEYWORD_FAIL +"- size of lists do not match";	
		
		for(int i=0;i<droplist_cotents.size();i++){
			if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
			}
		}
	}catch(Exception e){
		return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

	}
		
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
				}
			
			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}
	
	public  String selectRadio(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(String object, String data){
		APP_LOGS.debug("Verify Radio Selected");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return Constants.KEYWORD_FAIL+"- Radio not selected";	

				
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	
	public  String checkCheckBox(String object,String data){
		APP_LOGS.debug("Checking checkbox");
		try{
			// true or null
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbo";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public String unCheckCheckBox(String object,String data){
		APP_LOGS.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}
		
		
	}
	
	
	public String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
	    	String expected=data;

	    	if(actual.equals(expected))
	    		return Constants.KEYWORD_PASS;
	    	else
	    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}
	
	public  String writeInInput(String object,String data){
		APP_LOGS.debug("Writing in text box");
		
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String writeInInputByClass(String object,String data){
		APP_LOGS.debug("Writing in text box");
		
		try{
			driver.findElement(By.className(OR.getProperty(object))).sendKeys(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
		
		
		
	}
	public String clickButtonByText(String object, String data){
		try{
			driver.findElement(By.linkText("Create New Source Bundle")).click();
			return Constants.KEYWORD_PASS;	
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;	
		}
		
		//return Constants.KEYWORD_PASS;	
	}
	
	public String writeInInputById(String object, String data){
		
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			return Constants.KEYWORD_PASS;		
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		
	}
	
	public String clickLinkDashboard(String object, String data){
		try{
			//if(driver.findElement(By.xpath(OR.getProperty("dashboard_pg")))).isDisplayed())
			for(int i=0;i<=20;i++){
			if(!(driver.findElement(By.xpath("//label[text()='AutomatedQA']")).isDisplayed())){
				driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div[1]/div[2]/div/div[1]/div/div[8]")).click();
			}
			else{
				driver.findElement(By.xpath("//div[1]/div[2]/div[3]/div[1]/div[1]/div/table/tbody/tr[9]/td[6]/div/a")).click();				    		}
				 /*WebElement table = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div/div/table"));
				 List<WebElement> rows = table.findElements(By.tagName("tr"));
				 for(int rowNum=2; rowNum<rows.size(); rowNum++){ 
						List<WebElement> cells = rows.get(rowNum).findElements(By.tagName("td"));
				    	for(int colNum=1;colNum<cells.size();colNum++){
				    		//if(cells.get(colNum).getText().equals("AutomatedQA"))
				    		if((cells.get(colNum).findElement(By.xpath("//label[text()='AutomatedQA']")).isDisplayed())){
				    			Thread.sleep(5);
				    			//cells.get(8).findElement(By.xpath("//*[text()='Go to Dashboard']")).click();
				    		    //rows.get(9).findElement(By.xpath("//*[text()='Go to Dashboard']")).click();
				    			driver.findElement(By.xpath("//div[1]/div[2]/div[3]/div[1]/div[1]/div/table/tbody/tr[8]/td[6]/div/a")).click();				    		}
				    		
				    	}
				 }*/
			}
				

			
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
		}
		
		/*WebElement table = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div/div/table"));
	    List<WebElement> rows = table.findElements(By.tagName("tr"));
	    
	    for(int rowNum=1; rowNum<rows.size(); rowNum++){
	    	
	    	List<WebElement> cells = rows.get(rowNum).findElements(By.tagName("td"));
	    	
	    	for(int colNum=0;colNum<cells.size();colNum++){
	    		
	    		//System.out.print(cells.get(colNum).getText());
	    		 
	    		if((cells.get(colNum).getText().contains("AlphaQA"))){
	    		
	    		
	    		cells.get(rowNum).findElement(By.xpath("//*[text()='Go to Dashboard']")).click();
	    		    	
	    		//cells.get(colNum).findElement(By.xpath("//*[text()='Go to Dashboard']")).click();
	    	}//else
	    		//driver.findElement(By.xpath(OR.getProperty("nextbtn"))).click();
	           
	    	
	    			    }
	    }		}catch(Exception e)	{
	    	//System.out.println();
	    }*/
		
		
		return Constants.KEYWORD_FAIL;	
	}
	
	public String mouseHover(String object, String data){
		try{
			WebElement menu = driver.findElement(By.xpath(OR.getProperty(object)));
			
			Actions action = new Actions(driver);
			
			action.moveToElement(menu).moveToElement(menu);
			Thread.sleep(1000);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	public String mouseHover1(String object, String data){
		try{
			WebElement menu = driver.findElement(By.xpath(OR.getProperty(object)));
			
			Actions action = new Actions(driver);
			action.moveToElement(menu).click();
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	public String mouseHover2(String object, String data){
		try{
			WebElement menu = driver.findElement(By.xpath(OR.getProperty(object)));
			
			Actions action = new Actions(driver);
			action.moveToElement(menu);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	public String clickById(String object, String data){
		try{
			driver.findElement(By.xpath("//*[starts-with(@id, 'addbutton')]")).click();
			return Constants.KEYWORD_PASS;		
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		
	}
	
	
	
	public String selectAutosuggestOptionSEARCH(String object, String data)
	{
		try{
		
		Thread.sleep(2000L);
		
		/*List<WebElement> options= driver.findElements(By.className("displayName"));
		System.out.println(options.size());
		for(int i=0;i<options.size();i++){
			if(options.get(i).getText().contains("Apple")){
				options.get(i).click();
			}
			
		}*/
		System.out.println(isElementPresent(By.xpath("//li[1]/label")));
		for(int i=1;isElementPresent(By.xpath("//li["+i+"]/label"));i++){
			if(driver.findElement(By.xpath("//li["+i+"]/label")).getText().contains("apple")){
				driver.findElement(By.xpath("//li["+i+"]/label")).click();
			}
		}
		
		return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
		
		//return Constants.KEYWORD_PASS;		
	}
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public String maximize(String object, String data)
	{
		driver.manage().window().maximize();
		return Constants.KEYWORD_PASS;
	}
	
	public String addToList(String object, String data){
		try{
			driver.manage().window().maximize();
		driver.findElements(By.xpath(OR.getProperty(object))).get(0).click();
		return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
		}
		
	}
	
	public String writeInFieldByTag(String object, String data)
	{
		try{
			driver.findElements(By.xpath(OR.getProperty(object))).get(0).sendKeys(data);
			return Constants.KEYWORD_PASS;
		}catch(Exception e)
		{		return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	
	public String WritiningInHiddenFields(String object, String data){
		try{
			WebElement elem = driver.findElement(By.xpath(OR.getProperty(object)));
			String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";

			((JavascriptExecutor) driver).executeScript(js, elem);
			elem.sendKeys(data);
			return Constants.KEYWORD_PASS;
		}catch(Exception e)
		{		return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}	
	}
		
	public String scrollPage(String object, String data)
	{
		try{
	        driver.manage().window().maximize();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(250,350)");
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
		}
	}
	
	public String scrollToElement(String object, String data)
	{
		try{
		WebElement we = driver.findElement(By.xpath(OR.getProperty(object)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", we);
		Thread.sleep(1000);
		//we.click();
        return Constants.KEYWORD_PASS;
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	
	
	public String scrollToElementAndClick(String object, String data)
	{
		try{
		WebElement we = driver.findElement(By.xpath(OR.getProperty(object)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", we);
		Thread.sleep(1000);
		we.click();
        return Constants.KEYWORD_PASS;
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+" Unable to click "+e.getMessage();
	}
	}
	
	
	public String writeInInputRandomString(String object, String data){
        APP_LOGS.debug("Writing in text box");
        String uuid = UUID.randomUUID().toString();
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(uuid);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		//return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyTextinInput(String object,String data){
       APP_LOGS.debug("Verifying the text in input box");
       try{
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}
	
	public  String clickImage(){
	       APP_LOGS.debug("Clicking the image");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(){
	       APP_LOGS.debug("Verifying inage filename");
			
			return Constants.KEYWORD_PASS;
	}
	
	
	
	
	public  String verifyTitle(String object, String data){
	       APP_LOGS.debug("Verifying title");
	       try{
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle=data;
	    	   if(actualTitle.equals(expectedTitle))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	
	public String exist(String object,String data){
	       APP_LOGS.debug("Checking existance of element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object)));
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Object doest not exist";
			  }
	       
	       
			return Constants.KEYWORD_PASS;
	}
	
	public  String click(String object,String data){
	       APP_LOGS.debug("Clicking on any element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object))).click();
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Not able to click";
			  }
			return Constants.KEYWORD_PASS;
	}
	
	public  String synchronize(String object,String data){
		APP_LOGS.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				Thread.sleep(1000L);
				start++;
			}else{
				break;
			}
		 }
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBroswer(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{
			driver.quit();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	
	
	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		long time = (long)Double.parseDouble(object);
		Thread.sleep(time*1000L);
		return Constants.KEYWORD_PASS;
	}
	
	
	public String clickConfirmation(String object, String data)
	{    
		
		try{
             
			Alert javascriptAlert1 = driver.switchTo().alert();
			javascriptAlert1.accept();
			//driver.findElement(By.xpath(OR.getProperty(object))).click();
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
			
	}
	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
		
	public String validateLogin(String object, String data){
		APP_LOGS.debug("Validating Login");
		String data_flag=currentTestSuiteXLS.getCellData(currentTestCaseName, "Data_correctness",currentTestDataSetID );
		if (data_flag.equals("Y")){
        try{
			if(driver.getCurrentUrl().equals(CONFIG.getProperty("clientpageURL"))){
				return Constants.KEYWORD_PASS + "Able to login with correct credentials";
								}
		} catch(Exception e){
		  return Constants.KEYWORD_FAIL +"Object not found";
		}
		}
        else if((driver.findElement(By.id(OR.getProperty("errorid")))).isDisplayed())
        	return Constants.KEYWORD_PASS + "Not able to login with incorrect credentials, error message present";
		return Constants.KEYWORD_PASS;
		
      	
	}
	
	public String validateDashboard(String object, String data){
		APP_LOGS.debug("Validating Login");
		try{
			if(driver.getTitle().contains(OR.getProperty("dashboardtitle"))){
				return Constants.KEYWORD_PASS + "Dashboard Displayed";
								}
		} catch(Exception e){
		  return Constants.KEYWORD_FAIL +"Dashboard is not Displayed";
		}
		
		
		return Constants.KEYWORD_FAIL;
	}
	
	public String validateFindAndCuratePage(String object, String data)
	{
APP_LOGS.debug("Validating Login");
		
		try{
			if((driver.getTitle().equals(OR.getProperty("findandcuratetitle"))&&(driver.findElement(By.className("document-wrap ")).isDisplayed())&&(driver.findElement(By.xpath("html/body/div[1]/div[3]/div[3]/div[2]/div[2]/div[2]/div/div[1]/div/table/tbody/tr/td[1]")).isDisplayed())&&(driver.findElement(By.xpath("html/body/div[1]")).isDisplayed()))){
				return Constants.KEYWORD_PASS + "Find and Curate page is Displayed";
								}
		} catch(Exception e){
		  return Constants.KEYWORD_FAIL +"Find and Curate page is not Displayed";
		}
		
		
		return Constants.KEYWORD_FAIL;
	
	}

		
	/*public String clickdashboard(String object, String data){
	    //String selector = a[class= 'x-grid-row x-grid-row-alt x-grid-row-over'][type='a'];
		//driver.findElement(By.className("x-grid-row x-grid-row-alt x-grid-row-over")).click();
		driver.findElement(By.cssSelector(OR.getProperty("selectordashboard"))).click();
		return Constants.KEYWORD_PASS;
	}*/
	
	
	public String writeInInputByCSS(String object, String data){
		WebElement targetElement=driver.findElement(By.cssSelector("input[class='x-form-field x-form-text x-form-empty-field'] [id^='ext-gen']"));
		
		targetElement.sendKeys("apple");
		return Constants.KEYWORD_PASS;
	}
	
	

	
	
	// not a keyword
	
	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	
	public Boolean checkObjectExist(String xpath)
	{
		if((driver.findElement(By.xpath("xpath"))).isDisplayed())
		{
			return true;
		}
		
		else
			return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
