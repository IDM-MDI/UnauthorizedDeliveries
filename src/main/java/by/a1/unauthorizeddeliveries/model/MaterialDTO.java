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
@Schema(description = "Item information")
public class MaterialDTO {
    @Schema(description = "material id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "bought item")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ItemDTO item;
    @Schema(description = "position on posting")
    @Min(1)
    private Long itemPosition;
    @Schema(description = "quantity of item")
    @Min(1)
    private Integer quantity;
    @Schema(description = "base unit of measure")
    @NotBlank
    @Length(min = 1)
    private String measurementUnit;
}
