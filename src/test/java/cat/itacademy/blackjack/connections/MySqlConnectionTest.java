package cat.itacademy.blackjack.connections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

@SpringBootTest
public class MySqlConnectionTest {

    @Autowired
    private DatabaseClient client;

    @Test
    void can_connect_to_mysql() {
        StepVerifier.create(
                        client.sql("SELECT 1")
                                .map(row -> row.get(0, Integer.class))
                                .first()
                )
                .expectNext(1)
                .verifyComplete();
    }


}
