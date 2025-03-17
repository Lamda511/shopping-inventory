package com.lambda511.api.v1.response;

import org.springframework.stereotype.Component;

/**
 * Created by blitzer on 24.04.2016.
 */
@Component
public class DefaultResponseFactory implements ResponseFactory {

    @Override
    public <T> Response<T> createSuccessResponse(T content) {
        return new Response<>(Response.ResponseType.OK, content);
    }

    @Override
    public <T> Response<T> createErrorResponse(T content) {
        return new Response<>(Response.ResponseType.ERROR, content);
    }

}
