package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvPostingParser extends CsvParser<CsvPostingModel> {
    @Override
    public List<CsvPostingModel> parse(String path) throws IOException {
        return defaultBuilder(path, CsvPostingModel.class)
                .withSeparator(';')
                .build()
                .parse();
    }
}
