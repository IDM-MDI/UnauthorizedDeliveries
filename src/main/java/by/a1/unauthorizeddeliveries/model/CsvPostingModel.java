package by.a1.unauthorizeddeliveries.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvPostingModel implements CsvBean {
    @CsvBindByName(column = "Mat. Doc.")
    private String id;
    @CsvBindByName(column = "Item")
    private String item;
    @CsvBindByName(column = "Doc. Date")
    private String contractDate;
    @CsvBindByName(column = "Pstng Date")
    private String postingDate;
    @CsvBindByName(column = "Material Description")
    private String materialDescription;
    @CsvBindByName(column = "Quantity")
    private String quantity;
    @CsvBindByName(column = "BUn")
    private String measurementUnit;
    @CsvBindByName(column = "Amount LC")
    private String amount;
    @CsvBindByName(column = "Crcy")
    private String currency;
    @CsvBindByName(column = "User Name")
    private String username;

    @Override
    public void trim() {
        id = StringUtils.trim(id);
        item = StringUtils.trim(item);
        contractDate = StringUtils.trim(contractDate);
        postingDate = StringUtils.trim(postingDate);
        materialDescription = StringUtils.trim(materialDescription);
        quantity = StringUtils.trim(quantity);
        measurementUnit = StringUtils.trim(measurementUnit);
        amount = StringUtils.trim(amount);
        currency = StringUtils.trim(currency);
        username = StringUtils.trim(username);
    }
}
