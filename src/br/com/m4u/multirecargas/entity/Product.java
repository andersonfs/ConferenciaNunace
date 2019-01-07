package br.com.m4u.multirecargas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private String channel;
    private List<ProductValue> values;

    public Product(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<ProductValue> getValues() {
        return values;
    }

    public void setValues(List<ProductValue> values) {
        this.values = values;
    }

    public void addValue(final String ddd, final Integer amount) {
        if (this.values == null) {
            this.values = new ArrayList<>();
        }

        this.values.add(new ProductValue(this, ddd, amount));
    }
}
