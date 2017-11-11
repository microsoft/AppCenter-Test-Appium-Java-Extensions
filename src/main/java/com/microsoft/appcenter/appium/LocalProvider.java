package com.microsoft.appcenter.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

class LocalProvider implements Provider {
    private static final EventReporter eventReporter = new StdOutEventReporter();

    @Override
    public Watcher createWatcher() {
        return new Watcher(eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(remoteAddress, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(remoteAddress, httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(service, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(service, httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(builder, desiredCapabilities, eventReporter);    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(builder, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedAndroidDriver createAndroidDriver(Capabilities desiredCapabilities) {
        return new EnhancedAndroidDriver(desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(remoteAddress, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(remoteAddress, httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(service, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(service, httpClientFactory, desiredCapabilities, eventReporter);
    }

    @Override
    public EnhancedIOSDriver createIOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(builder, desiredCapabilities, eventReporter);    }

    @Override
    public EnhancedIOSDriver createIOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return new EnhancedIOSDriver(builder, desiredCapabilities, eventReporter);
    }
}
