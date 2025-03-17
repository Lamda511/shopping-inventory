package com.lambda511.model.services.add;

import com.lambda511.model.Product;
import com.lambda511.model.ProductCategory;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.Stock;
import com.lambda511.model.StockHistory;
import com.lambda511.model.repository.ProductsCategoryRepository;
import com.lambda511.model.repository.ProductsRepository;
import com.lambda511.model.repository.StocksHistoryRepository;
import com.lambda511.util.DateProvider;
import com.lambda511.util.file.FileIOError;
import com.lambda511.util.file.ImageResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by blitzer on 22.04.2016.
 */

@Component
public class AddProductServiceImpl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private StocksHistoryRepository stocksHistoryRepository;

    @Autowired
    private ProductsCategoryRepository productsCategoryRepository;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    private ImageResourcesUtil imageResourcesUtil;

    @Transactional
    public Long addProduct(String barcode, String name, Integer categoryId, Integer quantity, MultipartFile multipartImageFile) {
        try (InputStream inputStream = multipartImageFile.getInputStream()) {
            return addProduct(barcode, name, categoryId, quantity, inputStream);
        } catch (IOException e) {
            throw new FileIOError("Could not read the image file!", e);
        }
    }

    @Transactional
    public Long addProduct(String barcode, String name, Integer categoryId, Integer quantity, InputStream inputStream) {
        String newImageFileName = null;
        try {
            newImageFileName = getNewImageFileName(inputStream);
            ProductCategory productCategory = getProductCategory(categoryId);

            Date currentDate = dateProvider.getCurrentDate();

            Stock stock = new Stock(quantity, currentDate, (byte)((quantity >= 0) ? 1 : -1));
            Product product = new Product(barcode, name, productCategory, newImageFileName, stock);
            Product savedProduct = productsRepository.save(product);

            saveToHistory(savedProduct, quantity, currentDate);

            return savedProduct.getId();
        } catch (Exception ex) {
            if (newImageFileName != null) {
                imageResourcesUtil.deleteImage(newImageFileName);
            }

            throw new FileIOError(ex.getMessage(), ex);
        }
    }

    private String getNewImageFileName(InputStream inputStream) throws IOException {
        String newImageFileName = null;
        if (inputStream != null) {
            newImageFileName = imageResourcesUtil.storeImageFromInputStream(inputStream);
        }
        return newImageFileName;
    }

    private ProductCategory getProductCategory(Integer categoryId) {
        ProductCategory productCategory = null;
        if (categoryId != null) {
            productCategory = productsCategoryRepository.findById(categoryId);
        }
        return productCategory;
    }

    @Transactional
    public Long addProductQuantity(Long id, Integer quantity) {
        Date currentDate = dateProvider.getCurrentDate();

        Product product = getProductOrThrowException(id);
        Product updatedProduct = updateStockInfo(product, quantity, currentDate);
        saveToHistory(updatedProduct, quantity, currentDate);

        return product.getId();
    }

    public Long removeProductQuantity(Long id, Integer quantity) {
        return addProductQuantity(id, -Math.abs(quantity));
    }

    private Product getProductOrThrowException(Long id) {
        Product product = productsRepository.findOne(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    private Product updateStockInfo(Product product, Integer newQuantity, Date lastUpdatedDate) {
        Stock stock = product.getStock();
        stock.setQuantity(stock.getQuantity() + newQuantity);
        stock.setLastUpdateDate(lastUpdatedDate);
        stock.setLastOperation((byte)(newQuantity >=0 ? 1 : -1));

        return productsRepository.save(product);
    }

    private void saveToHistory(Product product, Integer quantity, Date lastUpdatedDate) {
        StockHistory stockHistory = new StockHistory(product, quantity, lastUpdatedDate);
        stocksHistoryRepository.save(stockHistory);
    }


}
