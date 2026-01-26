package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new game")
public record CreateGameRequest(@Schema(example = "player-123") String playerId) {
}
