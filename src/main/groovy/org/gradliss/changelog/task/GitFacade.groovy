package org.gradliss.changelog.task

/**
 * Encapsulates information provided by GIT in some static utility methods. 
 * 
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 * @author Henning Schütz <henning.schuetz@devbliss.com>
 *
 * @version 0.0.1
 */
class GitFacade {

  def static isGitInstalled() {
    def command = 'git version'

    try {
      def proc = command.execute()
      proc.waitFor()
      return true
    } catch(IOException e) {
      return false
    }
  }

  def static getFirstPartOfGitBranch(){
    def command = "git rev-parse --abbrev-ref HEAD"
    def proc = command.execute()
    proc.waitFor()
    def branch = proc.in.text
    def firstIndexOfSlash = branch.indexOf("/")

    if(firstIndexOfSlash > 0) {
      return branch.substring(0, firstIndexOfSlash)
    }
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
}
