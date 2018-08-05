package com.epam.yaremchuk.parser;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class JaxBParser implements Parser {
    private static final Logger log = Logger.getLogger(JaxBParser.class);

    @Override
    public Set<AbstractDeposit> create(String fileName) {

        Set<AbstractDeposit> deposits = new HashSet<AbstractDeposit>();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AbstractDeposit.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            String schemaName = "src/main/resources/deposits.xsd";
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaLocation = new File(schemaName);
            // создание схемы и перадача ее демарашаллизатору
            Schema schema = factory.newSchema(schemaLocation);
            unmarshaller.setSchema(schema);
            AbstractDeposit currentDeposit =
                    (AbstractDeposit) unmarshaller.unmarshal(new File(fileName));
            System.out.println(currentDeposit);
        } catch (JAXBException ex) {
            log.error(ex.getMessage(), ex);
        } catch (SAXException ex) {
            log.error(ex.getMessage(), ex);
        }

        return deposits;
    }

    public static void main(String[] args) {
        JaxBParser jaxBParser = new JaxBParser();
        System.out.println(jaxBParser.create("src/main/resources/deposits.xml"));
    }
}
