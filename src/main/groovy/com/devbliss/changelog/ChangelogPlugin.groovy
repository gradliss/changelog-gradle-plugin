package com.devbliss.changelog

import com.devbliss.changelog.task.ChangelogTask
import com.devbliss.changelog.task.FileExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ChangelogPlugin implements Plugin<Project> {
  def void apply(Project project) {

    def fileExtension = new FileExtension()

    project.extensions.releasenote = fileExtension
    project.tasks.withType(ChangelogTask){
      conventionMapping.filename = { fileExtension.filename }
    }
    project.task('changelog', type: ChangelogTask)
  }
}