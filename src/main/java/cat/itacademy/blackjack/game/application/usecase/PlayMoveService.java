package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.port.in.PlayCommand;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import reactor.core.publisher.Mono;

public class PlayMoveService implements PlayMoveUseCase {

    private final GameRepository gameRepository;
    private final DealerService dealerService;

    public PlayMoveService(GameRepository gameRepository,
                           DealerService dealerService) {
        this.gameRepository = gameRepository;
        this.dealerService = dealerService;
    }

    @Override
    public Mono<Game> play(String gameId, PlayCommand command) {

        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    Game updated;
                    switch (command.move()) {
                        case HIT -> updated = game.hit();
                        case STAND -> updated = game.stand(dealerService);
                        default -> updated = game;
                    }

                    return gameRepository.save(updated);
                });
    }

}
