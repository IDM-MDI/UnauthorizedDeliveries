package by.a1.unauthorizeddeliveries.util.impl;

import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.util.CsvParser;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvPostingParser extends CsvParser<CsvPostingModel> {
    @Override
    public List<CsvPostingModel> parse(String path) throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(path))) {
            return new CsvToBeanBuilder<CsvPostingModel>(reader)
                    .withType(CsvPostingModel.class)
                    .withFilter(stringValues -> Arrays.stream(stringValues)
                            .anyMatch(value -> value != null && value.length() > 0))
                    .withSeparator(';')
                    .build()
                    .parse();
        }
    }
}
