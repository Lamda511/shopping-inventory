package com.lambda511.model;

/**
 * Created by blitzer on 24.04.2016.
 */
public class ProductCategoryNotFoundException extends RuntimeException {

    private Integer productCategoryId;

    public ProductCategoryNotFoundException(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }
}
