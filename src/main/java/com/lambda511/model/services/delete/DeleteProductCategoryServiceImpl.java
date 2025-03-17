package com.lambda511.model.services.delete;

import com.lambda511.model.ProductCategoryNotFoundException;
import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.ProductCategory;
import com.lambda511.util.file.ImageResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class DeleteProductCategoryServiceImpl {

    @Autowired
    private ProductsCategoryRepository productsCategoryRepository;

    @Autowired
    private ImageResourcesUtil imageResourcesUtil;

    @Transactional
    public void deleteProductCategory(Integer id) {
        ProductCategory productCategory = productsCategoryRepository.findOne(id);
        if (productCategory == null) {
            throw new ProductCategoryNotFoundException(id);
        }

        productsCategoryRepository.delete(id);
    }

}
