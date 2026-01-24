package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameRepositoryAdapterTest {
    @Mock
    private SpringDataGameRepository mongoRepo;

    @InjectMocks
    private GameRepositoryAdapter adapter;

    private static Deck deck;

    @BeforeAll
    static void initialSetUp(){
        deck = Deck.standard52Cards();
    }

    @Test
    void save_should_save_And_return_game() {
        Game game = Game.start(GameId.newId(), "123", deck);
        GameDocument doc = GameMapper.toDocument(game);

        when(mongoRepo.save(any(GameDocument.class))).thenReturn(Mono.just(doc));

        StepVerifier.create(adapter.save(game))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(game.id());
                    assertThat(saved.playerId()).isEqualTo("123");
                })
                .verifyComplete();

        verify(mongoRepo).save(any(GameDocument.class));
    }

    @Test
    void findById_should_return_game_when_found() {
        Game game = Game.start(GameId.newId(), "123", deck);
        GameDocument doc = GameMapper.toDocument(game);

        when(mongoRepo.findById(game.id().value())).thenReturn(Mono.just(doc));

        StepVerifier.create(adapter.findById(game.id()))
                .assertNext(found -> {
                    assertThat(found.id()).isEqualTo(game.id());
                    assertThat(found.playerId()).isEqualTo("123");
                })
                .verifyComplete();

        verify(mongoRepo).findById(game.id().value());
    }

    @Test
    void findById_should_return_empty_when_game_does_not_exist(){
        when(mongoRepo.findById("123")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findById(new GameId("123")))
                .verifyComplete();

        verify(mongoRepo).findById("123");

    }

    @Test
    void deleteById_should_return_empty() {
        GameId id = GameId.newId();

        when(mongoRepo.deleteById(id.value())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(id))
                .verifyComplete();

        verify(mongoRepo).deleteById(id.value());
    }

}