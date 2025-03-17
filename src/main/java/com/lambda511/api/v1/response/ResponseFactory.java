package com.lambda511.api.v1.response;

/**
 * Created by blitzer on 15.06.2016.
 */
public interface ResponseFactory {
    <T> Response<T> createSuccessResponse(T content);

    <T> Response<T> createErrorResponse(T content);
}
