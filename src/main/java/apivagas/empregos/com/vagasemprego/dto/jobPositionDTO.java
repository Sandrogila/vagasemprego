package apivagas.empregos.com.vagasemprego.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class jobPositionDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal salary;

    private String location;
    private String requirements;
    private String benefits;
    private LocalDateTime expiresAt;
}