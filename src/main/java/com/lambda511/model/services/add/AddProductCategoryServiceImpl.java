package com.lambda511.model.services.add;

import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by blitzer on 22.04.2016.
 */

@Component
public class AddProductCategoryServiceImpl {

    @Autowired
    private ProductsCategoryRepository productCategoriesRepository;

    @Transactional
    public Integer addProductCategory(String name) {
        ProductCategory productCategory = new ProductCategory(name);
        productCategory = productCategoriesRepository.save(productCategory);

        return productCategory.getId();
    }
}
