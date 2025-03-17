package com.lambda511.api.v1.controllers;

import com.lambda511.model.services.query.QueryProductImageServiceImpl;
import com.lambda511.api.v1.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by blitzer on 05.05.2016.
 */

@Controller
public class ProductImagesQueryController extends APIV1BaseController {

    @Autowired
    private QueryProductImageServiceImpl queryProductImageService;

    @Autowired
    private ResponseFactory responseFactory;

    @RequestMapping(value = "/get_product_image", method = RequestMethod.GET)
    public void getProductImage(HttpServletResponse response,
                           @RequestParam(value = "imageName", defaultValue = "") String imageName,
                           @RequestParam(value = "width", required = false, defaultValue = "0") Integer width,
                           @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                           @RequestParam(value = "quality", required = false, defaultValue = "fast") String quality) throws IOException {

        response.setContentType("image/jpeg");
        if (width > 0 && height > 0) {
            queryProductImageService.writeProductImageScaledToOutputStream(imageName, width, height, quality, response.getOutputStream());
        } else {
            queryProductImageService.writeProductImageFullToOutputStream(imageName, response.getOutputStream());
        }
    }

}
