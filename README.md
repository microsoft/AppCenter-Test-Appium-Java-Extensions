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

This will make sure the enhanced Android and iOS drivers are avacompile time.

Step 2

Copy [this xml snippet](https://gist.github.com/skovsboll/bd49d271662254dfc74efa4e6c6ad646) into your `pom.xml` inside the `<project>` tag.

Step 3

Import these packages into your tests:

```java
import com.xamarin.testcloud.appium.Factory;
import com.xamarin.testcloud.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;
```

Step 4

Insert these lines into each of your test classes:

```java    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();
```

Step 5

Replace your _declaration_ of `AndroidDriver` with `EnhancedAndroidDriver` or `IOSDriver` with `EnhancedIOSDriver`

```java
    private static EnhancedAndroidDriver driver;
```

Step 6

Replace the way you _instantiate_ your driver, such that lines in the form of:

```java
    driver = new AndroidDriver<WebElement>(url, capabilities);
```

...becomes:

```java
    driver = Factory.createAndroidDriver(url, capabilities);
```

Using these drivers will still allow you to run your tests locally without modifications.


### 2. Prepare your workspace folder

Copy [this gist](https://gist.github.com/skovsboll/005db8653911349dc9a3062821d5348f/02be65561b830ea0e49adfc9ad7f76b39759cfd5) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

Then run

`mvn -DskipTests -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Xamarin Test Cloud.

### 3. Upload to Xamarin Test Cloud

Install the special version of the xamarin-test-cloud gem to enable uploading appium tests:

`gem install xamarin-test-cloud-appium`

When ready upload, run:

```
test-cloud-appium submit \
    /path/to/app.apk <api-key> --devices <selection> --user <email> --workspace /path/to/target/upload --test-parameters pipeline:appium
```

Notice there are three differences from when uploading Calabash tests:

* Replace `test-cloud` with `test-cloud-appium`
* Add `--test-parameters pipeline:appium`


### 4. Current Limitations

* iOS tests do not work yet.
* Only one Appium version supported: v. 1.4. 
* Android 4.2+ only. Selendroid support is expected soon.
* Automating UIWebView is not supported.
* Tests that launch multiple apps or no apps are not currently supported. The test must launch precisely one app.
* Performance data is not yet included in the test reports
* Devices known to cause trouble at this point:
    - HTC One M9 with Android 5.0.2
    - ASUS Google Nexus 7 (2012) with Android 4.3
    - Samsung Galaxy S6 Edge with Android 5.0.2
    - Samsung Galaxy A3 with Android 4.4.4
    - Samsung Galaxy Tab pro 10.1 - 4.4.2
    - Samsung Galaxy Note 3 (octo-core) - 4.4.2
    - samsung_galaxy_s_iii_(us_carrier)-4.3
    - samsung_galaxy_note_ii-4.4.2
    - samsung_galaxy_s4-4.3
    - samsung_galaxy_s_iii_(4g_lte)-4.3
    - samsung_galaxy_s_iii-4.3
    - samsung_galaxy_note_ii-4.3
    - samsung_galaxy_note_3_(quad-core)-4.3
    - asus_google_nexus_7_(2013)-4.3
    - htc_one_(m8)-5.0.1
    - samsung_galaxy_s5-4.4.2
    - asus_google_nexus_7_(2013)-5.0.2
 
    Please stay clear of these for now

Here's a list of devices that are well behaved at this point:

 *   htc_nexus_9-5.0.1
 *   lg_nexus_5-5.1.1
 *   lg_nexus_4-5.0.1
 *   lg_nexus_4-5.1.1
 *   asus_google_nexus_7_(2013)-5.1.1
 *   lava_pixel_v1-5.1
 *   lg_nexus_5-5.0.1
 *   lg_g4-5.1

All of these are in the device selection named `3d777c50`.


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

