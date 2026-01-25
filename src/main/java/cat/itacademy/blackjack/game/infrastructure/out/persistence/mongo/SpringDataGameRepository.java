package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataGameRepository  extends ReactiveMongoRepository<GameDocument, String> {
}
