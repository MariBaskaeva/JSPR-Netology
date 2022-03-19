package ru.netology;

import java.util.List;

public class Request {
    private String method;
    private String path;
    private String protocolVersion;
    private List<String> headers;
    private String body;

    public Request(String method, String path, String protocolVersion, List<String> headers){
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
    }

    public Request(String method, String path, String protocolVersion, List<String> headers, String body){
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
