package com.lambda511.model.services.delete;

import com.lambda511.model.Product;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.repository.ProductsRepository;
import com.lambda511.util.file.ImageResourcesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class DeleteProductServiceImpl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ImageResourcesUtil imageResourcesUtil;

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productsRepository.findOne(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        String imageFileName = product.getImageFileName();
        productsRepository.delete(id);

        if (StringUtils.isNotEmpty(imageFileName)) {
            imageResourcesUtil.deleteImage(imageFileName);
        }

        //TODO: Decide if the stock history should be deleted also.
    }

}
