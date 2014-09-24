package com.pgx.framework.listeners;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.IInvokedMethod;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import testlink.api.java.client.TestLinkAPIResults;
import ch.randelshofer.screenrecorder.ScreenRecorder;
import ch.randelshofer.screenrecorder.ScreenRecorder.CursorEnum;

import com.pgx.framework.core.EnvParameters;
import com.pgx.framework.core.LoggerUtil;
import com.pgx.framework.core.MainReporting;
import com.pgx.framework.core.TC;
import com.pgx.framework.core.VerifySafe;
import com.pgx.framework.utility.WebUtil;



public class WebTestListeners implements ITestListener {
    public static String sFilename;
	ScreenRecorder recorder = null;
    @SuppressWarnings("unused")
    private String testRoot = EnvParameters.getTestRootDir();
    public static String testResultVideos = "";
    private String testResultScreenshots = EnvParameters.getTestResultScreenshot();
    int retryCount = Integer.parseInt(EnvParameters.getPropertyValue("retry.attempts"));

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onFinish(ITestContext context) {
        recorder = null;
        removeIncorrectlyFailedTests(context);
    }

   /*
    * This method runs on the start of the suite
    */
    @Override
    public void onStart(ITestContext context) {
        for (ITestNGMethod testMethod : context.getAllTestMethods()) {
            testMethod.setRetryAnalyzer(new RetryAnalyzer());
        }
        recorder = null;
    }
    
    /*
     * This method runs on the failure and captures the screenshot and video
     */
    @SuppressWarnings("unused")
    @Override
    public void onTestFailure(ITestResult result) {
        // Get the current test name
        String sTestMethodName = result.getMethod().getMethodName();
        String sTestSuiteName =result.getTestClass().getRealClass().getSimpleName();
        String strmsg = result.getThrowable().toString();
        String imageFileName = null;
        String timeTaken = Long.toString((result.getEndMillis() - result.getStartMillis()) / 1000);
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        LoggerUtil.log("<<<*** END: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        LoggerUtil.log("=====================================================================================");
        LoggerUtil.log("Test Failed :" + testName + ", Took " + timeTaken + " seconds");
        MainReporting.reportError("Test Failed: " +testName + ". Exception Occured:"+strmsg, true);
        // Capture the screen shot and attach to the result
        if (EnvParameters.isEnableResultExcel())
        {
            updateResultInExcel(String.valueOf(getAnnotationId(result.getMethod().getConstructorOrMethod().getMethod())), "Fail");
        }
        if (EnvParameters.TestLinkReport())
        {
            TestLink.updateResult(String.valueOf(getAnnotationId(result.getMethod().getConstructorOrMethod().getMethod())),result.getThrowable().toString(),TestLinkAPIResults.TEST_FAILED);
        }
        if (EnvParameters.isScreenShotCapture()) {
            WebUtil.captureScreen(testResultScreenshots,result.getMethod().getMethodName());
            if (EnvParameters.getRunMode().equals("maven"))
            {
            	Reporter.log("<a href='../../failsafe-reports/screenshots/"
                        + result.getMethod().getMethodName()
                        + ".jpg'><img src='../../failsafe-reports/screenshots/"
                        + result.getMethod().getMethodName()
                        + ".jpg' height='100' width='100'/><br></a>");
            }
           	if (result.getMethod().getRetryAnalyzer().retry(result)) {
                    LoggerUtil.log("Retrying test : " + testName + ", "+ retryCount + " time(s)");
                    Reporter.log("Retrying test : '" + testName + "', "+ retryCount + " time(s)");
                    result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
                    retryCount++;
                } else {
                    Reporter.log("Test failed after max allowed retries  : "+ testName);
                    WebUtil.captureScreen(testResultScreenshots,result.getMethod().getMethodName());
                    if (EnvParameters.getRunMode().equals("maven"))
                    {
                    	Reporter.log("<br><a href='../../failsafe-reports/screenshots/"
                            + result.getMethod().getMethodName()
                            + ".jpg'><img src='../../failsafe-reports/screenshots/"
                            + result.getMethod().getMethodName()
                            + ".jpg' height='100' width='100'/><br></a>");
                    }
                    retryCount = 1;
                } 	
        } else {
            LoggerUtil.log("***WARN : Screen shot setting is set to false ");
        }
       //capture the video and attach to the result
        if (EnvParameters.isEnableVideoRecording()) {
        	String currentVideoFilePath = testResultVideos + File.separator +
        	sTestSuiteName + "." + sTestMethodName + ".avi";
        	WebUtil.captureVideo(currentVideoFilePath, recorder); 
        }
    }
    
    /*
     * this method runs the skip of test and logs the same in the result files
     * 
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    	  if (EnvParameters.TestLinkReport())
          {
          	TestLink.updateResult(String.valueOf(getAnnotationId(iTestResult.getMethod().getConstructorOrMethod().getMethod())),null,TestLinkAPIResults.TEST_DEPARTED);
          }
          if (EnvParameters.isEnableResultExcel()) {
    	        updateResultInExcel(String.valueOf(getAnnotationId(iTestResult.getMethod().getConstructorOrMethod().getMethod())), "Skipped");
    	        MainReporting.reportSkipped(String.valueOf(getAnnotationId(iTestResult.getMethod().getConstructorOrMethod().getMethod())));
    	  }
    }

    /*
     * This method runs on the start of the test and initiates video recording
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        // Get the current test case name
        String sTestMethodName = iTestResult.getMethod().getMethodName();
        String sTestSuiteName = iTestResult.getTestClass().getRealClass().getSimpleName();
        LoggerUtil.log("=====================================================================================");
        LoggerUtil.log("<<<*** START: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        LoggerUtil.log("=====================================================================================");
        if (EnvParameters.isEnableVideoRecording()) {
        	try {
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    recorder = new ScreenRecorder(ge.getDefaultScreenDevice().getDefaultConfiguration(), "AVI", 16, CursorEnum.NONE,0f);
                    testResultVideos=MainReporting.strImagePath+ "\\Video";
                    MainReporting.createFolder(testResultVideos);
                    sFilename=MainReporting.getCurrentDateTime("ddMMMyy_hhmmss");
                    recorder.createMovieWriter(testResultVideos, sFilename);     
                    recorder.start(testResultVideos, sFilename);
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerUtil.log("***ERROR : Recording Error in OnTestStart" + e.getMessage());
                }
        }

    }
    
    /*
     * This method runs on the success of test and logs the same in the result fiels
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String sTestMethodName = iTestResult.getMethod().getMethodName();
        String sTestSuiteName = iTestResult.getTestClass().getRealClass().getSimpleName();
        String timeTaken = Long.toString((iTestResult.getEndMillis() - iTestResult.getStartMillis()) / 1000);
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName();
        LoggerUtil.log("<<<*** END: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        LoggerUtil.log("=====================================================================================");
        LoggerUtil.log("Test Passed :" + testName + ", Took " + timeTaken + " seconds");
        LoggerUtil.log("=====================================================================================");
        MainReporting.reportPass("Test Passed: " +testName , false);
        if (retryCount > 1) {
            Reporter.log("Test '" + sTestMethodName + "' passed after "+ (retryCount - 1) + " retrie(s)");
            retryCount = 1;
        }
        if (EnvParameters.TestLinkReport())
        {
            TestLink.updateResult(String.valueOf(getAnnotationId(iTestResult.getMethod().getConstructorOrMethod().getMethod())),null,TestLinkAPIResults.TEST_PASSED);
        }
        if (EnvParameters.isEnableResultExcel()) {
            updateResultInExcel(String.valueOf(getAnnotationId(iTestResult.getMethod().getConstructorOrMethod().getMethod())), "Pass");
        }
        try {
            if (recorder != null)
                recorder.stop();
          File videofile = new File(testResultVideos + File.separator + sFilename + ".avi");
          // Delete the video file on test pass because we don't need it any more :)
          if (videofile.exists()) {
                videofile.delete();  
          }
        } catch (Exception e) {
            System.err.println("***ERROR : Error in onTestSuccess" + e.getMessage());
        }

    }
    /*
     * This method runs after the invocation of the test
     */
    @SuppressWarnings("unchecked")
    // @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Reporter.setCurrentTestResult(result);
        if (method.isTestMethod()) {
            List<Throwable> verificationFailures =VerifySafe.getVerificationFailures();
            // if there are verification failures...
            if (verificationFailures.size() > 0) {
                // set the test to failed
                result.setStatus(ITestResult.FAILURE);
                // if there is an assertion failure add it to verificationFailures
                if (result.getThrowable() != null) {
                    verificationFailures.add(result.getThrowable());
                }
                int size = verificationFailures.size();
                // if there's only one failure just set that
                if (size == 1) {
                    result.setThrowable(verificationFailures.get(0));
                } else {
                    // create a failure message with all failures and stack traces (except last failure)
                    StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):\n\n");
                    for (int i = 0; i < size; i++) {
                        failureMessage.append("Failure ").append(i + 1).append(" of ").append(size).append(":\n");
                        Throwable t = verificationFailures.get(i);
                        String errorMessage = null;
                        errorMessage = Utils.stackTrace(t, false)[1];
                        failureMessage.append(errorMessage).append("\n\n");
                    }
                    Throwable merged = new Throwable(failureMessage.toString());
                    result.setThrowable(merged);
                }
            }
        }
    }

    private IResultMap removeIncorrectlyFailedTests(ITestContext test) {
        List<ITestNGMethod> failsToRemove = new ArrayList<ITestNGMethod>();
        IResultMap returnValue = test.getFailedTests();
        for (ITestResult result : test.getFailedTests().getAllResults()) {
            long failedResultTime = result.getEndMillis();
            for (ITestResult resultToCheck : test.getFailedButWithinSuccessPercentageTests().getAllResults()) {
                if (failedResultTime == resultToCheck.getEndMillis()) {
                    failsToRemove.add(resultToCheck.getMethod());
                    break;
                }
            }
            for (ITestResult resultToCheck : test.getPassedTests().getAllResults()) {
                if (failedResultTime == resultToCheck.getEndMillis()) {
                    failsToRemove.add(resultToCheck.getMethod());
                    break;
                }
            }
        }
        for (ITestNGMethod method : failsToRemove) {
            returnValue.removeResult(method);
        }
        return returnValue;
    }
    
    /*
     * This method updates the results in output excel sheet
     */
    private void updateResultInExcel(String testcaseId,String testResult){
    	try {
            FileInputStream file = new FileInputStream(new File(EnvParameters.getPropertyValue("assignment.sheet")));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    Iterator<Row> rowIterator = sheet.iterator();
	    Row row = rowIterator.next();
	    while(rowIterator.hasNext()) {
	    	row = rowIterator.next();
	    	Cell cell = row.getCell(0);
	    	if (cell.toString().contains(testcaseId))
	    		row.createCell(2).setCellValue(testResult);
	    }
	    file.close();
	    FileOutputStream outFile =new FileOutputStream(new File(EnvParameters.getPropertyValue("assignment.sheet")));
	    workbook.write(outFile);
	    outFile.close();
        }catch(Exception e)
        {
            //to-do
        }
    }
    
    /*
     * This gets the annotaion ID from the test methods and 
     */
    private String getAnnotationId(Method m) {
        Annotation[] annotation = m.getAnnotations();
        for (Annotation a : annotation) {
            if (a instanceof TC) {
                TC testCaseAnnotation = (TC) a;
                return testCaseAnnotation.id();
            }
        }
        return null;
    }
    // @Override
    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
        // TODO Auto-generated method stub
    }
}
