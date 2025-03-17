package com.lambda511.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by blitzer on 24.06.2016.
 */
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @JsonProperty(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "productCategory", targetEntity = Product.class, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public ProductCategory() {
    }

    public ProductCategory(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
