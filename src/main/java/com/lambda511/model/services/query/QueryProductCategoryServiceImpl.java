package com.lambda511.model.services.query;

import com.lambda511.model.ProductCategoryNotFoundException;
import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.ProductCategory;
import com.lambda511.util.paging.SliceContentWrapper;
import com.lambda511.util.paging.SliceContentWrapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class QueryProductCategoryServiceImpl {

    @Autowired
    private ProductsCategoryRepository productsCategoryRepository;

    @Autowired
    private SliceContentWrapperFactory sliceContentWrapperFactory;

    @Transactional(readOnly = true)
    public ProductCategory findProductCategoryById(Integer productCategoryId) {
        ProductCategory productCategory = productsCategoryRepository.findById(productCategoryId);
        if (productCategory == null) {
            throw new ProductCategoryNotFoundException(productCategoryId);
        }

        return productCategory;
    }

    @Transactional(readOnly = true)
    public SliceContentWrapper<ProductCategory> findProductCategories(String name, Pageable pageable) {
        return createProductsSlice(productsCategoryRepository.findByNameIgnoreCaseContaining(name, pageable));
    }

    private <T> SliceContentWrapper<T> createProductsSlice(Slice<T> slice) {
        return sliceContentWrapperFactory.createSliceContentWrapper(slice.getNumber(), slice.getNumberOfElements(), slice.getSize(), slice.hasNext(), slice.hasPrevious(), slice.getContent());
    }
}
