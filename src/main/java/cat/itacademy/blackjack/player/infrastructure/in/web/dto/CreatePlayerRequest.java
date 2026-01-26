package cat.itacademy.blackjack.player.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a new player")
public record CreatePlayerRequest(
        @NotBlank(message = "Player name cannot be blank")
        @Size(min = 3, message = "Player name must have at least 3 characters")
        @Schema(example = "Pau", description = "The name of the player")
        String name
) {
}
