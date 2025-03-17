package com.lambda511.gui.vaadinui;

import com.lambda511.model.services.query.QueryProductServiceImpl;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by blitzer on 07.09.2016.
 */
@UIScope
@SpringComponent
public class ProductsImagesTableView extends VerticalLayout implements InitializingBean {

    @Autowired
    private QueryProductServiceImpl queryProductService;

//    @Autowired
//    private EventBus eventBus;

    @PostConstruct
    protected void init() {
        setCaption("Products Images");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
