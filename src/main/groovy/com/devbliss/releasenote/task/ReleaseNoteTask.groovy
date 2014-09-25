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
    String newLine = System.getProperty("line.separator");

    if(getFilename() != null){
      println "\033[31m Try to read changelog with name: \033[37m" + getFilename()
      def changelog = new File(getFilename())

      if(changelog.exists()) {
        println "\033[31m Successfully read existing changelog: "
        println ""
        println "\033[37m" + changelog.text
        println ""
      }else{
        println "\033[31m Changelog doesnt exist! New one would be created!"
        boolean success = new File(getFilename()).createNewFile()
        if(success){
          println "\033[31m File created: \033[37m" + getFilename()
        }
      }

      println "\033[1;31m -- Now write to your changelog -- \033[22m"

      def version
      def versionQuestion = System.console().readLine newLine + '\033[31m Is this a new Version? (y/n): \033[37m'

      if(versionQuestion == "y") {
        version = System.console().readLine '\033[31m Version: \033[37m'
      }

      def branch = Information.getGitBranch()
      def userName = Information.getGitUsername()
      def email = Information.getGitEmail()
      def today = new Date()

      //Remove line breaks
      def changes = "Last change from: $userName $email $today"
      changes = changes.replace("\r", "").replace("\n", "")

      def change = System.console().readLine "\033[31m Change:\033[37m [$branch] "
      def changeIsOk = System.console().readLine '\033[31m Is everything ok? (y/n): \033[37m'

      if(changeIsOk == "y") {
        def temp = changelog.text
        changelog.delete()
        changelog = new File(getFilename())
        changelog << newLine
        changelog << "### Version " + version
        changelog << newLine + "- [$branch] " + change
        changelog << newLine + "---" + newLine
        changelog << changes + newLine
        changelog << temp
      }else if (changeIsOk == "n"){
        println "ReRun releasenote setup"
        run()
      }else{
        println ""
        println "\033[1;31m -- Changelog not written -- \033[22;31m"
      }
    }else{
      println "\033[31m Please define a filename for changelog creation."
      println " The gradle task configuration looks like:"
      println ""
      println "\033[1;37m releaseNote {"
      println "\033[1;37m   filename = 'changelog.md'"
      println "\033[1;37m }"
    }
  }
}