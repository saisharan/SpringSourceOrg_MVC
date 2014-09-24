package com.pgx.framework.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.pgx.framework.core.EnvParameters.OSType;
import com.opera.core.systems.OperaDriver;
import static com.pgx.framework.core.MainReporting.strImagePath;
import static com.pgx.framework.core.MainReporting.strReportFilePath;
import javax.swing.JComboBox;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * @author Suchit biswal
 * 
 */
public class TestNGBase extends VerifySafe {
	protected WebDriver driver;
        public static String strScreenWidth;
	protected void log(String message, String level) {
		LoggerUtil.log(message, level);
	}
	protected void log(String message) {
		LoggerUtil.log(message);
	}
        /*
         * This method runs before every class and sets up the browser based on the config files
         */
	@Parameters({ "Browser" })
	@BeforeClass(alwaysRun = true)
	public void setUp(@Optional String browser) throws Exception {
		// setup the crome driver
		LogManager.getLogger("com.cognizant.webframe.core").setLevel(LoggerUtil.getLogLevel());
		if (browser != null) {
			new EnvParameters();
			System.setProperty("web.browser.override", browser);
			if (browser.equals("*chrome")) {
				setupChromeDriver();
			}
			if (browser.equals("*iexplore")) {
				setupIEDriver();
			}
		} else {
			new EnvParameters();
			String BrowserType = EnvParameters.webBrowserType;
			if (BrowserType.equals("*chrome")) {
				setupChromeDriver();
			}

			if (BrowserType.equals("*iexplore")) {
				setupIEDriver();
			}
		}
		WebUtility.makeDirectory(EnvParameters.getTestResultScreenshot(), false);
		WebUtility.makeDirectory(EnvParameters.getTestResultVideos(), false);
	}
        /*
         * This method runs before every test method and it launches the browser with the specified size
         */
	@Parameters({ "Browser","width", "height" })
	@BeforeMethod(alwaysRun = true)
	public void testSetup(@Optional String browser,@Optional String width,@Optional String height) {
                strScreenWidth=width;
                VerifySafe.verificationFailures=null;
		String BrowserType=null;
		if (browser != null) {
			System.setProperty("web.browser.override", browser);
			if (browser.equals("*safari") && EnvParameters.isGridEnabled()) {
				Reporter.log("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
				throw new SkipException("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
			} else if (browser.equals("*opera")&& EnvParameters.isGridEnabled()) {
				Reporter.log("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
				throw new SkipException("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
			}
		} else {
			new EnvParameters();
			BrowserType = EnvParameters.webBrowserType;
			if (BrowserType.equals("*safari") && EnvParameters.isGridEnabled()) {
				Reporter.log("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
				throw new SkipException("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
			} else if (BrowserType.equals("*opera")
					&& EnvParameters.isGridEnabled()) {
				Reporter.log("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
				throw new SkipException("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
			}
		}
		driver = WebUtility.buildWebDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		if(!(driver instanceof OperaDriver))
		driver.manage().window().setSize(new Dimension(Integer.parseInt(width), Integer.parseInt(height)));
                //driver.manage().window().maximize();
	}
        /*
         * This method runs after every test method and it closes the browser
         */
	@AfterMethod(alwaysRun = true)
	public void postTestCase(ITestResult _result) {
		if (driver != null){
                    if(!EnvParameters.webBrowserType.contains("iexplore")){
                        try{
                            driver.quit();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        driver.switchTo().activeElement().sendKeys(Keys.F4);
                        driver.switchTo().activeElement().sendKeys(Keys.CONTROL,Keys.F4);
                    }
                }

	}
	
        
        public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
	}
	
        public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
	}
	
        /*
         * This method runs after every class and kilss the driver instance
         */
        @AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		killIEDriver();
		killChromeDriver();
	}
        
        /*
         * This method runs before suit and it sets up the browser info and reporting initialization
         */
	@Parameters({ "Browser" })
	@BeforeSuite
	public void suiteConfig(@Optional String browser) {
            LoggerUtil log=new LoggerUtil();
            MainReporting.createInitXML();
            MainReporting.writeEnvDetailsToXMLReport();
            MainReporting.addTestCaseNode(EnvParameters.propValue("testcasename"));
            MainReporting.addTestFlowNode("TestSuit");
            MainReporting.addTestActivityNode("TestResults");
            log.updateLog4jConfiguration(MainReporting.strImagePath+"\\Report.log");
                    if (browser != null) {
                        System.setProperty("web.browser.override", browser);
                        if (browser.equals("*safari") && EnvParameters.isGridEnabled()) {
                                Reporter.log("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                                throw new SkipException("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
                        } else if (browser.equals("*opera")&& EnvParameters.isGridEnabled()) {
                                Reporter.log("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                                throw new SkipException("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                        }
                    } else {
                        new EnvParameters();
                        String BrowserType = EnvParameters.webBrowserType;
                        if (BrowserType.equals("*safari") && EnvParameters.isGridEnabled()) {
                                Reporter.log("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
                                throw new SkipException("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
                        } else if (BrowserType.equals("*opera")&& EnvParameters.isGridEnabled()) {
                                Reporter.log("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                                throw new SkipException("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                        }
                    }
	}

	@AfterSuite
	public void reportGeneration() {
            
	}
       
        /*
         * This setsup the chrome driver
         */
	public static void setupChromeDriver() throws Exception {
		String ChromProp = "webdriver.chrome.driver";
		new EnvParameters();
		File targetChromedriver = null;
		if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
			targetChromedriver = new File(EnvParameters.getTestRootDir()
					+ File.separator + "target" + File.separator + "classes"
					+ File.separator + "drivers" + File.separator + "chrome"
					+ File.separator + "win" + File.separator
					+ "chromedriver.exe");
		}
		else if (EnvParameters.getOSname() == OSType.mac) {
			targetChromedriver = new File(EnvParameters.getTestRootDir()
					+ File.separator + "target" + File.separator + "classes"
					+ File.separator + "drivers" + File.separator + "chrome"
					+ File.separator + "mac" + File.separator + "chromedriver");
		} else if (EnvParameters.getOSname() == OSType.linux) {
			targetChromedriver = new File(EnvParameters.getTestRootDir()
					+ File.separator + "target" + File.separator + "classes"
					+ File.separator + "drivers" + File.separator + "chrome"
					+ File.separator + "linux" + File.separator
					+ "chromedriver");
		}
		if (targetChromedriver.exists()) {
			System.setProperty(ChromProp, targetChromedriver.getAbsolutePath());
			return;
		} else {
			InputStream reader = null;
			if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
				reader = TestNGBase.class.getResourceAsStream("/drivers/chrome/win/chromedriver.exe");
			} else if (EnvParameters.getOSname() == EnvParameters.OSType.mac) {
				reader = TestNGBase.class.getResourceAsStream("/drivers/chrome/mac/chromedriver");
			} else if (EnvParameters.getOSname() == EnvParameters.OSType.linux) {
				reader = TestNGBase.class.getResourceAsStream("/drivers/chrome/linux/chromedriver");
				LoggerUtil.log("Copying the driver is successfull");
			} else {
				LoggerUtil.log("The chrome driver copying is not successfull");
			}
			if (reader.available() > 0) {
				new File(targetChromedriver.getParent()).mkdirs();
				FileOutputStream writer = new FileOutputStream(targetChromedriver);
				int ch = reader.read();
				while (ch != -1) {
					writer.write(ch);
					ch = reader.read();
				}
				writer.close();
				reader.close();
				targetChromedriver.setExecutable(true, false);
				System.setProperty(ChromProp,targetChromedriver.getAbsolutePath());
			} else {
				LoggerUtil.log("Cannot find chromedriver in the jar");
			}
		}
	}
       
        /*
         * This method setsup the IE browser
         */
	public static void setupIEDriver() throws Exception {
		String ieProperty = "webdriver.ie.driver";
		File targetIEdriver64 = null;
		// dont need it for other OS because IE is not available
		if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
			targetIEdriver64 = new File(EnvParameters.getTestRootDir()
					+ File.separator + "target" + File.separator + "classes"
					+ File.separator + "drivers" + File.separator + "ie"
					+ File.separator + "x64" + File.separator
					+ "IEDriverServer.exe");
		}
		if (targetIEdriver64.exists()) {
			System.setProperty(ieProperty, targetIEdriver64.getAbsolutePath());
			return;
		} else {
			InputStream reader = null;
			if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
				reader = TestNGBase.class.getResourceAsStream("/drivers/ie/win32/IEDriverServer.exe");
			}
			if (reader.available() > 0) {
				new File(targetIEdriver64.getParent()).mkdirs();
				FileOutputStream writer = new FileOutputStream(targetIEdriver64);
				int ch = reader.read();
				while (ch != -1) {
					writer.write(ch);
					ch = reader.read();
				}
				writer.close();
				reader.close();
				targetIEdriver64.setExecutable(true, false);
				System.setProperty(ieProperty,targetIEdriver64.getAbsolutePath());
			} else
				LoggerUtil.log(" IEDriverServer.exe is not found in the jar");
		}
	}
        
        /*
         * It kills the IE driver instance
         */
	private static void killIEDriver() {
		String _processName = "IEDriverServer.exe";
		if (WebUtility.isProcessRuning(_processName) == true)
			WebUtility.killProcess(_processName);
	}
        
        /*
         * It kills Chrome Instance
         */
	private static void killChromeDriver() {
		String _processName = "chromedriver.exe";
		if (WebUtility.isProcessRuning(_processName) == true)
			WebUtility.killProcess(_processName);
	}
}
