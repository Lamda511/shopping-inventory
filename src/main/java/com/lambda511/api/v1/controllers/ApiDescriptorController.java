package com.lambda511.api.v1.controllers;

import com.lambda511.api.v1.ApiDescriptor;
import com.lambda511.api.v1.response.Response;
import com.lambda511.api.v1.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by blitzer on 21.06.2016.
 */

@RestController
public class ApiDescriptorController extends APIV1BaseController {

    @Autowired
    private ApiDescriptor apiDescriptor;

    @Autowired
    private ResponseFactory responseFactory;

    @RequestMapping(method = RequestMethod.GET, value = "/help")
    public Response<ApiDescriptor> getApiDescriptor() {
        return responseFactory.createSuccessResponse(apiDescriptor);
    }

}
