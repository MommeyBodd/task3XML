package com.epam.yaremchuk.parser;

import com.epam.yaremchuk.entity.model.AbstractDeposit;
import com.epam.yaremchuk.entity.model.Deposits;
import com.epam.yaremchuk.entity.model.TimeLimitedDeposit;
import com.epam.yaremchuk.entity.model.TimeUnlimitedDeposit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;

public class ObjectFactory {
    private final static QName _AbstractDeposit_QNAME =
            new QName("http://www.example.com/deposits", "abstractDeposit");
    private final static QName _TimeUnlimitedDeposit_QNAME =
            new QName("http://www.example.com/deposits", "timeUnlimitedDeposit");
    private final static QName _TimeLimitedDeposit_QNAME =
            new QName("http://www.example.com/deposits", "timeLimitedDeposit");
    public ObjectFactory() {
    }
    public Deposits createStudents() {
        return new Deposits();
    }
    public AbstractDeposit createPersonType() {
        return new AbstractDeposit();
    }
    public TimeUnlimitedDeposit createStudent() {
        return new TimeUnlimitedDeposit();
    }
    public TimeLimitedDeposit createAbiturient() {
        return new TimeLimitedDeposit();
    }
    @XmlElementDecl(namespace = "http://www.example.com/deposits", name = "abstractDeposit")
    public JAXBElement<AbstractDeposit> createPerson(AbstractDeposit value) {
        return new JAXBElement<AbstractDeposit>(_AbstractDeposit_QNAME, AbstractDeposit.class, null, value);
    }
    @XmlElementDecl(namespace = "http://www.example.com/deposits", name = "timeLimitedDeposit",
            substitutionHeadNamespace = "http://www.example.com/deposits",
            substitutionHeadName = "abstractDeposit")
    public JAXBElement<TimeLimitedDeposit> createStudent(TimeLimitedDeposit value) {
        return new JAXBElement<TimeLimitedDeposit>(_TimeLimitedDeposit_QNAME, TimeLimitedDeposit.class, null, value);
    }
    @XmlElementDecl(namespace = "http://www.example.com/deposits", name = "timeUnlimitedDeposit",
            substitutionHeadNamespace = "http://www.example.com/deposits",
            substitutionHeadName = "abstractDeposit")
    public JAXBElement<TimeUnlimitedDeposit> createAbiturient(TimeUnlimitedDeposit value) {
        return new JAXBElement<TimeUnlimitedDeposit>(_TimeLimitedDeposit_QNAME, TimeUnlimitedDeposit.class, null, value);
    }

}
