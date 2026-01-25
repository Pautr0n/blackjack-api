package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

public record GameResponse(String id,
                           String playerId,
                           String status,
                           int playerScore,
                           int dealerScore
) {
}
