/*
 * PMD :: IT :: Plugin :: Tests
 * Copyright (C) 2013 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.it.java.suite;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.OrchestratorBuilder;
import com.sonar.orchestrator.locator.FileLocation;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  PmdTest.class
})
public class PmdTestSuite {

  @ClassRule
  public static final Orchestrator ORCHESTRATOR;

  static {
    OrchestratorBuilder orchestratorBuilder = Orchestrator.builderEnv()
      .addPlugin("java")
      .addPlugin("pmd")
      .setMainPluginKey("pmd")
      .addPlugin(FileLocation.of(TestUtils.pluginJar("pmd-extension-plugin")))
      .restoreProfileAtStartup(FileLocation.ofClasspath("/com/sonar/it/java/PmdTest/pmd-junit-rules.xml"))
      .restoreProfileAtStartup(FileLocation.ofClasspath("/com/sonar/it/java/PmdTest/pmd-extensions-profile.xml"))
      .restoreProfileAtStartup(FileLocation.ofClasspath("/com/sonar/it/java/PmdTest/pmd-backup.xml"));
    ORCHESTRATOR = orchestratorBuilder.build();
  }

  public static boolean is_plugin_2_2_or_later() {
    return ORCHESTRATOR.getConfiguration().getPluginVersion("pmd").isGreaterThanOrEquals("2.2");
  }

}
