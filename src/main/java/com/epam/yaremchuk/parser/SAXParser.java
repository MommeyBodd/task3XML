package com.epam.yaremchuk.parser;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import com.epam.yaremchuk.handler.SAXDepositHandler;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.util.Set;

public class SAXParser implements Parser {
    private static final Logger log = Logger.getLogger(SAXParser.class);

    @Override
    public Set<AbstractDeposit> create(String fileName) {
        SAXParser saxParser = new SAXParser();
        return saxParser.buildSetDeposits(fileName);
    }

    public Set<AbstractDeposit> buildSetDeposits(String fileName) {
        SAXDepositHandler depositHandler = new SAXDepositHandler();
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(depositHandler);
            reader.parse(fileName);
        } catch (IOException ex){
            log.error("File error or I/O error: " + ex.getMessage(), ex);
        } catch (SAXException ex){
            log.error("Parsing failure: " + ex.getMessage(), ex);
        }

        return depositHandler.getDeposit();
    }

}
