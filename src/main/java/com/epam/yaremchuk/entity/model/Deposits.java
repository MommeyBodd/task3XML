package com.epam.yaremchuk.entity.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "abstractDeposit"
})
@XmlRootElement(name = "deposits")

public class Deposits {

    List<AbstractDeposit> depositsList = new ArrayList<AbstractDeposit>();
    @XmlElementRef(name = "abstractDeposit", namespace = "http://www.example.com/deposits", type =
            JAXBElement.class)
    protected List<JAXBElement<? extends AbstractDeposit>> deposits;
    public List<JAXBElement<? extends AbstractDeposit>> getDeposits() {
        if (deposits == null) {
            deposits = new ArrayList<JAXBElement<? extends AbstractDeposit>>();
        }
        return this.deposits;
    }
}
