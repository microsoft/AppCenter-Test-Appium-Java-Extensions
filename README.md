# Test-Cloud Appium java extensions

Extension for producing test reports for JUnit based appium tests.

Running your Appium tests on Xamarin Test Cloud gives you access to a very large and diverse set of Android and iOS devices. In this guide you’ll learn how to make the necessary changes in order to run your existing Appium test suite on in Xamarin Test Cloud.


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

This will make sure the enhanced Android and iOS drivers are available on compile time.

Step 2

Import these packages into your tests:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;
```

Step 3

Insert these lines into each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

Step 4

Replace your _declaration_ of `AndroidDriver<MobileElement>` with `EnhancedAndroidDriver<MobileElement>` or `IOSDriver<MobileElement>` with `EnhancedIOSDriver<MobileElement>`

```java
    private static EnhancedAndroidDriver<MobileElement> driver;
```

Step 5

Replace the way you _instantiate_ your driver, such that lines in the form of:

```java
    driver = new AndroidDriver<MobileElement>(url, capabilities);
```

...becomes:

```java
    driver = Factory.createAndroidDriver(url, capabilities);
```

Using these drivers will still allow you to run your tests locally without additional modifications, but enables you to "label" certain points in your test execution using `driver.label("text")`. The text and a screenshot from the device will be visible in test report in Xamarin Test Cloud. A recommended practice is to have a call to label in the `@After` method, this will include a screenshot of the app final state in the test report. The screenshot will be taken, even if a test is failing, and often provides valuable information to why does so. An example `@After` method for a test could look like this: 
```java
    @After
    public void TearDown(){
        driver.label("Stopping App");
        driver.quit();
    }
```

### 2. Prepare your workspace folder

Copy [this snippet](uploadprofilesnippet.txt) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

Then run

`mvn -DskipTests -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Xamarin Test Cloud.



### 3. Upload to Xamarin Test Cloud

If you have not done so already, install our command line interface by following [the installation instructions](https://github.com/xamarinhq/test-cloud-uploader#installation).

To upload a test:

```
xtc test /path/to/app <api-key> --devices <selection> --user <email> --workspace /path/to/target/upload 
```


### 4. Current Limitations

* No support for iOS 10
* No support for Android 7.0 (Nougat)
* No support for Android 4.2 or prior
* Maven version must be atleast 3.3.9
* Only one Appium version supported: v. 1.5. 
* JUnit 4.9 or newer 
* Automating browsers is not supported.
* Tests that launch multiple apps or no apps are not currently supported. The test must launch precisely one app.
* Performance data is not yet included in the test reports


### 5. Performance Troubleshooting

Tests run on devices in the cloud may execute slightly slower than on a local device under certain circumstances. Normally, this is outweighed by the fact that you have many more devices available and therefore potentially able to parallelize test runs.

There are two main sources of potential slow test runs. Re-signing and re-installation.

#### Re-signing (on iOS)

Before being installed on the iOS device, your app goes through a process called re-signing. This is necessary to make the provisioning profile match the device in the cloud. Re-signing does take some time, typically ~1-2 minutes. This does rarely cause performance degrades because re-signed apps are cached. The time consuming process will only run once per binary.

If you have a very automated Continuous Delivery setup where the IPA is having it’s version bumped before being built and tested, then the binary will be different for each test and the re-signing penalty will occur more often.

#### Re-installation

On a shared device cloud, it is very important for us to guarantee that devices are cleaned between each test. The next customer using the device may be someone from another organization.
Running tests locally does not inflict any penalty because the app mostly stays installed through all tests. In Xamarin Test Cloud the app is automatically uninstalled after each test. The next test will then have to re-install the app before running the test. This can slow down your tests in the cloud.
Luckily, there’s a solution. Instead of having the appium driver create a new session for each test case, just have one session and re-use it for all tests. To control the app state, make a call to driver.reset() before each test. This will only incur a 5 seconds delay between test cases. 

The way to code that, is to move the driver initialization code from the setUp method to the setUpClass method. 

