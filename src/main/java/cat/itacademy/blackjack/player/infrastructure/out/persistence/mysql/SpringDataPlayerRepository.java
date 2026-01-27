package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataPlayerRepository extends ReactiveCrudRepository<PlayerEntity, String> {
    Mono<PlayerEntity> findByDomainId(String domainId);
    Mono<Void> deleteByDomainId(String domainId);
}
