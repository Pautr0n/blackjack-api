package cat.itacademy.blackjack.game.infrastructure.in.web.mapper;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.GameResponse;

public class GameApiMapper {

    public static GameResponse fromDomain(Game game) {
        return new GameResponse(
                game.id().value(),
                game.playerId(),
                game.status().name(),
                game.playerHand().score(),
                game.dealerHand().score()
        );
    }

}
