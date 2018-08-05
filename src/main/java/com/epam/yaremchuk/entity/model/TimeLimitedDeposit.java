package com.epam.yaremchuk.entity.model;

import com.epam.yaremchuk.enumType.PeriodInYearType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeLimitedDeposit", propOrder = { "periodInYear" })

public class TimeLimitedDeposit extends AbstractDeposit {
    @XmlElement(name = "periodInYear")

    private PeriodInYearType periodInYear;

    public TimeLimitedDeposit(String name,
                              String country,
                              int accountId,
                              double amount,
                              short profitInPercent,
                              PeriodInYearType periodInYear){
        super(name, country, accountId, amount, profitInPercent);
        this.periodInYear = periodInYear;
    }

    public TimeLimitedDeposit() { }

    public PeriodInYearType getPeriodInYear() {
        return periodInYear;
    }

    public void setPeriodInYear(PeriodInYearType periodInYear) {
        this.periodInYear = periodInYear;
    }

    @Override
    public String toString() {
        return  "TimeLimitedDeposit{" +
                "name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", accountId=" + getAccountId() +
                ", amount=" + getAmount() +
                ", profitInPercent=" + getProfitInPercent() +
                ", periodInYear=" + periodInYear +
                '}';
    }
}
