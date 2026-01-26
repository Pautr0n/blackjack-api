package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class GameRepositoryAdapterTestRealDataBase {

    @Autowired
    private GameRepository repository;

    @Test
    void save_and_find_game() {
        Game game = Game.start(GameId.newId(),"123",  new Deck(Deck.standard52Cards()));

        StepVerifier.create(repository.save(game))
                .assertNext(saved -> assertThat(saved.id()).isEqualTo(game.id()))
                .verifyComplete();

        StepVerifier.create(repository.findById(game.id()))
                .assertNext(found -> assertThat(found.id()).isEqualTo(game.id()))
                .verifyComplete();
    }


}