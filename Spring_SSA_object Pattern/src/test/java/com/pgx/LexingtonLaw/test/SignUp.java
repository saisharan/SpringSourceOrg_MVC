/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.test;

import com.pgx.LexingtonLaw.page.SignUpStepFourPage;
import static com.pgx.LexingtonLaw.page.SignUpStepFourPage.excelPath;
import com.pgx.LexingtonLaw.page.SignUpStepOnePage;
import com.pgx.LexingtonLaw.page.SignUpStepThreePage;
import com.pgx.LexingtonLaw.page.SignUpStepTwoPage;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LoggerUtil;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.TestNGBase;
import com.pgx.framework.core.VerifySafe;
import static com.pgx.framework.core.VerifySafe.verifyAssert;
import java.io.IOException;
import org.testng.annotations.Test;

/**
 *
 * @author nailli
 */
public class SignUp extends TestNGBase{
    public static String excelPath=EnvParameters.getTestRootDir();
    public static String excelSheetName1=excelPath+"\\CommonLinks.xls";
        @Test()
        public void discountsVerification(){
            MainReporting.addTestMethodNode("Discounts verification");
            SignUpStepOnePage signUpStepTwoPage = new SignUpStepOnePage(driver);
            verifyAssert(signUpStepTwoPage.discountCheck(excelPath+"\\DataSheet.xls", "SignUpDetails", 3),true,"Discounts verification test");
        }
        @Test()
        public void e2EFlow(){
           MainReporting.addTestMethodNode("SignUp Flow");
           SignUpStepFourPage sign=new SignUpStepFourPage(driver,"2,5");
           verifyAssert(SignUpStepFourPage.blnStatus,true,"Verifying the Sign Up flow");
        }
        @Test()
        public void selectFewAgreements(){
            MainReporting.addTestMethodNode("SignUp Flow for select few agreements");
            SignUpStepThreePage signUpStepThreePage = new SignUpStepThreePage(driver,6);
            verifyAssert(signUpStepThreePage.selectFewAgreements(excelPath+"\\DataSheet.xls","SignUpDetails",2),true,"Verifying the selection of agreements in the Sign Up Three Page");
        }
        //@Test()
        public void commonSignUpFlow() {
            MainReporting.addTestMethodNode("SignUp Flow for select few agreements");
            try {
                SignUpStepOnePage signUpStepOnePage = new SignUpStepOnePage(driver);
                SignUpStepTwoPage signUpStepTwoPage = new SignUpStepTwoPage(driver,2);
                SignUpStepThreePage signUpStepThreePage = new SignUpStepThreePage(driver,2);
                verifySafely(signUpStepOnePage.disclaimerPageOneValidation(),true,"Verifying the disclaimers in the Sign Up One Page");
                verifySafely(signUpStepOnePage.footerTestSignUpStepOnePage(),true,"Verifying the footer links in the Sign Up One Page");
                verifySafely(signUpStepTwoPage.disclaimerPageTwoValidation(),true,"Verifying the disclaimers in the Sign Up Two Page");
                verifySafely(signUpStepTwoPage.footerTestSignUpStepTwoPage(),true,"Verifying the footer links in the Sign Up Two Page");
                verifySafely(signUpStepThreePage.disclaimerPageThreeValidation(),true,"Verifying the disclaimers in the Sign Up Three Page");
                verifySafely(signUpStepThreePage.footerTestSignUpStepThreePage(),true,"Verifying the footer links in the Sign Up Three Page");
                verifySafely(signUpStepThreePage.orderOfAgreements(excelPath+"\\CommonLinks.xls","Signup_AgreementsOrder"),true,"Verifying the order of agreements in the Sign Up Three Page");
                verifySafely(signUpStepThreePage.stateConfirmation(excelPath+"\\DataSheet.xls","SignUpDetails",2),true,"Verifying the state information in the Sign Up three Page");                
                VerifySafe.results();
            } catch (IOException ex) {
                LoggerUtil.log("Exception occured"+ex.getMessage());
            }
        } 
    
}
