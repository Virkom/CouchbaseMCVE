package org.example.properties;

import io.micronaut.context.env.Environment;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PropertiesServiceImpl implements PropertiesService {

    @Inject
    Environment environment;

    @PostConstruct
    void init() {
        for (ApplicationProperty param : ApplicationProperty.values()) {
            environment.getProperty(paramToEnvKey(param), String.class);
        }
    }

    @Override
    public String getStringValue(ApplicationProperty param) {
        return environment.getRequiredProperty(paramToEnvKey(param), String.class);
    }

    private static String paramToEnvKey(ApplicationProperty p) {
        return String.join(".", "app", p.getValue());
    }
}
