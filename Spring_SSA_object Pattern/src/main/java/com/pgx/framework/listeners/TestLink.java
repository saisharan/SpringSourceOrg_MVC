package com.pgx.framework.listeners;

import com.pgx.framework.core.EnvParameters;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;

    /*
     * This calss is used for integrating test result with testlink
     */
    public class TestLink {
             public static final String DEV_KEY = EnvParameters.getTestLinkDevKey();
                // Substitute your Server URL Here
                public static final String SERVER_URL = EnvParameters.getTestLinkServerName();
               // Substitute your project name Here
                public static final String PROJECT_NAME = EnvParameters.getTestLinkProject();
                // Substitute your test plan Here
                public static final String PLAN_NAME = EnvParameters.getTestLinkRelease();
                // Substitute your build name
                public static final String BUILD_NAME = EnvParameters.getTestLinkBuild();

            public static void updateResult(String testCase, String exception, String result)    {
                TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEV_KEY,SERVER_URL);
                    try {
                            testlinkAPIClient.reportTestCaseResult(PROJECT_NAME, PLAN_NAME,testCase, BUILD_NAME, exception, result);
                    } catch (TestLinkAPIException e) {
                            e.printStackTrace();
                    }
            }
    }
