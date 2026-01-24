package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.in.DeletePlayerUseCase;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class DeletePlayerService implements DeletePlayerUseCase {

    private final PlayerRepository playerRepository;

    public DeletePlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        PlayerId playerId = new PlayerId(id);

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found")))
                .flatMap(player -> playerRepository.deleteById(playerId));
    }

}
