package by.a1.unauthorizeddeliveries.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "PostingRequest")
public class PostingRequestDTO {
    @Schema(description = "document header")
    private DocumentHeaderDTO documentHeader;
    @Schema(description = "material")
    private MaterialDTO material;
}
