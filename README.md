changelog-gradle-plugin
=======================

A plugin for the gradle build system to write changelogs painless

[ ![Download](https://api.bintray.com/packages/devbliss/gradle-plugins/changelog-gradle-plugin/images/download.svg) ](https://bintray.com/devbliss/gradle-plugins/changelog-gradle-plugin/_latestVersion)

### Installation

Build script snippet for use in all Gradle versions:

```
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "com.devbliss.changelog:changelog-gradle-plugin:1.2.0"
  }
}

apply plugin: "com.devbliss.changelog"

```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:

```
plugins {
  id "com.devbliss.changelog" version "1.2.0"
}

```
### Configuration

If you want to use this plugin you have nothing to configure. All options are optinal.

**Optional:**

- `filename` You can set the filename for your changelog file. Default filename is "changelog.md".

 - `snapshotWithTimestamp` You can define whether or not the snapshot version string contains a timestamp. By default the snapshot version does not contain a timestamp.
 
 - `readVersionFromGradleProperties` You can define if the version will be read from gradle.properties. If you set the option to true be sure you always set the version in the properties file correctly. Using this option in case of changelogSnapshotTask to ensure that "-SNAPSHOT" is appended to the version property in your gradle.properties E.g. version=0.1.0-SNAPSHOT. By default the version will not be read from the gradle.properties.

```
changelog {
	filename = 'myChangelogFile.md'				//default is changelog.md
	snapshotWithTimestamp = true				//default is false
	readVersionFromGradleProperties = true		//default is false
}

```
**Note:**

It is not required to add these lines **filename, snapshotWithTimestamp, readVersionFromGradleProperties** to the build.gradle file. The plugin set this automaticaly to false if you dont want to use this.

### Usage
You have two tasks which you can run via the gradle command line: 

```
gradle changelogRelease

```

```
gradle changelogSnapshot

```

The tasks are self explained if you run changelogRelease a new release version will be written to the changelog file and if you use changelogSnapshot a new snapshot version will be written.

### Local usage with this Repository

First u have to install a gradle wrapper with:

```
gradle wrapper
```

After this u can use the gradle wrapper to install the jar to you local maven repository.

```
./gradlew install
```

Now it is installed to your maven repo.
To use this plugin you have to use this snippet.

```
apply plugin: 'com.devbliss.changelog'

changelog {
  filename = 'changelog.md'
}

buildscript {
  repositories {
    mavenLocal()
  }
  dependencies { classpath "com.devbliss.changelog:changelog-gradle-plugin:1.2.0")
  }
}
```

After the defeniton of the task you can run **gradle changelogRelease** or **gradle changelogSnapshot**
and the command line will guide you to write the changelog.


### Requirements

Java SE Runtime Environment 8

Gradle 2.1

Plugin only support Markdown file extension


