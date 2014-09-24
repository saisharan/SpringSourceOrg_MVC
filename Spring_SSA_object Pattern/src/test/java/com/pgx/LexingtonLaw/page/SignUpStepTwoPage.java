/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.page;


import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.WebDriverBase;
import java.io.IOException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author rapriy
 */
public class SignUpStepTwoPage extends WebDriverBase {
    public static String excelPath=EnvParameters.getTestRootDir();
    public static String excelSheetName=excelPath+"\\DataSheet.xls";
    public static String SelectYourServiceLevelTab = ObjectLocators.getLocator("SelectYourServiceLevelTab");
    public static String ConcordPremierCheckBox = ObjectLocators.getLocator("ConcordPremierCheckBox");
    public static String ConcordStandardCheckBox = ObjectLocators.getLocator("ConcordStandardCheckBox");
    public static String PremierFeeAmountDiv = ObjectLocators.getLocator("PremierFeeAmountDiv");
    public static String StandardFeeAmountDiv = ObjectLocators.getLocator("StandardFeeAmountDiv");
    public static String FirstFeeDiv = ObjectLocators.getLocator("FirstFeeDiv");
    public static String QuickStartFeeDiv=ObjectLocators.getLocator("QuickStartFeeDiv");
    public static String FirstFeeWithDiscountSpan=ObjectLocators.getLocator("FirstFeeWithDiscountSpan");
    public static String FirstFeeDiscountCostDiv=ObjectLocators.getLocator("FirstFeeDiscountCostDiv");
    public static String FirstFeeDiscountTextDiv=ObjectLocators.getLocator("FirstFeeDiscountTextDiv");
    public static String CheckingAccountLabel = ObjectLocators.getLocator("CheckingAccountLabel");
    public static String DebitCreditCardLabel = ObjectLocators.getLocator("DebitCreditCardLabel");
    public static String DebitCreditCardNumberTextBox = ObjectLocators.getLocator("DebitCreditCardNumberTextBox");
    public static String DebitCreditCardExpirationTextBox = ObjectLocators.getLocator("DebitCreditCardExpirationTextBox");
    public static String DebitCreditCardCVVTextBox = ObjectLocators.getLocator("DebitCreditCardCVVTextBox");
    public static String SameAddressCheckBox=ObjectLocators.getLocator("SameAddressCheckBox");
    public static String CheckingAccountRoutingNumberTextBox=ObjectLocators.getLocator("CheckingAccountRoutingNumberTextBox");
    public static String CheckingAccounNumberTextBox=ObjectLocators.getLocator("CheckingAccounNumberTextBox");
    public static String AdditionalCardNumberTextBox=ObjectLocators.getLocator("AdditionalCardNumberTextBox");
    public static String AdditionalExpirationTextBox = ObjectLocators.getLocator("AdditionalExpirationTextBox");
    public static String AdditionalCVVTextBox = ObjectLocators.getLocator("AdditionalCVVTextBox");
    public static String DiffAddressNameOnCardTextBox=ObjectLocators.getLocator("DiffAddressNameOnCardTextBox");
    public static String DiffAddressStreetTextBox = ObjectLocators.getLocator("DiffAddressStreetTextBox");
    public static String DiffAddressZipTextBox = ObjectLocators.getLocator("DiffAddressZipTextBox");
    public static String GoToStepThreeButton= ObjectLocators.getLocator("GoToStepThreeButton");
    public static String excelSheetName1=excelPath+"\\CommonLinks.xls";
    public static String SignUpPageTwoDisclaimerDiv= ObjectLocators.getLocator("SignUpPageTwoDisclaimerDiv");
    public String disclaimerText="Order not complete\n" +
"Information provided on this page will be used for the Engagment Agreement.\n" +
"* Unless you request otherwise, this billing information will be used by Lexington Law to charge you for Lexington's services and by Credit Advantage to sell you a credit report.";

    public SignUpStepTwoPage(WebDriver driver, int rowNumber) {
    	this.driver = driver;
    	SignUpStepOnePage signUpStepOnePage = new SignUpStepOnePage(driver);
    	signUpStepOnePage.fillInSignUpStepOnePageDetails(excelSheetName,"SignUpDetails",rowNumber);
        signUpStepOnePage.clickOnGoToStepTwoButton();
        isPageLoaded();
    }
    public boolean footerTestSignUpStepTwoPage() throws IOException{
        LexingtonCommon lex=new LexingtonCommon();
        return lex.footerVerification(this.driver, excelSheetName1,"Footer_Verification");
    }
    public boolean isSelectYourServiceLevelTabHighlighted() {
        return isElementPresent(SelectYourServiceLevelTab);
    }
    public void fillInSignUpStepTwoPageDetails(String strXLPath,String strSheetName,int rowNumber) {
        try {
            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, rowNumber);
            String concordType = GlobalStatic.getHashMapValue(strSheetName + "_PremierOrStandard");
            String paymentMode = GlobalStatic.getHashMapValue(strSheetName + "_PaymentMode");
            String debitCreditCardNumber = GlobalStatic.getHashMapValue(strSheetName + "_DebitCreditCardNumber");
            String debitCreditCardExpiration = GlobalStatic.getHashMapValue(strSheetName + "_DebitCreditCardExpiration");
            String debitCreditCardCVV = GlobalStatic.getHashMapValue(strSheetName + "_DebitCreditCardCVV");
            String checkingAccountRoutingNumber = GlobalStatic.getHashMapValue(strSheetName + "_CheckingAccountRoutingNumber");
            String checkingAccounNumber = GlobalStatic.getHashMapValue(strSheetName + "_CheckingAccounNumber");
            String differentAddressRequired = GlobalStatic.getHashMapValue(strSheetName + "_DifferentAddressRequired");
            String differentAddressNameOnCard = GlobalStatic.getHashMapValue(strSheetName + "_DifferentAddressNameOnCard");
            String differentAddressStreetDetails = GlobalStatic.getHashMapValue(strSheetName + "_DifferentAddressStreetDetails");
            String differentAddressZipNumber = GlobalStatic.getHashMapValue(strSheetName + "_DifferentAddressZipNumber");
            String additionalCardRequired = GlobalStatic.getHashMapValue(strSheetName + "_AdditionalCardRequired");
            String additionalCardNumberTextBox = GlobalStatic.getHashMapValue(strSheetName + "_AdditionalCardNumberTextBox");
            String additionalExpirationTextBox = GlobalStatic.getHashMapValue(strSheetName + "_AdditionalExpirationTextBox");
            String additionalCVVTextBox = GlobalStatic.getHashMapValue(strSheetName + "_AdditionalCVVTextBox");
            if(!concordType.equals("")){
                 if(concordType.toLowerCase().equals("premier")){
                     clickOnElement(ConcordPremierCheckBox);
                 }
                 else if(concordType.toLowerCase().equals("standard"))
                     clickOnElement(ConcordStandardCheckBox);
                 else
                     System.out.println("Please enter the valid input for 'PremierOrStandard' column");
            }
            if(!paymentMode.equals("")){
                if(paymentMode.toLowerCase().equals("debit")){
                    clickOnElement(DebitCreditCardLabel);
                    type(DebitCreditCardNumberTextBox,debitCreditCardNumber);
                    type(DebitCreditCardExpirationTextBox,debitCreditCardExpiration);
                    type(DebitCreditCardCVVTextBox,debitCreditCardCVV);
                }
                else{
                    clickOnElement(CheckingAccountLabel);
                    type(CheckingAccountRoutingNumberTextBox,checkingAccountRoutingNumber);
                    type(CheckingAccounNumberTextBox,checkingAccounNumber);
                }
                
            }
            else
                System.out.println("please enter the valid input in 'PaymentMode' column");
            if (differentAddressRequired.toLowerCase().contentEquals("true") || additionalCardRequired.toLowerCase().contentEquals("yes")) {
                type(DiffAddressNameOnCardTextBox,differentAddressNameOnCard);
                type(DiffAddressStreetTextBox,differentAddressStreetDetails);
                type(DiffAddressZipTextBox,differentAddressZipNumber);
            }
            if (additionalCardRequired.toLowerCase().contentEquals("true") || additionalCardRequired.toLowerCase().contentEquals("yes")) {
                if (!additionalCardNumberTextBox.isEmpty()) {
                    type(AdditionalCardNumberTextBox,additionalCardNumberTextBox);
                    type(AdditionalExpirationTextBox,additionalExpirationTextBox);
                    type(AdditionalCVVTextBox,additionalCVVTextBox);
                }
                else {
                    System.out.println("Additional member details not available");
                    System.out.println("Sign Up for the User" + additionalCardNumberTextBox + "failed!");
                }
            }
        }
        catch(Exception e) {
            //gulp it
        }
    }
    public void clickOnGoToStepThreeButton() {
        clickOnElementJavavScript(GoToStepThreeButton);
    }
    public boolean disclaimerPageTwoValidation() {
        if (getText(SignUpPageTwoDisclaimerDiv).equals(disclaimerText)) {
            return true;
        }
        else {
            return false;
        }
    }

}
