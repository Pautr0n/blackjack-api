package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public interface PlayerLookupPort {

    Mono<PlayerInfo> findByName(String name);

}
