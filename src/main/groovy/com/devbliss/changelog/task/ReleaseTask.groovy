package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 * 
 * @version 0.0.1
 */

class ReleaseTask extends DefaultTask{

  def filename = project.changelog.filename

  @TaskAction
  public void run() {

    def releaseVersion
    def userName = Information.getGitUsername()
    def email = Information.getGitEmail()
    def today = new Date()

    //Check if filename is defined in build.gradle
    if (getFilename() == null){
      Information.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    def changelogFile = Information.readFileAndShow(getFilename())

    println "RELEASE" + getFilename()
    releaseVersion = System.console().readLine Utility.RED + " Version: " + Utility.WHITE

    //Remove line breaks
    def changeFrom = "-- Last change from: $userName $email $today"
    changeFrom = changeFrom.replace("\r", "").replace("\n", "")

    def temp = changelogFile.text

    temp = temp.replaceFirst(Utility.regexVersionWithSuffix, releaseVersion)
    temp = temp.replaceFirst(Utility.regexVersionWithoutSuffix, releaseVersion)
    temp = temp.replaceFirst(Utility.regexChangeNameDate, changeFrom + Utility.NEWLINE)

    changelogFile.delete()
    changelogFile = new File(getFilename())
    changelogFile << temp
  }
}

