
package org.example.couchbase;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

public class CouchbaseJUnitExtension implements Extension {

    public CouchbaseJUnitExtension() {
        BucketDefinition bucketDefinition = new BucketDefinition("testBucket");

        CouchbaseContainer couchbaseContainer = new CouchbaseContainer("couchbase/server")
                .withCredentials("testBucket", "testPassword")
                .withExposedPorts(8091, 8092, 8093, 8094, 11207, 11210, 11211, 18091, 18092, 18093)
                .withBucket(bucketDefinition);

        couchbaseContainer.start();
    }
}
