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
    println Utility.RED + " Please define a filename for changelog creation."
    println " The gradle task configuration looks like:"
    println ""
    println Utility.WHITE_BOLD + " changelog {"
    println Utility.WHITE_BOLD + "   filename = 'changelog.md'"
    println Utility.WHITE_BOLD + " }"
  }

  def static changelogNotWritten(){
    println ""
    println Utility.RED_BOLD + " -- Changelog not written -- " + Utility.NORMAL
  }

  def static readFileAndShow(def changelogFile){
    println Utility.RED + " Try to read changelog with name: " + Utility.WHITE + changelogFile
    def changelog = new File(changelogFile)

    if (changelog.exists()) {
      println Utility.RED + " Successfully read existing changelog: "
      println ""
      println Utility.WHITE + "" + changelog.text
      println ""
    } else {
      println Utility.RED + " Changelog doesnt exist! New one would be created!"
      boolean success = new File(changelogFile).createNewFile()
      if (success) {
        changelog = new File(changelogFile)
        def user = getGitUsername()
        def email = getGitEmail()
        def today = new Date()
        def changeFrom = "Last change from: $user $email $today"
        changeFrom = changeFrom.replace("\r", "").replace("\n", "")
        def initialVersion = "0.1.0-SNAPSHOT-" + today.time
        changelog << Utility.NEWLINE
        changelog << "### Version " + initialVersion
        changelog << Utility.NEWLINE + " - [initial] initial commit" + Utility.NEWLINE
        changelog << Utility.NEWLINE + "-- "
        changelog << changeFrom
        println Utility.RED + " File with initail Version $initialVersion was created: " + Utility.WHITE + changelogFile
      }
    }
    return changelog
  }

  def static secondChance(){
    println Utility.NEWLINE
    println Utility.RED_BOLD + " -!- Ok you have a second chance -!- " + Utility.NORMAL
    println Utility.NEWLINE
  }
}
