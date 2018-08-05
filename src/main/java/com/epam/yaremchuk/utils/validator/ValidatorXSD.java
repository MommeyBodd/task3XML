package com.epam.yaremchuk.utils.validator;

import com.epam.yaremchuk.exception.DepositIOException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorXSD {
    private static final Logger log = Logger.getLogger(ValidatorXSD.class);
    private static final String LANGUAGE = XMLConstants.W3C_XML_SCHEMA_NS_URI;

    public  boolean validateXMLSchema(String xsdPath, String xmlPath){
        File schemaLocation = new File(xsdPath);
        try {
            SchemaFactory factory = SchemaFactory.newInstance(LANGUAGE);
            // Schema creation
            Schema schema = factory.newSchema(schemaLocation);
            // create validator on base of schema
            Validator validator = schema.newValidator();
            // check is document correct
            Source source = new StreamSource(xmlPath);
            validator.validate(source);
            System.out.println(xmlPath + " is valid.");

        } catch ( SAXException e ) {
            log.error(e.getMessage(), e);
            return false;
        } catch ( IOException e ) {
            log.error(new DepositIOException("Can`t get info from xml/xsd file: "));
            return false;
        }
        return true;
    }
}

