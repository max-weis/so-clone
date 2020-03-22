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
