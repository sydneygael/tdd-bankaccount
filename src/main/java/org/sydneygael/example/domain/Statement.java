package org.sydneygael.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Statement {

    private List<StatementLine> statementLines = new ArrayList<>();

    public void add(Operation operation, Balance balance) {
        statementLines.add(new StatementLine(operation, balance));
    }

    public List<StatementLine> statementLines() {
        return statementLines;
    }
}
