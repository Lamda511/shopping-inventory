package com.lambda511.gui.vaadinui;

import com.lambda511.model.Product;
import com.lambda511.model.services.query.QueryProductServiceImpl;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by blitzer on 07.09.2016.
 */
@UIScope
@SpringComponent
public class ProductsInfoTableView extends VerticalLayout implements InitializingBean {

    @Autowired
    private QueryProductServiceImpl queryProductService;

//    @Autowired
//    private EventBus eventBus;

    private Grid productsGrid = new Grid("Products");
    private TextField nameFilterText = new TextField();

    @PostConstruct
    protected void init() {
        setCaption("Products Info");

        List<Product> productsList = queryProductService.findProductsByNameAndBarcode("", "", new PageRequest(0, 20)).getElements();
        final BeanItemContainer<Product> productsContainer = new BeanItemContainer<>(Product.class, productsList);
        productsContainer.addNestedContainerProperty("productCategory.name");
        productsContainer.addNestedContainerProperty("stock.quantity");
        productsContainer.addNestedContainerProperty("stock.lastUpdateDate");
        productsContainer.addNestedContainerProperty("stock.lastOperation");

        productsGrid.setContainerDataSource(productsContainer);
        productsGrid.setColumns("name", "barcode", "productCategory.name", "stock.quantity", "stock.lastUpdateDate", "stock.lastOperation");
        productsGrid.getColumn("name").setHeaderCaption("Name");
        productsGrid.getColumn("barcode").setHeaderCaption("Barcode");
        productsGrid.getColumn("productCategory.name").setHeaderCaption("Product Category");
        productsGrid.getColumn("stock.quantity").setHeaderCaption("Quantity");
        productsGrid.getColumn("stock.lastUpdateDate").setHeaderCaption("Last Updated");
        productsGrid.getColumn("stock.lastOperation").setHeaderCaption("Last Operation");

        addComponent(productsGrid);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
