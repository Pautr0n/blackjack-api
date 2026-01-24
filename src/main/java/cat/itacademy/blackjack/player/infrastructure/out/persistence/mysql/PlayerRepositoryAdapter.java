package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class PlayerRepositoryAdapter implements PlayerRepository {

    private final SpringDataPlayerRepository repository;

    public PlayerRepositoryAdapter(SpringDataPlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Player> findById(PlayerId id) {
        return repository.findById(id.value())
                .map(PlayerMapper::toDomain);
    }

    @Override
    public Mono<Player> save(Player player) {
        PlayerEntity entity = PlayerMapper.toEntity(player);
        return repository.save(entity)
                .map(PlayerMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(PlayerId id) {
        return repository.deleteById(id.value());
    }

}
