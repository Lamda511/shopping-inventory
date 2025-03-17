package com.lambda511.model;

/**
 * Created by blitzer on 24.04.2016.
 */
public class ProductNotFoundException extends RuntimeException {

    private Long productId;

    public ProductNotFoundException(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
