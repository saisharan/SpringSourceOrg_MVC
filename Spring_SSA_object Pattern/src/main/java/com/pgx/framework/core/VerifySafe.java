package com.pgx.framework.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public abstract class VerifySafe {
    private static Map<ITestResult, List<Throwable>> verificationFailuresHashMap =new HashMap<ITestResult, List<Throwable>>();
    public static List verificationFailures;
    public static boolean blnResult=true;
    // protected static Long SYNC_DELAY= EnvParameters.syncDelayTime();
    protected void print(String s) {
        Reporter.log(s, true);
    }

    /**
     * Description : This Method will get the current verification failure into
     * a list
     * 
     * @return [List]
     */
    @SuppressWarnings("rawtypes")
    public static List getVerificationFailures() {
        List verificationFailures =verificationFailuresHashMap.get(Reporter.getCurrentTestResult());
        return verificationFailures == null ? new ArrayList(): verificationFailures;
    }
   
    /**
     * Description : This method will compare the actual and expected 
     * 
     */
    public static void results(){
        if(!blnResult){
            blnResult=true;
            verifyAssert(false, true,verificationFailures.toString());
        }
        else{
            verifyAssert(true, true,"Passed");
        }
    }
   
    /**
     * Description : This method will add verification failures to the hashmap
     * 
     * @param e
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void addVerificationFailure(Throwable e) {
        verificationFailures = getVerificationFailures();
        verificationFailuresHashMap.put(Reporter.getCurrentTestResult(),verificationFailures);
        verificationFailures.add(e);
        blnResult=false;
    }

    /**
     * Description : This Method will verify the the actual with expected
     * string, will not throw exception if the verification fails.
     * 
     * @param actual
     * @param expected
     * @param message
     */
    public static void verifySafely(Object actual, Object expected,String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            Reporter.log("Expected value: " + expected + " Actual value: "+ actual + " - PASSED : " + message, true);
        } catch (Throwable e) {
            Reporter.log("Expected value: " + expected + " Actual value: "+ actual + " - FAILED : " + message, true);
            addVerificationFailure(e);
        }
    }

    /**
     * Description : This Method will verify the actual with the expected
     * string, and throws runtime exception if the verification fails, and the
     * execution stops.
     * 
     * @param actual
     * @param expected
     * @param message
     * @throws RuntimeException
     */
    public static void verifyAssert(Object actual, Object expected,String message) throws RuntimeException {
        try {
            Assert.assertEquals(actual, expected, message);
            Reporter.log("Expected value: " + expected + " Actual value: "+ actual + " - PASSED : " + message, true);
        } catch (Exception e) {
            Reporter.log("Expected value: " + expected + " Actual value: "+ actual + " - FAILED  : " + message, true);
            throw new RuntimeException(e);
        }
    }

}
