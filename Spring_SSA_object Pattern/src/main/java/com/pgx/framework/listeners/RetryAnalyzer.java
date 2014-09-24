package com.pgx.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.pgx.framework.core.EnvParameters;

/*
 * This class is for retrying the falied test cases with the specified no.of times
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    // Set this to twice the number of times to retry
    private int remainingRetries = Integer.parseInt(EnvParameters.getPropertyValue("retry.attempts")) * 2;

    @Override
    public boolean retry(ITestResult result) {
        if (remainingRetries > 0) {
            result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
            remainingRetries = remainingRetries - 1;
            return true;
        } else {
            return false;
        }
    }
}
