package com.epam.yaremchuk.parser;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import com.epam.yaremchuk.entity.model.TimeLimitedDeposit;
import com.epam.yaremchuk.entity.model.TimeUnlimitedDeposit;
import com.epam.yaremchuk.enumType.PeriodInYearType;
import com.epam.yaremchuk.exception.DepositFileIsEmptyException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DOMParser implements Parser {

    private static final Logger log = Logger.getLogger(DOMParser.class);
    private DocumentBuilder documentBuilder;
    private static final String TIME_LIMITED_DEPOSIT = "timeLimitedDeposit";
    private static final String TIME_UNLIMITED_DEPOSIT = "timeUnlimitedDeposit";
    private static final String BANK_NAME = "bankName";
    private static final String BANK_COUNTRY = "bankCountry";
    private static final String ACCOUNT_ID = "accountId";
    private static final String AMOUNT = "amount";
    private static final String PROFIT_IN_PERCENT = "profit";
    private static final String PERIOD_IN_DAYS = "periodInYear";
    private static final String MINIMAL_AMOUNT = "minimalAmount";
    private static final int INDEX_OF_ELEMENT = 0;


    @Override
    public Set<AbstractDeposit> create(String fileName) {
        DOMParser domParser = new DOMParser();
        return domParser.buildSetDeposits(fileName);
    }

    public Set<AbstractDeposit> buildSetDeposits(String fileName) {
        Set<AbstractDeposit> deposits = new HashSet<AbstractDeposit>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            log.error(ex.getMessage(), ex);
        }
        try {
            if (documentBuilder == null){
                throw new DepositFileIsEmptyException("File is empty");
            }
            Document document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList depositsList = root.getElementsByTagName(TIME_UNLIMITED_DEPOSIT);
            for (int i = 0; i < depositsList.getLength(); i++) {
                Element depositElement = (Element)depositsList.item(i);
                AbstractDeposit deposit = buildTimeUnlimitedDeposit(depositElement);
                deposits.add(deposit);
            }
            depositsList = root.getElementsByTagName(TIME_LIMITED_DEPOSIT);
            for (int i = 0; i < depositsList.getLength(); i++) {
                Element depositElement = (Element)depositsList.item(i);
                AbstractDeposit deposit = buildTimeLimitedDeposit(depositElement);
                deposits.add(deposit);
            }
        } catch (IOException ex){
            log.error("File error or I/O error: " + ex.getMessage(), ex);
        } catch (SAXException ex){
            log.error("Parsing failure: " + ex.getMessage(), ex);
        }
        return deposits;
    }

    private AbstractDeposit buildTimeLimitedDeposit(Element depositElement){
        AbstractDeposit deposit = new TimeLimitedDeposit();
        String attributeBankName = depositElement.getAttribute(BANK_NAME);
        String attributeBankCountry = depositElement.getAttribute(BANK_COUNTRY);
        deposit.setName(attributeBankName);
        deposit.setCountry(attributeBankCountry);
        int accountId = Integer.parseInt(getElementTextContent(depositElement, ACCOUNT_ID));
        deposit.setAccountId(accountId);
        double amount = Double.parseDouble(getElementTextContent(depositElement, AMOUNT));
        deposit.setAmount(amount);
        int profit = Integer.parseInt(getElementTextContent(depositElement, PROFIT_IN_PERCENT));
        deposit.setProfitInPercent(profit);
        PeriodInYearType periodInYear = PeriodInYearType.valueOf(getElementTextContent(depositElement, PERIOD_IN_DAYS));
        ((TimeLimitedDeposit) deposit).setPeriodInYear(periodInYear);
        return deposit;
    }

    private AbstractDeposit buildTimeUnlimitedDeposit(Element depositElement){
        AbstractDeposit deposit = new TimeUnlimitedDeposit();

        String attributeBankName = depositElement.getAttribute(BANK_NAME);
        String attributeBankCountry = depositElement.getAttribute(BANK_COUNTRY);
        deposit.setName(attributeBankName);
        deposit.setCountry(attributeBankCountry);

        int accountId = Integer.parseInt(getElementTextContent(depositElement, ACCOUNT_ID));
        deposit.setAccountId(accountId);
        double amount = Double.parseDouble(getElementTextContent(depositElement, AMOUNT));
        deposit.setAmount(amount);
        int profit = Integer.parseInt(getElementTextContent(depositElement, PROFIT_IN_PERCENT));
        deposit.setProfitInPercent(profit);
        double minimalAmount = Double.parseDouble(getElementTextContent(depositElement, MINIMAL_AMOUNT));
        ((TimeUnlimitedDeposit)deposit).setMinimalAmount(minimalAmount);

        return deposit;
    }

    protected static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(INDEX_OF_ELEMENT);
        return node.getTextContent();
    }

}

