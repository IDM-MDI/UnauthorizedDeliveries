package by.a1.unauthorizeddeliveries.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "PostingResponse")
public class PostingResponseDTO {
    @Schema(description = "posting id")
    private Long id;
    @Schema(description = "document header")
    private DocumentHeaderDTO documentHeader;
    @Schema(description = "all materials of posting")
    private List<MaterialDTO> materials;
}
