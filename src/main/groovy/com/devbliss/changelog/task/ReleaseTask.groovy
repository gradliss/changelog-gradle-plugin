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
    releaseVersion = System.console().readLine Utility.RED + " Version: " + Utility.WHITE

    println Utility.RED + " New release version created" + Utility.RED_BOLD + " $releaseVersion" + Utility.WHITE

    def temp = changelogFile.text

    temp = temp.replaceFirst(Utility.regexVersionWithSuffix, releaseVersion)
    temp = temp.replaceFirst(Utility.regexVersionWithoutSuffix, releaseVersion)
    temp = temp.replaceFirst(Utility.regexChangeNameDate, Information.getChangeFrom(today))

    changelogFile.delete()
    changelogFile = new File(getFilename())
    changelogFile << temp
  }
}

