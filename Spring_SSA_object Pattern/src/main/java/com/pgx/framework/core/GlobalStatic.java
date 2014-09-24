package com.pgx.framework.core;
import static com.pgx.framework.core.EnvParameters.getPropertyValue;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import javax.imageio.ImageIO;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class GlobalStatic  {
	public static HashMap<String, String> excelData = new HashMap<String, String>();
	public static int intOutputRowCntr = 0;
	public static String strReportFolderName="";
	public static String strReportFolderPath="";
	public static String strFilePath="";
	public static int intSheetCount=0;
	public static String strGlobalSheetName="";
	public static DesiredCapabilities capabilities;
	public static HashMap<String, String> getProps = new HashMap<String, String>();
        WebDriver driver=WebUtility.scrndriver;
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getDriver
		 *     Description          : returns the driver object 
		 *     Input ParameterList	: none
		 *     Return Type          : void
		 *     Class Name           : AllCommon
		 *     Package Name         : src.cts.Common
		 *     Created By           : Nainappa Illi
		 *     Created On           : July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By           : <Revision Author>
		 *     Revised On           : <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public WebDriver getDriver() {
	        return driver;
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: quit
		 *     Description          : quits the webdriver instance 
		 *     Input ParameterList	: none
		 *     Return Type          : void
		 *     Class Name           : AllCommon
		 *     Package Name         : src.cts.Common
		 *     Created By           : Nainappa Illi
		 *     Created On           : July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By           : <Revision Author>
		 *     Revised On           : <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public void quit() {
	        driver.quit();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: changeWindowSize
		 *     Description          : opens the window with the specified size passed through to the method
		 *     Input ParameterList	: @param windowSize-Dimension object
		 *     Return Type          : void
		 *     Class Name           : AllCommon
		 *     Package Name         : src.cts.Common
		 *     Created By           : Nainappa Illi
		 *     Created On           : July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By           : <Revision Author>
		 *     Revised On           : <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public void changeWindowSize(Dimension windowSize) {
	        driver.manage().window().setSize(new org.openqa.selenium.Dimension(windowSize.width, windowSize.height));
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: load
		 *     Description          : Opens the browser with the specified link in the parameters 
		 *     Input ParameterList	: none
		 *     Return Type          : void
		 *     Class Name           : AllCommon
		 *     Package Name         : src.cts.Common
		 *     Created By           : Nainappa Illi
		 *     Created On           : July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By           : <Revision Author>
		 *     Revised On           : <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public void load(String url) {
	        driver.get(url);
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: formatScreenSize
		 *     Description          : formats and retuns the screensize in string format 
		 *     Input ParameterList	: @param screenSize - Dimension object
		 *     Return Type          : String
		 *     Class Name           : AllCommon
		 *     Package Name         : src.cts.Common
		 *     Created By           : Nainappa Illi
		 *     Created On           : July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By           : <Revision Author>
		 *     Revised On           : <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String formatScreenSize(Dimension screenSize) {
	        if (screenSize != null) {
	            return String.format("%dx%d", screenSize.width, screenSize.height);
	        }
	        else return "0x0";
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: executeJavascript
		 *     Description          : executes the script  
		 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public void executeJavascript(String javascript) {
	        ((JavascriptExecutor)driver).executeScript(javascript);
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getUrl
		 *     Description          : returns the url of the currently open window 
		 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public String getUrl() {
	        return driver.getCurrentUrl();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getScreenSize
		 *     Description          : returns the screen size of the given window in dimension format
		 *     Input ParameterList	: none
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public Dimension getScreenSize() {
	        org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();
	        return new Dimension(windowSize.getWidth(), windowSize.getHeight());
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: createScreenshot
		 *     Description          : creates the full screenshot of the opened page 
		 *     Input ParameterList	: none
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public String createScreenshot() {
	            try {
	                return makeFullScreenshot(driver);
	            } catch (Exception e) {
	                throw new RuntimeException("Error making screenshot", e);
	            }
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: makeFullScreenshot
		 *     Description          : creates the full screenshot of the opened page 
		 *     Input ParameterList	: @param driver
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String makeFullScreenshot(WebDriver driver) throws IOException, InterruptedException {
	        byte[] bytes = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
	        int capturedWidth = image.getWidth();
	        int capturedHeight = image.getHeight();
	        
	        long longScrollHeight = (Long)((JavascriptExecutor)driver).executeScript("return Math.max(" + 
	                "document.body.scrollHeight, document.documentElement.scrollHeight," +
	                "document.body.offsetHeight, document.documentElement.offsetHeight," +
	                "document.body.clientHeight, document.documentElement.clientHeight);"
	            );
	        
	        int scrollHeight = (int)longScrollHeight;
	        
	        File file = File.createTempFile("screenshot", ".png");
	        
	        if (Math.abs(capturedHeight - scrollHeight) > 40) {
	            int scrollOffset = capturedHeight;
	            
	            int times = scrollHeight / capturedHeight;
	            int leftover = scrollHeight % capturedHeight;
	            
	            final BufferedImage tiledImage = new BufferedImage(capturedWidth, scrollHeight, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g2dTile = tiledImage.createGraphics();
	            g2dTile.drawImage(image, 0,0, null);

	            
	            int scroll = 0;
	            for (int i = 0; i < times - 1; i++) {
	                scroll += scrollOffset;
	                scrollVerticallyTo(driver, scroll);
	                Thread.sleep(100);
	                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
	                g2dTile.drawImage(nextImage, 0, (i+1) * capturedHeight, null);
	            }
	            if (leftover > 0) {
	                scroll += scrollOffset;
	                scrollVerticallyTo(driver, scroll);
	                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
	                BufferedImage lastPart = nextImage.getSubimage(0, nextImage.getHeight() - leftover, nextImage.getWidth(), leftover);
	                g2dTile.drawImage(lastPart, 0, scrollHeight - leftover, null);
	            }
	            
	            scrollVerticallyTo(driver, 0);
	            
	            ImageIO.write(tiledImage, "png", file);
	        }
	        else {
	            ImageIO.write(image, "png", file);
	        }
	        return file.getAbsolutePath();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: makeSimpleScreenshot
		 *     Description          : takes the normal screenshot 
		 *     Input ParameterList	: none
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    private String makeSimpleScreenshot() {
	        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        return file.getAbsolutePath();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: refresh
		 *     Description          : refreshes the current page
		 *     Input ParameterList	: None
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public void refresh() {
	        driver.navigate().refresh();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: scrollVerticallyTo
		 *     Description          : Scrolls the screen vertically to specified index 
		 *     Input ParameterList	: @param driver,scroll
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static void scrollVerticallyTo(WebDriver driver, int scroll) {
	        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, " + scroll + ");");
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getObjectDomensions
		 *     Description          : returns the size of the webelement
		 *     Input ParameterList	: @param wbelm
		 *     Return Type             	: Dimension
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static Dimension getObjectDomensions(WebElement wbelm){
	    	return wbelm.getSize();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getObjectHeight
		 *     Description          : returns the height of the object 
		 *     Input ParameterList	: @param wbelm
		 *     Return Type             	: int
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static int getObjectHeight(WebElement wbelm){
	    	return getObjectDomensions(wbelm).getHeight();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getObjectWidth
		 *     Description          : returns the width of the object
		 *     Input ParameterList	: @param wbelm
		 *     Return Type             	: int
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static int getObjectWidth(WebElement wbelm){
	    	return getObjectDomensions(wbelm).getWidth();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getLocation
		 *     Description          : returns the location of the object 
		 *     Input ParameterList	: @param wbelm
		 *     Return Type             	: Point
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static Point getLocation(WebElement wbelm){
	    	return wbelm.getLocation();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getXAxis
		 *     Description          : returns the x location of the object 
		 *     Input ParameterList	: @param wbelm
		 *     Return Type             	: int
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	     public static int getXAxis(WebElement wbelm){
	    	 return getLocation(wbelm).getX();
	     }
	     /**
	 	 ***************** Creation History ******************
	 	 *     Method Name			: getYAxis
	 	 *     Description          : returns the y location of the object 
	 	 *     Input ParameterList	: @param wbelm
	 	 *     Return Type             	: int
	 	 *     Class Name              	: AllCommon
	 	 *     Package Name          : src.cts.Common
	 	 *     Created By             	 	: Nainappa Illi
	 	 *     Created On              	: July 09, 2014
	 	 ***************** Revision History ******************
	 	 *     Revised By             	 	: <Revision Author>
	 	 *     Revised On              	: <Date>
	 	 *     Revision Description	: <Multi � Line Entry >
	 	 ***************** End of Header 
	 	 */
	     public static int getYAxis(WebElement wbelm){
	    	return getLocation(wbelm).getY();
	     }
	     /**
	 	 ***************** Creation History ******************
	 	 *     Method Name			: getObjectColor
	 	 *     Description          : returns the color of the objetc in rgba format
	 	 *     Input ParameterList	: @param wbelm
	 	 *     Return Type             	: String
	 	 *     Class Name              	: AllCommon
	 	 *     Package Name          : src.cts.Common
	 	 *     Created By             	 	: Nainappa Illi
	 	 *     Created On              	: July 09, 2014
	 	 ***************** Revision History ******************
	 	 *     Revised By             	 	: <Revision Author>
	 	 *     Revised On              	: <Date>
	 	 *     Revision Description	: <Multi � Line Entry >
	 	 ***************** End of Header 
	 	 */
	    public static String getObjectColor(WebElement wbelm){
	    	return wbelm.getCssValue("color");
	     }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getObjectHexaColorCode
		 *     Description          : returns the color of the object in hexa decimal format 
		 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	     public static String getObjectHexaColorCode(WebElement wbelm){
	    	String[] numbers = wbelm.getCssValue("color").replace("rgba(", "").replace(")", "").split(",");
			int r = Integer.parseInt(numbers[0].trim());
			int g = Integer.parseInt(numbers[0].trim());
			int b = Integer.parseInt(numbers[0].trim());
			String hex = "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
			return hex;
	    }
	     /**
	 	 ***************** Creation History ******************
	 	 *     Method Name			: getObjectHighlight
	 	 *     Description          : highlights the element passed in the parameter with yellow color
	 	 *     Input ParameterList	: @param wbelm
	 	 *     Return Type             	: void
	 	 *     Class Name              	: AllCommon
	 	 *     Package Name          : src.cts.Common
	 	 *     Created By             	 	: Nainappa Illi
	 	 *     Created On              	: July 09, 2014
	 	 ***************** Revision History ******************
	 	 *     Revised By             	 	: <Revision Author>
	 	 *     Revised On              	: <Date>
	 	 *     Revision Description	: <Multi � Line Entry >
	 	 ***************** End of Header 
	 	 */
	    public void getObjectHighlight(WebElement wbelm){
	       for (int i = 0; i < 2; i++) 
	    	  { 
		    	JavascriptExecutor js = (JavascriptExecutor) driver; 
		        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", wbelm, "color: yellow; border: 2px solid yellow;"); 
		    	js.executeScript("arguments[0].setAttribute('style', arguments[1]);", wbelm, "");
	    	  } 
	    }
	   /*public static void findScreenshot(String strFilePath){
	    	Screen screen = new Screen();
			Pattern image = new Pattern(strFilePath);
			try {
				screen.find(image);
			} catch (FindFailed e) {
				e.printStackTrace();
			}
	    }*/
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: compareScreenshot
		 *     Description          : compares two screenshots and returns the results
		 *     Input ParameterList	: @param strFilePath
		 *     Return Type             	: boolean
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   /* public static boolean compareScreenshot(String strFilePath){
	    	try{
		    	Screen displayScreen = new Screen();
				ScreenImage bf = displayScreen.capture();
				Finder comp = new Finder(bf, Region.create(0,0, 1900, 1500));
				comp.find("strFilePath", 0.99);
				if(comp.hasNext()){
					return true;
				}
				else
					return false;
		    }
	    	catch(Exception e){
	    		//gulp the exception
	    	}
	    	return false;
	    }*/
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: stringSplit
		 *     Description          : splits the string with specified delimiter 
		 *     Input ParameterList	: @param strProperty,strDelimiter
		 *     Return Type             	: String[]
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String[] stringSplit(String strProperty,String strDelimiter){
			String[] strProps;
			strProps=strProperty.split(strDelimiter);
			return strProps;
		}
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: subString
		 *     Description          : returns the substring with specified 
		 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String subString(String strSize,int supressCharacters){
	    	return strSize.substring(0,strSize.length()-supressCharacters);
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: readFromPropFile
		 *     Description          : reads the values from property file and returns them all
		 *     Input ParameterList	: @param strFilePath
		 *     Return Type             	: String[]
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String[] readFromPropFile(String strFilePath) {
	    	String key,value="";String[] keys = null;
	    	try {
				File file = new File(strFilePath);
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				Enumeration enuKeys = properties.keys();
				while (enuKeys.hasMoreElements()) {
					int i=0;
					key = (String) enuKeys.nextElement();
					value = properties.getProperty(key);
					getProps().put(key, value);
					keys[i]=key;
					i++;
				}
				return keys;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	return keys;
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getProps
		 *     Description          : put the values in hash map and returns hasmap object of them 
		 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
		 *     Return Type             	: HashMap<String, String>
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static HashMap<String, String> getProps() {
			return getProps;
		}
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getHashMapValueProps
		 *     Description          : gives the value from the hashmap with the given key 
		 *     Input ParameterList	: @param strKey
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String getHashMapValueProps(String strKey)  {
			String strValue = null;
			try {
				// MainReporting.addTestMethodNode(intTestInstance,"GetHashMapValues");
				strValue = getProps().get(strKey).toString().trim();
				return strValue;
			} catch (NullPointerException e) {
				//gulp the exception
			}
			return strValue;
		}
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getText
		 *     Description          : gets the text from the given object
		 *     Input ParameterList	: @param webelem
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String getText(WebElement webelem){
	    	return webelem.getText(); 
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getPageSource
		 *     Description          : gets the page source of the current page 
		 *     Input ParameterList	: none
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public String getPageSource() {
	        return driver.getPageSource();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getTitle
		 *     Description          : returns the title of the window 
		 *     Input ParameterList	: none
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public String getTitle() {
	        return driver.getTitle();
	    }
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getExcelCellValue
		 *     Description          : reads the cell value from the excel and place it in hashmap
		 *     Input ParameterList	: @param strFilePath,strSheetName,strRowIdentifier,strColIdentifier
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	    public static String getExcelCellValue(String strFilePath, String strSheetName, String strRowIdentifier, String strColIdentifier) throws IOException {
			Workbook objWorkBook;
			Sheet objCurrentSheet;
			String strAbsFilePath = getAbsolutePath(strFilePath);
			String strContent;
			int intCol, intRow;
			int intNoOfColumns = 0;
			int row;
			boolean blnSpecialCase = false;
			try {
				objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
				objCurrentSheet = objWorkBook.getSheet(strSheetName);
				intCol = getColumnNumber(strFilePath, strSheetName, strColIdentifier);
				intRow = getRowNumber(strFilePath, strSheetName, strRowIdentifier);
				if (intRow == -1 || intCol == -1) {
					return null;
				}
				strContent = objCurrentSheet.getCell(intCol, intRow).getContents();
				objWorkBook.close();
				return strContent;
			} catch (BiffException e) {
				return null;
			}
		}
	    /**
		 ***************** Creation History ******************
		 *     Method Name			: getAbsolutePath
		 *     Description          : returns the absolute path of the given file
		 *     Input ParameterList	: @param strFilePath
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   //To get absolute path of the file from relative path
	   public static String getAbsolutePath(String strFilePath) {
			try {
				if (strFilePath.contains("https")) {
					return strFilePath;
				}
				else {
					return new File(strFilePath).getAbsolutePath();
				}
			} catch (Exception objException) {
				return "";
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getColumnNumber
		 *     Description          : reads the data from the excel and returns the column count from it
		 *     Input ParameterList	: @param strFilePath,strSheetName,strColIdentifier
		 *     Return Type             	: int
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   //To get the column number with the given column identifies
	   public static int getColumnNumber(String strFilePath, String strSheetName, String strColIdentifier) throws IOException {
			Workbook objWorkBook;
			Sheet objCurrentSheet;
			String strAbsFilePath = getAbsolutePath(strFilePath);
			String strContent;
			int intNoOfColumns = 0;
			int col;
			boolean blnSpecialCase = false;
			try {
				objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
				objCurrentSheet = objWorkBook.getSheet(strSheetName);
				col = objCurrentSheet.findCell(strColIdentifier).getColumn();
				objWorkBook.close();
				return col;
			} catch (Exception e) {
				return -1;
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getRowNumber
		 *     Description          : reads the data from the excel and returns the rows count from it
		 *     Input ParameterList	: @param strFilePath,strSheetName,strColIdentifier
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   //To get the row number from the given row identifier
	   public static int getRowNumber(String strFilePath, String strSheetName, String strRowIdentifier) throws IOException {
			Workbook objWorkBook;
			Sheet objCurrentSheet;
			String strAbsFilePath = getAbsolutePath(strFilePath);
			String strContent;
			int intNoOfColumns = 0;
			int row;
			boolean blnSpecialCase = false;
			try {
				objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
				objCurrentSheet = objWorkBook.getSheet(strSheetName);
				if (strSheetName.equalsIgnoreCase("global")) {
					Cell[] arrCells = objCurrentSheet.getColumn(0);
					int i;
					boolean blnFound = false;
					for (i = 1; i < arrCells.length; i++) {
						if (arrCells[i].getContents().trim().equals(strRowIdentifier)) {
							blnFound = true;
							break;
						}
					}
					if (blnFound)
						row = i;
					else
						row = objCurrentSheet.findCell(strRowIdentifier).getRow();
				}
				else {
					row = objCurrentSheet.findCell(strRowIdentifier).getRow();
				}
				objWorkBook.close();
				return row;
			} catch (Exception e) {
				return -1;
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getColumnValuesFromExcel
		 *     Description          : returns the column values from the excel
		 *     Input ParameterList	: @param strFilePath,strSheetName,strColIdentifier
		 *     Return Type             	: int
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   //To get all the values from column with the given identifier
	   public static int getColumnValuesFromExcel( String strFilePath, String strSheetName, String strColIdentifier) throws IOException {
			Workbook objWorkBook;
			Sheet objCurrentSheet;
			String strAbsFilePath = getAbsolutePath(strFilePath);
			int intColNum, intNoOfRows = 0;
			boolean blnSpecialCase = false;
			try {
				objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
				objCurrentSheet = objWorkBook.getSheet(strSheetName);
				intColNum = objCurrentSheet.findCell(strColIdentifier).getColumn();
				Cell[] colValues = objCurrentSheet.getColumn(intColNum);
				// Column count of row(0) i.e.Column header
				for (int intLpCtr = 0; intLpCtr < colValues.length; intLpCtr++) {
					if (colValues[intLpCtr].getContents().trim().equals("")) {
						if (intLpCtr != 1) {
							intNoOfRows = intLpCtr;
							blnSpecialCase = true;
							break;
						}
					}
				}
				if (!blnSpecialCase) {
					intNoOfRows = colValues.length;
				}
			} 
			catch (Exception e) {
				System.out.println("Error occurred in getColumnValuesFromExcel");
			}
			return intNoOfRows;
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getDataFromExcelToHashmap
		 *     Description          : reads the data from the specified row and puts it in the hasmap 
		 *     Input ParameterList	: @param strFilePath,strSheetName,intCurrentRow
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static void getDataFromExcelToHashmap(String strFilePath, String strSheetName, int intCurrentRow) throws IOException {
			File objInputFile;
			Workbook objWorkBook;
			Sheet objCurrentSheet;
			objInputFile = new File(strFilePath);
			String strContent;
			int intNoOfColumns = 0;
			boolean blnSpecialCase = false;
			try {
				objWorkBook = Workbook.getWorkbook(objInputFile);
				objCurrentSheet = objWorkBook.getSheet(strSheetName);
				Cell[] arrMetaData = objCurrentSheet.getRow(0);
				// Column count of row(0) i.e.Column header
				for (int intLpCtr = 0; intLpCtr < arrMetaData.length; intLpCtr++) {
					if (arrMetaData[intLpCtr].getContents().trim().equals("")) {
						intNoOfColumns = intLpCtr;
						blnSpecialCase = true;
						break;
					}
				}
				if (!blnSpecialCase) {
					intNoOfColumns = arrMetaData.length;
				}
				intCurrentRow = intCurrentRow - 1;
				for (int intColCntr = 0; intColCntr < intNoOfColumns; intColCntr++) {
					strContent = objCurrentSheet.getCell(intColCntr, intCurrentRow).getContents().trim();
					getExcelData().put(strSheetName + "_" + arrMetaData[intColCntr].getContents().trim(), strContent);
				}
				objWorkBook.close();
			} catch (BiffException e) {
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getExcelData
		 *     Description          : returns hashmap object 
		 *     Input ParameterList	: none
		 *     Return Type             	: HashMap
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static HashMap<String, String> getExcelData() {
			return excelData;
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getHashMapValue
		 *     Description          : put the given data in the hashmap 
		 *     Input ParameterList	: @param strKey
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static String getHashMapValue(String strKey)  {
			String strValue = null;
			String[] strArrKey = strKey.split("_");
			try {
				// MainReporting.addTestMethodNode(intTestInstance,"GetHashMapValues");
				strValue = getExcelData().get(strKey).toString().trim();
				return strValue;
			} catch (NullPointerException e) {}
			return strValue;
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: createWriteableWorkbook
		 *     Description          : creates the writable excel sheet with the specified headers 
		 *     Input ParameterList	: @param strFilePath,strSheetName,arrColumnHeader
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static void createWriteableWorkbook(String strFilePath, String strSheetName, String[] arrColumnHeader) throws IOException, RowsExceededException, WriteException {
			try {
				WorkbookSettings workbookSettings = new WorkbookSettings();
				workbookSettings.setLocale(new Locale("en", "EN"));
				WritableWorkbook writableWorkbook = jxl.Workbook.createWorkbook(new File(strFilePath), workbookSettings);
				WritableSheet writableSheet = writableWorkbook.createSheet(strSheetName, intSheetCount);
				intSheetCount=intSheetCount+1;
				// Format the Font
				WritableCellFormat cfwithColor = new WritableCellFormat(getHeaderFormat(Colour.AQUA, Pattern.SOLID,Border.ALL,BorderLineStyle.THIN));
				// Creates Label and writes date to one cell of sheet
				Label label;
				CellView cellView;
				for (int intColHdrCntr = 0; intColHdrCntr < arrColumnHeader.length; intColHdrCntr++) {
					// Creates Label and writes float number to one cell of sheet
					label = new Label(intColHdrCntr, 0, arrColumnHeader[intColHdrCntr], cfwithColor);
					writableSheet.addCell(label);
					cellView = writableSheet.getColumnView(intColHdrCntr);
					cellView.setSize(arrColumnHeader[intColHdrCntr].length()*256+100);
					writableSheet.setColumnView(intColHdrCntr, cellView);
				}
				writableWorkbook.write();
				writableWorkbook.close();
			} catch (Exception e) {
				if (e.getMessage().contains("Access is denied")) {
					System.out.println("Access denied");
				}
				else{
					e.printStackTrace();
				}
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: createNewSheet
		 *     Description          : creates a new sheet for the existing excel sheet in the runtime
		 *     Input ParameterList	: @param strSheetName,arrColumnHeader
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static void createNewSheet(String strSheetName,String[] arrColumnHeader) throws IOException, BiffException, WriteException {
			try {
				jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File(strFilePath));
				WritableWorkbook writableWorkbook = jxl.Workbook.createWorkbook(new File(strFilePath), workbook);
				WritableSheet writableSheet = writableWorkbook.createSheet(strSheetName,intSheetCount);
				strGlobalSheetName=strSheetName;
				// Format the Font
				WritableCellFormat cfwithColor = new WritableCellFormat(getHeaderFormat(Colour.AQUA, Pattern.SOLID,Border.ALL,BorderLineStyle.THIN));
				// Creates Label and writes date to one cell of sheet
				intOutputRowCntr=0;
				intSheetCount=intSheetCount+1;
				Label label;
				CellView cellView;
				for (int intColHdrCntr = 0; intColHdrCntr < arrColumnHeader.length; intColHdrCntr++) {
					// Creates Label and writes float number to one cell of sheet
					label = new Label(intColHdrCntr, 0, arrColumnHeader[intColHdrCntr], cfwithColor);
					writableSheet.addCell(label);
					cellView = writableSheet.getColumnView(intColHdrCntr);
					cellView.setSize(arrColumnHeader[intColHdrCntr].length()*256+100);
					writableSheet.setColumnView(intColHdrCntr, cellView);
				}
				writableWorkbook.write();
				writableWorkbook.close();
				workbook.close();
			} catch (Exception e) {
				//gulp the exception
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: updateExcel
		 *     Description          : Enters the data given to the specified excel sheet 
		 *     Input ParameterList	: @param strFilePath,arrValues
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static void updateExcel(String strFilePath, ArrayList arrValues) throws IOException, BiffException, WriteException {
			try {
				jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File(strFilePath));
				WritableWorkbook writableWorkbook = jxl.Workbook.createWorkbook(new File(strFilePath), workbook);
				WritableSheet writableSheet = writableWorkbook.getSheet(strGlobalSheetName);
				WritableCellFormat cfwithColor = new WritableCellFormat(getCellFormat(Colour.RED, Pattern.SOLID,Border.ALL,BorderLineStyle.THIN));
				WritableCellFormat cfwithoutColor = new WritableCellFormat(getCellFormat(Colour.RED, Pattern.NONE,Border.ALL,BorderLineStyle.THIN));
				// Creates Label and writes date to one cell of sheet
				Label label;
				CellView cellView;
				intOutputRowCntr = intOutputRowCntr + 1;
				for (int intColHdrCntr = 0; intColHdrCntr < arrValues.size(); intColHdrCntr++) {
					if(arrValues.get(intColHdrCntr).toString().contains("~")){
						String strTemp=arrValues.get(intColHdrCntr).toString();
						strTemp=strTemp.substring(0, strTemp.length()-1);
						label = new Label(intColHdrCntr, intOutputRowCntr, strTemp, cfwithColor);
					}
					else
						label = new Label(intColHdrCntr, intOutputRowCntr, arrValues.get(intColHdrCntr).toString(), cfwithoutColor);
					writableSheet.addCell(label);
					cellView = writableSheet.getColumnView(intColHdrCntr);
					cellView.setSize(arrValues.get(intColHdrCntr).toString().length()*256+100);
					writableSheet.setColumnView(intColHdrCntr, cellView);
				}
				writableWorkbook.write();
				writableWorkbook.close();
				workbook.close();
			} catch (Exception e) {
				//gulp the exception
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getCellFormat
		 *     Description          : format the cell of the excel with the given specifications 
		 *     Input ParameterList	: @param colour,pattern,border,borderLineStyle
		 *     Return Type             	: WritableCellFormat
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
                private static WritableCellFormat getCellFormat(Colour colour, Pattern pattern,Border border, BorderLineStyle borderLineStyle) throws WriteException {
                         WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD);
                         WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
                         cellFormat.setBackground(colour, pattern);
                         cellFormat.setBorder(border, borderLineStyle);
                         return cellFormat;
                }
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getHeaderFormat
		 *     Description          : format the cell of the excel with the given specifications 
		 *     Input ParameterList	: @param colour,pattern,border,borderLineStyle
		 *     Return Type             	: WritableCellFormat
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   private static WritableCellFormat getHeaderFormat(Colour colour, Pattern pattern,Border border, BorderLineStyle borderLineStyle) throws WriteException {
		   WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(colour, pattern);
		    cellFormat.setBorder(border, borderLineStyle);
		    return cellFormat;
	   }
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: createFolder
		 *     Description          : creates the folder with the given path
		 *     Input ParameterList	: @param strDirectoy
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static void createFolder(String strDirectoy) {
			boolean success = (new File(strDirectoy)).mkdirs();
			if (success) {
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: getCurrentReportFolderName
		 *     Description          : give the folder name with the time stamp 
		 *     Input ParameterList	: none
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
	   public static String getCurrentReportFolderName() {
			if (strReportFolderName == "") {
				try{
					DateFormat dtYearFormat = new SimpleDateFormat("yyyy");
					DateFormat dtMonthFormat = new SimpleDateFormat("M");
					String strCurrYear = dtYearFormat.format(new Date());
					String strCurrMonth = dtMonthFormat.format(new Date());
					int intYear = Integer.parseInt(strCurrYear);
						strReportFolderPath = "C:\\RWD Automation Excel Reports\\RWD Test Results\\" + strCurrYear + "\\" + theMonth(Integer.parseInt(strCurrMonth)) + "\\" + System.getProperty("user.name") + "\\";
				}
				catch(Exception e){
					
				}
				strReportFolderName = getCurrentDateTime("ddMMMyy_hhmmss") + "_"+ "RWDAutomation";
				return strReportFolderName;
			}
			else {
				return strReportFolderName;
			}
		}
	   /**
		 ***************** Creation History ******************
		 *     Method Name			: theMonth
		 *     Description          : returns the month with the given integer value
		 *     Input ParameterList	: @param month
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
		public static String theMonth(int month){
		    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		    return monthNames[month-1];
		}
		/**
		 ***************** Creation History ******************
		 *     Method Name			: getCurrentDateTime
		 *     Description          : gives the current date  
		 *     Input ParameterList	: @param strFormat
		 *     Return Type             	: String
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
		public static String getCurrentDateTime(String strFormat) {
			java.util.Date currentDate = new java.util.Date();
			SimpleDateFormat newFormat = new SimpleDateFormat(strFormat);
			return newFormat.format(currentDate);
		}
		/**
		 ***************** Creation History ******************
		 *     Method Name			: createExcel
		 *     Description          : create the excel sheet with the column headers given in the specified path
		 *     Input ParameterList	: @param arrColumnHeader,strSheetName
		 *     Return Type             	: void
		 *     Class Name              	: AllCommon
		 *     Package Name          : src.cts.Common
		 *     Created By             	 	: Nainappa Illi
		 *     Created On              	: July 09, 2014
		 ***************** Revision History ******************
		 *     Revised By             	 	: <Revision Author>
		 *     Revised On              	: <Date>
		 *     Revision Description	: <Multi � Line Entry >
		 ***************** End of Header 
		 */
		public static void createExcel(String strXLPath,String[] arrColumnHeader,String strSheetName){
			strGlobalSheetName=strSheetName;
			String strDirFilePath=getCurrentReportFolderName();
			String strFolderPath=strReportFolderPath+strDirFilePath+"\\";
			createFolder(strFolderPath);
			strFilePath=strFolderPath+"RWDTestResults.xls";
			try {
				createWriteableWorkbook(strFilePath,strGlobalSheetName,arrColumnHeader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

