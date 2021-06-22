package org.example.properties;

public enum ApplicationProperty {
    SERVER_IP("server_ip"),
    SERVER_PORT("server_port"),
    SERVER_BUCKET("server_bucket"),
    SERVER_PASSWORD("server_password");

    private final String value;

    ApplicationProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
