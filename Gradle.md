*** not complete yet ***

# Setup for Gradle Users
The following steps are for users whose project is configured to use the Gradle build system, for example projects built using Android Studio. This approach requires you to move your UI test package from your Android project and into a new Maven-based project in order to be compatible with Test Cloud. We recommend using IntelliJ or any other IDE which supports the Maven build system.

### 2. Create the new Maven project

**Step 1 - Download and edit the sample pom.xml**
Download [this file](https://raw.githubusercontent.com/xamarinhq/test-cloud-appium-java-extensions/master/sample-files/gradle/pom.xml) which will serve as the project's configuration. Update the values for `groupId`, `artifactId`, and `versionId` (lines 7, 8, and 9) to match your organization and project. If you are new to Maven and want to learn more about these values, you can [read more  here](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html).

**Step 2 - Create the project using the pom.xml**
Create a new Maven project using an IDE such as IntelliJ. Because you will be replacing the contents of the generated `pom.xml` with the file you modified in step 1, the values for `groudId`, `artifactId`, and `versionId` are not important when creating the project.

After you have created the project, replace the contents of the generated `pom.xml` with the one you created in step 1. Remember you will need to run an import once you have made this change. Once you've finished importing, open the External Libraries folder and verify you see `com.xamarin.testcloud:appium:1.0`.

**Step 3 - Move your test package to the Maven project**
Copy your test package from your project and place it into `/src/test/java` within the newly created Maven project. Open at least one test class to verify that all of the imports are working as expected.

## 2. Prepare your workspace folder

Copy [this snippet](uploadprofilesnippet.txt) into your `pom.xml` in the `<profiles>` tag. If there's no `<profiles>` section in your pom, make one.

Then run

`mvn -DskipTests -P prepare-for-upload package` 

This will pack your test classes and all dependencies into the `target/upload` folder, ready to be uploaded to Test Cloud.

