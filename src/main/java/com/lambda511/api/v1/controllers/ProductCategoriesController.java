package com.lambda511.api.v1.controllers;

import com.lambda511.model.services.add.AddProductCategoryServiceImpl;
import com.lambda511.model.services.delete.DeleteProductCategoryServiceImpl;
import com.lambda511.model.ProductCategoryNotFoundException;
import com.lambda511.model.ProductNotFoundException;
import com.lambda511.model.services.query.QueryProductCategoryServiceImpl;
import com.lambda511.model.services.update.UpdateProductCategoryServiceImpl;
import com.lambda511.model.ProductCategory;
import com.lambda511.api.v1.response.ErrorMessage;
import com.lambda511.api.v1.response.Response;
import com.lambda511.api.v1.response.ResponseFactory;
import com.lambda511.util.PageableFactory;
import com.lambda511.util.paging.SliceContentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by blitzer on 22.03.2016.
 */

@RestController
public class ProductCategoriesController extends APIV1BaseController {

    @Autowired
    private AddProductCategoryServiceImpl addService;

    @Autowired
    private UpdateProductCategoryServiceImpl updateService;

    @Autowired
    private DeleteProductCategoryServiceImpl deleteService;

    @Autowired
    private QueryProductCategoryServiceImpl queryService;

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private PageableFactory pageableFactory;

    @RequestMapping(method = RequestMethod.POST, value = "/add_new_product_category")
    public Response<Integer> addProductCategory(@RequestParam(value = "name", required = false, defaultValue = "") String name) {

        return createSuccessResponse(
                addService.addProductCategory(name));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update_product_category")
    public Response<Integer> updateProductCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id,
                                                @RequestParam(value = "name", required = false, defaultValue = "") String name) {

        return createSuccessResponse(
                updateService.updateProductCategory(id, name));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete_product_category")
    public Response<Void> deleteProductCategory(@RequestParam(value = "id") Integer id) {
        deleteService.deleteProductCategory(id);
        return createSuccessResponse(null);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/find_product_category_by_id")
    public Response<ProductCategory> findProductCategoryById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        return createSuccessResponse (queryService.findProductCategoryById(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find_product_categories")
    public Response<SliceContentWrapper<ProductCategory>> findProductCategories(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                            Pageable p) {
        return createSuccessResponse(
                queryService.findProductCategories(name,
                        pageableFactory.createPageRequestWith(p.getPageNumber(), p.getPageSize())));
    }

    private <T> Response<T> createSuccessResponse(T content) {
        return responseFactory.createSuccessResponse(content);
    }

    @ExceptionHandler(ProductCategoryNotFoundException.class)
    public Response<ErrorMessage> productCategoryNotFoundHandler(ProductNotFoundException ex) {
        return responseFactory.createErrorResponse(new ErrorMessage(
                ErrorMessage.ErrorType.RESOURCE_NOT_FOUND,
                String.format("Product category not found! ID: [%s]", ex.getProductId())));
    }

}
