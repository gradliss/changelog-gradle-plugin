package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * The changelog task does some rudimentary stuff which belongs either to {@link SnapshotTask.groovy} or to {@link ReleaseTask.groovy}.
 * For example preparing same information (e.g. user name, email, branch etc.) needed by each sub task or more
 * essential, check for an existing changelog file and trigger the creation of a changelog file within a initial snapshot version
 * if no changelog file exists.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 *
 */

abstract class ChangelogTask extends DefaultTask{

  def filename = project.changelog.filename
  def branch
  def today = new Date()
  def changelogFile

  @TaskAction
  public void run() {
    
    if(GitFacade.isGitInstalled()) {
      branch = GitFacade.getFirstPartOfGitBranch()
    } else {
      Messages.gitIsNotInstalled()
      branch = "change"
    }

    //Check if filename is defined in build.gradle
    if (getFilename() == null){
      Messages.fileNameIsNotDefined()
      return
    }

    //Read file and show existing changelog
    //if no changelog file exist new one would created
    changelogFile = Utility.readFileAndShow(getFilename())
  }
}

