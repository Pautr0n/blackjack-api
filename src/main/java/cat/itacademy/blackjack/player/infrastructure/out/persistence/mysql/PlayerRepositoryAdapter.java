package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PlayerRepositoryAdapter implements PlayerRepository {

    private final SpringDataPlayerRepository repository;

    public PlayerRepositoryAdapter(SpringDataPlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Player> findById(PlayerId id) {
        return repository.findByDomainId(id.value())
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found: " + id.value())))
                .map(PlayerMapper::toDomain);
    }

    @Override
    public Mono<Player> save(Player player) {
        String domainId = player.id().value();
        return repository.findByDomainId(domainId)
                .defaultIfEmpty(new PlayerEntity(
                        null,
                        domainId,
                        player.name(),
                        player.score()
                ))
                .flatMap(existing->{
                    existing.setDomainId(domainId);
                    existing.setName(player.name());
                    existing.setScore(player.score());
                    return repository.save(existing);
                })
                .map(PlayerMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(PlayerId id) {
        return repository.deleteByDomainId(id.value());
    }

}
