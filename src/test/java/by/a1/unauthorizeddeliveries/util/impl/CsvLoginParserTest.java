package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class CsvLoginParserTest {
    private static final String PATH = "src/test/resources/csv/logins.csv";
    private static final CsvLoginModel MODEL = CsvLoginModel.builder()
            .application("SAP")
            .accountName("\tBORODZUTPNI")
            .active("\tTrue")
            .jobTitle("\tКомплектовщик товаров")
            .department("\tГруппа складского хозяйства")
            .build();
    private CsvParser<CsvLoginModel> parser;
    @BeforeEach
    void setup() {
        parser = new CsvLoginParser();
    }

    @Test
    void parseShouldReturnCorrectModel() throws IOException {
        List<CsvLoginModel> actual = parser.parse(PATH);
        Assertions.assertThat(actual)
                        .isNotEmpty()
                        .contains(MODEL);
    }
}