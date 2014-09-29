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
    if(getFilename() == null){
      Information.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    def changelogFile = Information.readFilenAndShow(getFilename())

    println "\033[1;31m -- Now write to your changelog -- \033[22m"

    //Loop so long until the input is "y" or "n"
    def i = 0
    while(loopInput){
      i++
      isNewRelease = System.console().readLine Utility.NEWLINE + "\033[31m Is this a new release Version? (y/n): \033[37m"
      if(isNewRelease == "y" || isNewRelease == "n"){
        loopInput = false
      }
      if(i == 3){
        println Utility.NEWLINE
        println Utility.WHITE_BOLD + "!!! Go Home, You're Drunk. !!!" + Utility.NORMAL
        println Utility.NEWLINE
      }
    }

    if(isNewRelease == "y") {
      releaseVersion = System.console().readLine '\033[31m Version: \033[37m'
    }else if (isNewRelease == "n"){
      println "\33[31m New snapshot version created"
      def changelogToString = changelogFile.text
      def versionNumber = changelogToString.find(Utility.regexVersionWithoutSuffix)
      snapshotVersion = versionNumber + "-SNAPSHOT-" + new Date().time
    }else{
      println "SOMETHING GOES WRONG STOP PLUGIN"
      return
    }

    //Remove line breaks
    def changeFrom = "Last change from: $userName $email $today"
    changeFrom = changeFrom.replace("\r", "").replace("\n", "")

    //Ask user is everything ok
    def change = System.console().readLine "\033[31m Change:\033[37m [$branch] "
    def changeIsOk = System.console().readLine "\033[31m Is everything ok? (y/n): \033[37m"

    if(changeIsOk == "y") {
      def temp = changelogFile.text
      if(isNewRelease == "y") {
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << Utility.NEWLINE
        changelogFile << "### Version " + releaseVersion
        changelogFile << Utility.NEWLINE + "- [$branch] " + change + Utility.NEWLINE
        changelogFile << "---" + Utility.NEWLINE
        changelogFile << changeFrom + Utility.NEWLINE
        changelogFile << temp
      }else if (isNewRelease == "n"){
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
    }else if (changeIsOk == "n"){
      Information.secondChance()
      run()
    }else{
      Information.changelogNotWritten()
    }
  }
}