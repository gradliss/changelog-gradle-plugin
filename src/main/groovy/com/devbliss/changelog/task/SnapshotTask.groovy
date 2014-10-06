package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * This class defines the changelogSnapshot task.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 * 
 * @version 0.0.1
 */

class SnapshotTask extends ChangelogTask{

  def filename = project.changelog.filename

  @TaskAction
  public void run() {
    super.run()
    def snapshotVersion
    //def branch = Information.getGitBranch()
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

    println "Add Snapshot to "+ getFilename()
    def changelogToString = changelogFile.text
    def versionLine = changelogToString.find(Utility.regexVersionWithoutSuffix)
    def isAlreadySnapshotVersion = versionLine.contains("-SNAPSHOT-")
    def versionNumber = versionLine.find(Utility.regexVersionNumber)

    // handle version line and increment version number for a new snapshot version
    if (isAlreadySnapshotVersion) {
      snapshotVersion = versionLine.replaceFirst(versionLine, "$versionNumber-SNAPSHOT-" + today.time)
    } else {
      // extract minor version number in order to increment it.
      // only works for the version number layout we use at the moment: e.g. --> 0.0.0
      def major = versionNumber.subSequence(0, 1)
      def minor = versionNumber.subSequence(2, 3) as int
      minor++
      def incrementedVersionNumber = major + "." + minor + ".0"

      snapshotVersion = versionLine.replaceFirst(versionLine, "$incrementedVersionNumber-SNAPSHOT-" + today.time)
    }

    println Utility.RED + " New snapshot version created" + Utility.RED_BOLD + " $snapshotVersion"

    //Remove line breaks
    def changeFrom = "-- Last change from: $userName $email $today"
    changeFrom = changeFrom.replace("\r", "").replace("\n", "")
    def change = System.console().readLine Utility.RED + " Change:" + Utility.WHITE + " [$branch] "

    def temp = changelogFile.text
    if (isAlreadySnapshotVersion) {
      temp = temp.replaceFirst(Utility.regexVersionWithSuffix, snapshotVersion)
      def oldChanges = temp.find(Utility.regexText)
      def newstuff = " - [$branch] " + change + Utility.NEWLINE + oldChanges// + Utility.NEWLINE
      temp = temp.replaceFirst(Utility.regexText, newstuff)
      temp = temp.replaceFirst(Utility.regexChangeNameDate, changeFrom)
      changelogFile.delete()
      changelogFile = new File(getFilename())
      changelogFile << temp
    } else {
      changelogFile.delete()
      changelogFile = new File(getFilename())
      changelogFile << Utility.NEWLINE + "### Version $snapshotVersion" + Utility.NEWLINE
      changelogFile << " - [$branch] " + change + Utility.NEWLINE
      changelogFile << Utility.NEWLINE + changeFrom + Utility.NEWLINE + Utility.NEWLINE
      changelogFile << temp
    }
  }
}

