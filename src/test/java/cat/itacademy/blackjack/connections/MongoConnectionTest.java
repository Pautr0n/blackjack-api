package cat.itacademy.blackjack.connections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.test.StepVerifier;

@SpringBootTest
public class MongoConnectionTest {
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Test
    void can_Connect_to_mongodb() {
        StepVerifier.create(mongoTemplate.getCollectionNames())
                .expectNextCount(0)
                .thenCancel()
                .verify();
    }

}
