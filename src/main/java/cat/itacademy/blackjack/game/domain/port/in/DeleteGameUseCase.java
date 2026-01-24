package cat.itacademy.blackjack.game.domain.port.in;

import reactor.core.publisher.Mono;

public interface DeleteGameUseCase {

    Mono<Void> deleteById(String gameId);

}
