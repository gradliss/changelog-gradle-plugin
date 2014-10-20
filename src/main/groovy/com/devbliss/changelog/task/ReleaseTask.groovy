package com.devbliss.changelog.task

import org.gradle.api.tasks.TaskAction

/**
 * The release task sets the latest snapshot version to the user given version number.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class ReleaseTask extends ChangelogTask {

  @TaskAction
  public void run() {
    super.run()
    def releaseVersion


    if(readVersionFromGradleProperties){
      releaseVersion = project.getRootProject().getVersion()

      //Check if version in gradle.properties contains SNAPSHOT if true then quit
      def isSnapshotVersion = releaseVersion.contains("-SNAPSHOT")
      if(isSnapshotVersion){
        println Constants.RED_BOLD + "You will create a RELEASE but you have defined a SNAPSHOT version in your gradle.properties" + Constants.NEWLINE
        println Constants.RED_BOLD + "Plugin will terminated." + Constants.RESET_COLOR_AND_STYLE
        return
      }

      println "Add release to " + getFilename()
      println Constants.RED + " New release version created from gradle.properties" + Constants.RED_BOLD + " $releaseVersion" + Constants.WHITE
    }else{

      println " Add release to " + getFilename()
      releaseVersion = System.console().readLine Constants.RED + " Version: " + Constants.WHITE
      println Constants.RED + " New release version created" + Constants.RED_BOLD + " $releaseVersion" + Constants.WHITE
    }

    def temp = changelogFile.text

    temp = temp.replaceFirst(Constants.regexVersionWithSuffix, releaseVersion)
    temp = temp.replaceFirst(Constants.regexVersionWithoutSuffix, releaseVersion)
    temp = temp.replaceFirst(Constants.regexChangeNameDate, Utility.getChangeFrom(today))

    changelogFile.delete()
    changelogFile = new File(getFilename())
    changelogFile << temp

    println Constants.RESET_COLOR_AND_STYLE
  }
}

