package com.epam.yaremchuk.parser;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import com.epam.yaremchuk.entity.model.TimeLimitedDeposit;
import com.epam.yaremchuk.entity.model.TimeUnlimitedDeposit;
import com.epam.yaremchuk.enumType.PeriodInYearType;
import org.apache.log4j.Logger;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StAXParser implements Parser {
    private static final Logger log = Logger.getLogger(StAXParser.class);
    private static final String TIME_LIMITED_DEPOSIT = "timeLimitedDeposit";
    private static final String TIME_UNLIMITED_DEPOSIT = "timeUnlimitedDeposit";
    private static final String ACCOUNT_ID = "accountId";
    private static final String AMOUNT = "amount";
    private static final String PROFIT_IN_PERCENT = "profit";
    private static final String PERIOD_IN_YEAR = "periodInYear";
    private static final String MINIMAL_AMOUNT = "minimalAmount";

    @Override
    public Set<AbstractDeposit> create(String fileName) {
        StAXParser staxParser = new StAXParser();
        return staxParser.buildSetDeposits(fileName);
    }

    private Set<AbstractDeposit> buildSetDeposits(String fileName) {
        Set<AbstractDeposit> deposits = new HashSet<AbstractDeposit>();
        AbstractDeposit currentDeposit = null;
        boolean accountId = false;
        boolean amount = false;
        boolean profit = false;
        boolean periodInYear = false;
        boolean minimalAmount = false;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader
                    = factory.createXMLEventReader(new FileReader(fileName));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String currentElement = startElement.getName().getLocalPart();

                        if (currentElement.equalsIgnoreCase(TIME_LIMITED_DEPOSIT)) {
                            currentDeposit = new TimeLimitedDeposit();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String bankName = attributes.next().getValue();
                            String bankCountry = attributes.next().getValue();
                            currentDeposit.setName(bankName);
                            currentDeposit.setCountry(bankCountry);

                        } else if (currentElement.equalsIgnoreCase(TIME_UNLIMITED_DEPOSIT)){
                            currentDeposit = new TimeUnlimitedDeposit();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String bankName = attributes.next().getValue();
                            String bankCountry = attributes.next().getValue();
                            currentDeposit.setName(bankName);
                            currentDeposit.setCountry(bankCountry);

                        } else if (currentElement.equalsIgnoreCase(ACCOUNT_ID)) {
                            accountId = true;
                        } else if (currentElement.equalsIgnoreCase(AMOUNT)) {
                            amount = true;
                        } else if (currentElement.equalsIgnoreCase(PROFIT_IN_PERCENT)) {
                            profit = true;
                        }
                        else if (currentElement.equalsIgnoreCase(PERIOD_IN_YEAR)) {
                            periodInYear = true;
                        }
                        else if (currentElement.equalsIgnoreCase(MINIMAL_AMOUNT)) {
                            minimalAmount = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        String dataCharacteristic = characters.getData();
                        if(accountId) {
                            int character = Integer.parseInt(dataCharacteristic);
                            currentDeposit.setAccountId(character);
                            accountId = false;
                        }
                        if(amount) {
                            double character = Double.parseDouble(dataCharacteristic);
                            currentDeposit.setAmount(character);
                            amount = false;
                        }
                        if(profit) {
                            int character = Integer.parseInt(dataCharacteristic);
                            currentDeposit.setProfitInPercent(character);
                            profit = false;
                        }
                        if(periodInYear) {
                            PeriodInYearType period = PeriodInYearType.valueOf(dataCharacteristic);
                            ((TimeLimitedDeposit) currentDeposit).setPeriodInYear(period);
                            periodInYear = false;
                        }
                        if(minimalAmount) {
                            double minimalAmountDouble = Double.parseDouble(dataCharacteristic);
                            ((TimeUnlimitedDeposit)currentDeposit).setMinimalAmount(minimalAmountDouble);
                            minimalAmount = false;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        currentElement = endElement.getName().getLocalPart();

                        if(currentElement.equalsIgnoreCase(TIME_LIMITED_DEPOSIT)
                                || currentElement.equalsIgnoreCase(TIME_UNLIMITED_DEPOSIT)) {
                            deposits.add(currentDeposit);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            log.error("ошибка I/О потока: " + e.getMessage(), e);
        } catch (XMLStreamException e) {
            log.error("ошибка XMLS потока в StAX парсере: " + e.getMessage(), e);
        }
        return deposits;
    }

}

