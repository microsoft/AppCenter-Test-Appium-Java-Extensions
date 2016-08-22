package com.xamarin.testcloud.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Factory for creating Watcher and Enhanced drivers
 */
public class Factory {
    private Factory() {
    }

    private static final Provider provider = createProvider();

    private static Provider createProvider() {
        Iterator<Provider> service = ServiceLoader.load(Provider.class).iterator();
        if(service.hasNext()) {
            return service.next();
        }
        return new LocalProvider();
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(URL, Capabilities)}
     * @param url url of the server
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(URL url, DesiredCapabilities desiredCapabilities) {
        return provider.createAndroidDriver(url, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(URL, HttpClient.Factory, Capabilities)}
     * @param url of the server
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(URL url, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(url, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(AppiumDriverLocalService, Capabilities)}
     * @param service local driver service
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(service, desiredCapabilities);

    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(AppiumDriverLocalService, HttpClient.Factory, Capabilities)}
     * @param service local driver service
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(service, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(AppiumServiceBuilder, Capabilities)}
     * @param builder service builder
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(builder, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(AppiumDriverLocalService, HttpClient.Factory, Capabilities)}
     * @param builder service builder
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(builder, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(URL, Capabilities)}
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced android driver, use in place of {@link io.appium.java_client.android.AndroidDriver#AndroidDriver(URL, Capabilities)}
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced Android driver
     */
    public static <T extends WebElement> EnhancedAndroidDriver<T> createAndroidDriver(Capabilities desiredCapabilities) {
        return provider.createAndroidDriver(desiredCapabilities);
    }
    
    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(URL, HttpClient.Factory, Capabilities)}
     * @param url of the server
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(URL url, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(url, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(AppiumDriverLocalService, Capabilities)}
     * @param service local driver service
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(service, desiredCapabilities);

    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(AppiumDriverLocalService, HttpClient.Factory, Capabilities)}
     * @param service local driver service
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(service, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(AppiumServiceBuilder, Capabilities)}
     * @param builder service builder
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(builder, desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(AppiumDriverLocalService, HttpClient.Factory, Capabilities)}
     * @param builder service builder
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(builder, httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(URL, Capabilities)}
     * @param httpClientFactory http client factory
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        return provider.createIOSDriver(httpClientFactory, desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver, use in place of {@link io.appium.java_client.ios.IOSDriver#IOSDriver(URL, Capabilities)}
     * @param desiredCapabilities desired capabilities for the session
     * @return enhanced IOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(Capabilities desiredCapabilities) {
        return provider.createIOSDriver(desiredCapabilities);
    }

    /**
     * Create enhanced iOS driver
     * @param url url of the server
     * @param capabilities desired capabilities for the session
     * @return enhanced iOS driver
     */
    public static <T extends WebElement> EnhancedIOSDriver<T> createIOSDriver(URL url, DesiredCapabilities capabilities) {
        return provider.createIOSDriver(url, capabilities);
    }

    /**
     * Create watcher for JUnit
     * @return a watcher for use with JUnit
     */
    public static TestWatcher createWatcher() {
        return provider.createWatcher();
    }

}
