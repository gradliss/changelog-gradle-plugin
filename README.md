changelog-gradle-plugin
=======================

A plugin for the gradle build system to write changelogs painless


How to use
==========
Firt u have to install a gradle wrapper with:

```
gradle wrapper
```

After this u can use the gradle wrapper to install the jar to you local maven repository.

```
./gradlew install
```

Now it is installed to you maven repo.
To use this plugin you have to define task in your build script.

```
apply plugin: 'releasnote'

releaseNote {
  filename = 'changelog.md'
}

buildscript {
  repositories {
    mavenLocal()
  }
  dependencies { classpath "com.devbliss.releasenote:gradle-releasnote-plugin:0.1.0")
  }
}
```
After the defeniton of the task you can run

```
./gradlew releaseNote
```
and the command line will guide you to write the changelog.


