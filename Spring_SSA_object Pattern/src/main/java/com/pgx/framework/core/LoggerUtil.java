package com.pgx.framework.core;

import static com.pgx.framework.core.MainReporting.strReportFilePath;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {

    static Logger logger = Logger.getLogger(LoggerUtil.class);
    /*
     * This method checks the level of logging required from evn property file and returns the same
     */
    //static DailyRollingFileAppender loggerAppender=(DailyRollingFileAppender) logger.getAppender("FileAppender");
    public void updateLog4jConfiguration(String logFile) { 
       Properties props = new Properties(); 
       try { 
           InputStream configStream = getClass().getResourceAsStream( "/log4j.properties"); 
           props.load(configStream); 
           configStream.close(); 
       } catch (IOException e) { 
           System.out.println("Error: Cannot load configuration file "); 
       } 
       props.setProperty("log4j.appender.FileAppender.File", logFile); 
       LogManager.resetConfiguration(); 
       PropertyConfigurator.configure(props); 
    }
    public static Level getLogLevel() {
        //loggerAppender.setFile(MainReporting.strReportFilePath);
        //loggerAppender.activateOptions(); 
        String _level = EnvParameters.getLoggerLevel();
        if (_level.equalsIgnoreCase("debug"))
            return Level.DEBUG;
        else
            return Level.INFO;
    }
    /*
     * This is to log the results in the log file
     */
    public static void log(String message) {
        logger.info(message);
        Reporter.log(message + "<br/>", false);
    }
    /*
     * This method is to log various kind of logs in the log file
     */
    public static void log(String message, Level lv) {
        Level _lv = getLogLevel();
        if (_lv.toInt() <= lv.toInt()) {
            if (lv == Level.INFO) {
                logger.info(message);
                Reporter.log(message + "<br/>", false);
            }
            if (lv == Level.DEBUG) {
                logger.debug(message);
                Reporter.log(message + "<br/>", false);
            }
            if (lv == Level.ERROR) {
                logger.error(message);
                Reporter.log(message + "<br/>", false);
            }
        }
    }
    /*
     * This method logs the error messages in different levels
     */
     public static void log(String message, String lv) {
        if (lv.equalsIgnoreCase("debug")) {
            log(message, Level.DEBUG);
        } else if (lv.equalsIgnoreCase("error")) {
            log(message, Level.ERROR);
        } else {
            log(message, Level.INFO);
        }
     }
}
