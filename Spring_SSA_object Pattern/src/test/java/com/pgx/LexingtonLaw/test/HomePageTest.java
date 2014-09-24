/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.test;

import com.pgx.LexingtonLaw.page.HomePage;
import com.pgx.framework.core.CookiesUtilities;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.RWDDriverScript;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import com.pgx.framework.core.VerifySafe;
import static com.pgx.framework.core.VerifySafe.verifyAssert;
import static com.pgx.framework.core.VerifySafe.verifySafely;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;

/**
 *
 * @author nailli
 */
public class HomePageTest extends TestNGBase{
    
    /*
     * Test to cross check cookie value from the page with user expected value
     */
    @Test()
    public void cookieTest(){
       HomePage home=new HomePage(driver);
       MainReporting.addTestMethodNode("Cookie Test --> Home Page");
       verifyAssert(CookiesUtilities.getCookieValue(driver,"strCookieTID"),home.pageLevelCookie(),"Verifying the cookie from the page");
    }
    
    /*
     * Test to cross check cookie value from the DB with user expected value
     */
    @Test()
    public void cookieDBTest(){
        HomePage home=new HomePage(driver);
        MainReporting.addTestMethodNode("Cookie DB Test --> Home Page");
        verifyAssert(home.cookieDBVerification(),home.dbLevelCookie(),"Cookie DB Test");
    }
    
    /*
     * Test to cross check phone number from the page with DB with the given query
     */
    @Test()
    public void phoneNumberVerification(){
        HomePage home=new HomePage(driver);
        MainReporting.addTestMethodNode("Phone Number Verification Test --> Home Page");
        verifyAssert(home.phoneNumberVerification().replace(".", ""),new HomePage(driver).getTextPhoneNumber().replace("-", ""),"Phone Number Verification Test");
    }
    
    /*
     * Test to cross check footer links with the user input
     */
    @Test()
    public void footerTest() throws IOException{
        MainReporting.addTestMethodNode("Footer Link Verification --> Home Page");
        verifyAssert(new HomePage(driver).homePageFooterVerification(),true,"Footer Link Verification Test");
    }
    
    /*
     * This method checks the object properties of the home page with spread sheet values 
     */
    @Test()
    public void rWDTest() throws IOException{
        MainReporting.addTestMethodNode("RWD Verification --> Home Page");
        HomePage home=new HomePage(driver);
        home.rWDTestcase();
    }
    
    /*
     * This method verifies the presence of FooterLogo and PhoneConfirm button in Home Page
     */
    @Test()
    public void FooterLogoAndPhoneConfirm()throws IOException{
        MainReporting.addTestMethodNode("blogLinks_FooterLogo_phoneConfirm --> HomePage");
        HomePage homePage=new  HomePage(driver);
        verifySafely(homePage.footerLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(homePage.phoneConfirmVerification(),true,"phoneConfirm  Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This method verifies all the validations related to the Homepage
     */
    @Test()
    public void verifyCommon() throws IOException{
        MainReporting.addTestMethodNode("Home Page Test  --> Common Verification Scenarios");
        HomePage homePage=new  HomePage(driver);
        //verifySafely(CookiesUtilities.getCookieValue(driver,"strCookieTID"),homePage.pageLevelCookie(),"Verifying the cookie from the page");
        verifySafely(homePage.cookieDBVerification(),homePage.dbLevelCookie(),"Cookie DB Test");
        verifySafely(homePage.phoneNumberVerification().replace(".", ""),new HomePage(driver).getTextPhoneNumber().replace("-", ""),"Phone Number Verification Test");
        verifySafely(homePage.homePageFooterVerification(),true,"Footer Link Verification Test");
        verifySafely(homePage.footerLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(homePage.phoneConfirmVerification(),true,"phoneConfirm  Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This methods is to check Lexington Logo in the Bad Credit page
     */
    @Test
    public void verifyImages(){
        MainReporting.addTestMethodNode("Sikuli Verification");
        HomePage homePage=new  HomePage(driver);
        verifyAssert(homePage.logoVerification(), true, "Verifying the Screenshot");
    }
}
