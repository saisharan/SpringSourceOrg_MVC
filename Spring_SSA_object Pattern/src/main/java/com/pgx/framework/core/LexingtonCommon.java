package com.pgx.framework.core;

import com.pgx.framework.ajax.Until;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LexingtonCommon {
    WebDriverBase base=new WebDriverBase();
    public static String winHandleBefore="";
    //Test to check 
    public static String sqlQueryPhoneNumber =DBManipulation.getPropertyValue("sqlQuery_phoneNumber");
    public static String sqlQueryCookie = DBManipulation.getPropertyValue("sqlQuery_Cookie");
        /*
         * This method checks the cookie value from the application with given value
         */
	public boolean cookieTIDVerification(String strTID,String strCookie){
		String strCookieValue;
		strCookieValue=CookiesUtilities.getCookieValue(base.driver,strCookie);
		if(strCookieValue.contains(strTID)){
                    return true;
		}
		else
                    return false;
	}
        /*
         * This method returns the cookie from the DB 
         */
	public String cookieDBVerification(){
		String strResults="";
		try{
                    String strServerName;
                    String  strQuery;
                    Connection objSQLConnection;
                    String strQueryFilepath="";
                    strQuery=sqlQueryCookie;
                    strServerName="jdbc:mysql://austria-dev.pgx.local:3306/lexingtonlaw_www?user=qaf.automation&password=kuVa7ethUpre";
                    objSQLConnection = DBManipulation.establishConnection(strServerName);
                    strQuery=DBManipulation.replaceValueForVariableInSQL(strQuery,"2241");
                    strResults = DBManipulation.executeSQLDBQuery(objSQLConnection, strQuery);
                    LoggerUtil.log("Db results are"+strResults);
		}
		catch(Exception e){
			//gulp the exception
		}
		return strResults;
	}
        /*
         * This method returns the phone number from DB 
         */
        public String phoneNumberDBVerification(){
		String strResults="";
		try{
                    String strServerName;
                    String strQuery;
                    Connection objSQLConnection;
                    strQuery=sqlQueryPhoneNumber;
                    strServerName="jdbc:mysql://austria-dev.pgx.local:3306/lexingtonlaw_www?user=qaf.automation&password=kuVa7ethUpre";
                    objSQLConnection = DBManipulation.establishConnection(strServerName);
                    strQuery=DBManipulation.replaceValueForVariableInSQL(strQuery,"2241");
                    strResults = DBManipulation.executeSQLDBQuery(objSQLConnection, strQuery);
                   LoggerUtil.log("The DB results are"+strResults);
		}
		catch(Exception e){
                    System.out.println("Exception occured while fetching data from db for phone number verification");
			//gulp the exception
		}
		return strResults;
	}
        /*
         * This method verifies footer from spread sheet with the application and returns the result
         */
         public boolean footerVerification(WebDriver driver,String strXLPath,String strSheetName)  {
            boolean link_present=true;
            String headingName="";
            String headerClass="";
            String weburl = driver.getCurrentUrl();
            WebElement wbelm;
            String footerName,footerURL,expectedFooterURL;
            WebDriverWait wait = new WebDriverWait(driver, 60);
            System.out.println(Integer.parseInt(TestNGBase.strScreenWidth));
            if (Integer.parseInt(TestNGBase.strScreenWidth) >= 560) {
                try{
                    int intRows=GlobalStatic.getColumnValuesFromExcel(strXLPath, strSheetName, "FooterLinkName");
                    for(int intlpctrl=1;intlpctrl<intRows;intlpctrl++)
                    {
                        GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intlpctrl+1);
                        footerName = GlobalStatic.getHashMapValue(strSheetName + "_FooterLinkName");
                        expectedFooterURL = GlobalStatic.getHashMapValue(strSheetName + "_ExpectedFooterLinkURL").toString();
                        driver.get(weburl);
                        if (footerName.contains("*****")) {
                            headingName = footerName.replace("*****", "");
                            if (headingName.equals("Lexington Law")) {
                                headerClass="bottom_nav_section_main";
                            }
                            else if(headingName.equals("Credit Repair Services")) {
                                headerClass="bottom_nav_section_credit_repair_services";
                            }
                            else if(headingName.equals("Our Firm")) {
                                headerClass="bottom_nav_section_our_firm";
                            }
                            else if(headingName.equals("Credit Education")) {
                                headerClass="bottom_nav_section_credit_education";
                            }
                        }
                        else
                        {
                            if (!driver.findElements(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/a") ).isEmpty()) {
                                if (!headingName.equals("Lexington Law")) {
                                    driver.findElement(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/a")).click();
                                }
                            }
                            if (!driver.findElements(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li/a[text()='"+footerName+"']")).isEmpty()) {
                                System.out.println("Header class is"+headerClass+"Footer Name is"+footerName);
                                wbelm=driver.findElement(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li[not(contains(@class,'redundant'))]/a[text()='"+footerName+"']"));
                                try {
                                    wait.until(ExpectedConditions.elementToBeClickable(wbelm));
                                    wbelm.click();
                                    System.out.println("The url after clicking is" + driver.getCurrentUrl());
                                }
                                catch (Exception e) {
                                    link_present = false;
                                    System.out.println("Element not clickable");
                                }                        
                            }
                            else {
                                System.out.println("Footer Link" + footerName + "not available");
                                link_present = false;
                            }
                        }
                        driver.get(weburl);
                    }
                }
                catch(Exception e){
                    //gulp the exception
                }
            }
            else {
                try{
                    int intRows=GlobalStatic.getColumnValuesFromExcel(strXLPath, strSheetName, "FooterLinkName_318");
                    int count = 0;
                    for(int intlpctrl=1;intlpctrl<intRows;intlpctrl++)
                    {
                        GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intlpctrl+1);
                        footerName = GlobalStatic.getHashMapValue(strSheetName + "_FooterLinkName_318");
                        expectedFooterURL = GlobalStatic.getHashMapValue(strSheetName + "_ExpectedFooterLinkURL_318").toString();
                        driver.get(weburl);
                        if (footerName.contains("*****")) {
                            headingName = footerName.replace("*****", "");
                            if (headingName.equals("Lexington Law")) {
                                headerClass="bottom_nav_section_main";
                            }
                            else if(headingName.equals("Credit Repair Services")) {
                                headerClass="bottom_nav_section_credit_repair_services";
                            }
                            else if(headingName.equals("Our Firm")) {
                                headerClass="bottom_nav_section_our_firm";
                            }
                            else if(headingName.equals("Credit Education")) {
                                headerClass="bottom_nav_section_credit_education";
                            }
                            count = 0;
                        }
                        else
                        {
                            count = count + 1;
                            if (!driver.findElements(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/a") ).isEmpty()) {
                                if (!headingName.equals("Lexington Law")) {
                                    driver.findElement(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/a")).click();
                                }
                            }
                            if (count == 1) {
                                if (!driver.findElements(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li[(contains(@class,'redundant'))]/a[text()='"+footerName+"']")).isEmpty()) {
                                    wbelm=driver.findElement(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li[(contains(@class,'redundant'))]/a[text()='"+footerName+"']"));
                                    try {
                                        wait.until(ExpectedConditions.elementToBeClickable(wbelm));
                                        wbelm.click();
                                    }
                                    catch (Exception e) {
                                        link_present = false;
                                        System.out.println("Element not clickable");
                                    }                        
                                }
                                else {
                                    System.out.println("Footer Link" + footerName + "not available");
                                    link_present = false;
                                }
                            }
                            else
                            {
                                if (!driver.findElements(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li[not(contains(@class,'redundant'))]/a[text()='"+footerName+"']")).isEmpty()) {
                                    wbelm=driver.findElement(By.xpath("//div[contains(@class,'bottom_nav_section') and contains(@class,'"+headerClass+"')]/ul/li[not(contains(@class,'redundant'))]/a[text()='"+footerName+"']"));
                                    try {
                                        wait.until(ExpectedConditions.elementToBeClickable(wbelm));
                                        wbelm.click();
                                    }
                                    catch (Exception e) {
                                        link_present = false;
                                        System.out.println("Element not clickable");
                                    }                        
                                }
                                else {
                                    System.out.println("Footer Link" + footerName + "not available");
                                    link_present = false;
                                }
                            }
                        }
                    }
                    driver.get(weburl);
                }
                catch(Exception e){
                    //gulp the exception
                }
            }
            return link_present;
        }
        /*
        * This method extracts the Right navigation link contents from the excel sheet
        */
        public ArrayList<String> rightNavigationLinkVerification(WebDriver driver,String strXLPath,String strSheetName,int columnNo) {
            ArrayList<String> linkIdentifiers = new ArrayList<String>();

            try{
                String sectionName,subSectionName,nextSectionName,nextSectionButtonName;
                String xpathNextLinkButton="//div[contains(@class,'page_content_padding') and contains(@class,'bottom_next_button')]";
                GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, columnNo);
                sectionName = GlobalStatic.getHashMapValue(strSheetName + "_SectionNames");
                subSectionName = GlobalStatic.getHashMapValue(strSheetName + "_SubSectionNames");
                nextSectionName = GlobalStatic.getHashMapValue(strSheetName + "_NextSectionNames");
                nextSectionButtonName = GlobalStatic.getHashMapValue(strSheetName + "_NextSectionButtonNames");
                linkIdentifiers.add("//a[contains(text(),\""+sectionName+"\")]");
                linkIdentifiers.add(subSectionName);
                linkIdentifiers.add("//a[contains(text(),\""+nextSectionName+"\")]");
                linkIdentifiers.add(xpathNextLinkButton + "/a[contains(text(),\""+nextSectionButtonName+"\") and contains(text(),'Next Topic')]");
            }
            catch(Exception e){
                System.out.println("Exception occured while reading the values from excel sheet");
            }
            return(linkIdentifiers);
        }
        /*
         * This method verifies the existence of right navigation of the page
         */
        public boolean RightNavigationLinkVerification(WebDriver driver ,ArrayList<String> rightNavigationLinkVerification)
        {
            WebDriverBase base=new WebDriverBase();
            boolean link_false=true;           
            if (driver.findElements(By.xpath(rightNavigationLinkVerification.get(0))).size()>0) {
                for (String var : rightNavigationLinkVerification.get(1).split("~")) {
                    if (driver.findElements(By.xpath("//a[contains(text(),\""+var+"\")]")).isEmpty()) {
                        link_false=false;
                    }
                }
                if (driver.findElements(By.xpath(rightNavigationLinkVerification.get(2))).isEmpty()&&driver.findElements(By.xpath(rightNavigationLinkVerification.get(3))).isEmpty()) {
                    link_false=false;
                }
            }
            else
            {
                link_false=false;
            }
            return link_false;
        }
        /*
         * This method gives the value of cookie to be verified from spreadsheet to page.
         */
         public String pageLevelCookieParsing(String strXLPath,String strSheetName)  {
                String pageCookies="";
               try{ 
                            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, 2);
                            pageCookies = GlobalStatic.getHashMapValue(strSheetName + "_PageLevelCookies");
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
               return pageCookies;
         }
         /*
          * This method gives the value of cookie to be verified from spreadsheet to DB.
          */
          public String dbLevelCookieParsing(String strXLPath,String strSheetName) {
                String dbCookie="";
                try{ 
                            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, 2);
                            dbCookie = GlobalStatic.getHashMapValue(strSheetName + "_dbLevelCookies");
                 }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
               return dbCookie;
         }
        /*
        * This method verifies whether the Left Navigation Link has got expanded
        */
        public boolean isLeftNavigationLinkExpanded(WebDriver driver, String xpath1,String xpath2) {
            boolean link_highlighted=true;
            System.out.println("inside expandable link" + xpath1+xpath2);
            driver.findElement(By.xpath(xpath1+xpath2)).click();
            if (driver.findElements(By.xpath(xpath1+"[contains(@class,'expanded')]"+xpath2)).isEmpty()) {
                link_highlighted=false;
            }
            return link_highlighted;
        }
        /*
        * This method verifies whether the Left Navigation Link has got highlighted
        */
        public boolean isLeftNavigationLinkHighlighted(WebDriver driver, String xpath1,String xpath2,String xpath3) {
            boolean link_highlighted=true;
            driver.findElement(By.xpath(xpath1+xpath2)).click();
            if (xpath3.isEmpty()) {
                if (driver.findElements(By.xpath(xpath1+"[contains(@class,'active')]"+xpath2)).isEmpty()) {
                    link_highlighted=false;
                }
            }
            else
            {
                if (driver.findElements(By.xpath(xpath3)).isEmpty())
                    link_highlighted=false;
            }
            return link_highlighted;
        }
        
}
