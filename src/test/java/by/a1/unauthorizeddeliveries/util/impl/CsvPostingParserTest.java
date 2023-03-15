package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvPostingParserTest {
    private static final String PATH = "src/main/resources/csv/postings.csv";
    private CsvParser<CsvPostingModel> parser;
    @BeforeEach
    void setUp() {
        parser = new CsvPostingParser();
    }

    @Test
    void parseShouldReturnCorrectModel() throws IOException {
        List<CsvPostingModel> parse = parser.parse(PATH);
        System.out.println(parse);
    }
}