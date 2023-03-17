package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Document Header")
public class DocumentHeaderDTO {
    @Schema(description = "document header id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "posting number")
    @Min(1)
    private Long postingNumber;
    @Schema(description = "username which bought")
    @NotBlank
    @Length(min = 3)
    private String username;
    @Schema(description = "is user active")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean authorizeDelivery;
    @Schema(description = "when contract subscribed")
    @PastOrPresent
    private LocalDate contractDate;
    @Schema(description = "when post will be delivered")
    @FutureOrPresent
    private LocalDate postingDate;
}
