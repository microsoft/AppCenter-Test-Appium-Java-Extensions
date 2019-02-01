package com.microsoft.appcenter.appium;

public interface LabellingDriver {
    /**
     * Label a point of time in a test. Will insert a screenshot and label {@code label} into test report when running
     * in test-cloud.
     * @param label text to insert into report.
     */
    void label(String label);
}
