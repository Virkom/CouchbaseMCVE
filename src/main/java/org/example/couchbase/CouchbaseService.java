package org.example.couchbase;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.example.properties.PropertiesService;
import org.example.properties.ApplicationProperty;

@Singleton
public class CouchbaseService {

    @Inject
    private PropertiesService propertiesService;

    private CouchbaseClient couchbaseClient = null;

    @PostConstruct
    void init() {
        couchbaseClient = new CouchbaseClient(
                propertiesService.getStringValue(ApplicationProperty.SERVER_IP),
                propertiesService.getStringValue(ApplicationProperty.SERVER_PORT),
                propertiesService.getStringValue(ApplicationProperty.SERVER_BUCKET),
                propertiesService.getStringValue(ApplicationProperty.SERVER_PASSWORD));

        couchbaseClient.initCacheClient();
    }

    @PreDestroy
    void destroy() {
        couchbaseClient.closeCacheClient();
    }

    void set(String key, Serializable o, int ttlSeconds) {
        couchbaseClient.set(key, o, ttlSeconds);
    }

    public <T> T get(String key, Class<T> cls) {
        return couchbaseClient.get(key, cls);
    }
}
