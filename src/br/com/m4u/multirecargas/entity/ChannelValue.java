package br.com.m4u.multirecargas.entity;

import java.io.Serializable;

public class ChannelValue implements Serializable {
    private Channel channel;
    private String ddd;
    private Integer value;
    private Integer bonusValue;
    private Integer daysOfValidity;

    public ChannelValue(String ddd, Integer value, Integer bonusValue, Integer daysOfValidity) {
        this.ddd = ddd;
        this.value = value;
        this.bonusValue = bonusValue;
        this.daysOfValidity = daysOfValidity;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(Integer bonusValue) {
        this.bonusValue = bonusValue;
    }

    public Integer getDaysOfValidity() {
        return daysOfValidity;
    }

    public void setDaysOfValidity(Integer daysOfValidity) {
        this.daysOfValidity = daysOfValidity;
    }
}