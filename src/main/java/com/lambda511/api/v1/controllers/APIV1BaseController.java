package com.lambda511.api.v1.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by blitzer on 22.03.2016.
 */

@RequestMapping(value = "/api-v1/", headers = "Accept=application/com.lambda511.shopping-inventory.api-v1+json")
public class APIV1BaseController {

    final static Logger logger = Logger.getLogger(APIV1BaseController.class);

}
