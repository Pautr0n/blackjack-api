package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.PlayerLookupPort;
import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public class CreateGameService implements CreateGameUseCase {

    private final PlayerLookupPort playerLookupPort;
    private final GameRepository gameRepository;
    private final DeckFactory deckFactory;

    public CreateGameService(PlayerLookupPort playerLookupPort,
                             GameRepository gameRepository,
                             DeckFactory deckFactory) {
        this.playerLookupPort = playerLookupPort;
        this.gameRepository = gameRepository;
        this.deckFactory = deckFactory;
    }


    @Override
    public Mono<Game> create(String playerName) {
        return playerLookupPort.findByName(playerName)
                .switchIfEmpty(Mono.error(new RuntimeException("Player does not exist")))
                .flatMap(playerInfo -> {
                    Game game = Game.start(
                            GameId.newId(),
                            playerInfo.id(),
                            deckFactory.create()
                    );
                    return gameRepository.save(game);
                });

    }

}
