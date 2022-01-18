package infra;

import org.sydneygael.example.domain.Statement;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class ConsoleConsumerPrinter implements Consumer<Statement> {

    private final String header = "| OPERATION | DATE | AMOUNT | BALANCE |\n";
    private final String line = "| %s | %s | %s | %s |\n";
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void accept(Statement statement) {
        StringBuilder stringBuilder = new StringBuilder(header);
        statement.statementLines().forEach(l->{
            String printedLine = String.format(line,
                    l.operation().operationType(),
                    l.operation().date().format(formatter),
                    l.operation().amount().value(),
                    l.balance().value());
            stringBuilder.append(printedLine);
        });

        System.out.print(stringBuilder);
    }
}
