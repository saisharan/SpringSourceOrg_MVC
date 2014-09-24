/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.page;

import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.WebDriverBase;
import org.openqa.selenium.WebDriver;
import com.pgx.framework.core.ObjectLocators;

/**
 *
 * @author rapriy
 */
public class SignUpStepFourPage extends WebDriverBase {
    public static String excelPath=EnvParameters.getTestRootDir();
    public static String excelSheetName=excelPath+"\\DataSheet.xls";
    public static String stepFourConfirmationDiv=ObjectLocators.getLocator("stepFourConfirmationDiv");
    public static boolean blnStatus=true;
    public SignUpStepFourPage(WebDriver driver, String strRows) {
       this.driver=driver;
       int strtrow,endrow;
       try{
            for(int i=0;i<strRows.split(",").length;i++){
                     if(strRows.split(",")[i].contains("-")){
                         strtrow=Integer.parseInt(strRows.split(",")[i].split("-")[0]);
                         endrow=Integer.parseInt(strRows.split(",")[i].split("-")[1]);
                         for(int k=strtrow;k<=endrow;k++){
                             SignUpStepThreePage signUp3 = new SignUpStepThreePage(driver,k);
                             signUp3.fillInSignUpStepThreePageDetails(excelSheetName,"SignUpDetails",k);
                             signUp3.selectCheckBox();
                             signUp3.clickOnGoToStepFourButton();
                             isPageLoaded();
                             if(!isSignUpSuccessful()){
                                 blnStatus=false;
                                 MainReporting.reportWarning("Login creation for row number: "+k+" got failed.", true);
                             }
                         }
                     }
                     else{
                         SignUpStepThreePage signUp3 = new SignUpStepThreePage(driver,Integer.parseInt(strRows.split(",")[i]));
                         signUp3.fillInSignUpStepThreePageDetails(excelSheetName,"SignUpDetails",Integer.parseInt(strRows.split(",")[i]));
                         signUp3.selectCheckBox();
                         signUp3.clickOnGoToStepFourButton();
                         isPageLoaded();
                         if(!isSignUpSuccessful()){
                                 blnStatus=false;
                                 MainReporting.reportWarning("Login creation for row number: "+Integer.parseInt(strRows.split(",")[i])+" got failed.", true);
                         }
                     }
             }
       }
       catch(Exception e){
           blnStatus=false;
       }
    }
    public boolean isSignUpSuccessful() {
        return isElementPresent(stepFourConfirmationDiv);
    }
}
