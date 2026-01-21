package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.game.domain.model.Game;
import reactor.core.publisher.Mono;

public interface GameRepository {

    Mono<Game> save(Game game);

    Mono<Game> findById(String id);

    Mono<Void> deleteById(String id);

}
