package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import reactor.core.publisher.Mono;

public class GetGameService implements GetGameUseCase {

    private final GameRepository gameRepository;

    public GetGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Game> getById(String gameId) {
        return gameRepository.findById(gameId);
    }

}
