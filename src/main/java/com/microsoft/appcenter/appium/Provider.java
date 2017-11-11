package com.microsoft.appcenter.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

interface Provider {
    Watcher createWatcher();

    EnhancedAndroidDriver createAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedAndroidDriver createAndroidDriver(Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(URL remoteAddress, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities);

    EnhancedIOSDriver createIOSDriver(Capabilities desiredCapabilities);

}
