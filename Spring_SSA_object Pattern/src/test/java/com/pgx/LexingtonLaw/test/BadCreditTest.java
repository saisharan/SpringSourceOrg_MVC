/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.test;

import com.pgx.LexingtonLaw.page.BadCreditPage;
import com.pgx.LexingtonLaw.page.CreditEducationPage;
import com.pgx.LexingtonLaw.page.HomePage;
import com.pgx.framework.core.CookiesUtilities;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.Sikuli;
import com.pgx.framework.core.TestNGBase;
import com.pgx.framework.core.VerifySafe;
import static com.pgx.framework.core.VerifySafe.verifyAssert;
import com.pgx.framework.core.WebDriverBase;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.lift.find.Finder;
import org.testng.annotations.Test;

/**
 * This class holds all the test methods of Bad credit page
 * @author nailli
 */
public class BadCreditTest extends TestNGBase{
    
    /*
     * Test to cross check cookie value from the page with user expected value
     */
    //@Test()
    public void cookieTest(){
       MainReporting.addTestMethodNode("Cookie Test  --> Bad Credit Page");
       BadCreditPage badCredit=new  BadCreditPage(driver);
       verifyAssert(CookiesUtilities.getCookieValue(driver,"strCookieTID"),badCredit.pageLevelCookie(),"Verifying the cookie from the page");
    }
    
    /*
     * Test to cross check cookie value from the DB with user expected value
     */
    //@Test()
    public void cookieDBTest(){
        MainReporting.addTestMethodNode("Cookie DB Test --> Bad Credit Page");
        BadCreditPage badCredit=new  BadCreditPage(driver);
        verifyAssert(badCredit.cookieDBVerification(),badCredit.dbLevelCookie(),"Cookie DB Test");
    }
    
    /*
     * Test to cross check phone number from the page with DB with the given query
     */
    //@Test()
    public void phoneNumberVerification(){
        MainReporting.addTestMethodNode("Phone Number Verification Test --> Bad Credit Page");
        BadCreditPage badCredit=new  BadCreditPage(driver);
        verifyAssert(badCredit.phoneNumberVerification().replace(".", ""),new BadCreditPage(driver).getTextPhoneNumberCall().replace("-", ""),"Phone Number Verification Test");
    }
    
    /*
     * Test to cross check footer links with the user input
     */
    //@Test()
    public void footerTest() throws IOException{
        MainReporting.addTestMethodNode("Footer Link Verification --> Bad Credit Page");
        verifyAssert(new BadCreditPage(driver).BadCreditPageFooterVerification(),true,"Footer Link Verification Test");
    }
    
    /*
     * Test to cross check right navigation links with the user input
     */
    @Test()
    public void rightLinkVerification() throws IOException{
        MainReporting.addTestMethodNode("Right Links Verification --> Bad Credit Page");
        BadCreditPage badCredit=new  BadCreditPage(driver);
        verifyAssert(badCredit.rightLinkVerif(),true,"Right Link Verification Test");
    }
    
    /*
     * Test to verify the presence of BadCredit page banner and Content Title
     */
    @Test()
    public void badCreditBannerAndContentTitleVerification()throws IOException{
        BadCreditPage badCredit=new  BadCreditPage(driver);
        MainReporting.addTestMethodNode("badCreditBanner and ContentTitle Verification --> BadCreditPage");
        verifySafely(badCredit.badCreditBannerVerification(),true,"badCreditBanner image  Verification Test");
        verifySafely(badCredit.badCreditpageContentTitle(),true,"pageContentTitle  Verification Test");
        VerifySafe.results();
    }
    
    /*
     * Test to verify the presence of Bad Credit page Footer Logo and Phone Confirm button
     */
    @Test()
    public void footerLogoAndPhoneConfirm() {
        BadCreditPage badCredit=new  BadCreditPage(driver);
        MainReporting.addTestMethodNode("blogLinks_FooterLogo_phoneConfirm --> BadCreditPage");
        verifySafely(badCredit.FooterLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(badCredit.phoneConfirmVerification(),true,"phoneConfirm  Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This method is to check the RWD Components of the Bad Credit page
     */
    //@Test()
    public void rWDTest() throws IOException{
        MainReporting.addTestMethodNode("RWD Verification --> Bad Credit Page");
        BadCreditPage bad=new  BadCreditPage(driver);
        bad.rWDTestcase();
    }
    
    /*
     * This methods is to check all the validations related to the Bad Credit page
     */
    //@Test()
    public void verifyCommon() throws IOException{
        MainReporting.addTestMethodNode("BadCreditTest  --> Common Verification Scenarios");
        BadCreditPage badCredit=new  BadCreditPage(driver);
        verifySafely(CookiesUtilities.getCookieValue(driver,"strCookieTID"),badCredit.pageLevelCookie(),"Verifying the cookie from the page");
        verifySafely(badCredit.cookieDBVerification(),badCredit.dbLevelCookie(),"Cookie DB Test");
        verifySafely(badCredit.phoneNumberVerification().replace(".", ""),new BadCreditPage(driver).getTextPhoneNumberCall().replace("-", ""),"Phone Number Verification Test");
        verifySafely(badCredit.BadCreditPageFooterVerification(),true,"Footer Link Verification Test");
        verifySafely(badCredit.FooterLogoVerification(),true,"Footer Logo Verification Test");
        verifySafely(badCredit.phoneConfirmVerification(),true,"phoneConfirm  Verification Test");
        verifySafely(badCredit.rightLinkVerif(),true,"Right Link Verification Test");
        verifySafely(badCredit.badCreditBannerVerification(),true,"badCreditBanner image  Verification Test");
        verifySafely(badCredit.badCreditpageContentTitle(),true,"pageContentTitle  Verification Test");
        VerifySafe.results();
    }
    
    /*
     * This methods is to check Lexington Logo in the Bad Credit page
     */
    @Test
    public void verifyImages(){
        MainReporting.addTestMethodNode("Sikuli Verification");
        BadCreditPage badCredit=new  BadCreditPage(driver);
        verifyAssert(badCredit.signUpVerification(), true, "Verifying the Screenshot");
    }
}
