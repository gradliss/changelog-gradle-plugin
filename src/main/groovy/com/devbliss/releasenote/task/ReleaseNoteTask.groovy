package com.devbliss.releasenote.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ReleaseNoteTask extends DefaultTask{

  def filename
  def newLine = System.getProperty("line.separator");

  @TaskAction
  public void run() {
    def releaseVersion
    def snapshotVersion
    def branch = Information.getGitBranch()
    def userName = Information.getGitUsername()
    def email = Information.getGitEmail()
    def today = new Date()
    //Find version number (0.0.1) only with 2 dots
    //def regex = /((\d)+\.){2}(\d)+/
    //Find version number (0.0.1.0.1.1) up to 5 dots
    def regex = /((\d)+(\.\d+){0,5}(\.\*)?)/

    //Check if filename is defined in build.gradle
    if(getFilename() == null){
      Information.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    def changelogFile = Information.readFilenAndShow(getFilename())

    println "\033[1;31m -- Now write to your changelog -- \033[22m"

    def isNewRelease = System.console().readLine newLine + "\033[31m Is this a new release Version? (y/n): \033[37m"

    if(isNewRelease == "y") {
      releaseVersion = System.console().readLine '\033[31m Version: \033[37m'
    }else if (isNewRelease == "n"){
      println "\33[31m New snapshot version created"
      def changelogToString = changelogFile.text
      def versionNumber = changelogToString.find(regex)
      snapshotVersion = versionNumber + "-SNAPSHOT-" + new Date().time
    }else{
      println "SOMETHING GOES WRONG STOP PLUGIN"
      return
    }

    //Remove line breaks
    def changes = "Last change from: $userName $email $today"
    changes = changes.replace("\r", "").replace("\n", "")

    //Ask user is everything ok
    def change = System.console().readLine "\033[31m Change:\033[37m [$branch] "
    def changeIsOk = System.console().readLine "\033[31m Is everything ok? (y/n): \033[37m"

    if(changeIsOk == "y") {
      def temp = changelogFile.text
      if(isNewRelease == "y") {
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << newLine
        changelogFile << "### Version " + releaseVersion
        changelogFile << newLine + "- [$branch] " + change
        changelogFile << newLine + "---" + newLine
        changelogFile << changes + newLine
        changelogFile << temp
      }else if (isNewRelease == "n"){
        def tmp = changelogFile.text
        def changeVersionToSnapshot = tmp.replaceFirst(regex, snapshotVersion)
        changelogFile.delete()
        changelogFile = new File(getFilename())
        changelogFile << changeVersionToSnapshot
      }
    }else if (changeIsOk == "n"){
      println "\033[31m Ok you have a second chance."
      run()
    }else{
      Information.changelogNotWritten()
    }
  }
}