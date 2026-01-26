package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.model.exception.GameNotFoundException;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public class GameRepositoryAdapter implements GameRepository {

    private final SpringDataGameRepository repository;

    public GameRepositoryAdapter(SpringDataGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Game> save(Game game) {
        GameDocument document = GameMapper.toDocument(game);
        return repository.save(document)
                .map(GameMapper::toDomain);
    }

    @Override
    public Mono<Game> findById(GameId id) {
        return repository.findById(id.value())
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found: " + id.value())))
                .map(GameMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(GameId id) {
        return repository.deleteById(id.value());
    }


}
