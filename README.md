# Appium Java Extensions


This project provides extensions for producing test reports for JUnit-based Appium tests in Visual Studio App Center. Visit our [docs](https://docs.microsoft.com/en-us/mobile-center/test-cloud/preparing-for-upload/appium) for instructions on how to convert and run your existing Appium test suite in App Center.

## Upload to Xamarin Test Cloud

The extension also works with Xamarin Test Cloud. Below are the upload instructions for Xamarin Test Cloud. Upload instructions for Visual Studio App Center can be found using the link above.

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


# Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
