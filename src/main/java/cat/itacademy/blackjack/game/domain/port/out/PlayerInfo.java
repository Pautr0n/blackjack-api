package cat.itacademy.blackjack.game.domain.port.out;

public record PlayerInfo(String domainId,
                         String name,
                         int score
) {
}
