package com.devbliss.changelog

import com.devbliss.changelog.task.FileExtension
import com.devbliss.changelog.task.ReleaseTask
import com.devbliss.changelog.task.SnapshotTask
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * The changelog-gradle-plugin is a plugin for the gradle build system to write changelogs painless.
 * You have two tasks u can use to write into the changelog. (changelogSnapshot, changelogRelease)
 * If no changelog file exist initial would be a new one created with a given version number.
 * It is recommanded to use markddown for the changelog to use all the feature we have implemented.
 * The plugin supports Versions like 0.0.1 if you create an snapshot the revision would be automaticily
 * incremented and the the suffix -SNAPSHOT-timestamp would be append.
 * If you create a Release the snapshot suffix would deleted.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class ChangelogPlugin implements Plugin<Project> {
  def void apply(Project project) {

    project.extensions.create("changelog", FileExtension)
    project.task("changelogSnapshot", type: SnapshotTask)
    project.task("changelogRelease", type: ReleaseTask)
  }
}
