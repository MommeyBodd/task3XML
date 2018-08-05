package com.epam.yaremchuk.entity.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractDeposit", propOrder = {
        "accountId",
        "amount",
        "profit"
})
@XmlSeeAlso({
        TimeLimitedDeposit.class,
        TimeUnlimitedDeposit.class
})
public class AbstractDeposit {
    @XmlAttribute(name = "bankName")
    private String name;
    @XmlAttribute(name = "bankCountry")
    private String country;
    @XmlElement(required = true)
    private int accountId;
    @XmlElement(required = true)
    private double amount;
    @XmlElement(required = true)
    private int profitInPercent;

    public AbstractDeposit(String name,
                           String country,
                           int accountId,
                           double amount,
                           int profitInPercent) {
        this.name = name;
        this.country = country;
        this.accountId = accountId;
        this.amount = amount;
        this.profitInPercent = profitInPercent;
    }

    public AbstractDeposit() {
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public int getAccountId() { return accountId; }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public int getProfitInPercent() { return profitInPercent; }

    public void setProfitInPercent(int profitInPercent) {
        this.profitInPercent = profitInPercent;
    }

}
