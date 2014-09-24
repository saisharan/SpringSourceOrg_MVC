/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.test;

import com.pgx.LexingtonLaw.page.CreditEducationPage;
import com.pgx.LexingtonLaw.page.HomePage;
import com.pgx.framework.core.CookiesUtilities;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import com.pgx.framework.core.VerifySafe;
import static com.pgx.framework.core.VerifySafe.verifyAssert;
import static com.pgx.framework.core.VerifySafe.verifySafely;
import java.io.IOException;
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
import org.testng.annotations.Test;

/**
 * This class holds all the test methods of Credit Education page
 * @author nailli
 */
public class CreditEducationTest extends TestNGBase{ 
    
    /*
     * Test to cross check cookie value from the page with user expected value
     */
    //@Test()
    public void cookieTest(){
       MainReporting.addTestMethodNode("Cookie Test --> Credit Education Page");
       CreditEducationPage credit=new  CreditEducationPage(driver);
       verifyAssert(CookiesUtilities.getCookieValue(driver,"strCookieTID"),credit.pageLevelCookie(),"Verifying the cookie from the page");
    }
   
    /*
     * Test to cross check cookie value from the DB with user expected value
     */
    //@Test()
    public void cookieDBTest(){
        MainReporting.addTestMethodNode("Cookie DB Test --> Credit Education Page");
        CreditEducationPage credit=new  CreditEducationPage(driver);
        verifyAssert(credit.cookieDBVerification(),credit.dbLevelCookie(),"Cookie DB Test");
    }
    
    /*
     * Test to cross check Left Navigation Links from the page with user input
     */
    @Test
    public void leftNavigationLinkVerification(){
        MainReporting.addTestMethodNode("Left Navigation Link Verification Test");
        verifyAssert(new CreditEducationPage(driver).leftNavigationLinkVerification(),true,"Left Navigation test");
    }
    
    /*
     * Test to cross check phone number from the page with DB with the given query
     */
    //@Test()
    public void phoneNumberVerification(){
        MainReporting.addTestMethodNode("Phone Number Verification Test --> Credit Education Page");
        CreditEducationPage credit=new  CreditEducationPage(driver);
        verifyAssert(credit.phoneNumberVerification().replace(".", ""),credit.getTextPhoneNumberCall().replace("-", ""),"Phone Number Verification Test");
    }
    
    /*
     * Test to cross check footer links with the user input
     */
    //@Test()
    public void footerTest()throws IOException{
        MainReporting.addTestMethodNode("Footer Link Verification --> Credit Education Page");
        verifyAssert(new CreditEducationPage(driver).CreditEducationPageFooterVerification(),true,"Footer Link Verification Test");
    }
    
    /*
     * This method tests the objects properties of the page with the Credit Education  components 
     */
    //@Test()
    public void rWDTest() throws IOException{
        MainReporting.addTestMethodNode("RWD Verification --> Credit Education Page");
        CreditEducationPage credit=new  CreditEducationPage(driver);
        credit.rWDTestcase();
    }
    
    /*
     * This method verifies Marketing Banners and Icons related to the Credit Education page
     */
    @Test()
    public void marketingBannersIconsVerification()throws IOException{
        CreditEducationPage credit=new CreditEducationPage(driver);
        MainReporting.addTestMethodNode("MarketingBanners/iconsVerification --> Credit Education Page");
        verifySafely(credit.creditPageIconsVerification(),true,"IconsVerification Verification Test");
        verifySafely(credit.MarketingBannersVerification(),true,"MarketingBanners Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This method verifies Blog links, Footer Logo and Phone Confirm related to the Credit Education page
     */
    @Test()
    public void blogLinksAndFooterLogoAndPhoneConfirm()throws IOException{
        CreditEducationPage credit=new CreditEducationPage(driver);
        MainReporting.addTestMethodNode("blogLinks_FooterLogo_phoneConfirm --> Credit Education Page");
        verifySafely(credit.footerLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(credit.phoneConfirmVerification(),true,"phoneConfirm Verification Test");
        verifySafely(credit.creditPageblogLinksVerification(),true,"Blog Links Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This method verifies all the common validation related to the Credit Education page
     */
    //@Test()
    public void verifyCommon() throws IOException{
        MainReporting.addTestMethodNode("Credit Education Test  --> Common Verification Scenarios");
        CreditEducationPage creditEducation=new  CreditEducationPage(driver);
        verifySafely(CookiesUtilities.getCookieValue(driver,"strCookieTID"),creditEducation.pageLevelCookie(),"Verifying the cookie from the page");
        verifySafely(creditEducation.cookieDBVerification(),creditEducation.dbLevelCookie(),"Cookie DB Test");
        verifySafely(creditEducation.phoneNumberVerification().replace(".", ""),new CreditEducationPage(driver).getTextPhoneNumberCall().replace("-", ""),"Phone Number Verification Test");
        verifySafely(creditEducation.CreditEducationPageFooterVerification(),true,"Footer Link Verification Test");
        verifySafely(creditEducation.creditPageIconsVerification(),true,"IconsVerification Verification Test");
        verifySafely(creditEducation.MarketingBannersVerification(),true,"MarketingBanners Verification Test");
        verifySafely(creditEducation.footerLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(creditEducation.phoneConfirmVerification(),true,"phoneConfirm  Verification Test");
        verifySafely(creditEducation.creditPageblogLinksVerification(),true,"Blog Links Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This methods is to check Lexington Logo in the Bad Credit page
     */
    @Test
    public void verifyImages(){
        MainReporting.addTestMethodNode("Sikuli Verification");
        CreditEducationPage creditEducation=new  CreditEducationPage(driver);
        verifyAssert(creditEducation.signUpVerification(), true, "Verifying the Screenshot");
    }
}
