package org.example.couchbase;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@MicronautTest
@ExtendWith(CouchbaseJUnitExtension.class)
class CouchbaseServiceTest {

    @Inject
    private CouchbaseService couchbaseService;

    @Test
    void test() {
        String value = "test-value";
        Object o = couchbaseService.get("init-test", String.class);
        assertEquals(value, o);
    }
}
