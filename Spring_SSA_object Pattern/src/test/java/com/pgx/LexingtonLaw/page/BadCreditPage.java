package com.pgx.LexingtonLaw.page;


import static com.pgx.LexingtonLaw.page.HomePage.excelPath;
import org.openqa.selenium.WebDriver;

import com.pgx.framework.ajax.Until;
import com.pgx.framework.core.WebDriverBase;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.RWDDriverScript;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class holds the objects and all the common methods of the BadCredit page
 * @author nailli
 */
public class BadCreditPage extends WebDriverBase {
    public static String bread_link = ObjectLocators.getLocator("BreadLink");
    public static String crumbs_link = ObjectLocators.getLocator("CrumbLink");
    public static String creditEducation_link = ObjectLocators.getLocator("CreditEducation");
    public static String PhoneNumber_BadCredit_others=ObjectLocators.getLocator("PhoneNumber_BadCredit_others");
    public static String phoneNumberSlider_Badcredit=ObjectLocators.getLocator("phoneNumberSlider_Badcredit");
    public static String PhoneConfirm_BadCredit=ObjectLocators.getLocator("PhoneConfirm_BadCredit");
    public static String FooterLogo_BadCredit=ObjectLocators.getLocator("FooterLogo_BadCredit");
    public String excelSheetName1=excelPath+"\\CommonLinks.xls";
    public static String excelSheetName=excelPath+"\\DataSheet.xls";
    public static String mainBanner_image =ObjectLocators.getLocator("badCreditBanner");;
    public static String contentTitle=ObjectLocators.getLocator("ContentTitle");
    public BadCreditPage(WebDriver driver) {
        this.driver = driver;
        CreditEducationPage creditEducationPage = new CreditEducationPage(driver);
        if(Integer.parseInt(TestNGBase.strScreenWidth)>1270)
            creditEducationPage.clickOnBadCredLink();
            creditEducationPage.navigateToBadCreditPage();
        pageLoadCheck();
    }
        
    public boolean phoneConfirmVerification() {
        return isElementPresent(PhoneConfirm_BadCredit);
    }
        
    public boolean rightLinkVerif() {
        LexingtonCommon lexcomn=new LexingtonCommon();
        boolean rightLinkVerif = true;
        int intRows=0;
        try {
            intRows = GlobalStatic.getColumnValuesFromExcel(excelSheetName1, "Right_Navigation_Links", "SectionNames");
        } catch (IOException ex) {
            Logger.getLogger(BadCreditPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int intlpctrl=1;intlpctrl<intRows;intlpctrl++)
        {
            ArrayList<String> str1 = lexcomn.rightNavigationLinkVerification(driver,excelSheetName1,"Right_Navigation_Links",intlpctrl+1);
            if (!lexcomn.RightNavigationLinkVerification(driver,str1)) {
                rightLinkVerif=false;
            }
        }
        return rightLinkVerif;
    }
    
    public boolean FooterLogoVerification() {
        return isElementPresent(FooterLogo_BadCredit);
    }
    public boolean isBreadLinksExists() {
        return isElementPresent(bread_link);
    }
    public boolean isCrumbsLinksExists() {
        return isElementPresent(crumbs_link);
    }
    public String cookieDBVerification(){
        LexingtonCommon lexcomn=new LexingtonCommon();
        return lexcomn.cookieDBVerification();
    }
    public String phoneNumberVerification(){
        LexingtonCommon lexcomn=new LexingtonCommon();
        return lexcomn.phoneNumberDBVerification();
    }
    public boolean BadCreditPageFooterVerification() throws IOException {
        LexingtonCommon lexcomn=new LexingtonCommon();
        return lexcomn.footerVerification(this.driver, excelSheetName1,"Footer_Verification");
    }
    public String pageLevelCookie(){
       LexingtonCommon lexcomn=new LexingtonCommon();
       return lexcomn.pageLevelCookieParsing(excelSheetName, "CookieData");
    }  
    public String dbLevelCookie(){
       LexingtonCommon lexcomn=new LexingtonCommon();
       return lexcomn.dbLevelCookieParsing(excelSheetName, "CookieData");
    } 
    public String getTextPhoneNumber(){
       clickOnElement(phoneNumberSlider_Badcredit);
       return getText(PhoneNumber_BadCredit_others);
    }
    public String getTextPhoneNumberCall(){
       Until.elementIsInteractable(phoneNumberSlider_Badcredit);
       clickOnElement(phoneNumberSlider_Badcredit);
       Until.elementIsInteractable(PhoneNumber_BadCredit_others);
       return getText(PhoneNumber_BadCredit_others);
    }
    public void rWDTestcase(){
        RWDDriverScript rwd=new RWDDriverScript();
        rwd.process("BadCredit");
    }
    public boolean badCreditBannerVerification() {
        return isElementPresent(mainBanner_image);
    }
    public boolean badCreditpageContentTitle() {
        return isElementPresent(contentTitle);
    }
    public boolean signUpVerification(){
             String strFilepath=EnvParameters.getTestRootDir()+"\\Resources\\SikuliFeed\\"+TestNGBase.strScreenWidth+"\\signup.png";
             return Sikuli.findScreenshot(strFilepath);
    }
}