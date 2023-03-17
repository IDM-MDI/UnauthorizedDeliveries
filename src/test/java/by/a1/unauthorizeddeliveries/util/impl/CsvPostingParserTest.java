package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

class CsvPostingParserTest {
    private static final String PATH = "src/test/resources/csv/postings.csv";
    private static final CsvPostingModel MODEL = CsvPostingModel.builder()
            .postingNumber("6777727662")
            .item("\t2")
            .contractDate("\t25.07.2020")
            .postingDate("\t25.07.2020")
            .materialDescription("\tHeadphones JBL C100SIU BLK black")
            .quantity("\t3")
            .measurementUnit("\tpc")
            .amount("\t38,1")
            .currency("\tBYN")
            .username("\tNLIMONOV")
            .build();

    private CsvParser<CsvPostingModel> parser;
    @BeforeEach
    void setUp() {
        parser = new CsvPostingParser();
    }

    @Test
    void parseShouldReturnCorrectModel() throws IOException {
        List<CsvPostingModel> actual = parser.parse(PATH);
        Assertions.assertThat(actual)
                .isNotEmpty()
                .contains(MODEL);
    }
    @Test
    void parseShouldThrowNoSuchFileFound() throws IOException {
        Assertions.assertThatThrownBy(() -> parser.parse("incorrect location"))
                .isInstanceOf(NoSuchFileException.class);
    }
}