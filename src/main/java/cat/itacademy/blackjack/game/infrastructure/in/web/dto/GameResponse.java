package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Game state response")
public record GameResponse(
        @Schema(example = "game-123") String id,
        @Schema(example = "player-123") String playerId,
        @Schema(example = "IN_PROGRESS") String status,
        @Schema(example = "15") int playerScore,
        @Schema(example = "11") int dealerScore
) {
}
