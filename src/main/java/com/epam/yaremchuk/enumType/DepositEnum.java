package com.epam.yaremchuk.enumType;

public enum DepositEnum {
    DEPOSITS("deposits"),
    BANKNAME("bankName"),
    BANKCOUNTRY("bankCountry"),
    TIMELIMITEDDEPOSIT("timeLimitedDeposit"),
    TIMEUNLIMITEDDEPOSIT("timeUnlimitedDeposit"),
    ACCOUNTID("accountId"),
    AMOUNT("amount"),
    PROFIT("profit"),
    MINIMALAMOUNT("minimalAmount"),
    PERIODINYEAR("periodInYear");

    private String value;
    DepositEnum(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
