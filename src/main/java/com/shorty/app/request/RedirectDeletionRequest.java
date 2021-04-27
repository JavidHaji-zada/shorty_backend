package com.shorty.app.request;

import javax.validation.constraints.NotNull;

public class RedirectDeletionRequest {
    @NotNull
    private String alias;
    @NotNull
    private String id;

    public RedirectDeletionRequest() {
    }

    public RedirectDeletionRequest(String id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RedirectDeletionRequest{" +
                "alias='" + alias + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
