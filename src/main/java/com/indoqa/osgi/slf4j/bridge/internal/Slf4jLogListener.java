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
import org.osgi.framework.Version;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogListener implements LogListener {

    @Override
    public void logged(LogEntry logEntry) {
        Logger logger = this.getLogger(logEntry.getBundle());
        String message = logEntry.getMessage();
        Throwable throwable = logEntry.getException();

        switch (logEntry.getLevel()) {
            case LogService.LOG_ERROR:
                logger.error(message, throwable);
                break;
            case LogService.LOG_WARNING:
                logger.warn(message, throwable);
                break;
            case LogService.LOG_INFO:
                logger.info(message, throwable);
                break;
            case LogService.LOG_DEBUG:
                logger.debug(message, throwable);
                break;
            default:
                logger.warn("[unsupported level: " + logEntry.getLevel() + "] " + message, throwable);
                break;
        }
    }

    private Logger getLogger(final Bundle bundle) {
        return LoggerFactory.getLogger(bundle.getSymbolicName() + '.' + this.getVersion(bundle));
    }

    private Version getVersion(final Bundle bundle) {
        Version version = bundle.getVersion();
        return version == null ? version : Version.emptyVersion;
    }
}
