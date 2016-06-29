# Test-Cloud Appium java extensions

Extension for producing test reports for JUnit based appium tests.

## Running tests locally and in Xamarin Test Cloud

### 1. Replace your Appium driver with the EnhancedIOSDriver or EnhancedAndroidDriver

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

Using these drivers will still allow you to run your tests locally without modifications.

---



## Running tests in Xamarin Test Cloud


### 2. Prepare your workspace folder

Copy [this gist](https://gist.github.com/skovsboll/005db8653911349dc9a3062821d5348f/02be65561b830ea0e49adfc9ad7f76b39759cfd5) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

Copy
 [this other gist](https://gist.github.com/skovsboll/bd49d271662254dfc74efa4e6c6ad646) into your `pom.xml` inside the `<project>` tag.

Then run

`mvn -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Xamarin Test Cloud.

### 3. Upload to Xamarin Test Cloud

Install the special version of the xamarin-test-cloud gem to enable uploading appium tests:

`gem install xamarin-test-cloud-appium`

When ready upload, run:

```
XTC_ENDPOINT=https://testcloud.xamstage.com/ci test-cloud-appium submit \
    /path/to/app.apk <api-key> --devices <selection> --user <email> --workspace /path/to/target/upload --test-parameters pipeline:appium
```

Notice there are three differences from when uploading Calabash tests:

* Replace `test-cloud` with `test-cloud-appium`
* Add `--test-parameters pipeline:appium`
* XTC_ENDPOINT is given. This will make it run on the staging environment.


### 4. Current Limitations

* iOS tests do not work yet.
* Only one Appium version supported: v. 1.4. 
* Android 4.4+ only. Selendroid support is expected soon.
* Automating UIWebView is not supported.
* Tests that launch multiple apps or no apps are not currently supported. The test must launch precisely one app.


