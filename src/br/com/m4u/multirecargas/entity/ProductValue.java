package br.com.m4u.multirecargas.entity;

import java.io.Serializable;

public class ProductValue implements Serializable {
    private Product product;
    private String ddd;
    private Integer amount;

    public ProductValue(final Product product, final String ddd, final Integer amount) {
        this.product = product;
        this.ddd = ddd;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
