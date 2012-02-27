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
package org.apache.deltaspike.test.security.impl.secured;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.impl.exclude.ExcludeExtension;
import org.apache.deltaspike.core.util.ClassUtils;
import org.apache.deltaspike.security.api.AccessDeniedException;
import org.apache.deltaspike.test.util.ShrinkWrapArchiveUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.Extension;


/**
 * Test for {@link org.apache.deltaspike.security.api.annotation.Secured}
 */
@RunWith(Arquillian.class)
public class SecuredAnnotationTest
{
    @Deployment
    public static WebArchive deploy()
    {
        //X TODO remove this workaround
        boolean isOwbAvailable = ClassUtils.tryToLoadClassForName("org.apache.webbeans.spi.ContainerLifecycle") != null;

        String[] excludedFiles;

        Asset beansXml = new StringAsset(
            "<beans>" +
                "<interceptors><class>org.apache.deltaspike.security.impl.SecurityInterceptor</class></interceptors>" +
            "</beans>"
        );

        if (isOwbAvailable)
        {
            excludedFiles = new String[]{"META-INF.apache-deltaspike.properties"};
        }
        else
        {
            excludedFiles = new String[]{"META-INF.apache-deltaspike.properties", "META-INF.beans.xml"};
        }
        
        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(ShrinkWrapArchiveUtil.getArchives(null,
                        "META-INF/beans.xml",
                        new String[]{"org.apache.deltaspike.core",
                                "org.apache.deltaspike.security",
                                "org.apache.deltaspike.test.security.impl.secured"},
                        excludedFiles))
                .addAsServiceProvider(Extension.class, ExcludeExtension.class)
                .addAsWebInfResource(beansXml, "beans.xml");
    }

    @Test
    public void simpleInterceptorTest()
    {
        SecuredBean1 testBean = BeanProvider.getContextualReference(SecuredBean1.class, false);

        Assert.assertEquals("result", testBean.getResult());

        try
        {
            testBean.getBlockedResult();
            Assert.fail();
        }
        catch (AccessDeniedException e)
        {
            //expected exception
        }
    }

    @Test
    public void interceptorTestWithStereotype()
    {
        SecuredBean2 testBean = BeanProvider.getContextualReference(SecuredBean2.class, false);

        Assert.assertEquals("result", testBean.getResult());

        try
        {
            testBean.getBlockedResult();
            Assert.fail();
        }
        catch (AccessDeniedException e)
        {
            //expected exception
        }
    }

    @Test
    public void simpleInterceptorTestOnMethods()
    {
        SecuredBean3 testBean = BeanProvider.getContextualReference(SecuredBean3.class, false);

        Assert.assertEquals("result", testBean.getResult());

        try
        {
            testBean.getBlockedResult();
            Assert.fail();
        }
        catch (AccessDeniedException e)
        {
            //expected exception
        }
    }
}
