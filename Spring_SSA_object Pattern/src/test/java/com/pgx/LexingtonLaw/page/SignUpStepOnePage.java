/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.page;



import static com.pgx.LexingtonLaw.page.SignUpStepTwoPage.ConcordStandardCheckBox;
import static com.pgx.LexingtonLaw.page.SignUpStepTwoPage.ConcordPremierCheckBox;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.LoggerUtil;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.WebDriverBase;
import java.io.IOException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author rapriy
 */
public class SignUpStepOnePage extends WebDriverBase {
    public static String EnterYourPersonalInformationTab = ObjectLocators.getLocator("EnterYourPersonalInformationTab");
    public static String ClientOneFirstNameTextBox = ObjectLocators.getLocator("ClientOneFirstNameTextBox");
    public static String ClientOneLastNameTextBox = ObjectLocators.getLocator("ClientOneLastNameTextBox");
    public static String ClientOneEmailTextBox = ObjectLocators.getLocator("ClientOneEmailTextBox");
    public static String ClientOnePhoneNumberTextBox = ObjectLocators.getLocator("ClientOnePhoneNumberTextBox");
    public static String ClientOneAddressTextBox = ObjectLocators.getLocator("ClientOneAddressTextBox");
    public static String ClientOneZipCodeTextBox=ObjectLocators.getLocator("ClientOneZipCodeTextBox");
    public static String AddAdditionalMemberRadioButton=ObjectLocators.getLocator("AddAdditionalMemberRadioButton");
    public static String DontAddAdditionalMemberRadioButton=ObjectLocators.getLocator("DontAddAdditionalMemberRadioButton");
    public static String ClientTwoFirstNameTextBox=ObjectLocators.getLocator("ClientTwoFirstNameTextBox");
    public static String ClientTwoLastNameTextBox = ObjectLocators.getLocator("ClientTwoLastNameTextBox");
    public static String ClientTwoEmailTextBox = ObjectLocators.getLocator("ClientTwoEmailTextBox");
    public static String ClientTwoPhoneNumberTextBox = ObjectLocators.getLocator("ClientTwoPhoneNumberTextBox");
    public static String ClientTwoAddressTextBox = ObjectLocators.getLocator("ClientTwoAddressTextBox");
    public static String ClientTwoZipCodeTextBox = ObjectLocators.getLocator("ClientTwoZipCodeTextBox");
    public static String GoToStepTwoButton = ObjectLocators.getLocator("GoToStepTwoButton");
    public static String strFirstFeeCost = ObjectLocators.getLocator("FirstFeeCost");
    public static String strConcordStandardFees = ObjectLocators.getLocator("ConcordSatndardFees");
    public static String strConcordPremierFees= ObjectLocators.getLocator("ConcordPremierFees");
    public static String strSpouseDiscount= ObjectLocators.getLocator("SpouseDiscount");
    public static String excelSheetName=EnvParameters.getTestRootDir()+"\\CommonLinks.xls";
    public static String SignUpPageOneDisclaimerDiv = ObjectLocators.getLocator("SignUpPageOneDisclaimerDiv");
    String disclaimerText="By clicking \"Go To Step 2\" I agree by electronic signature to: (1) be contacted about credit repair or credit repair marketing by a live agent, artificial or prerecorded voice, and SMS text at my residential or cellular number, dialed manually or by autodialer, and by email (consent to be contacted is not a condition to purchase services); and (2) the Privacy Policy and Terms of Use.";
    
    public SignUpStepOnePage(WebDriver driver) {
    	this.driver = driver;
    	HomePage homePage = new HomePage(driver);
    	homePage.clickOnSignUpButton();
        pageLoadCheck();
    }
    
    public boolean isEnterYourPersonalInformationTabHighlighted() {
        return isElementPresent(EnterYourPersonalInformationTab);
    }
    
    public void fillInSignUpStepOnePageDetails(String strXLPath,String strSheetName,int rowNumber) {
        try {
            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, rowNumber);
            String firstName = GlobalStatic.getHashMapValue(strSheetName + "_FirstName");
            String lastName = GlobalStatic.getHashMapValue(strSheetName + "_LastName");
            String email = GlobalStatic.getHashMapValue(strSheetName + "_EmailId");
            String phoneNumber = GlobalStatic.getHashMapValue(strSheetName + "_PhoneNumber");
            String address = GlobalStatic.getHashMapValue(strSheetName + "_Address");
            String zipCode = GlobalStatic.getHashMapValue(strSheetName + "_ZipCode");
            type(ClientOneFirstNameTextBox,firstName);
            type(ClientOneLastNameTextBox,lastName);
            type(ClientOneEmailTextBox,email);
            type(ClientOnePhoneNumberTextBox,phoneNumber);
            type(ClientOneAddressTextBox,address);
            type(ClientOneZipCodeTextBox,zipCode);
            String additionalMember = GlobalStatic.getHashMapValue(strSheetName + "_AdditionalMember");
            if (additionalMember.toLowerCase().contentEquals("true") || additionalMember.toLowerCase().contentEquals("yes")) {
                String twoFirstName = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoFirstName");
                if (!twoFirstName.isEmpty()) {
                    String twoLastName = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoLastName");
                    String twoEmail = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoEmailId");
                    String twoPhoneNumber = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoPhoneNumber");
                    String twoAddress = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoAddress");
                    String twoZipCode = GlobalStatic.getHashMapValue(strSheetName + "_ClientTwoZipCode");
                    clickOnElementJavavScript(AddAdditionalMemberRadioButton);
                    type(ClientTwoFirstNameTextBox,twoFirstName);
                    type(ClientTwoLastNameTextBox,twoLastName);
                    type(ClientTwoEmailTextBox,twoEmail);
                    type(ClientTwoPhoneNumberTextBox,twoPhoneNumber);
                    type(ClientTwoAddressTextBox,twoAddress);
                    type(ClientTwoZipCodeTextBox,twoZipCode);
                }
                else
                {
                    System.out.println("Additional member details not available");
                    System.out.println("Sign Up for the User" + firstName + "failed!");
                }
            }
            else {
                clickOnElementJavavScript(DontAddAdditionalMemberRadioButton);
            }
        }
        catch(Exception e) {
            //gulp it
        }
            
    }
     public boolean footerTestSignUpStepOnePage() throws IOException{
        LexingtonCommon lex=new LexingtonCommon();
        return lex.footerVerification(this.driver, excelSheetName,"Footer_Verification");
    }
    public void clickOnGoToStepTwoButton() {
        clickOnElementJavavScript(GoToStepTwoButton);
    }
    public boolean disclaimerPageOneValidation() {
        if (getText(SignUpPageOneDisclaimerDiv).equals(disclaimerText)) {
            return true;
        }
        else {
            return false;
        }
    }
    public void navigateWithTID(String strXLPath,String strSheetName,int intRowNumber){
        try{
            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intRowNumber);
            String strTID = GlobalStatic.getHashMapValue(strSheetName + "_TID");
            goToUrl(EnvParameters.getPropertyValue("webHomeUrl")+"/signup?tid="+strTID+".0.100");
            pageLoadCheck();
        }
        catch(Exception e){
           LoggerUtil.log("Exception occured while filling data in first page"+e.getMessage());
        }
    }
    public boolean discountCheck(String strXLPath,String strSheetName,int intRowNumber){
            boolean blnResult=true;
        try{
            String strActualFirstFeeCost,strActualConcordSatndardFees,strActualConcordPremierFees,strActualSpouseDiscount;
            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intRowNumber);
            String strTID = GlobalStatic.getHashMapValue(strSheetName + "_TID");
            String strFirstFeeDiscount = GlobalStatic.getHashMapValue(strSheetName + "_FirstFeeDiscount");
            String strConcordPremier = GlobalStatic.getHashMapValue(strSheetName + "_ConcordPremierServiceLevelMonthlyPrice");
            String strConcordStandard = GlobalStatic.getHashMapValue(strSheetName + "_ConcordStandardServiceLevelMonthlyPrice");
            String strSpouseDiscount = GlobalStatic.getHashMapValue(strSheetName + "_SpouseDiscount");
            if(!strTID.equals(""))
                navigateWithTID(strXLPath, strSheetName, intRowNumber);
            fillInSignUpStepOnePageDetails(strXLPath, strSheetName, intRowNumber);
            clickOnGoToStepTwoButton();
            if(!strFirstFeeDiscount.equals("")){
                 strActualFirstFeeCost= getText(strFirstFeeCost);
                 if(!strActualFirstFeeCost.equals("")){
                    if(!strFirstFeeDiscount.equals(strActualFirstFeeCost.substring(1,strActualFirstFeeCost.length()))){
                        MainReporting.reportWarning("First fee discount is not matching with actual for TID:"+strTID+".Actual is: "+strActualFirstFeeCost.substring(1,strActualFirstFeeCost.length())+",but Expected is: "+strFirstFeeDiscount, true);
                        blnResult=false;
                    }
                 }
                 else{
                     LoggerUtil.log("Null value returned for First fee discount from application");
                     blnResult=false; 
                 }
            }
            if(!strConcordPremier.equals("")){
                 strActualConcordSatndardFees= getText(strConcordPremierFees);
                 if(!strActualConcordSatndardFees.equals("")){
                    if(!strConcordPremier.equals(strActualConcordSatndardFees.substring(1,strActualConcordSatndardFees.length()))){
                        MainReporting.reportWarning("Concord Premier fees is not matching with actual for TID:"+strTID+".Actual is: "+strActualConcordSatndardFees.substring(1,strActualConcordSatndardFees.length())+",but Expected is: "+strConcordPremier, true);
                        blnResult=false;
                    }
                 }
                 else{
                     LoggerUtil.log("Null value returned for Concord Premier fees from application");
                     blnResult=false; 
                 }
            }
            if(!strConcordStandard.equals("")){
                 clickOnElement(ConcordStandardCheckBox);
                 strActualConcordPremierFees= getText(strConcordStandardFees);
                 if(!strActualConcordPremierFees.equals("")){
                    if(!strConcordStandard.equals(strActualConcordPremierFees.substring(1,strActualConcordPremierFees.length()))){
                        MainReporting.reportWarning("Concord Standard fees discount is not matching with actual for TID:"+strTID+".Actual is: "+strActualConcordPremierFees.substring(1,strActualConcordPremierFees.length())+",but Expected is: "+strConcordStandard, true);
                        blnResult=false;
                    }
                 }
                 else{
                     LoggerUtil.log("Null value returned for Concord Standard Fees from application");
                     blnResult=false; 
                 }
            }
            if(!strSpouseDiscount.equals("")){
                 strActualSpouseDiscount= getText(strSpouseDiscount);
                 if(!strActualSpouseDiscount.equals("")){
                    if(!strConcordStandard.equals(strActualSpouseDiscount.substring(1,strActualSpouseDiscount.length()))){
                        MainReporting.reportWarning("Spouse discount is not matching with actual for TID:"+strTID+".Actual is: "+strActualSpouseDiscount.substring(1,strActualSpouseDiscount.length())+",but Expected is: "+strSpouseDiscount, true);
                        blnResult=false;
                    }
                 }
                 else{
                     LoggerUtil.log("Null value returned for Spouse discount from application");
                     blnResult=false; 
                 }
            }
       }
       catch(Exception e){
           LoggerUtil.log("Exception occured while checking discounts with different TID"+e.getMessage());
       }
       return blnResult;
    }
}
