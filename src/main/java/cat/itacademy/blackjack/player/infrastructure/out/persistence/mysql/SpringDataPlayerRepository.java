package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataPlayerRepository extends ReactiveCrudRepository<PlayerEntity, String> {
}
