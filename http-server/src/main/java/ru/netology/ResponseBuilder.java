package ru.netology;

public class ResponseBuilder {
    private int status;
    private String statusMessage;
    private String contentType;
    private byte[] content;

    public ResponseBuilder setStatus(int status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public ResponseBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public ResponseBuilder setContent(byte[] content){
        this.content = content;
        return this;
    }

    public Response build(){
        Response response = new Response(status, statusMessage, contentType, content);
        return response;
    };
}
