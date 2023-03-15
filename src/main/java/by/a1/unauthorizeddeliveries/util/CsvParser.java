package by.a1.unauthorizeddeliveries.util;

import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface CsvParser<T> {
    List<T> parse(String path) throws IOException;
    default List<T> parse(String path, Class<T> clazz) throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(path))) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .build()
                    .parse();
        }
    }
}
