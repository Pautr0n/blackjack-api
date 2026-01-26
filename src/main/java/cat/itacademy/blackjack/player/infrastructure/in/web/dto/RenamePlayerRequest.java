package cat.itacademy.blackjack.player.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to rename a player")
public record RenamePlayerRequest(@Schema(example = "newName") String newName) {
}
