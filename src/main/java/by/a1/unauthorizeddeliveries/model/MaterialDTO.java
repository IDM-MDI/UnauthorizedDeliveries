package by.a1.unauthorizeddeliveries.model;

import by.a1.unauthorizeddeliveries.entity.Item;
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
@Schema(description = "Item information")
public class MaterialDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "material id")
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "bought item")
    private ItemDTO item;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "position on posting")
    private Long itemPosition;
    @Schema(description = "quantity of item")
    private Integer quantity;
    @Schema(description = "base unit of measure")
    private String measurementUnit;
}
