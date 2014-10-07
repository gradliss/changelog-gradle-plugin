changelog-gradle-plugin
=======================

A plugin for the gradle build system to write changelogs painless


### Installation

Build script snippet for use in all Gradle versions:

```
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "com.devbliss:changelog-gradle-plugin:0.1.0"
  }
}
apply plugin: "com.devbliss.gradle.changelog"

```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:

```
plugins {
  id "com.devbliss.gradle.changelog" version "0.1.0"
}

```
### Configuration

The only thing you have to define in your build.gradle is the filename from you changelog. At this point we only support .md extension so that the plugin in can work correctly. 

```
changelog {
	filename = 'changelog.md'
}

```

### Usage

You have two tasks you can run via the gradle comman line **`gradle changelogRelease`** or **`gradle changelogSnapshot`**

The tasks are self explained if you run changeloRelease a new release version would be written to the chanlog file and if you use changelogSnapshot a new snapshot version would written.

### Local usage

First u have to install a gradle wrapper with:

```
gradle wrapper
```

After this u can use the gradle wrapper to install the jar to you local maven repository.

```
./gradlew install
```

Now it is installed too your maven repo.
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


