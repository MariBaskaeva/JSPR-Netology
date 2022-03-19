package ru.netology;

import java.util.List;

public class RequestBuilder {
    private String method;
    private String path;
    private String protocolVersion;
    private List<String> headers;
    private String body = "";

    public RequestBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    public RequestBuilder setHeaders(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public Request build(){
        return body.equals("") ? new Request(method, path, protocolVersion, headers)
                : new Request(method, path, protocolVersion, headers, body);
    }
}
