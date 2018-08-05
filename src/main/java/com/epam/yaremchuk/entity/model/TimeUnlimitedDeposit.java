package com.epam.yaremchuk.entity.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeUnlimitedDeposit", propOrder = { "minimalAmount" })


public class TimeUnlimitedDeposit extends AbstractDeposit {
    @XmlElement(name = "minimalAmount")
    private double minimalAmount;

    public TimeUnlimitedDeposit(String name,
                                String country,
                                int accountId,
                                double amount,
                                short profitInPercent,
                                double minimalAmount) {
        super(name, country, accountId, amount, profitInPercent);
        this.minimalAmount = minimalAmount;
    }

    public TimeUnlimitedDeposit() {

    }


    public double getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(double minimalAmount) {
        this.minimalAmount = minimalAmount;
    }
    @Override
    public String toString() {
        return "TimeUnlimitedDeposit{" +
                "name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", accountId=" + getAccountId() +
                ", amount=" + getAmount() +
                ", profitInPercent=" + getProfitInPercent() +
                ", minimalAmount=" + minimalAmount + '}';
    }
}
