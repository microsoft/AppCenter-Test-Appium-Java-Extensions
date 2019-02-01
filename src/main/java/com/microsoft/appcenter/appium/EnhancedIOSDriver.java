package com.microsoft.appcenter.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import java.util.List;

public class EnhancedIOSDriver<T extends WebElement> extends io.appium.java_client.ios.IOSDriver<WebElement> implements LabellingDriver {

    protected final EventReporter eventReporter;

    EnhancedIOSDriver(URL remoteAddress, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(remoteAddress, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(service, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(service, httpClientFactory, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(builder, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(builder, httpClientFactory, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(httpClientFactory, desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    EnhancedIOSDriver(Capabilities desiredCapabilities, EventReporter eventReporter) {
        super(desiredCapabilities);
        this.eventReporter = eventReporter;
    }

    /**
     * Get screenshot from device. Will store a copy of the screenshot in parent working directory and in test-cloud
     * it will be inserted into test report.
     * @param outputType output format of screenshot
     * @param <X> output format of screenshot
     * @return screenshot
     * @throws WebDriverException if an error occurs
     * @see #label(String) for the perfered way to insert screenshots in test reports.
     */
    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        Object value = execute(DriverCommand.SCREENSHOT).getValue();
        return DriverHelper.getScreenshotToWorkspace(value, outputType, path ->
                eventReporter.reportScreenshot(path.toAbsolutePath().toString(), 0, false));
    }

    @Override
    public void label(String label) {
        DriverHelper.getScreenshotToWorkspace(execute(DriverCommand.SCREENSHOT).getValue(), OutputType.FILE, path ->
                eventReporter.reportLabel(DriverHelper.sanitize(label), path.toAbsolutePath().toString(), 0, false));
    }
}
