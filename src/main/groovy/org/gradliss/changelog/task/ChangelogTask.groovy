package org.gradliss.changelog.task

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

  def filename
  def snapshotWithTimestamp
  def readVersionFromGradleProperties
  def branch
  def today = new Date()
  def changelogFile

  @TaskAction
  public void run() {
    branch = tryToGetBranch()
    filename = getFilenameFromBuildfile()
    snapshotWithTimestamp = getSnapshotWithTimestampFromBuildfile()
    readVersionFromGradleProperties = getReadVersionFromGradlePropertiesFromBuildFile()

    if (filename == null) {
      Messages.fileNameIsNotDefined(Constants.DEFAULT_CHANGELOG_FILENAME)
      filename = Constants.DEFAULT_CHANGELOG_FILENAME
    }

    changelogFile = Utility.readFileAndShowOrCreate(filename, snapshotWithTimestamp)
  }

  private def getFilenameFromBuildfile() {
    return project.changelog.filename
  }

  private def getSnapshotWithTimestampFromBuildfile() {
    return project.changelog.snapshotWithTimestamp
  }

  private def getReadVersionFromGradlePropertiesFromBuildFile() {
    return project.changelog.readVersionFromGradleProperties
  }



  private def tryToGetBranch() {
    if(GitFacade.isGitInstalled()) {
      def firstPartOfGitBranch = GitFacade.getFirstPartOfGitBranch()

      if(firstPartOfGitBranch) {
        return '[' + firstPartOfGitBranch + '] '
      }
    } else {
      Messages.gitIsNotInstalled()
    }

    return ""
  }
}

