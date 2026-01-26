package cat.itacademy.blackjack.player.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response with player information")
public record PlayerResponse(@Schema(example = "123") String id,
                             @Schema(example = "Pau") String name,
                             @Schema(example = "10") int score
) {
}
