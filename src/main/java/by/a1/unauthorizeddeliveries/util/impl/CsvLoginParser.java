package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvLoginParser implements CsvParser<CsvLoginModel> {
    @Override
    public List<CsvLoginModel> parse(String path) throws IOException {
        return parse(path,CsvLoginModel.class);
    }
}