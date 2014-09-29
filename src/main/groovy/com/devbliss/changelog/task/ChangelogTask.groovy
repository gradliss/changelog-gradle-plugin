package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ChangelogTask extends DefaultTask{

  def filename

  @TaskAction
  public void run() {

    def releaseVersion
    def snapshotVersion
    def branch = Information.getGitBranch()
    def userName = Information.getGitUsername()
    def email = Information.getGitEmail()
    def today = new Date()
    def loopInput = true
    def isNewRelease

    //Check if filename is defined in build.gradle
    if (getFilename() == null){
      Information.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    def changelogFile = Information.readFilenAndShow(getFilename())

    println Utility.RED_BOLD + " -- Now write to your changelog -- " + Utility.NORMAL

    //Loop so long until the input is "y" or "n"
    while(loopInput){
      isNewRelease = System.console().readLine Utility.NEWLINE + Utility.RED + " Is this a new release Version? (y/n): " + Utility.WHITE
      if(isNewRelease == "y" || isNewRelease == "n"){
        loopInput = false
      }
    }

    if (isNewRelease == "y") {
      releaseVersion = System.console().readLine Utility.RED + " Version: " + Utility.WHITE
    } else if (isNewRelease == "n"){
      println Utility.RED + " New snapshot version created"
      def changelogToString = changelogFile.text
      def versionNumber = changelogToString.find(Utility.regexVersionWithoutSuffix)
      snapshotVersion = versionNumber + "-SNAPSHOT-" + new Date().time

    } else {
      println "SOMETHING GOES WRONG STOP PLUGIN"
      return
    }

    //Remove line breaks
    def changeFrom = "Last change from: $userName $email $today"
    changeFrom = changeFrom.replace("\r", "").replace("\n", "")

    //Ask user is everything ok
    def change = System.console().readLine Utility.RED + " Change:" + Utility.WHITE + " [$branch] "
    def changeIsOk = System.console().readLine Utility.RED + " Is everything ok? (y/n): " + Utility.WHITE

    if (changeIsOk == "y") {
      def temp = changelogFile.text
      if (isNewRelease == "y") {
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << Utility.NEWLINE
        changelogFile << "### Version " + releaseVersion
        changelogFile << Utility.NEWLINE + "- [$branch] " + change + Utility.NEWLINE
        changelogFile << "---" + Utility.NEWLINE
        changelogFile << changeFrom + Utility.NEWLINE
        changelogFile << temp
      } else if (isNewRelease == "n") {
        temp = temp.replaceFirst(Utility.regexVersionWithSuffix, snapshotVersion)
        temp = temp.replaceFirst(Utility.regexVersionWithoutSuffix, snapshotVersion)
        def oldChanges = temp.find(Utility.regexText)
        def newstuff = "- [$branch] " + change + Utility.NEWLINE + oldChanges + Utility.NEWLINE
        temp = temp.replaceFirst(Utility.regexText, newstuff)
        temp = temp.replaceFirst(Utility.regexChangeNameDate, changeFrom)
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << temp
      }
    } else if (changeIsOk == "n") {
      Information.secondChance()
      run()
    } else {
      Information.changelogNotWritten()
    }
  }
}
