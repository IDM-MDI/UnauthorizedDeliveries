package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Document Header")
public class DocumentHeaderDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "document header id")
    private Long id;
    @Schema(description = "posting number")
    private Long postingNumber;
    @Schema(description = "username which bought")
    private String username;
    @Schema(description = "is user active")
    private Boolean authorizeDelivery;
    @Schema(description = "when contract subscribed")
    private LocalDate contractDate;
    @Schema(description = "when post will be delivered")
    private LocalDate postingDate;
}
