package com.pgx.framework.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

/**
 * @author Suchit biswal
 * 
 */
public class RunTestNGSuiteMain {
    /*
     * This method is to run the testng suite file from Command prompt
     */
    public static void runSuite(String args[]) {
        String sApplicationDir = System.getProperty("user.dir");
        List<String> XMLfiles = new ArrayList<String>();
        String sXMLPath;
        TestNG testng = new TestNG();
        if (sApplicationDir.equals("")) {
            System.err.println("***ERROR: Not able to get current working directory please investigate");
        }
        if (args.length <= 0) {
            System.err.println("***ERROR: No parameters passed Please pass the parameters");
        }
        String agr1 = null;
        String arg2 = null;
        String arg3 = null;

        Boolean btestNGxml = false;
        @SuppressWarnings("unused")
        Boolean bProperties = false;
        Boolean bGroup = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-PROP")) {
                agr1 = args[i + 1];
                LoggerUtil.log("Custom location of propertiesfile is provided");
                System.setProperty("customProperties", agr1);
                bGroup = true;
            } else if (args[i].equalsIgnoreCase("-SUIT")) {
                LoggerUtil.log("Custom location of Suite.xml is provided");
                arg2 = args[i + 1];
                System.out.println(arg2);
                sXMLPath = arg2;
                XMLfiles.add(sXMLPath);
                testng.setTestSuites(XMLfiles);
                btestNGxml = true;
            } else if (args[i].equalsIgnoreCase("-GROUP")) {
                LoggerUtil.log("testng Groups provided for group execution");
                arg3 = args[i + 1];
                testng.setGroups(arg3);
                bGroup = true;
            } else {
                if (i + 1 >= args.length) {
                    if (args[0] == null) {
                        LoggerUtil.log("Provide the testng suite xml file as a commandline parameter");
                    }
                    if (btestNGxml == false) {
                        sXMLPath =
                                System.getProperty("user.dir")
                                        + File.separatorChar + args[0];
                        XMLfiles.add(sXMLPath);
                        testng.setTestSuites(XMLfiles);
                    }

                    if (bGroup == false) {
                        if (args.length > 2) {
                            testng.setGroups(args[2]);
                        }
                    }
                }
            }
        }
        testng.setOutputDirectory(System.getProperty("user.dir")
                + File.separatorChar + "target");
        testng.run();
    }
}
