package by.a1.unauthorizeddeliveries.util;

import by.a1.unauthorizeddeliveries.validator.CsvValidator;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class CsvParser<T> {
    public abstract List<T> parse(String path) throws IOException;
    protected List<T> defaultBuilder(String path, Class<T> clazz) throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(path))) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withFilter(CsvValidator::isLineNotEmpty)
                    .build()
                    .parse();
        }
    }
}
