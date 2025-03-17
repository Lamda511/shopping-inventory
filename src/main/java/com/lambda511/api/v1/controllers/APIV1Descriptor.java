package com.lambda511.api.v1.controllers;

import com.lambda511.api.v1.ApiDescriptor;
import com.lambda511.api.v1.ApiLink;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by blitzer on 21.06.2016.
 */

@Component
public class APIV1Descriptor implements ApiDescriptor {

    public static final String API_V1_FULL_NAME = "API version 1.0";

    private Logger logger = Logger.getLogger(APIV1Descriptor.class);

    @Override
    public String getApiVersion() {
        return API_V1_FULL_NAME;
    }

    @Override
    public List<ApiLink> getApiLinks() {
        List<ApiLink> apiLinks = new ArrayList<>();
        logger.debug("Creating api descriptor");

        apiLinks.addAll(getApiLinksForController(ProductsController.class));
        apiLinks.addAll(getApiLinksForController(ProductImagesQueryController.class));
        apiLinks.addAll(getApiLinksForController(ApiDescriptorController.class));

        return apiLinks;
    }


    private List<ApiLink> getApiLinksForController(Class controllerClass) {
        List<ApiLink> apiLinks = new ArrayList<>();

        logger.debug(String.format("Searching for request mappings in controller: %s", controllerClass.getName()));
        for (Method method : controllerClass.getMethods()) {
            RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
            if (requestMappingAnnotation != null) {
                logger.debug(String.format("Found request mapping annotation for method \"%s\" : %s", method.getName(), requestMappingAnnotation.toString()));
                ApiLink apiLink = new ApiLink(
                        Arrays.asList(requestMappingAnnotation.value()),
                        getRequestMethodNames(requestMappingAnnotation.method()),
                        getRequestParamAnnotations(method.getParameterAnnotations()));

                apiLinks.add(apiLink);
            }
        }

        return apiLinks;
    }

    private List<String> getRequestMethodNames(RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods).map(requestMethod -> requestMethod.name()).collect(Collectors.toList());
    }


    private List<ApiLink.ApiLinkParams> getRequestParamAnnotations(Annotation[][] annotations) {
        List<ApiLink.ApiLinkParams> requestParamAnnotations = Arrays.stream(annotations)
                .flatMap(annotations1 ->  Arrays.stream(annotations1))
                .filter(annotation -> RequestParam.class.equals(annotation.annotationType()))
                .map(annotation -> {
                    RequestParam requestParam = (RequestParam) annotation;
                    return new ApiLink.ApiLinkParams(
                            StringUtils.isNotEmpty(requestParam.name()) ? requestParam.name() : requestParam.value(),
                            requestParam.required(),
                            StringUtils.trim(requestParam.defaultValue()));
                })
                .collect(Collectors.toList());

        logger.debug(String.format("Request parameters: %s", requestParamAnnotations));
        return requestParamAnnotations;
    }

}
