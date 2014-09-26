package com.devbliss.releasenote.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ReleaseNoteTask extends DefaultTask{

  def filename


  @TaskAction
  public void run() {
    def releaseVersion
    def snapshotVersion
    def branch = Information.getGitBranch()
    def userName = Information.getGitUsername()
    def email = Information.getGitEmail()
    def today = new Date()

    //get only vertsion number
    //    def regexVersion1 = /(\d)+(\.\d)+(\d+)/
    def regexVersion1 = /(\d)*(\.\d*).*/
    //get version with snap and replace
    def regexVersion = /(\d)*(\.\d*)+(-).*/
    //    def regexText = /(- \[(.*?)\])(.*?)(\d+).*/
    def regexText = /(- \[(.*)\])(.*?).*/
    def regexChange = /\bLast change from:(.*?)(\d+).*/

    //Check if filename is defined in build.gradle
    if(getFilename() == null){
      Information.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    def changelogFile = Information.readFilenAndShow(getFilename())

    println "\033[1;31m -- Now write to your changelog -- \033[22m"

    def isNewRelease = System.console().readLine Cmd.NEWLINE + "\033[31m Is this a new release Version? (y/n): \033[37m"

    if(isNewRelease == "y") {
      releaseVersion = System.console().readLine '\033[31m Version: \033[37m'
    }else if (isNewRelease == "n"){
      println "\33[31m New snapshot version created"
      def changelogToString = changelogFile.text
      def versionNumber = changelogToString.find(regexVersion1)
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
        changelogFile << Cmd.NEWLINE
        changelogFile << "### Version " + releaseVersion
        changelogFile << Cmd.NEWLINE + "- [$branch] " + change + Cmd.NEWLINE
        changelogFile << "---" + Cmd.NEWLINE
        changelogFile << changeFrom + Cmd.NEWLINE
        changelogFile << temp
      }else if (isNewRelease == "n"){
        def tmp = changelogFile.text
        println "-->" + snapshotVersion

        tmp = tmp.replaceFirst(regexVersion1, snapshotVersion)
        tmp = tmp.replaceFirst(regexVersion, snapshotVersion)
        def oldChanges = tmp.find(regexText)
        def newstuff = "- [$branch] " + change + Cmd.NEWLINE + oldChanges + Cmd.NEWLINE
        tmp = tmp.replaceFirst(regexText, newstuff)
        tmp = tmp.replaceFirst(regexChange, changeFrom)
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << tmp
      }
    }else if (changeIsOk == "n"){
      Information.secondChance()
      run()
    }else{
      Information.changelogNotWritten()
    }
  }
}