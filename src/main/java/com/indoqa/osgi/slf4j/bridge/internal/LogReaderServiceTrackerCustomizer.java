/*
 * Licensed to the Indoqa Software Design und Beratung GmbH (Indoqa) under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Indoqa licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.indoqa.osgi.slf4j.bridge.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogReaderService;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class LogReaderServiceTrackerCustomizer implements ServiceTrackerCustomizer<LogReaderService, LogReaderService> {

    private final Slf4jLogListener slf4jLogListener = new Slf4jLogListener();

    @Override
    public LogReaderService addingService(ServiceReference<LogReaderService> serviceReference) {
        LogReaderService logReaderService = this.getLogReaderService(serviceReference);
        logReaderService.addLogListener(this.slf4jLogListener);
        return logReaderService;
    }

    @Override
    public void modifiedService(ServiceReference<LogReaderService> serviceReference, LogReaderService logReaderService) {
        // nothing to do
    }

    @Override
    public void removedService(ServiceReference<LogReaderService> serviceReference, LogReaderService logReaderService) {
        logReaderService.removeLogListener(this.slf4jLogListener);
    }

    private LogReaderService getLogReaderService(ServiceReference<LogReaderService> serviceReference) {
        Bundle bundle = serviceReference.getBundle();
        BundleContext bundleContext = bundle.getBundleContext();
        return bundleContext.getService(serviceReference);
    }
}
