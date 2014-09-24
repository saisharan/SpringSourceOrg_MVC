
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;
 
public class SeleniumTestLinkIntegration {
 
    
    // Substitute your Dev Key Here
    public final String DEV_KEY = "b570cb0a523a36c7d3bc025ba49af52a";
 
    // Substitute your Server URL Here
    public final String SERVER_URL = "http://localhost/testlink-1.9.9/lib/api/xmlrpc/v1/xmlrpc.php";
 
   // Substitute your project name Here
    public final String PROJECT_NAME = "SalesForce";
 
    // Substitute your test plan Here
    public final String PLAN_NAME = "MyRelease";
 
    // Substitute your build name
    public final String BUILD_NAME = "MyBuild";
 
    @BeforeSuite
    public void setUp() throws Exception {
//         driver = new FirefoxDriver();              
    }
 
    @Test
    public void testSearchCountry() throws Exception {
         String result = "";
              result = TestLinkAPIResults.TEST_PASSED;
              updateTestLinkResult("SFDC-1", null, result);
        
          
 
        
    }
    
    @Test
    public void TestFail() throws Exception {
    	String result = "";
        String exception = null;
        result = TestLinkAPIResults.TEST_FAILED;
        exception ="NullPointerException";
        updateTestLinkResult("SFDC-2", exception, result);
    }
 
 
    @AfterSuite
    public void tearDown() throws Exception {
  //       driver.quit();                                                      
    }
    
    public void updateTestLinkResult(String testCase, String exception, String result)    throws TestLinkAPIException {
         TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEV_KEY,
                                SERVER_URL);
         testlinkAPIClient.reportTestCaseResult(PROJECT_NAME, PLAN_NAME,
                                testCase, BUILD_NAME, exception, result);
    }
}