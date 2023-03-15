package by.a1.unauthorizeddeliveries.util.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvLoginModel {
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
