package model;

import java.util.UUID;

public class Client {

    private final String host;
    private final UUID uuid;

    public Client(String host, UUID uuid) {
        this.host = host;
        this.uuid = uuid;
    }

    public String getHost() {
        return host;
    }

    public UUID getUuid() {
        return uuid;
    }
}
