package com.epam.yaremchuk.handler;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import com.epam.yaremchuk.entity.model.TimeLimitedDeposit;
import com.epam.yaremchuk.entity.model.TimeUnlimitedDeposit;
import com.epam.yaremchuk.enumType.DepositEnum;
import com.epam.yaremchuk.enumType.PeriodInYearType;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class SAXDepositHandler extends DefaultHandler {
    private static final String TIME_LIMITED_DEPOSIT = "timeLimitedDeposit";
    private static final String TIME_UNLIMITED_DEPOSIT = "timeUnlimitedDeposit";
    private static final int INDEX_FIRST_ATTRIBUTE = 0;
    private static final int COUNT_ATTRIBUTES = 2;
    private static final int INDEX_SECOND_ATTRIBUTE = 1;

    private Set<AbstractDeposit> deposits = new HashSet<AbstractDeposit>();
    private AbstractDeposit currentDeposit = null;
    private DepositEnum currentEnum = null;
    private EnumSet<DepositEnum> withText;

    public SAXDepositHandler() {
        DepositEnum leftBreak = DepositEnum.ACCOUNTID;
        DepositEnum rightBreak = DepositEnum.PERIODINYEAR;
        withText = EnumSet.range(leftBreak, rightBreak);
    }
    public Set<AbstractDeposit> getDeposit() {
        return deposits;
    }
    public void startElement(String uri, String localName, String qName, Attributes attrs) {

        if (TIME_UNLIMITED_DEPOSIT.equals(localName)) {
            currentDeposit = new TimeUnlimitedDeposit();
            currentDeposit.setName(attrs.getValue(INDEX_FIRST_ATTRIBUTE));
            if (attrs.getLength() == COUNT_ATTRIBUTES) {
                currentDeposit.setCountry(attrs.getValue(INDEX_SECOND_ATTRIBUTE));
            }
        } else if (TIME_LIMITED_DEPOSIT.equals(localName)){
            currentDeposit = new TimeLimitedDeposit();
            String attributeValueName = attrs.getValue(INDEX_FIRST_ATTRIBUTE);
            currentDeposit.setName(attributeValueName);
            if (attrs.getLength() == COUNT_ATTRIBUTES) {
                String attributeValueCountry = attrs.getValue(INDEX_SECOND_ATTRIBUTE);
                currentDeposit.setCountry(attributeValueCountry);
            }
        } else {
            String localNameUpperCase = localName.toUpperCase();
            DepositEnum depositEnum = DepositEnum.valueOf(localNameUpperCase);
            if (withText.contains(depositEnum)) {
                currentEnum = depositEnum;
            }
        }
    }
    public void endElement(String uri, String localName, String qName) {
        if (TIME_UNLIMITED_DEPOSIT.equals(localName) || TIME_LIMITED_DEPOSIT.equals(localName)) {
            deposits.add(currentDeposit);
        }
    }
    public void characters(char[] ch, int start, int length) {
        String characteristic = new String(ch, start, length).trim();
        if (currentEnum != null){
            switch (currentEnum) {
                case ACCOUNTID:
                    int accountId = Integer.parseInt(characteristic);
                    currentDeposit.setAccountId(accountId);
                    break;
                case AMOUNT:
                    double amount = Double.parseDouble(characteristic);
                    currentDeposit.setAmount(amount);
                    break;
                case PROFIT:
                    int profit = Integer.parseInt(characteristic);
                    currentDeposit.setProfitInPercent(profit);
                    break;
                case MINIMALAMOUNT:
                    double minimalAmount = Double.parseDouble(characteristic);
                    ((TimeUnlimitedDeposit)currentDeposit).setMinimalAmount(minimalAmount);
                    break;
                case PERIODINYEAR:
                    PeriodInYearType period = PeriodInYearType.valueOf(characteristic);
                    ((TimeLimitedDeposit) currentDeposit).setPeriodInYear(period);
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }

}
