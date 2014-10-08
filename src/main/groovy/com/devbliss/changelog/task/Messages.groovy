package com.devbliss.changelog.task

/**
 * Provides static methods for printing messages to the user.
 * 
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 * @author Henning Schütz <henning.schuetz@devbliss.com>
 *
 * @version 0.0.1
 *
 */
class Messages {

  def static fileNameIsNotDefined() {
    println Constants.RED + " Please define a filename for changelog creation."
    println " The gradle task configuration looks like:"
    println ""
    println Constants.WHITE_BOLD + " changelog {"
    println Constants.WHITE_BOLD + "   filename = 'changelog.md'"
    println Constants.WHITE_BOLD + " }"
  }

  def static changelogFileReadSuccess(def changelogContent) {
    println Constants.RED + " Successfully read existing changelog file: "
    println ""
    println Constants.WHITE + "" + changelogContent
    println ""
  }
  
  def static changelogFileDoesNotExist() {
    println Constants.RED + " Changelog file doesn't exist. New one will be created."
  }
  
  def static changelogFileCreated(String initialVersion, String changelogFile) {
    println Constants.RED + " File with initial version $initialVersion was created: " + Constants.WHITE + changelogFile
  }
  
  def static gitIsNotInstalled() {
    println Constants.RED + 
      " WARNING: Git seems not to be installed at your system. Information in generated changelog may be incomplete."
  }
}
