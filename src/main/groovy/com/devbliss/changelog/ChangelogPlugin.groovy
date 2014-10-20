package com.devbliss.changelog

import com.devbliss.changelog.task.TaskConfigExtension
import com.devbliss.changelog.task.ReleaseTask
import com.devbliss.changelog.task.SnapshotTask
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * The changelog-gradle-plugin is a plugin for the gradle build system to write changelogs painless.
 * You have two tasks which you can use to write into the changelog. (changelogSnapshot, changelogRelease)
 * If no changelog file exists, a new one will be created with a given version number.
 * It is recommanded to use markddown for the changelog to use all the features we have implemented.
 * The plugin supports versions like 0.0.1 if you create a snapshot the revision will be automatically
 * incremented and the the suffix -SNAPSHOT-timestamp will be appended.
 * If you create a Release the snapshot suffix will be removed.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class ChangelogPlugin implements Plugin<Project> {
  def void apply(Project project) {

    project.extensions.create("changelog", TaskConfigExtension)
    project.task("changelogSnapshot", type: SnapshotTask)
    project.task("changelogRelease", type: ReleaseTask)
  }
}
