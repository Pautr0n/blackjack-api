package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to play a move")
public record PlayRequest(@Schema(example = "HIT", allowableValues = {"HIT", "STAND"}) String moveType) {
}
