package com.lambda511.api.v1.controllers;

import com.lambda511.model.Product;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.services.add.AddProductServiceImpl;
import com.lambda511.model.services.delete.DeleteProductServiceImpl;
import com.lambda511.model.services.query.QueryProductServiceImpl;
import com.lambda511.model.services.update.UpdateProductServiceImpl;
import com.lambda511.api.v1.response.ErrorMessage;
import com.lambda511.api.v1.response.Response;
import com.lambda511.api.v1.response.ResponseFactory;
import com.lambda511.util.PageableFactory;
import com.lambda511.util.date.DateParsingUtil;
import com.lambda511.util.paging.SliceContentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by blitzer on 22.03.2016.
 */

@RestController
public class ProductsController extends APIV1BaseController {

    @Autowired
    private AddProductServiceImpl addService;

    @Autowired
    private UpdateProductServiceImpl updateService;

    @Autowired
    private DeleteProductServiceImpl deleteService;

    @Autowired
    private QueryProductServiceImpl queryService;

    @Autowired
    private DateParsingUtil dateParsingUtil;

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private PageableFactory pageableFactory;

    @RequestMapping(method = RequestMethod.POST, value = "/add_new_product", consumes = "multipart/form-data")
    public Response<Long> addProduct(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
                                     @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
                                     @RequestParam(value = "quantity", required = false, defaultValue = "0") Integer quantity,
                                     @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        return createSuccessResponse(
                addService.addProduct(barcode, name, quantity, categoryId, imageFile));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update_product", consumes = "multipart/form-data")
    public Response<Long> updateProduct(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                                        @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                        @RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        return createSuccessResponse(
                updateService.updateProduct(id, barcode, name, categoryId, imageFile));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete_product")
    public Response<Void> deleteProduct(@RequestParam(value = "id") Long id) {
        deleteService.deleteProduct(id);
        return createSuccessResponse(null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add_quantity_for_product")
    public Response<Long> addProductQuantity(@RequestParam(value = "id") Long id,
                                   @RequestParam(value = "quantity")Integer quantity) {

        return createSuccessResponse(
                addService.addProductQuantity(id, Math.abs(quantity)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/remove_quantity_for_product")
    public Response<Long> removeProductQuantity(@RequestParam(value = "id") Long id,
                                   @RequestParam(value = "quantity")Integer quantity) {

        return createSuccessResponse(
                addService.removeProductQuantity(id, quantity));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/find_product_by_id")
    public Response<Product> findProductById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return createSuccessResponse (queryService.findProductById(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_products")
    public Response<SliceContentWrapper<Product>> findProducts(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                         @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
                                                                         Pageable p) {
        return createSuccessResponse(
                queryService.findProductsByNameAndBarcode(name, barcode,
                        pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_products_updated_by_date")
    public Response<SliceContentWrapper<Product>> findProductsByDate(@RequestParam(value = "startDate", required = false) String startDate,
                                                                         @RequestParam(value = "endDate", required = false) String endDate,
                                                                         Pageable p) {

        return createSuccessResponse(
                queryService.findProductsModifiedByDate(
                        dateParsingUtil.parseDateDefaultToMin(startDate),
                        dateParsingUtil.parseDateDefaultToMax(endDate),
                        pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_products_with_image")
    public Response<SliceContentWrapper<Product>> findProductsWithImage(Pageable p) {
        return createSuccessResponse(
                queryService.findProductsHavingImageFile(pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_product_names")
    public Response<SliceContentWrapper<String>> findProductsNamesContaining(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                             @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
                                                                             Pageable p) {
        return createSuccessResponse(
                queryService.findProductNamesContaining(name, barcode,
                        pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_product_barcodes")
    public Response<SliceContentWrapper<String>> findProductsBarcodesContaining(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                                @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
                                                                                Pageable p) {
        return createSuccessResponse(
                queryService.findProductBarcodesContaining(name, barcode,
                        pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    private <T> Response<T> createSuccessResponse(T content) {
        return responseFactory.createSuccessResponse(content);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public Response<ErrorMessage> productNotFoundHandler(ProductNotFoundException ex) {
        return responseFactory.createErrorResponse(new ErrorMessage(
                ErrorMessage.ErrorType.RESOURCE_NOT_FOUND,
                String.format("Product not found! ID: [%s]", ex.getProductId())));
    }

}
