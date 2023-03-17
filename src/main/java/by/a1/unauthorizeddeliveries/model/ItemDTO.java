package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    @Schema(description = "item id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "small description of current item")
    @NotBlank
    @Length(min = 1)
    private String description;
    @Schema(description = "the required amount of money to buy")
    @Min(1)
    private Integer amount;
    @Schema(description = "currency in which the item can be purchased")
    @NotBlank
    @Length(min = 1)
    private String currency;
}
