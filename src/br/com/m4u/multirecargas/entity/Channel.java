package br.com.m4u.multirecargas.entity;

import java.io.Serializable;
import java.util.List;

public class Channel implements Serializable {
    private String name;
    private List<ChannelValue> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Channel(String name) {
        this.name = name;
    }

    public Channel(String name, List<ChannelValue> values) {
        this.name = name;
        this.values = values;
    }
}
