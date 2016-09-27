# Changes to the gradle build script

The following steps are for users whose project is configured to use the Gradle build system, for example projects built using Android Studio. This approach automatically generates a `pom.xml` file that are compatible with default gradle file locations. The `pom.xml` file is minimal and can only be used for packaging the needed files for uploading. We recommend using IntelliJ or any other IDE which supports the Maven build system.

Add the following snippet to the `build.gradle in the 'app' folder:

```gradle 
apply plugin: 'maven'

task createPom {
    pom {
        withXml {
            def dependenciesNode = asNode().appendNode('dependencies')

            //Iterate over the compile dependencies (we don't want the test ones), adding a <dependency> node for each
            configurations.testCompile.allDependencies.each {
                def dependencyNode = dependenciesNode.appendNode('dependency')
                dependencyNode.appendNode('groupId', it.group)
                dependencyNode.appendNode('artifactId, it.name)
                dependencyNode.appendNode('version', it.version)
            }

            def profilesNode = asNode().appendNode('profiles')
            profilesNode.append(new XmlParser().parse('https://raw.githubusercontent.com/xamarinhq/test-cloud-appium-java-extensions/master/gradleuploadprofilesnippet.xml'))
        }
    }.writeTo("pom.xml")
```

Building the gradle project will compile the test classes and generate a `pom.xml` file in the `app` folder. With the changes to the gradle build script files in place please follow [the main guide from section 2 and forward](../master/README.md##2-changes-to-the-tests) 
