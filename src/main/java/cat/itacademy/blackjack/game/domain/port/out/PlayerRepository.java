package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public interface PlayerRepository {
    Mono<Player> findById(String id);

    Mono<Player> findByName(String name);

    Mono<Player> save(Player player);

}
