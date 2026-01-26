package cat.itacademy.blackjack.player.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new player")
public record CreatePlayerRequest( @Schema(example = "Pau") String name) {
}
