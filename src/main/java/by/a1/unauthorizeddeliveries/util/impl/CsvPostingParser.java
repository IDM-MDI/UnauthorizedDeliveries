package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvPostingParser implements CsvParser<CsvPostingModel> {
    @Override
    public List<CsvPostingModel> parse(String path) throws IOException {
        return parse(path, CsvPostingModel.class);
    }
}
