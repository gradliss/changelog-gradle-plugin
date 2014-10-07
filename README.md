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
    classpath "com.devbliss.changelog:changelog-gradle-plugin:1.0.0"
  }
}

apply plugin: "com.devbliss.changelog"

```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:

```
plugins {
  id "com.devbliss.changelog" version "1.0.0"
}

```
### Configuration

The only thing you have to define in your build.gradle is the filename of your changelog. At this point we only support .md extension so that the plugin can work correctly. 

```
changelog {
	filename = 'changelog.md'
}

```

### Usage
You have two tasks which you can run via the gradle command line **`gradle changelogRelease`** or **`gradle changelogSnapshot`**

The tasks are self explained if you run changelogRelease a new release version will be written to the changelog file and if you use changelogSnapshot a new snapshot version will be written.

### Local usage

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
apply plugin: 'changelog'

changelog {
  filename = 'changelog.md'
}

buildscript {
  repositories {
    mavenLocal()
  }
  dependencies { classpath "com.devbliss.changelog:changelog-gradle-plugin:0.1.0")
  }
}
```

After the defeniton of the task you can run **`gradle changelogRelease`** or **`gradle changelogSnapshot`**
and the command line will guide you to write the changelog.


