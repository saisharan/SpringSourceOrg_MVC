package com.pgx.framework.core;

import com.pgx.framework.exception.BrowserBotException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Suchit biswal
 * 
 */
public class EnvParameters {
    public static final String PROP_FILE;
    private static Properties properties = new Properties();
    // private static String testRootDir = getTestRootDir();
    public final static String webBrowserType;
    public final static String pageLoadTime;
    private final static String TEST_ROOT_DIR, TEST_RESULT_VIDEOS,
            TEST_RESULT_SCREENSHOTS,
            TEST_ENV;
  

    static {
        
           
            TEST_ROOT_DIR = System.getProperty("user.dir");
                  
            TEST_ENV=System.getProperty("Env");
            System.out.println("in the env ");
            if (TEST_ENV == null){
                System.out.println("env parameters are empty");
                  PROP_FILE = getConfigFile();
            }
            else
                if (TEST_ENV.equals("dev"))
                    PROP_FILE="dev_env_config.properties";
                else if (TEST_ENV.equals("staging"))
                    PROP_FILE="Staging_env_config.properties";
                else if (TEST_ENV.equals("production"))
                    PROP_FILE="production_env_config.properties";
                else
                    throw new BrowserBotException("You have selected the invalid environment, please choose the valid environment");
            
           try {
                   FileInputStream in = new FileInputStream(PROP_FILE);
                    properties.load(in);
                in.close();
            } catch (Exception e) {
                throw new BrowserBotException("Unable to read Environment property file");
            }
           

           
                TEST_RESULT_VIDEOS =
                        TEST_ROOT_DIR + File.separator + "target"
                                + File.separator + "failsafe-reports"
                                + File.separator + "videos";
            
           
      
                TEST_RESULT_SCREENSHOTS =
                        TEST_ROOT_DIR + File.separator + "target"
                                + File.separator + "failsafe-reports"
                                + File.separator + "screenshots";
            
                if (System.getProperty("Browser")==null)
                webBrowserType=getPropertyValue("web.browser");
                else
                     webBrowserType=System.getProperty("Browser");
                pageLoadTime=getPropertyValue("pageLoadTime");
            
           }

    public enum OSType {
        windows,
        mac,
        linux,
    }

    public enum BrowserType {
        iexplore,
        firefox,
        safari,
        chrome,
        opera,
        htmlunit
    }

    public static BrowserType getBrowserType(String browser) {
        if (browser == null)
            return BrowserType.htmlunit;
        if (browser.equalsIgnoreCase("*iexplore"))
            return BrowserType.iexplore;
        else if (browser.equalsIgnoreCase("*firefox"))
            return BrowserType.firefox;
        else if (browser.equalsIgnoreCase("*safari"))
            return BrowserType.safari;
        else if (browser.equalsIgnoreCase("*chrome"))
            return BrowserType.chrome;
        else if (browser.equalsIgnoreCase("*opera"))
            return BrowserType.opera;
        else
            return BrowserType.htmlunit;
    }

    public static BrowserType getBrowserType() {
        
        String jenkinsParam_browser=System.getProperty("browser").trim();
        System.out.println("jenkinsParam_browser is "+jenkinsParam_browser);
        String browser = getPropertyValue("web.browser");
        if (jenkinsParam_browser != null)
            return getBrowserType(jenkinsParam_browser);
        else
            return getBrowserType(browser);
        
    }

    /**
     * Description : this method will get the value of a properly from the
     * EnvParameters.properties
     * 
     * @param propertyname
     *            [String]
     * @return Property value [String]
     * @throws Exception
     *             Usage : GetPropertyValue ("web.browser") will return the
     *             browsername
     */
        public static String getPropertyValue(String propertyname) {
            return properties.getProperty(propertyname);
            
        }
        public static String getConfigFile(){
                String PROP_FILE = "Main_env_Config.properties";
                String strConfigFile="";
                System.out.println("picking up the env from property file");
                Properties properties = new Properties();
                 try {
                        FileInputStream in =
                                new FileInputStream(System.getProperty("user.dir")+File.separator+PROP_FILE);
                        properties.load(in);
                        in.close();
                    
                       
                       
                       
                            strConfigFile=properties.getProperty("web.environment").trim();
                       
                    } catch (IOException e) {
                        System.err.println("Failed to read from "+System.getProperty("user.dir")+File.separator+PROP_FILE+ " file.");
                    }
                 System.out.println("strConfigFile is "+strConfigFile);
                return strConfigFile;
                
         }

    /**
     * Description :
     * 
     * @return
     * @throws Exception
     */
    public static String getTestRootDir() {
          return TEST_ROOT_DIR;
    }

    public static OSType getOSname() {
        String osType = System.getProperty("os.name");
        return getOSname(osType);
    }

    public static OSType getOSname(String osType) {
        if (osType.toLowerCase().contains("win"))
            return OSType.windows;
        else if (osType.toLowerCase().contains("mac"))
            return OSType.mac;
        else if (osType.toLowerCase().contains("linux"))
            return OSType.linux;
        else
            return OSType.windows; // default to window
    }

    public static String getTestResultScreenshot() {
        return TEST_RESULT_SCREENSHOTS;
    }

    public static String getTestResultVideos() {
        return TEST_RESULT_VIDEOS;
    }

   

    public static boolean isScreenShotCapture() {
        String env_captureScreenshot = System.getProperty("capture.screenshot");
        String prop_captureScreenshot =
                String.valueOf(properties.getProperty("capture.screenshot"));
        if (env_captureScreenshot != null)
            return (env_captureScreenshot.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else if (prop_captureScreenshot != null)
            return (prop_captureScreenshot.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else
            return false;
    }

    public static boolean isEnableVideoRecording() {
        String env_enableVideoRecording = System.getProperty("capture.video");
        String prop_enableVideoRecording =
                String.valueOf(properties.getProperty("capture.video"));
        if (env_enableVideoRecording != null)
            return (env_enableVideoRecording.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else if (prop_enableVideoRecording != null)
            return (prop_enableVideoRecording.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else
            return false;
    }

    public static String getLoggerLevel() {
        String env_level = System.getProperty("logger.level");
        String prop_level =
                String.valueOf(properties.getProperty("logger.level"));
        if (env_level != null)
            return env_level.trim();
        else if (prop_level != null)
            return prop_level.trim();
        else
            return null;
    }

    public static boolean isEnableResultExcel() {
        String env_EnableResultExcel = System.getProperty("excel.report");
        String prop_EnableResultExcel =
                String.valueOf(properties.getProperty("excel.report"));
        if (env_EnableResultExcel != null)
            return (env_EnableResultExcel.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else if (prop_EnableResultExcel != null)
            return (prop_EnableResultExcel.trim().equalsIgnoreCase("true"))
                    ? true : false;
        else
            return false;
    }

    

    public static String getRetryAttempts() {
        String env_retry = System.getProperty("retry.attempts");
        String prop_retry =
                String.valueOf(properties.getProperty("retry.attempts"));
        if (env_retry != null)
            return env_retry.trim();
        else if (!prop_retry.equals("null"))
            return prop_retry.trim();
        else
            return null;
    }
    
    public static String getRunMode() {
    	String env_runmode=System.getProperty("run.mode");
    	String prop_runmode=String.valueOf(properties.getProperty("run.mode"));
    	if(env_runmode != null)
    		return env_runmode;
    	else
    		return prop_runmode;
    }
    
    public static boolean TestLinkReport() {
    	 String env_testlinkreport = System.getProperty("testlink.report");
         String prop_testlinkreport =String.valueOf(properties.getProperty("testlink.report"));
         if (env_testlinkreport != null)
             return (env_testlinkreport.trim().equalsIgnoreCase("true"))
                     ? true : false;
         else if (prop_testlinkreport != null)
             return (prop_testlinkreport.trim().equalsIgnoreCase("true"))
                     ? true : false;
         else
             return false;
         
    }
    public static String getPageLoadTime(){
       return System.getProperty("pageLoadTime");
    }
    public static String getHubIp() {
    	String env_hubip=System.getProperty("hub.ip");
    	String prop_hubip=String.valueOf(properties.getProperty("hub.ip"));
    	if(env_hubip != null)
    		return env_hubip;
    	else
    		return prop_hubip;
    }
    
    public static String getFirefoxBin() {
    	String env_firefoxbin=System.getProperty("firefox.bin");
    	String prop_firefoxbin=String.valueOf(properties.getProperty("firefox.bin"));
    	if(env_firefoxbin != null)
    		return env_firefoxbin;
    	else
    		return prop_firefoxbin;
    }
    
        
    public static String getHubPort() {
    	String env_hubport=System.getProperty("hub.port");
    	String prop_hubport=String.valueOf(properties.getProperty("hub.port"));
    	if(env_hubport != null)
    		return env_hubport;
    	else
    		return prop_hubport;
    }
    
    public static String getTestLinkServerName() {
    	String env_testlinkserver=System.getProperty("ServerUrl");
    	String prop_testlinkserver=String.valueOf(properties.getProperty("ServerUrl"));
    	if(env_testlinkserver !=null)
    		return env_testlinkserver;
    	else
    		return prop_testlinkserver;
    }
    
    public static String getTestLinkDevKey() {
    	String env_testlinkdevkey=System.getProperty("DevKey");
    	String prop_testlinkdevkey=String.valueOf(properties.getProperty("DevKey"));
    	if(env_testlinkdevkey !=null)
    		return env_testlinkdevkey;
    	else
    		return prop_testlinkdevkey;
    }
    
    public static String getTestLinkProject() {
    	String env_testlinkproject=System.getProperty("ProjectName");
    	String prop_testlinkproject=String.valueOf(properties.getProperty("ProjectName"));
    	if(env_testlinkproject !=null)
    		return env_testlinkproject;
    	else
    		return prop_testlinkproject;
    }
    
    public static String getTestLinkRelease() {
    	String env_testlinkrelease=System.getProperty("PlanName");
    	String prop_testlinkrelease=String.valueOf(properties.getProperty("PlanName"));
    	if(env_testlinkrelease !=null)
    		return env_testlinkrelease;
    	else
    		return prop_testlinkrelease;
    }
    
    public static String getTestLinkBuild() {
    	String env_testlinkbuild=System.getProperty("BuildName");
    	String prop_testlinkbuild=String.valueOf(properties.getProperty("BuildName"));
    	if(env_testlinkbuild !=null)
    		return env_testlinkbuild;
    	else
    		return prop_testlinkbuild;
    }
    
    public static String getDataSheetFileName() {
    	String env_datasheetfilename=System.getProperty("datasheet.filename");
    	String prop_datasheetfilename=String.valueOf(properties.getProperty("datasheet.filename"));
    	if(env_datasheetfilename != null)
    		return env_datasheetfilename;
    	else
    		return prop_datasheetfilename;
    }
    
    public static String getLocatorSheetFileName() {
    	String env_locatorsheetfilename=System.getProperty("locator.filename");
    	String prop_locatorsheetfilename=String.valueOf(properties.getProperty("locator.filename"));
    	if(env_locatorsheetfilename != null)
    		return env_locatorsheetfilename;
    	else
    		return prop_locatorsheetfilename;
    }
    
    public static boolean isGridEnabled() {
        String env_GridEnabled = System.getProperty("grid.enabled");
        String prop_GridEnabled =
                String.valueOf(properties.getProperty("grid.enabled"));
        if (env_GridEnabled != null)
            return (env_GridEnabled.trim().equalsIgnoreCase("true")) ? true
                    : false;
        else if (prop_GridEnabled != null)
            return (prop_GridEnabled.trim().equalsIgnoreCase("true")) ? true
                    : false;
        else
            return false;
    }
    
    public static String propValue(String strkey){
        Properties props = new Properties();
        FileInputStream in = null; String locvalue="";
        try {
            in = new FileInputStream(EnvParameters.getTestRootDir()
                            + File.separator + "EnvParameters.properties");
            props.load(in);
            locvalue = props.getProperty(strkey);
        } catch (IOException e) {
            System.err.println("Failed to read: "
                    + "ObjectRepository.properties");
        }
        return locvalue;
     }
}
