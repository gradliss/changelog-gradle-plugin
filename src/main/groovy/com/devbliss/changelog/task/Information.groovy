package com.devbliss.changelog.task

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class Information {

  def static getGitBranch(){
    def command = "git rev-parse --abbrev-ref HEAD"
    def proc = command.execute()
    proc.waitFor()
    def branch = proc.in.text
    def branchDefinition = branch.substring(0,branch.lastIndexOf("/"))
  }

  def static getGitUsername(){
    def commandGetName = "git config user.name"
    def procGetName = commandGetName.execute()
    procGetName.waitFor()
    def name = procGetName.in.text
  }

  def static getGitEmail(){
    def commandGetEmail = "git config user.email"
    def procGetEmail = commandGetEmail.execute()
    procGetEmail.waitFor()
    def email = procGetEmail.in.text
  }

  def static fileNameIsNotDefined(){
    println "\033[31m Please define a filename for changelog creation."
    println " The gradle task configuration looks like:"
    println ""
    println "\033[1;37m changelog {"
    println "\033[1;37m   filename = 'changelog.md'"
    println "\033[1;37m }"
  }

  def static changelogNotWritten(){
    println ""
    println "\033[1;31m -- Changelog not written -- \033[22;31m"
  }

  def static readFilenAndShow(def changelogFile){
    println "\033[31m Try to read changelog with name: \033[37m" + changelogFile
    def changelog = new File(changelogFile)

    if(changelog.exists()) {
      println "\033[31m Successfully read existing changelog: "
      println ""
      println "\033[37m" + changelog.text
      println ""
    }else{
      println "\033[31m Changelog doesnt exist! New one would be created!"
      boolean success = new File(changelogFile).createNewFile()
      if(success){
        println "\033[31m File created: \033[37m" + changelogFile
      }
    }
    return changelog
  }

  def static secondChance(){
    println Utility.NEWLINE
    println "\033[1,31m -!- Ok you have a second chance -!- \033[22m"
    println Utility.NEWLINE
  }
}
