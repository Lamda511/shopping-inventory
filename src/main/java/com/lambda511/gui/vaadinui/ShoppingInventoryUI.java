package com.lambda511.gui.vaadinui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by blitzer on 02.08.2016.
 */
@SpringUI
@Theme("valo")
public class ShoppingInventoryUI extends UI {

    @Autowired
    private ProductsInfoTableView productsTableView;

    @Autowired
    private ProductsImagesTableView productsImagesTableView;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Page.getCurrent().setTitle("Shopping Inventory");

        TabSheet sheet = new TabSheet();
        sheet.setSizeFull();
        sheet.addComponents(this.productsTableView, this.productsImagesTableView);
        this.setContent(sheet);
    }




}
