package com.devbliss.changelog

import com.devbliss.changelog.task.FileExtension
import com.devbliss.changelog.task.ReleaseTask
import com.devbliss.changelog.task.SnapshotTask
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ChangelogPlugin implements Plugin<Project> {
  def void apply(Project project) {

    project.extensions.create("changelog", FileExtension)
    project.task("changelogSnapshot", type: SnapshotTask)
    project.task("changelogRelease", type: ReleaseTask)
  }
}