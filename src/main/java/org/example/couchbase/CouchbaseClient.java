package org.example.couchbase;

import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.Duration;

import com.couchbase.client.core.env.AggregatingMeterConfig;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.codec.SerializableTranscoder;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.kv.PersistTo;
import com.couchbase.client.java.kv.ReplicateTo;
import com.couchbase.client.java.kv.UpsertOptions;

@Slf4j
public class CouchbaseClient {

    private final String serverIp;
    private final String serverPort;
    private final String serverBucket;
    private final String serverPassword;

    private Cluster couchbaseCluster;
    private Bucket couchbaseBucket;

    public CouchbaseClient(String serverIp, String serverPort, String serverBucket, String serverPassword) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.serverBucket = serverBucket;
        this.serverPassword = serverPassword;
    }

    public void initCacheClient() {
        ClusterEnvironment env = ClusterEnvironment.builder()
                .transcoder(SerializableTranscoder.INSTANCE)
                .aggregatingMeterConfig(AggregatingMeterConfig.builder()
                        .enabled(true)
                        .emitInterval(Duration.ofSeconds(60)))
                .build();

        ClusterOptions opts = ClusterOptions.clusterOptions(serverBucket, serverPassword).environment(env);
        couchbaseCluster = Cluster.connect(serverIp, opts);
        couchbaseBucket = couchbaseCluster.bucket(serverBucket);
    }

    public void closeCacheClient() {
        couchbaseCluster.disconnect();
    }

    public void set(String key, Serializable o, int ttlSeconds) {
        UpsertOptions opts = UpsertOptions.upsertOptions()
                .durability(PersistTo.NONE, ReplicateTo.NONE)
                .expiry(Duration.ofSeconds(ttlSeconds));
        couchbaseBucket.defaultCollection().upsert(key, o, opts);
    }

    public <T> T get(String key, Class<T> cls) {
        try {
            return couchbaseBucket.defaultCollection().get(key).contentAs(cls);
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }
}
