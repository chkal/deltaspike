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
package org.apache.deltaspike.security.api;

import org.apache.deltaspike.security.spi.Authenticator;

/**
 * Abstract base class that Authenticator implementations can extend for convenience. 
 *
 */
public abstract class BaseAuthenticator implements Authenticator
{
    private AuthenticationStatus status;
    
    private User user;

    public AuthenticationStatus getStatus() 
    {
        return status;
    }

    public void setStatus(AuthenticationStatus status) 
    {
        this.status = status;
    }

    public User getUser() 
    {
        return user;
    }

    public void setUser(User user) 
    {
        this.user = user;
    }

    public void postAuthenticate() 
    {
        // No-op, override if any post-authentication processing is required.
    }
}