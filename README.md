# Appium Java Extensions


This project provides extensions for producing test eports for JUnit-based Appium tests in Visual Studio App Center. Visit our [docs](https://docs.microsoft.com/en-us/mobile-center/test-cloud/preparing-for-upload/appium) for instructions on how to convert and run your existing Appium test suite in App Center.

## Upload to Xamarin Test Cloud

The extension also works with Xamarin Test Cloud. Below are the upload instructions for Xamarin Test Cloud. Upload instructions for Visual Studio App Center can be found using the link above.

## Upload to Xamarin Test Cloud

If you have not done so already, install our command line interface by following [the installation instructions](XamarinTestCloudUploaderInstall.md/#installation).

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
