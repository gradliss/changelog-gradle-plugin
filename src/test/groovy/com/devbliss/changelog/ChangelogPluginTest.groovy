/*
 * Copyright 2014, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.changelog

import static org.gradle.util.WrapUtil.toLinkedSet
import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import com.devbliss.changelog.task.ChangelogTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Test for {@link ChangelogPlugin}.
 *
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 */
class ChangelogPluginTest {
  private Project project;
  private final ChangelogPlugin changelogPlugin = new ChangelogPlugin();

  @Test public void applyStandardTast() {
    project = ProjectBuilder.builder().build();
    changelogPlugin.apply(project);

    def task = project.tasks["changelogSnapshot"];
    assertThat(task, instanceOf(ChangelogTask));

    task = project.tasks["changelogRelease"];
    assertThat(task, instanceOf(ChangelogTask));
  }
}
