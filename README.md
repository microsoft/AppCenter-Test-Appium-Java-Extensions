# Test-Cloud Appium Java Extensions

This project provides extensions for producing test reports for JUnit-based Appium tests in Test Cloud. Test Cloud offers access to a very large and diverse set of Android and iOS devices.

In this guide, you’ll learn how to make the changes necessary to run your existing Appium test suite in Test Cloud. As of this writing, Test Cloud uses the Maven build system to prepare your test assets for upload and execute the uploaded tests. In an effort to provide access to more users faster, we have provided a process for Gradle users who are willing to move their test package into a new Maven project.

## 1a. Setup for Maven Users
The following steps are for users whose project is configured to use the Maven build system.

### 1. Replace your Appium driver with the EnhancedIOSDriver or EnhancedAndroidDriver

**Step 1 - Update your pom.xml**

Add the following dependency in your `pom.xml` file:

```xml
<dependency>
    <groupId>com.xamarin.testcloud</groupId>
    <artifactId>appium</artifactId>
    <version>1.0</version>
</dependency>
```

Copy [this snippet](uploadprofilesnippet.txt) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

This will ensure the enhanced Android and iOS drivers are available at compile time. The ehanced drivers are provided primarily to enable the *label* feature. See Step 5 for more detail on the *label* feature.

**Step 2 - Add dependencies**

Import these packages into your test classes:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;
```

**Step 3 - Instantiate the TestWatcher**

Insert these lines at the beginning of each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

**Step 4 - Update your driver declerations**

Replace your _declaration_ of `AndroidDriver<MobileElement>` with `EnhancedAndroidDriver<MobileElement>` or `IOSDriver<MobileElement>` with `EnhancedIOSDriver<MobileElement>`

```java
    private static EnhancedAndroidDriver<MobileElement> driver;
```

**Step 5 - Update your driver instantiations**

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

## 1b. Setup for Gradle Users
The following steps are for users whose project is configured to use the Gradle build system, for example projects built using Android Studio. This approach requires you to move your UI test package from your Android project and into a new Maven-based project in order to be compatible with Test Cloud. We recommend using IntelliJ or any other IDE which supports the Maven build system.

### 1. Replace your Appium driver with the EnhancedIOSDriver or EnhancedAndroidDriver in your existing project

**Step 1 - Add dependencies**

Import these packages into your test classes:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;
```

**Step 2 - Instantiate the TestWatcher**

Insert these lines at the beginning of each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

**Step 3 - Update your driver declerations**

Replace your _declaration_ of `AndroidDriver<MobileElement>` with `EnhancedAndroidDriver<MobileElement>` or `IOSDriver<MobileElement>` with `EnhancedIOSDriver<MobileElement>`

```java
    private static EnhancedAndroidDriver<MobileElement> driver;
```

**Step 4 - Update your driver instantiations**

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

### 2. Create the new Maven project

**Step 1 - Download and edit the sample pom.xml**
Download [this file](https://raw.githubusercontent.com/xamarinhq/test-cloud-appium-java-extensions/master/sample-files/gradle/pom.xml) which will serve as the project's configuration. Update the values for `groupId`, `artifactId`, and `versionId` (lines 7, 8, and 9) to match your organization and project. If you are new to Maven and want to learn more about these values, you can [read more  here](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html).

**Step 2 - Create the project using the pom.xml**
Create a new Maven project using an IDE such as IntelliJ. Because you will be replacing the contents of the generated `pom.xml` with the file you modified in step 1, the values for `groudId`, `artifactId`, and `versionId` are not important when creating the project.

After you have created the project, replace the contents of the generated `pom.xml` with the one you created in step 1. Remember you will need to run an import once you have made this change. Once you've finished importing, open the External Libraries folder and verify you see `com.xamarin.testcloud:appium:1.0`.

**Step 3 - Move your test package to the Maven project**
Copy your test package from your project and place it into `/src/test/java` within the newly created Maven project. Open at least one test class to verify that all of the imports are working as expected.

## 2. Prepare your workspace folder

From the root of your project directory, run

`mvn -D skipTests -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Test Cloud.

## 3. Upload to Test Cloud

If you have not done so already, install our command line interface by following [the installation instructions](https://github.com/xamarinhq/test-cloud-uploader#installation).

*Note: If you are an existing Test Cloud user currently using the command line tools for Calabash or UITest, you will need to install this new tool.*

If you do not have an existing device key ready, you can generate one by following the *new test run* dialog in [Test Cloud](https://testcloud.xamarin.com). On the final screen, extract only the device key from the generated command.

To upload a test:

```
xtc test /path/to/app <api-key> --devices <selection> --user <email> --workspace /path/to/target/upload 
```
*Note: If you are having trouble targeting the `xtc` command, try executing with the fully qualified path to the package.*

*Note: For Android apps, ensure your app was not built with Instant Run enabled as this will cause failures in Test Cloud.*



## 4. Current Limitations

* No support for TestNG
* No support for iOS 10
* No support for Android 7.0 (Nougat)
* No support for Android 4.2 or prior
* Maven version must be atleast 3.3.9
* Support for Appium version 1.5 only 
* JUnit 4.9 or newer 
* Automating browsers (web testing) is not supported.
* Tests that launch multiple apps or no apps are not currently supported. The test must launch precisely one app.
* Performance data is not yet included in the test reports


## 5. Performance Troubleshooting

Tests run on devices in the cloud may execute slightly slower than on a local device under certain circumstances. Normally, this is outweighed by the fact that you have many more devices available and therefore potentially able to parallelize test runs.

There are two main sources of potential slow test runs: re-signing and re-installation.

#### Re-signing (on iOS)

Before being installed on the iOS device, your app goes through a process called re-signing. This is necessary to make the provisioning profile match the device in the cloud. Re-signing does take some time, typically ~1-2 minutes. This does rarely cause performance degrades because re-signed apps are cached. The time consuming process will only run once per binary.

If you have a very automated Continuous Delivery setup where the IPA is having it’s version bumped before being built and tested, then the binary will be different for each test and the re-signing penalty will occur more often.

#### Re-installation

On a shared device cloud, it is very important for us to guarantee that devices are cleaned between each test. The next customer using the device may be someone from another organization.
Running tests locally does not inflict any penalty because the app mostly stays installed through all tests. In Test Cloud, the app is automatically uninstalled after each test. The next test will then have to re-install the app before running the test. This can slow down your tests in the cloud.
Luckily, there’s a solution. Instead of having the appium driver create a new session for each test case, just have one session and re-use it for all tests. To control the app state, make a call to driver.reset() before each test. This will only incur a 5-second delay between test cases. You can implement this by moving the driver initialization code from the `setUp` method to the `setUpClass` method. 

