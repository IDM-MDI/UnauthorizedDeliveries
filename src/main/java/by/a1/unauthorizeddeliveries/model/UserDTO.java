package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User-employee")
public class UserDTO {
    @Schema(description = "Login or simple username")
    private String username;
    @Schema(description = "Application which user use")
    private String application;
    @Schema(description = "Position current user")
    private String job;
    @Schema(description = "Department which he work")
    private String department;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Is current user active")
    private Boolean active;
}
