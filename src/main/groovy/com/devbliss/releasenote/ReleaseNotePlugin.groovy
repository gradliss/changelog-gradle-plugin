package com.devbliss.releasenote

import com.devbliss.releasenote.task.ReleaseNoteTask
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ReleaseNotePlugin implements Plugin<Project> {
  def void apply(Project project) {
    def fileExtension = new FileExtension()

    project.extensions.releasenote = fileExtension

    project.tasks.withType(ReleaseNoteTask){
      conventionMapping.filename = { fileExtension.filename }
    }

    project.task('releaseNote', type: ReleaseNoteTask)
  }
}