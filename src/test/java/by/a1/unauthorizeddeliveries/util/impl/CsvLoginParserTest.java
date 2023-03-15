package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class CsvLoginParserTest {
    private static final String PATH = "src/main/resources/csv/logins.csv";
    private CsvParser<CsvLoginModel> parser;
    @BeforeEach
    void setup() {
        parser = new CsvLoginParser();
    }

    @Test
    void parseShouldReturnCorrectModel() throws IOException {
        List<CsvLoginModel> parsed = parser.parse(PATH);
        parsed.forEach(CsvLoginModel::trim);
        System.out.println(parsed);
    }
}