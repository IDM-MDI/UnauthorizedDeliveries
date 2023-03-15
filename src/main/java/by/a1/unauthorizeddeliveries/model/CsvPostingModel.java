package by.a1.unauthorizeddeliveries.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvPostingModel implements CsvBeanMarker {
    @CsvBindByName(column = "Mat. Doc.")
    private long id;
    @CsvBindByName(column = "Item")
    private long item;
    @CsvBindByName(column = "Doc. Date")
    private LocalDate contractDate;
    @CsvBindByName(column = "Pstng Date")
    private LocalDate postingDate;
    @CsvBindByName(column = "Material Description")
    private String materialDescription;
    @CsvBindByName(column = "Quantity")
    private int quantity;
    @CsvBindByName(column = "BUn")
    private String measurementUnit;
    @CsvBindByName(column = "Amount LC")
    private long amount;
    @CsvBindByName(column = "Crcy")
    private String currency;
    @CsvBindByName(column = "User Name")
    private String username;
}
