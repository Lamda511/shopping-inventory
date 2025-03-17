package com.lambda511.api.v1;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by blitzer on 21.06.2016.
 */

@Component
public interface ApiDescriptor {

    String getApiVersion();
    List<ApiLink> getApiLinks();

}
