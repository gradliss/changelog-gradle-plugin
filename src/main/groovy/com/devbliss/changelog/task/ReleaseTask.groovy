package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
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

    println "Add release to " + getFilename()
    releaseVersion = System.console().readLine Constants.RED + " Version: " + Constants.WHITE

    println Constants.RED + " New release version created" + Constants.RED_BOLD + " $releaseVersion" + Constants.WHITE

    def temp = changelogFile.text

    temp = temp.replaceFirst(Constants.regexVersionWithSuffix, releaseVersion)
    temp = temp.replaceFirst(Constants.regexVersionWithoutSuffix, releaseVersion)
    temp = temp.replaceFirst(Constants.regexChangeNameDate, Utility.getChangeFrom(today))

    changelogFile.delete()
    changelogFile = new File(getFilename())
    changelogFile << temp
  }
}

