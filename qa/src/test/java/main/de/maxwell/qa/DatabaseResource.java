/*
 * MIT License
 *
 * Copyright (c) 2020 Max Weis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.de.maxwell.qa;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseResource.class);


    private static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>("postgres:12-alpine")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withLogConsumer(new Slf4jLogConsumer(LOG))
            .withExposedPorts(5432);

    @Override
    public Map<String, String> start() {
        DATABASE.start();

        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.username", DATABASE.getUsername());
        properties.put("quarkus.datasource.password", DATABASE.getPassword());
        properties.put("quarkus.datasource.url", DATABASE.getJdbcUrl());

        LOG.info(properties.toString());

        return properties;
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
