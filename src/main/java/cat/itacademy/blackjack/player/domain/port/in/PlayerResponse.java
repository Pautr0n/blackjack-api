package cat.itacademy.blackjack.player.domain.port.in;

public record PlayerResponse(String domainId,
                             String name,
                             int score
) {
}
