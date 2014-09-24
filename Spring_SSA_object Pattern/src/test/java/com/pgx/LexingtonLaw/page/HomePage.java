package com.pgx.LexingtonLaw.page;


import org.openqa.selenium.WebDriver;

import com.pgx.framework.ajax.Until;
import com.pgx.framework.core.WebDriverBase;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.CookiesUtilities;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.RWDDriverScript;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import java.io.IOException;
/**
 * This class holds all the objects and common methods of Homepage
 * @author nailli
 */
public class HomePage extends WebDriverBase {
	public static String creditEducation_link = ObjectLocators.getLocator("CreditEducation");
	public static String bread_link = ObjectLocators.getLocator("BreadLink");
        public static String SignUpButton = ObjectLocators.getLocator("SignUpButton");
	public static String crumbs_link = ObjectLocators.getLocator("CrumbLink");
        public static String phone_Number = ObjectLocators.getLocator("PhoneNumber_Home");
        public static String CreditEducationdiv = ObjectLocators.getLocator("CreditEducationdiv_Home");
        public static String CreditEducationLink = ObjectLocators.getLocator("CreditEducationLink_Home");
        public static String PhoneNumber_Home_others=ObjectLocators.getLocator("PhoneNumber_Home_others");
        public static String phoneNumberSlider=ObjectLocators.getLocator("phoneNumberSlider_Home");
	public static String excelPath=EnvParameters.getTestRootDir();
	public static String excelSheetName=excelPath+"\\DataSheet.xls";
        public String excelSheetName1=excelPath+"\\CommonLinks.xls";
        public static String PhoneConfirm_Home=ObjectLocators.getLocator("PhoneConfirm_Home");
        public static String FooterLogo_Home=ObjectLocators.getLocator("FooterLogo_Home");
	public HomePage(WebDriver driver) {
            this.driver = driver;
            goToUrl(EnvParameters.getPropertyValue("webHomeUrl"));
            pageLoadCheck();
	}
	public void clickOnCreditEducationLink() {
            if(Integer.parseInt(TestNGBase.strScreenWidth)>576)
		clickOnElementJavavScript(creditEducation_link);
            else{
                clickOnElementJavavScript(CreditEducationdiv);
                clickOnElementJavavScript(CreditEducationLink);
            }
	}
        public void clickOnSignUpButton() {
            clickOnElementJavavScript(SignUpButton);
        }
        public boolean footerLogoVerification() {
            return isElementPresent(FooterLogo_Home);
        }
        public boolean phoneConfirmVerification() {
            return isElementPresent(PhoneConfirm_Home);
        }
        public String cookieDBVerification(){
            LexingtonCommon lexcomn=new LexingtonCommon();
            return lexcomn.cookieDBVerification();
        }
        public String phoneNumberVerification(){
            LexingtonCommon lexcomn=new LexingtonCommon();
            return lexcomn.phoneNumberDBVerification();
        }
	 public boolean isBreadLinksExists() {
            return isElementPresent(bread_link);
	 }
	 public boolean isCrumbsLinksExists() {
            return isElementPresent(crumbs_link);
	 }
         public String getTextPhoneNumber(){
            return getText(PhoneNumber_Home_others);
         }
         public boolean homePageFooterVerification()throws IOException{
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
         public void rWDTestcase(){
            RWDDriverScript rwd=new RWDDriverScript();
            rwd.process("Home");
         }
         public boolean logoVerification(){
             String strFilepath=EnvParameters.getTestRootDir()+"\\Resources\\SikuliFeed\\"+TestNGBase.strScreenWidth+"\\Logo.png";
             return Sikuli.findScreenshot(strFilepath);
         }
}

