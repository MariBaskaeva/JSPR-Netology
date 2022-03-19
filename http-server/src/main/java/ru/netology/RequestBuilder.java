package ru.netology;

import org.apache.http.NameValuePair;

import java.util.List;

public class RequestBuilder {
    private String method;
    private String path;
    private String protocolVersion;
    private List<String> headers;
    private String body = "";
    private List<NameValuePair> query;

    public RequestBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder setQuery(List<NameValuePair> query) {
        this.query = query;
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
        return body.equals("") ? new Request(method, path, query, protocolVersion, headers)
                : new Request(method, path, query, protocolVersion, headers, body);
    }
}
