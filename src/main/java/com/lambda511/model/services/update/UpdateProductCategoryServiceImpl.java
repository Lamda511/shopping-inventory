package com.lambda511.model.services.update;

import com.lambda511.model.ProductCategoryNotFoundException;
import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.ProductCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class UpdateProductCategoryServiceImpl {

    @Autowired
    private ProductsCategoryRepository productCategoriesRepository;

    @Transactional
    public Integer updateProductCategory(Integer id, String name) {
        String newImageFileName = null;

        ProductCategory productCategory = productCategoriesRepository.findOne(id);
        if (productCategory == null) {
            throw new ProductCategoryNotFoundException(id);
        }

        if (StringUtils.isNotEmpty(name)) {
            productCategory.setName(name);
        }

        productCategory = productCategoriesRepository.save(productCategory);

        return productCategory.getId();

    }

}
