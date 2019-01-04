package br.com.m4u.multirecargas.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String channel;
    private String ddd;
    private Integer amount;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public Product(String channel, String ddd, Integer amount) {
        this.channel = channel;
        this.ddd = ddd;
        this.amount = amount;
    }
}
