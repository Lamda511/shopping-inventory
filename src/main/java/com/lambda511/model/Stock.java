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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by blitzer on 24.03.2016.
 */

@Entity
@Table(name = "stock_info")
public class Stock {

    @JsonProperty(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(value = "quantity")
    @Column(name = "quantity")
    private Integer quantity;

    @JsonProperty(value = "lastUpdateDate")
    @JsonFormat(pattern = DateFormatProvider.DATE_FORMAT)
    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @JsonProperty(value = "lastOperation")
    @Column(name = "last_operation")
    private Byte lastOperation;

    @JsonIgnore
    @OneToOne(fetch=FetchType.LAZY, mappedBy="stock")
    private Product product;

    public Stock() {
    }

    public Stock(Integer quantity, Date lastUpdateDate, Byte lastOperation) {
        this.quantity = quantity;
        this.lastUpdateDate = lastUpdateDate;
        this.lastOperation = lastOperation;
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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Byte getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(Byte lastOperation) {
        this.lastOperation = lastOperation;
    }
}
