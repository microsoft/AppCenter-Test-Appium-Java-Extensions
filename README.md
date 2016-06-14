# Test-Cloud Appium java extensions

Extension for producing test reports for JUnit based appium tests.

## Instructions

### 1. Replace your Appium driver with the Xamarin Test Cloud Appium driver

Step 1

Add the following dependency in you `pom.xml` file:

```xml
    <dependency>
        <groupId>com.xamarin.testcloud</groupId>
        <artifactId>appium</artifactId>
        <version>1.0</version>
    </dependency>
```

Step 2

Import these packages into your tests:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
```

Step 3

Insert these lines into each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

Step 4

Replace your _declaration_ of `AndroidDriver` with `EnhancedAndroidDriver` or `IOSDriver` with `EnhancedIOSDriver`

```java
    private static EnhancedAndroidDriver driver;
```

Step 5

Replace the way you _instantiate_ your driver, such that lines in the form of:

```java
    driver = new AndroidDriver<WebElement>(url, capabilities);
```

...becomes:

```java
    driver = Factory.createAndroidDriver(url, capabilities);
```


### 2. Prepare your workspace folder

Copy [this gist](https://gist.github.com/skovsboll/005db8653911349dc9a3062821d5348f) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

Then run

`mvn -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Xamarin Test Cloud.

### 3. Upload to Xamarin Test Cloud



