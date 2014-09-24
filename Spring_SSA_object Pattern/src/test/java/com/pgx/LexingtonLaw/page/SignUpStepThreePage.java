/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgx.LexingtonLaw.page;

import static com.pgx.LexingtonLaw.test.SignUp.excelPath;
import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.GlobalStatic;
import com.pgx.framework.core.LexingtonCommon;
import com.pgx.framework.core.ObjectLocators;
import com.pgx.framework.core.WebDriverBase;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
/**
 *
 * @author rapriy
 */
public class SignUpStepThreePage extends WebDriverBase {
    public static String excelPath=EnvParameters.getTestRootDir();
    public static String excelSheetName=excelPath+"\\DataSheet.xls";
    public static String excelSheetName1=excelPath+"\\CommonLinks.xls";
    public static String LegalAgreementsTab = ObjectLocators.getLocator("LegalAgreementsTab");
    public static String LoginNameTextBox=ObjectLocators.getLocator("LoginNameTextBox");
    public static String PasswordTextBox=ObjectLocators.getLocator("PasswordTextBox");
    public static String RetypePasswordTextBox = ObjectLocators.getLocator("RetypePasswordTextBox");
    public static String SelectAllCheckBox = ObjectLocators.getLocator("SelectAllCheckBox");
    public static String ESignAgreementsText= ObjectLocators.getLocator("ESignAgreementsText");
    public static String FederalDisclosureStatementText= ObjectLocators.getLocator("FederalDisclosureStatementText");
    public static String StateDisclosureStatementText= ObjectLocators.getLocator("StateDisclosureStatementText");
    public static String EngagementAgreementText= ObjectLocators.getLocator("EngagementAgreementText");
    public static String FederalCancellationFormText= ObjectLocators.getLocator("FederalCancellationFormText");
    public static String StateCancellationFormText= ObjectLocators.getLocator("StateCancellationFormText");
    public static String DigitalSignatureTextBox= ObjectLocators.getLocator("DigitalSignatureTextBox");
    public static String SSNTextBox= ObjectLocators.getLocator("SSNTextBox");
    public static String DateOfBirthTextBox= ObjectLocators.getLocator("DateOfBirthTextBox");
    public static String GoToStepFourButton= ObjectLocators.getLocator("GoToStepFourButton");
    public static String ESignAgreementsCheckBox= ObjectLocators.getLocator("ESignAgreementsCheckBox");
    public static String FederalDisclosureStatementCheckBox= ObjectLocators.getLocator("FederalDisclosureStatementCheckBox");
    public static String StateDisclosureStatementCheckBox= ObjectLocators.getLocator("StateDisclosureStatementCheckBox");
    public static String EngagementAgreementCheckBox= ObjectLocators.getLocator("EngagementAgreementCheckBox");
    public static String FederalCancellationFormCheckBox= ObjectLocators.getLocator("FederalCancellationFormCheckBox");
    public static String StateCancellationFormCheckBox= ObjectLocators.getLocator("StateCancellationFormCheckBox");
    public static String xpathAgreementError = "//div[contains(@class,'agreement') and contains(@class,'field_error')]";
    public static String xpathAgreementOrder = "//div[agreement_name]";
    public static String stepFourConfirmationDiv=ObjectLocators.getLocator("stepFourConfirmationDiv");
    public static String EngagementAgreementLimitedAgencyCheckBox = ObjectLocators.getLocator("EngagementAgreementLimitedAgencyCheckBox");
    public static String xpathStateConfirmation = "";
    public static String SignUpPageThreeDisclaimerDiv= ObjectLocators.getLocator("SignUpPageThreeDisclaimerDiv");
    public String disclaimerText="By clicking this \"Submit\" button, you are providing your electronic signature and completing the sign up process.";

    public SignUpStepThreePage(WebDriver driver, int rowNumber) {
        this.driver = driver;
    	SignUpStepTwoPage signUpStepTwoPage = new SignUpStepTwoPage(driver,rowNumber);
    	signUpStepTwoPage.fillInSignUpStepTwoPageDetails(excelSheetName,"SignUpDetails",rowNumber);
        signUpStepTwoPage.clickOnGoToStepThreeButton();
        isPageLoaded();
    }
    public boolean isLegalAgreementsTabHighlighted() {
        return isElementPresent(LegalAgreementsTab);
    }
    public void fillInSignUpStepThreePageDetails(String strXLPath,String strSheetName,int rowNumber) {
        try {
            GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, rowNumber);
            String loginName = GlobalStatic.getHashMapValue(strSheetName + "_LoginName");
            String password = GlobalStatic.getHashMapValue(strSheetName + "_Password");
            String retypePassword = GlobalStatic.getHashMapValue(strSheetName + "_RetypePassword");
            String ESignAgreements = GlobalStatic.getHashMapValue(strSheetName + "_ESignAgreements");
            String FederalDisclosureStatement = GlobalStatic.getHashMapValue(strSheetName + "_FederalDisclosureStatement");
            String StateDisclosureStatement = GlobalStatic.getHashMapValue(strSheetName + "_StateDisclosureStatement");
            String EngagementAgreement = GlobalStatic.getHashMapValue(strSheetName + "_EngagementAgreement");
            String FederalCancellationForm = GlobalStatic.getHashMapValue(strSheetName + "_FederalCancellationForm");
            String StateCancellationForm = GlobalStatic.getHashMapValue(strSheetName + "_StateCancellationForm");
            String digitalSignature = GlobalStatic.getHashMapValue(strSheetName + "_DigitalSignature");
            String sSN = GlobalStatic.getHashMapValue(strSheetName + "_SSN");
            String dateOfBirth = GlobalStatic.getHashMapValue(strSheetName + "_DateOfBirth");
            String state = GlobalStatic.getHashMapValue(strSheetName + "_State");
            xpathStateConfirmation="//div[contains(@class,'" + "agreement_section_by_submitting" + "')]/descendant::" + "u[contains(text(),'For " + state + " Clients')]"; 
            type(LoginNameTextBox,loginName);
            type(PasswordTextBox,password);
            type(RetypePasswordTextBox,retypePassword);
            //javaScriptClickOnElement(SelectAllCheckBox);
            type(DigitalSignatureTextBox,digitalSignature); 
            type(DateOfBirthTextBox,dateOfBirth);
            specialHandlingType(SSNTextBox,sSN);
        }
        catch(Exception e) {
            //gulp it
        }
    }
     public void selectCheckBox(){
        //javaScriptClickOnElement(SelectAllCheckBox);
        javaScriptClickOnElement(ESignAgreementsCheckBox);
        javaScriptClickOnElement(FederalDisclosureStatementCheckBox);
        javaScriptClickOnElement(EngagementAgreementLimitedAgencyCheckBox);
        javaScriptClickOnElement(FederalCancellationFormCheckBox);
        javaScriptClickOnElement(StateCancellationFormCheckBox);   
        javaScriptClickOnElement(StateDisclosureStatementCheckBox);
    }
     
    public boolean footerTestSignUpStepThreePage() throws IOException{
        LexingtonCommon lex=new LexingtonCommon();
        return lex.footerVerification(this.driver, excelSheetName1,"Footer_Verification");
    }

    public boolean selectFewAgreements(String strXLPath,String strSheetName,int rowNumber) {
        boolean agreementVerif = false;
        try {
            javaScriptClickOnElement(ESignAgreementsCheckBox);
            clickOnElementJavavScript(GoToStepFourButton);
            if (driver.findElements(By.xpath(xpathAgreementError)).size() == 5) {
                javaScriptClickOnElement(FederalDisclosureStatementCheckBox);
                clickOnElementJavavScript(GoToStepFourButton);
                if (driver.findElements(By.xpath(xpathAgreementError)).size() == 4) {
                    javaScriptClickOnElement(StateDisclosureStatementCheckBox);
                    clickOnElementJavavScript(GoToStepFourButton);
                    if (driver.findElements(By.xpath(xpathAgreementError)).size() == 3) {
                        javaScriptClickOnElement(EngagementAgreementLimitedAgencyCheckBox);
                        clickOnElementJavavScript(GoToStepFourButton);
                        if (driver.findElements(By.xpath(xpathAgreementError)).size() == 2) {
                            javaScriptClickOnElement(FederalCancellationFormCheckBox);
                            clickOnElementJavavScript(GoToStepFourButton);
                            if (driver.findElements(By.xpath(xpathAgreementError)).size() == 1) {
                                javaScriptClickOnElement(StateCancellationFormCheckBox);
                                fillInSignUpStepThreePageDetails(strXLPath,strSheetName,rowNumber);
                                clickOnElementJavavScript(GoToStepFourButton);
                                if (isElementPresent(stepFourConfirmationDiv)) {
                                    agreementVerif = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            //gulp it
        }
        return agreementVerif;
    }
    public boolean orderOfAgreements(String strXLPath,String strSheetName) {
        boolean agreementVerif = true;
        int intRows =0;
        try {
            //fillInSignUpStepThreePageDetails(strXLPath,strSheetName,2);
            intRows=GlobalStatic.getColumnValuesFromExcel(strXLPath, strSheetName, "AgreementList");        
            if (!driver.findElements(By.xpath(xpathAgreementOrder)).isEmpty()) {
                if (driver.findElements(By.xpath(xpathAgreementOrder)).size() == intRows) {
                    List<WebElement> agreementsDiv = driver.findElements(By.xpath(xpathAgreementOrder));
                    for (int i=0;i<intRows;i++) {
                        GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intRows+2);
                        if(!agreementsDiv.get(i).getText().contains(GlobalStatic.getHashMapValue(strSheetName + "_AgreementList"))) {
                            agreementVerif=false;
                        }
                    }
                }            
            }
        }   
        catch(Exception e) {
            //gulp it
        }
        return agreementVerif;
    }
    
    public boolean stateConfirmation(String strXLPath,String strSheetName,int rowNumber){
        fillInSignUpStepThreePageDetails(strXLPath,strSheetName,rowNumber);
        if (driver.findElements(By.xpath(xpathStateConfirmation)).size() > 0) {
            return true;
        }
        else {
            return false;
        }
        
    }
    public void clickOnGoToStepFourButton() {
        clickOnElementJavavScript(GoToStepFourButton);
    }
    
     public boolean disclaimerPageThreeValidation() {
        if (getText(SignUpPageThreeDisclaimerDiv).equals(disclaimerText)) {
            return true;
        }
        else {
            return false;
        }
    }
    
}
