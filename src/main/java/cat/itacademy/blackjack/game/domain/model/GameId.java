package cat.itacademy.blackjack.game.domain.model;

import java.util.UUID;

public record GameId(String value) {

    public static GameId newId() {
        return new GameId(UUID.randomUUID().toString());
    }

}
