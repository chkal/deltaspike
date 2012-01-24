/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.apache.deltaspike.test.core.api.projectstage;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.core.api.projectstage.ProjectStageProducer;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class ProjectStageProducerTest
{
    /**
     * Test a ProjectStage which got set by the <i>javax.faces.ProjectStage</i>
     */
    @Test
    public void testProjectStageSetByEnvironment()
    {
        String envName = "org.apache.deltaspike.ProjectStage";
        String oldEnvVal = "" + System.getProperty(envName);
        try
        {
            System.setProperty(envName, "SystemTest");

            ProjectStageProducer psp = ProjectStageProducer.getInstance();
            Assert.assertNotNull(psp);

            ProjectStageProducer.setProjectStage(null);

            ProjectStage ps = psp.getProjectStage();
            Assert.assertNotNull(ps);
            Assert.assertEquals(ps, ProjectStage.SystemTest);
            Assert.assertTrue(ps == ProjectStage.SystemTest);

            ProjectStageProducer.setProjectStage(null);
            System.setProperty(envName, "IntegrationTest");

            ps = psp.getProjectStage();
            Assert.assertNotNull(ps);
            Assert.assertEquals(ps, ProjectStage.IntegrationTest);
            Assert.assertTrue(ps == ProjectStage.IntegrationTest);
        }
        finally
        {
            System.setProperty(envName, oldEnvVal);
        }
    }
}
