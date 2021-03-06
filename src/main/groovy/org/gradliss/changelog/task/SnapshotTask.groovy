package org.gradliss.changelog.task

import org.gradle.api.tasks.TaskAction

/**
 * This task defines the changelogSnapshot. It differentiates whether a snapshot version already exists.
 * In case of an already existing snapshot version, only a new entry tab will be popped
 * onto the first position of the current snapshot and the actual timestamp will be replaced in snapshot name.
 * In case there is no snapshot version in the changelog file, there will be the latest release number
 * increased at the minor level to generate a completely new snapshot version paragraph within a new
 * entry tab depending on the branch type (e.g. feature, bug, refactor etc.) and the given user description.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class SnapshotTask extends ChangelogTask{

  @TaskAction
  public void run() {
    super.run()
    def snapshotVersion


    def changelogToString = changelogFile.text
    def versionLine = changelogToString.find(Constants.regexVersionWithoutSuffix)
    def isAlreadySnapshotVersion = versionLine.contains("-SNAPSHOT")
    def versionNumber = versionLine.find(Constants.regexVersionNumber)

    if(readVersionFromGradleProperties){
      snapshotVersion = project.getRootProject().getVersion()
      def isSnapshotVersion = snapshotVersion.contains("-SNAPSHOT")

      //If versionNumber in gradle.properties not contains SNAPSHOT than quit plugin
      if(!isSnapshotVersion){
        println Constants.RED_BOLD + "You will create a SNAPSHOT but you have defined a RELEASE version in your gradle.properties" + Constants.NEWLINE
        println Constants.RED_BOLD + "Plugin will terminate." + Constants.RESET_COLOR_AND_STYLE
        return
      }
    }else{
      // handle version line and increment version number for a new snapshot version
      if (isAlreadySnapshotVersion) {
        snapshotVersion = versionLine
        if(snapshotWithTimestamp) {
          snapshotVersion = versionLine.replaceFirst(versionLine, "$versionNumber-SNAPSHOT-" + today.time)
        }
      } else {
        // extract minor version number in order to increment it.
        // only works for the version number layout we use at the moment: e.g. --> 0.0.0
        def major = versionNumber.subSequence(0, 1)
        def minor = versionNumber.subSequence(2, 3) as int
        minor++
        def incrementedVersionNumber = major + "." + minor + ".0"

        snapshotVersion = versionLine.replaceFirst(versionLine, "$incrementedVersionNumber-SNAPSHOT")
        if(snapshotWithTimestamp) {
          snapshotVersion = versionLine.replaceFirst(versionLine, "$incrementedVersionNumber-SNAPSHOT-" + today.time)
        }
      }

      Utility.writeVersionToGradleProperties(snapshotVersion)
    }

    println " Add Snapshot to "+ getFilename()
    println Constants.RED + " New snapshot version created" + Constants.RED_BOLD + " $snapshotVersion"

    def change = readChange(branch)

    def temp = changelogFile.text
    if (isAlreadySnapshotVersion) {
      temp = temp.replaceFirst(Constants.regexVersionWithSuffix, snapshotVersion)
      def oldChanges = temp.find(Constants.regexText)
      def newstuff = " - $branch" + change + Constants.NEWLINE + oldChanges
      temp = temp.replaceFirst(Constants.regexText, newstuff)
      temp = temp.replaceFirst(Constants.regexChangeNameDate, Utility.getChangeFrom(today))
      changelogFile.delete()
      changelogFile = new File(getFilename())
      changelogFile << temp
    } else {
      changelogFile.delete()
      changelogFile = new File(getFilename())
      changelogFile << Constants.NEWLINE + "### Version $snapshotVersion" + Constants.NEWLINE
      changelogFile << " - $branch" + change + Constants.NEWLINE
      changelogFile << Constants.NEWLINE + Utility.getChangeFrom(today) + Constants.NEWLINE + Constants.NEWLINE
      changelogFile << temp
    }

    println Constants.RESET_COLOR_AND_STYLE
  }

  private String readChange(String branch) {
    if (System.properties.'sun.java.command'.contains('launcher.daemon')) {
      print Constants.RED + " Change:" + Constants.WHITE + " $branch "
      System.out.flush()
      return new InputStreamReader(System.in).readLine()

    } else {
      return System.console().readLine(Constants.RED + " Change:" + Constants.WHITE + " $branch ")
    }
  }
}

