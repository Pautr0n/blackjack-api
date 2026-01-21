package cat.itacademy.blackjack.game.domain.port.in;

import cat.itacademy.blackjack.game.domain.model.Game;
import reactor.core.publisher.Mono;

public interface PlayMoveUseCase {

    Mono<Game> play(String gameId, PlayCommand command);

}
