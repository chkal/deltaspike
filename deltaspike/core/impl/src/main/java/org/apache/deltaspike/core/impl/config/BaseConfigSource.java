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
package org.apache.deltaspike.core.impl.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.deltaspike.core.spi.config.ConfigSource;

/**
 * Base class for all our ConfigSources
 */
public abstract class BaseConfigSource implements ConfigSource
{
    protected Logger log = Logger.getLogger(getClass().getName());

    private int ordinal = 1000; // default

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrdinal()
    {
        return ordinal;
    }

    /**
     * Init method e.g. for initializing the ordinal.
     * This method can be used from a subclass to determine
     * the ordinal value
     * @param defaultOrdinal the default value for the ordinal if not set via configuration
     */
    protected void initOrdinal(int defaultOrdinal)
    {
        ordinal = defaultOrdinal;

        String configuredOrdinalString = getPropertyValue(ConfigSource.DELTASPIKE_ORDINAL);

        try
        {
            if (configuredOrdinalString != null)
            {
                ordinal = Integer.parseInt(configuredOrdinalString.trim());
            }
        }
        catch (NumberFormatException e)
        {
            log.log(Level.WARNING,
                    "The configured config-ordinal isn't a valid integer. Invalid value: " + configuredOrdinalString);
        }
    }
}
