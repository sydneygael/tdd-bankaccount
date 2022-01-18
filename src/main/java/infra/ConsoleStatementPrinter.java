package infra;

import org.sydneygael.example.domain.Statement;

public class ConsoleStatementPrinter implements StatementPrinter {

    @Override
    public void print(Statement statement) {
        new ConsoleConsumerPrinter().accept(statement);
    }
}
