package by.a1.unauthorizeddeliveries.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvLoginModel implements CsvBean {
    @CsvBindByName(column = "Application")
    private String application;
    @CsvBindByName(column = "AppAccountName")
    private String accountName;
    @CsvBindByName(column = "IsActive")
    private String active;
    @CsvBindByName(column = "JobTitle")
    private String jobTitle;
    @CsvBindByName(column = "Department")
    private String department;

    @Override
    public void trim() {
        application = StringUtils.trim(application);
        accountName = StringUtils.trim(accountName);
        active = StringUtils.trim(active);
        jobTitle = StringUtils.trim(jobTitle);
        department = StringUtils.trim(department);
    }
}
