package com.devbliss.changelog.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class ReleaseTask extends DefaultTask{

  def filename = project.changelog.filename

  @TaskAction
  public void run() {
    println "RELEASE" + getFilename()
  }
}

