package com.lambda511.model.services.update;

import com.lambda511.model.Product;
import com.lambda511.model.ProductCategory;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.repository.ProductsRepository;
import com.lambda511.util.file.FileIOError;
import com.lambda511.util.file.ImageResourcesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class UpdateProductServiceImpl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsCategoryRepository productCategoriesRepository;

    @Autowired
    private ImageResourcesUtil imageResourcesUtil;

    @Transactional
    public Long updateProduct(Long id, String barcode, String name, Integer categoryId, MultipartFile multipartImageFile) {
        try (InputStream inputStream = multipartImageFile.getInputStream()) {
            return updateProduct(id, barcode, name, categoryId, inputStream);
        } catch (IOException e) {
            throw new FileIOError("Could not read the image file!", e);
        }
    }

    @Transactional
    public Long updateProduct(Long id, String barcode, String name, Integer categoryId, InputStream inputStream) {
        String newImageFileName = null;

        Product product = getProduct(id);

        try {
            String oldImageFileName = null;
            if (inputStream != null) {
                newImageFileName = imageResourcesUtil.storeImageFromInputStream(inputStream);
                oldImageFileName = product.getImageFileName();
                product.setImageFileName(newImageFileName);
            }

            if (StringUtils.isNotEmpty(barcode)) {
                product.setBarcode(barcode);
            }

            if (StringUtils.isNotEmpty(name)) {
                product.setName(name);
            }

            if (categoryId != null && categoryId > 0) {
                ProductCategory productCategory = productCategoriesRepository.findById(categoryId);
                product.setProductCategory(productCategory);
            }

            Product savedProduct = productsRepository.save(product);
            long productId = savedProduct.getId();

            if (StringUtils.isNotEmpty(oldImageFileName)) {
                imageResourcesUtil.deleteImage(oldImageFileName);
            }

            return productId;

        } catch (Exception ex) {
            if (newImageFileName != null) {
                imageResourcesUtil.deleteImage(newImageFileName);
            }
            throw new FileIOError(ex.getMessage(), ex);
        }
    }

    private Product getProduct(Long id) {
        Product product = productsRepository.findOne(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

}
