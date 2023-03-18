package by.a1.unauthorizeddeliveries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User-employee")
public class UserDTO {
    @Schema(description = "Login or simple username")
    @NotBlank
    @Length(min = 3)
    private String username;
    @Schema(description = "Application which user use")
    @NotBlank
    @Length(min = 2)
    private String application;
    @Schema(description = "Position current user")
    @NotBlank
    @Length(min = 2)
    private String job;
    @Schema(description = "Department which he work")
    @NotBlank
    @Length(min = 2)
    private String department;
    @Schema(description = "user status")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @Schema(description = "Is current user active")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean active;
}
