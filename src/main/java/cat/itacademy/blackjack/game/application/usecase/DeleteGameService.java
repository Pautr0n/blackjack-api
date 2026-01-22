package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import reactor.core.publisher.Mono;

public class DeleteGameService implements DeleteGameUseCase {

    private final GameRepository gameRepository;

    public DeleteGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Void> delete(String gameId) {
        return gameRepository.deleteById(gameId);
    }

}
