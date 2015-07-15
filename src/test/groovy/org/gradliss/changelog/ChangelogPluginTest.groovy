package org.gradliss.changelog

import static org.gradle.util.WrapUtil.toLinkedSet
import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import org.gradliss.changelog.task.ChangelogTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import spock.lang.Specification

/**
 * Test for {@link ChangelogPlugin}.
 */
class ChangelogPluginTest extends Specification {
  private Project project
  private ChangelogPlugin changelogPlugin

  def setup() {
    project = ProjectBuilder.builder().build()
    changelogPlugin = new ChangelogPlugin()
  }

  def "applyTasks"() {
    when:
    changelogPlugin.apply(project)

    then:
    project.tasks["changelogSnapshot"] instanceOf(ChangelogTask)
    project.tasks["changelogRelease"] instanceOf(ChangelogTask)
  }
}
