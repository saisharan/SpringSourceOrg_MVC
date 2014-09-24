package com.pgx.LexingtonLaw.page;
import static com.pgx.LexingtonLaw.page.HomePage.excelPath;
import static com.pgx.LexingtonLaw.page.HomePage.phone_Number;
import org.openqa.selenium.WebDriver;

import com.pgx.framework.ajax.Until;
import com.pgx.framework.core.ElementLocator;
import com.pgx.framework.core.WebDriverBase;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.RWDDriverScript;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * This class holds all the objects and common methods used in this page
 * @author nailli
 */
public class CreditEducationPage extends WebDriverBase {
    public static String creditEducationHighlighted_link = ObjectLocators.getLocator("CreditEducationHighlight");
    public static String badCredit_link = ObjectLocators.getLocator("BadCredit");
    public static String expandable_link = ObjectLocators.getLocator("BadCreditExpandable");
    public static String bread_link = ObjectLocators.getLocator("BreadLink");
    public static String crumbs_link = ObjectLocators.getLocator("CrumbLink");
    public static String phoneNumberMain = ObjectLocators.getLocator("phoneNumberMain");
    public static String phoneNumberSlider = ObjectLocators.getLocator("phoneNumberSlider");
    public static String phoneNumberMain_others = ObjectLocators.getLocator("phoneNumberMain_others");
    public String excelSheetName1=excelPath+"\\CommonLinks.xls";
    public static String excelSheetName=excelPath+"\\DataSheet.xls";
    public static String creditPageIcons = ObjectLocators.getLocator("CreditPageIcon");
    public static String marketing_Banner_up= ObjectLocators.getLocator("MarketingBanner_up");
    public static String marketing_Banner_down= ObjectLocators.getLocator("MarketingBanner_down");
    public static String readThisPost_CreditBlog_CreditEducation = ObjectLocators.getLocator("readThisPost_CreditBlog_CreditEducation");
    public static String readTheBlog_CreditBlog_CreditEducation= ObjectLocators.getLocator("readTheBlog_CreditBlog_CreditEducation");
    public static String readThisPost_NewsRoom_CreditEducation = ObjectLocators.getLocator("readThisPost_NewsRoom_CreditEducation");
    public static String readTheBlog_NewsRoom_CreditEducation = ObjectLocators.getLocator("readTheBlog_NewsRoom_CreditEducation");
    public static String PhoneConfirm_CreditEducation=ObjectLocators.getLocator("PhoneConfirm_CreditEducation");
    public static String FooterLogo_CreditEducation=ObjectLocators.getLocator("FooterLogo_CreditEducation");
    LexingtonCommon lexcomn=new LexingtonCommon();
    public CreditEducationPage(WebDriver driver) {
    	this.driver = driver;
    	HomePage homePage = new HomePage(driver);
    	homePage.clickOnCreditEducationLink();
        pageLoadCheck();
    }
    public boolean isCreditEducationLinkHighlighted() {
        return isElementChecked(creditEducationHighlighted_link);
    }
    public void clickOnBadCredLink() {
        clickOnElement(expandable_link);
        clickOnElement(badCredit_link);
    }
    public void navigateToBadCreditPage() {
        goToUrl("http://rebuild.lexingtonlaw.test/credit-education/bad-credit/");
    }
    public boolean isBreadLinksExists() {
        return isElementPresent(bread_link);
    }
    public String cookieDBVerification(){
        return lexcomn.cookieDBVerification();
    }
    public String phoneNumberVerification(){
        return lexcomn.phoneNumberDBVerification();
    }
    public boolean isCrumbsLinksExists() {
        return isElementPresent(crumbs_link);
    }
    public String getTextPhoneNumberMain(){
        return getText(phoneNumberMain);
    }
    public String getTextPhoneNumberCall(){
        Until.elementIsInteractable(phoneNumberSlider);
        clickOnElement(phoneNumberSlider);
        Until.elementIsInteractable(phoneNumberMain_others);
        return getText(phoneNumberMain_others);
    }
    public boolean CreditEducationPageFooterVerification() throws IOException {
        return lexcomn.footerVerification(this.driver, excelSheetName1,"Footer_Verification");
    }
    public String pageLevelCookie(){
        return lexcomn.pageLevelCookieParsing(excelSheetName, "CookieData");
    }  
    public String dbLevelCookie(){
        return lexcomn.dbLevelCookieParsing(excelSheetName, "CookieData");
    } 
    public void rWDTestcase(){
        RWDDriverScript rwd=new RWDDriverScript();
        rwd.process("CreditEducation");
    }
    public boolean phoneConfirmVerification() {
        return isElementPresent(PhoneConfirm_CreditEducation);
    }
    public boolean footerLogoVerification() {
        return isElementPresent(FooterLogo_CreditEducation);
    }
    public boolean leftNavigationLinkVerif(WebDriver driver,String strXLPath,String strSheetName) {
        boolean link_present = true;
        try {
            String leftLinkName,leftLinkExpandable="";
            String weburl = driver.getCurrentUrl();
            String xpath_leftlink = "//div[contains(@class,\"section_left_side\")]/nav/ul[@class=\"section_left_nav\"]";
            int intRows=GlobalStatic.getColumnValuesFromExcel(strXLPath, strSheetName, "LeftLinkName");
            for(int intlpctrl=1;intlpctrl<intRows;intlpctrl++){
                GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intlpctrl+1);
                leftLinkName = GlobalStatic.getHashMapValue(strSheetName + "_LeftLinkName");
                if (leftLinkName.contains("*****")) {
                    leftLinkExpandable = leftLinkName.replace("*****", "");
                    leftLinkName = "";
                }
                System.out.println(leftLinkName);
                driver.get(weburl);
                if (leftLinkExpandable.isEmpty()) {
                    if (!(driver.findElements(By.xpath(xpath_leftlink+"/li/a[text()='"+leftLinkName+"']")).size() > 0 && lexcomn.isLeftNavigationLinkHighlighted(driver,xpath_leftlink+"/li","/a[text()='"+leftLinkName+"']","")))
                            link_present = false;
                }
                else{
                    if (!leftLinkName.equals("")) {
                        if (!(driver.findElements(By.xpath(xpath_leftlink+"/li/div[contains(@class,'section_left_nav_section_heading') and text()='"+leftLinkExpandable+"']")).size() > 0 && lexcomn.isLeftNavigationLinkExpanded(driver,xpath_leftlink+"/li","/div[contains(@class,'section_left_nav_section_heading') and text()='"+leftLinkExpandable+"']"))) 
                                link_present = false;
                        if (!(driver.findElements(By.xpath(xpath_leftlink+"/li/ul[@class='"+"section_left_nav_sub_section"+"']/li/a[text()='"+leftLinkName+"']")).size() > 0 && lexcomn.isLeftNavigationLinkHighlighted(driver,xpath_leftlink+"/li/ul[@class=\"section_left_nav_sub_section\"]/li","/a[text()='"+leftLinkName+"']",xpath_leftlink+"/li/ul[@class=\"section_left_nav_sub_section\"]/li/a[contains(@class,\"active\") and text()='"+leftLinkName+"']"))) 
                                link_present = false;
                     }
                }
            }
        }
        catch(Exception e){
            System.out.println("Exception occured while reading the values from excel sheet");
            link_present = false;
        }
        return link_present;
    }
    public boolean leftNavigationLinkVerification() {
        return leftNavigationLinkVerif(driver,excelSheetName1,"Left_Navigation_Links");
    }
    public boolean creditPageIconsVerification() {
        return isElementPresent(creditPageIcons);
    }
    public boolean MarketingBannersVerification() {
        if(isElementPresent(marketing_Banner_up)&&isElementPresent(marketing_Banner_down)){
            return true;
        }
        else
            return false;
    }
    public boolean creditPageblogLinksVerification() {
        if (isElementPresent(readThisPost_CreditBlog_CreditEducation)&&isElementPresent(readTheBlog_CreditBlog_CreditEducation)&&isElementPresent(readThisPost_NewsRoom_CreditEducation)&&isElementPresent(readTheBlog_NewsRoom_CreditEducation)) {
            return true;
        }
        else
            return false;
    }
    public boolean signUpVerification(){
             String strFilepath=EnvParameters.getTestRootDir()+"\\Resources\\SikuliFeed\\"+TestNGBase.strScreenWidth+"\\signup.png";
             return Sikuli.findScreenshot(strFilepath);
    }
}