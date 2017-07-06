# Test-Cloud Appium Java Extensions

This project provides extensions for producing test reports for JUnit-based Appium tests in Test Cloud. Test Cloud offers access to a very large and diverse set of Android and iOS devices.

In this guide, you’ll learn how to make the changes necessary to run your existing Appium test suite in Test Cloud. As of this writing, Test Cloud uses the [Maven](https://maven.apache.org/) build system to prepare your test assets for upload and execute the uploaded tests.

There's also a [guide for Gradle users](Gradle.md).

## 1. Changes to the build system

### Step 1 - Add dependency

Add the following dependency in your `pom.xml` file:

```xml
<dependency>
    <groupId>com.xamarin.testcloud</groupId>
    <artifactId>appium</artifactId>
    <version>1.0</version>
</dependency>
```

This will ensure the enhanced Android and iOS drivers are available at compile time. The enhanced drivers are provided primarily to enable the `label` feature. See Step 4 for more detail on the `label` feature.

### Step 2 - Add upload profile

Copy [this snippet](uploadprofilesnippet.xml) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

The profile, when activated, will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Test Cloud.

## 2. Changes to the tests

### Step 1 - Add imports

Import these packages into your test classes:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;
```

### Step 2 - Instantiate the TestWatcher

Insert this declaration in each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

### Step 3 - Update your driver declaration  

Replace your _declaration_ of `AndroidDriver<MobileElement>` with `EnhancedAndroidDriver<MobileElement>` or `IOSDriver<MobileElement>` with `EnhancedIOSDriver<MobileElement>`

```java
    private static EnhancedAndroidDriver<MobileElement> driver;
```

### Step 4 - Update your driver instantiations 

Replace the way you _instantiate_ your driver, such that lines in the form of:

```java
    driver = new AndroidDriver<MobileElement>(url, capabilities);
```

...become:

```java
    driver = Factory.createAndroidDriver(url, capabilities);
```

Using these drivers will still allow you to run your tests locally without additional modifications, but enables you to "label" test steps in your test execution using `driver.label("text")`. The text and a screenshot from the device will be visible in test report in  Test Cloud. 

A recommended practice is to have a call to label in the `@After` method, this will include a screenshot of the app final state in the test report. The screenshot will be taken, even if a test is failing, and often provides valuable information as to why it does so. An example `@After` method for a test could look like this: 

```java
    @After
    public void TearDown(){
        driver.label("Stopping App");
        driver.quit();
    }
```

## 3. Upload to Test Cloud

If you have not done so already, install our command line interface by following [the installation instructions](UploaderInstall.md/#installation).

*Note: If you are an existing Test Cloud user currently using the command line tools for Calabash or UITest, you will need to install this new tool.*

If you do not have an existing device key ready, you can generate one by following the *new test run* dialog in [Test Cloud](https://testcloud.xamarin.com). On the final screen, extract only the device key from the generated command.

Steps to upload a test:

Pack your test classes and all dependencies into the `target/upload` folder:

```
mvn -DskipTests -P prepare-for-upload package
```

Perform upload:

```
xtc test /path/to/app <api-key> --devices <selection> --user <email> --workspace target/upload 
```
*Note: If you are having trouble targeting the `xtc` command, try executing with the fully qualified path to the package.*

*Note: For Android apps, ensure your app was not built with Instant Run enabled as this will cause failures in Test Cloud.*

## 4. Current Limitations

* No support for TestNG
* No support for Android 4.2 or prior
* Maven version must be atleast 3.3.9
* Support for Appium version 1.6.4 only 
* JUnit 4.9 or newer 
* Automating browsers (web testing), including hybrid apps, are not supported.
* Tests that launch multiple apps or no apps are not currently supported. The test must launch precisely one app.

## 5. Performance Troubleshooting

Tests run on devices in the cloud may execute slightly slower than on a local device under certain circumstances. Normally, this is outweighed by the fact that you have many more devices available and therefore potentially able to parallelize test runs.

There are two main sources of potential slow test runs: re-signing and re-installation.

#### Re-signing (on iOS)

Before being installed on the iOS device, your app goes through a process called re-signing. This is necessary to make the provisioning profile match the device in the cloud. Re-signing does take some time, typically ~1-2 minutes. This does rarely cause performance degrades because re-signed apps are cached. The time consuming process will only run once per binary.

If you have a very automated Continuous Delivery setup where the IPA is having it’s version bumped before being built and tested, then the binary will be different for each test and the re-signing penalty will occur more often.

#### Re-installation

On a shared device cloud, it is very important for us to guarantee that devices are cleaned between each test. The next customer using the device may be someone from another organization.
Running tests locally does not inflict any penalty because the app mostly stays installed through all tests. In Test Cloud, the app is automatically uninstalled after each test. The next test will then have to re-install the app before running the test. This can slow down your tests in the cloud.
Luckily, there’s a solution. Instead of having the appium driver create a new session for each test case, just have one session and re-use it for all tests. To control the app state, make a call to `driver.resetApp()` before each test. This will only incur a 5-second delay between test cases. You can implement this by moving the driver initialization code from the `setUp` method to the `setUpClass` method. 
