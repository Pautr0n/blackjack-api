package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import reactor.core.publisher.Mono;

public interface GameRepository {

    Mono<Game> save(Game game);

    Mono<Game> findById(GameId id);

    Mono<Void> deleteById(GameId id);

}
