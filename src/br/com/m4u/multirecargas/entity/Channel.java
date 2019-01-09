package br.com.m4u.multirecargas.entity;

import java.io.Serializable;
import java.util.List;

public class Channel implements Serializable {
    private String name;
    private List<ChannelValue> values;

    public Channel(String name, List<ChannelValue> values) {
        this.name = name;
        for (final ChannelValue value : values) {
            value.setChannel(this);
        }
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Channel(String name) {
        this.name = name;
    }

    public List<ChannelValue> getValues() {
        return values;
    }

    public void setValues(List<ChannelValue> values) {
        this.values = values;
    }
}
