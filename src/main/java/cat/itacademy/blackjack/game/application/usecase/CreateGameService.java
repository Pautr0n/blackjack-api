package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.PlayerRepository;
import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public class CreateGameService implements CreateGameUseCase {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final DeckFactory deckFactory;

    public CreateGameService(PlayerRepository playerRepository,
                             GameRepository gameRepository,
                             DeckFactory deckFactory) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.deckFactory = deckFactory;
    }


    @Override
    public Mono<Game> create(String playerName) {
        return playerRepository.findByName(playerName)
                .switchIfEmpty(createNewPlayer(playerName))
                .flatMap(player -> {
                    Game game = Game.start(
                            GameId.newId(),
                            player.id().value(),
                            deckFactory.create()
                    );
                    return gameRepository.save(game);
                });

    }

    private Mono<Player> createNewPlayer(String name) {
        Player newPlayer = Player.create(name);
        return playerRepository.save(newPlayer);
    }

}
