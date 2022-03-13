package ru.netology;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class Response {
    private final String version = "HTTP/1.1";

    private int status;
    private String statusMessage;
    private String contentType;
    private byte[] content;

    public Response(int status, String statusMessage, String contentType, byte[] content) {
        this.status = status;
        this.statusMessage = statusMessage;
        this.contentType = contentType;
        this.content = content;
    }

    public void send(BufferedOutputStream out) throws IOException {
        out.write(toString().getBytes());
        out.write(content);
    }

    @Override
    public String toString(){
        return version + " " + status + " " + statusMessage + "\r\n" +
                (content == null ? "" : ("Content-Length: " + content.length + "\r\n" +
                "Content-Type: " + contentType + "\r\n")) +
                "Connection: close\r\n" +
                "\r\n";
    }
}
