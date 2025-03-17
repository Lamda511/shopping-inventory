package com.lambda511.model.services.query;

import com.lambda511.model.Product;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.repository.ProductsRepository;
import com.lambda511.util.paging.SliceContentWrapper;
import com.lambda511.util.paging.SliceContentWrapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by blitzer on 23.04.2016.
 */
@Component
public class QueryProductServiceImpl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SliceContentWrapperFactory sliceContentWrapperFactory;

    @Transactional(readOnly = true)
    public Product findProductById(Long productId) {
        Product product = productsRepository.findOne(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        return product;
    }

    @Transactional(readOnly = true)
    public SliceContentWrapper<Product> findProductsByNameAndBarcode(String name, String barcode, Pageable pageRequest) {
        return createProductsSlice(productsRepository.findByNameIgnoreCaseContainingAndBarcodeIgnoreCaseContainingOrderByNameAsc(name, barcode, pageRequest));
    }

    @Transactional(readOnly = true)
    public SliceContentWrapper<Product> findProductsModifiedByDate(Date startDate, Date endDate, Pageable pageable) {
        return createProductsSlice(productsRepository.findByStockLastUpdateDateBetweenOrderByStockLastUpdateDateDesc(startDate, endDate, pageable));
    }

    public SliceContentWrapper<Product> findProductsHavingImageFile(Pageable pageable) {
        return createProductsSlice(productsRepository.findByImageFileNameNotNullOrderByStockLastUpdateDateDesc(pageable));
    }

    @Transactional (readOnly = true)
    public SliceContentWrapper<String> findProductNamesContaining(String name, String barcode, Pageable pageable) {
        return createProductsSlice(productsRepository.findNameByNameAndBarcode(name, barcode, pageable));
    }

    @Transactional (readOnly = true)
    public SliceContentWrapper<String> findProductBarcodesContaining(String name, String barcode, Pageable pageable) {
        return createProductsSlice(productsRepository.findBarcodeByNameAndBarcode(name, barcode, pageable));
    }

    private <T> SliceContentWrapper<T> createProductsSlice(Slice<T> slice) {
        return sliceContentWrapperFactory.createSliceContentWrapper(slice.getNumber(), slice.getNumberOfElements(), slice.getSize(), slice.hasNext(), slice.hasPrevious(), slice.getContent());
    }
}
