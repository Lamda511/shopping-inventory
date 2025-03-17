package com.lambda511.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by blitzer on 22.03.2016.
 */

@Entity
@Table(name = "products")
public class Product {

    @JsonProperty(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(value = "barcode")
    @Column(name = "barcode")
    private String barcode;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;

    @JsonProperty(value = "productCategory")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    private ProductCategory productCategory;

    @JsonProperty(value = "imageFileName")
    @Column(name = "image_file_name")
    private String imageFileName;

    @JsonProperty(value = "stockInfo")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public Product() {
    }

    public Product(String barcode, String name, ProductCategory productCategory, String imageFileName, Stock stock) {
        this.barcode = barcode;
        this.name = name;
        this.productCategory = productCategory;
        this.imageFileName = imageFileName;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting object to JSON", e);
        }
    }

//    public static void main(String[] args) throws Exception {
//
//        Product product = new Product("Product 1", 12345l);
//        System.out.println(new ObjectMapper().writerWithView(ProductViews.Extended.class).writeValueAsString(product));
//
//    }
}
