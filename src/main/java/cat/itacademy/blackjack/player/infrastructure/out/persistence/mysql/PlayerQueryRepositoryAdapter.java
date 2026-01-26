package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlayerQueryRepositoryAdapter implements PlayerQueryRepository {
    private final SpringDataPlayerRepository springRepo;

    public PlayerQueryRepositoryAdapter(SpringDataPlayerRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Flux<PlayerSummary> findAll() {
        return springRepo.findAll()
                .map(this::toSummary);
    }

    @Override
    public Mono<PlayerSummary> findById(String playerId) {
        return springRepo.findByDomainId(playerId)
                .map(this::toSummary);
    }

    @Override
    public Flux<PlayerSummary> searchByName(String partialName) {
        return springRepo.findAll()
                .filter(entity -> entity.getName().toLowerCase()
                        .contains(partialName.toLowerCase()))
                .map(this::toSummary);
    }

    @Override
    public Flux<PlayerRankingEntry> getRanking() {
        return springRepo.findAll()
                .sort((a, b) -> Integer.compare(b.getScore(), a.getScore()))
                .map(entity -> new PlayerRankingEntry(
                        entity.getDomainId(),
                        entity.getName(),
                        entity.getScore()
                ));
    }

    private PlayerSummary toSummary(PlayerEntity entity) {
        return new PlayerSummary(
                entity.getDomainId(),
                entity.getName(),
                entity.getScore()
        );
    }

}
