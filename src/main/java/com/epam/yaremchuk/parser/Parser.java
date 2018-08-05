package com.epam.yaremchuk.parser;


import com.epam.yaremchuk.entity.model.AbstractDeposit;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Set;

public interface Parser {
    Set<AbstractDeposit> create(String fileName);

}
