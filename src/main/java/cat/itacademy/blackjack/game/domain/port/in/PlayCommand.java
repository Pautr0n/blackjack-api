package cat.itacademy.blackjack.game.domain.port.in;

public record PlayCommand(Move move) {

    public enum Move {
        HIT,
        STAND

    }
}
