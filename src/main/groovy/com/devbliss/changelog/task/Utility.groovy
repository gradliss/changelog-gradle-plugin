package com.devbliss.changelog.task

/**
 * Providing some information stuff like user name, branch type and email extracted from git configuration.
 * The case if no file exists yet will be handled here - There will be a new changelog file created,
 * including an initial snapshot version in format 0.1.0-SNAPSHOT-<current-timestamp>.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class Utility {

  def static readFileAndShow(def changelogFile){
    println Constants.RED + " Try to read changelog with name: " + Constants.WHITE + changelogFile
    def changelog = new File(changelogFile)

    if (changelog.exists()) {
      Messages.changelogFileReadSuccess(changelog.text)
    } else {
      Messages.changelogFileDoesNotExist()
      boolean success = new File(changelogFile).createNewFile()
      
      if (success) {
        changelog = new File(changelogFile)

        def today = new Date()

        def initialVersion = "0.1.0-SNAPSHOT-" + today.time
        changelog << Constants.NEWLINE
        changelog << "### Version " + initialVersion
        changelog << Constants.NEWLINE + " - [initial] initial commit" + Constants.NEWLINE
        changelog << Constants.NEWLINE + getChangeFrom(today)
        Messages.changelogFileCreated(initialVersion, changelogFile)
      }
    }
    return changelog
  }

  def static getChangeFrom(today) {
    def user = getUsername()
    def email = getEmail()
    def changeFrom = "-- Last change by: $user $email $today"
    changeFrom = changeFrom.replace("\r", "").replace("\n", "")
    return changeFrom
  }
  
  def private static getUsername() {
    if(GitFacade.isGitInstalled()) {
      return GitFacade.getGitUsername()
    } else {
      return System.getProperty('user.name')
    }
  }
  
  def private static getEmail() {
    if(GitFacade.isGitInstalled()) {
      return GitFacade.getGitEmail()
    } else {
      return '<unknown email address>'
    }
  }
}
