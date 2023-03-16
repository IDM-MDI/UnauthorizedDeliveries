package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "item id")
    private Long id;
    @Schema(description = "small description of current item")
    private String description;
    @Schema(description = "the required amount of money to buy")
    private Integer amount;
    @Schema(description = "currency in which the item can be purchased")
    private String currency;
}
