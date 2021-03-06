package org.gradliss.changelog.task

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 * This is the class to define an extension for the plugin to access the filename
 * from the build.gradle file. Filename can be defined in the build file.
 */

class TaskConfigExtension {
  def String filename = "changelog.md"
  def boolean snapshotWithTimestamp = false
  def boolean readVersionFromGradleProperties = false
}
