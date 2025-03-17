package com.lambda511.api.v1.response;

/**
 * Created by blitzer on 24.04.2016.
 */
public class Response<T> {

    public enum ResponseType {
        OK, ERROR
    }

    private ResponseType responseType;
    private T content;

    public Response(ResponseType responseType, T content) {
        this.responseType = responseType;
        this.content = content;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public T getContent() {
        return content;
    }

}
