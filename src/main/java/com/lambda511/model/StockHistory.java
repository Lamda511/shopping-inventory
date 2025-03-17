package com.lambda511.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lambda511.util.DateFormatProvider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by blitzer on 29.06.2016.
 */

@Entity
@Table(name = "stock_history")
public class StockHistory {

    @JsonProperty(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @JsonProperty(value = "quantity")
    @Column(name = "quantity")
    private Integer quantity;

    @JsonProperty(value = "date")
    @JsonFormat(pattern = DateFormatProvider.DATE_FORMAT)
    @Column(name = "record_date")
    private Date date;

    public StockHistory() {

    }

    public StockHistory(Product product, Integer quantity, Date date) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
