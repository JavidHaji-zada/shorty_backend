package com.shorty.app.request;

import javax.validation.constraints.NotNull;

public class RedirectCreationRequest {
    @NotNull
    private String alias;
    @NotNull
    private String url;

    public RedirectCreationRequest() {
    }

    public RedirectCreationRequest(String alias, String url) {
        this.alias = alias;
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RedirectCreationRequest{" + "alias='" + alias + '\'' + ", url='" + url + '\'' + '}';
    }
}
