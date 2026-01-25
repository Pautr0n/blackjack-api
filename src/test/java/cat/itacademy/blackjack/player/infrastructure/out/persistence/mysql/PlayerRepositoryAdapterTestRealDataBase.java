package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PlayerRepositoryAdapterTestRealDataBase {

    @Autowired
    private PlayerRepository repository;

    @Test
    void saveAndFindPlayer() {
        Player player = Player.create("Pau");

        StepVerifier.create(repository.save(player))
                .assertNext(saved -> {
                    assertThat(saved.id().value()).isEqualTo(player.id().value());
                    assertThat(saved.name()).isEqualTo("Pau");
                })
                .verifyComplete();

        StepVerifier.create(repository.findById(player.id()))
                .assertNext(found -> {
                    assertThat(found.name()).isEqualTo("Pau");
                })
                .verifyComplete();
    }

}