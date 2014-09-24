package com.pgx.framework.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import com.pgx.framework.core.EnvParameters.BrowserType;
import com.opera.core.systems.OperaDriver;


/**
 * @author 
 * 
 */
public class WebUtility {
	/**
	 * Description : This Method need to be modified for Safari and other
	 * requiremnts
	 * 
	 * @throws Exception
	 */
	static WebDriver driver = null;
	static WebDriver scrndriver = null;

	public static WebDriver buildWebDriver() {
		// String BrowserType = env.webBrowserType;
		String BrowserType;
		if (System.getProperty("web.browser.override") != null) {
			BrowserType = System.getProperty("web.browser.override");
		} else {
			BrowserType = EnvParameters.webBrowserType;
		}
		try {
                    if (BrowserType.equals("*firefox")) {
                        System.setProperty("webdriver.firefox.bin",EnvParameters.getFirefoxBin());
                        FirefoxProfile firefoxProfile = new FirefoxProfile();
                        firefoxProfile.setAcceptUntrustedCertificates(true);
                        firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
                        if (!EnvParameters.isGridEnabled()) {
                                scrndriver=new FirefoxDriver(firefoxProfile);
                                return scrndriver;
                        }
                        else {
                                DesiredCapabilities FFcapability = DesiredCapabilities.firefox();
                                FFcapability.setCapability(FirefoxDriver.PROFILE,firefoxProfile);
                                scrndriver= new RemoteWebDriver(new URL("http://"+EnvParameters.getHubIp()+":".concat(EnvParameters.getHubPort()).concat("/wd/hub")), FFcapability);
                                return scrndriver;
                        }
                    } else if (BrowserType.equals("*iexplore")) {
                        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
                        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                        WebDriver _driver = null;
                        try {
                                if (!EnvParameters.isGridEnabled()) {
                                    _driver = new InternetExplorerDriver(ieCapabilities);
                                }
                                else
                                {
                                    _driver= new RemoteWebDriver(new URL("http://"+EnvParameters.getHubIp()+":".concat(EnvParameters.getHubPort()).concat("/wd/hub")), ieCapabilities);
                                }
                        } catch (Exception e) {
                                Reporter.log("Cannot create InternetExplorerDriver", true);
                                String _processName = "IEDriverServer.exe";
                                if (isProcessRuning(_processName) == true)
                                killProcess(_processName);
                        }
                        scrndriver=_driver;
                        return _driver;
                    } else if (BrowserType.equals("*chrome")) {
                        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                        capabilities.setCapability("chrome.switches",Arrays.asList("--ignore-certificate-errors"));
                        if (!EnvParameters.isGridEnabled()) {	
                            scrndriver= new ChromeDriver(capabilities);
                            return scrndriver;
                        }
                        else
                        {
                            scrndriver=new RemoteWebDriver(new URL("http://"+EnvParameters.getHubIp()+":".concat(EnvParameters.getHubPort()).concat("/wd/hub")), capabilities);
                            return scrndriver;
                        }
                    }
                    else if (BrowserType.equals("*safari")) {
                        if (!EnvParameters.isGridEnabled()) {
                            scrndriver=new SafariDriver();
                            return scrndriver;
                        }
                        else
                        {
                            throw new SkipException("Parallel Mode is not applicable for Safari browser, please disable parallel mode");
                        }
                    }
                    else if (BrowserType.equals("*opera")) {
                        if (!EnvParameters.isGridEnabled()) {	
                            return new OperaDriver();
                        }
                        else
                        {
                           throw new SkipException("Parallel Mode is not applicable for Opera browser, please disable parallel mode");
                        }	
                }
		} catch (Exception e) {
			Reporter.log(e.getMessage(), true);
		}
		return driver;
	}
        /*
         * It is to creates directory in the specified location
         */
	public static boolean makeDirectory(File path, boolean recreate) {
		if (path.exists() && !recreate) {
                    // do nothing
                    return true;
		} else if (path.exists() && recreate) {
                    // delete path, and recreate
                    deleteDirectory(path);
                    path.mkdirs();
                    path.setExecutable(true);
                    return true;
		} else {
                    // path not exists, so create the path
                    path.mkdirs();
                    path.setExecutable(true);
                    return true;
		}
	}

	public static boolean makeDirectory(String path, boolean recreate) {
		return makeDirectory(new File(path), recreate);
	}
        /*
         * It deletes the directory 
         */
	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
                    // include a test for root directory
                    File[] files = path.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isDirectory()) {
                                deleteDirectory(files[i]);
                        } else {
                                files[i].delete();
                        }
                    }
		}
		return (path.delete());
	}
        /*
         * It is to clear the browser cache
         */
	public static void clearBrowserCache(WebDriver driver) throws Exception {
		if (EnvParameters.getBrowserType() == BrowserType.iexplore) {
			clearBrowsingDataIE();
		} else if (EnvParameters.getBrowserType() == BrowserType.firefox) {
			clearBrowsingDataFireFox();
		} else if (EnvParameters.getBrowserType() == BrowserType.safari) {
			clearBrowsingDataSafari();
		} else if (EnvParameters.getBrowserType() == BrowserType.chrome) {
			clearBrowsingDataChrome();
		}
		driver.manage().deleteAllCookies();
	}
        /*
         * This method clears the browsing data from IE browser
         */
	private static void clearBrowsingDataIE() throws Exception {
		Runtime rt = Runtime.getRuntime();
		// Clear temporary Internet files
		Process proc8 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 8");
		proc8.waitFor();
		// Clear Cookies
		Process proc2 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
		proc2.waitFor();
		// Clear History
		Process proc1 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 1");
		proc1.waitFor();
	}
        /*
         * This method clears the browsing data from Firefox browser
         */
	private static void clearBrowsingDataFireFox() throws Exception {
		String tempFilePath = "";
		Map<String, String> map = System.getenv();
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.equals("windows") || osName.equals("windows 7")) {
			tempFilePath = map.get("LOCALAPPDATA")+ "\\Mozilla\\Firefox\\Profiles\\";
		} else if (osName.equals("windows xp")) {
			tempFilePath = map.get("HOMEPATH")+ "\\Local Settings\\Application Data\\Mozilla\\Firefox\\Profiles\\";
		}
		File tempFilesDir = new File(tempFilePath);
		deleteDirectory(tempFilesDir);

	}
        
        /*
         * This method clears the browsing data from safari browser
         */
	private static void clearBrowsingDataSafari() throws Exception {
		Map<String, String> map = System.getenv();
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.equals("windows") || osName.equals("windows 7")) {
                    String tempFilePath1 = map.get("LOCALAPPDATA")+ "\\Apple Computer\\Safari\\";
                    String tempFilePath2 = map.get("HOMEPATH")+ "\\AppData\\LocalLow\\Apple Computer\\Safari\\";
                    String tempFilePath3 = map.get("APPDATA")+ "\\AppData\\Roaming\\Apple Computer\\Safari\\";
                    File tempFilesDir1 = new File(tempFilePath1);
                    deleteDirectory(tempFilesDir1);
                    File tempFilesDir2 = new File(tempFilePath2);
                    deleteDirectory(tempFilesDir2);
                    File tempFilesDir3 = new File(tempFilePath3);
                    deleteDirectory(tempFilesDir3);
		} else if (osName.equals("windows xp")) {
                    String tempFilePath = map.get("HOMEPATH")+ "\\Local Settings\\Apple Computer\\Safari\\";
                    File tempFilesDir = new File(tempFilePath);
                    deleteDirectory(tempFilesDir);
		}
	}
        
        /*
         * This method clears the browsing data from safari browser
         */
	private static void clearBrowsingDataChrome() throws Exception {
		Map<String, String> map = System.getenv();
		String tempFilePath = "";
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.equals("windows") || osName.equals("windows 7")) {
			tempFilePath = map.get("LOCALAPPDATA") + "\\Chrome\\";
		} else if (osName == "windows xp") {
			tempFilePath = map.get("HOMEPATH")+ "\\Local Settings\\Application Data\\Google\\Chrome\\";
		}
		File tempFilesDir = new File(tempFilePath);
		deleteDirectory(tempFilesDir);
	}

        /*
         * This method is used to check whether the given process is running or not
         */
	public static boolean isProcessRuning(String serviceName) {
            if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
                Process p;
                try {
                    p = Runtime.getRuntime().exec("tasklist");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(serviceName)) {
                            Reporter.log(serviceName + " is running", true);
                            return true;
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return false;
            } else {
                LoggerUtil.log("Client OS is not window, cannot check running process: "+ serviceName);
                return false;
            }
	}
        
        /*
         * This method kills the given service name 
         */
	public static void killProcess(String serviceName) {
            Reporter.log("Trying to kill " + serviceName, true);
            String KILL = "taskkill /F /T /IM ";
            if (EnvParameters.getOSname() == EnvParameters.OSType.windows) {
                try {
                    Runtime.getRuntime().exec(KILL + serviceName);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else
                Reporter.log("Client OS is not window, can not kill " + serviceName,true);
	}
}
