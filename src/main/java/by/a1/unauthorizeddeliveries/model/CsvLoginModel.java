package by.a1.unauthorizeddeliveries.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvLoginModel implements CsvBeanMarker {
    @CsvBindByName(column = "Application")
    private String application;
    @CsvBindByName(column = "AppAccountName")
    private String accountName;
    @CsvBindByName(column = "IsActive")
    private boolean active;
    @CsvBindByName(column = "JobTitle")
    private String jobTitle;
    @CsvBindByName(column = "Department")
    private String department;
}
