package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.port.in.PlayCommand;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayMoveServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DealerService dealerService;

    @InjectMocks
    private PlayMoveService playMoveService;

    @BeforeEach
    void setup() {
        lenient().when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    }

    @Test
    void play_hit_updates_game_with_hit() {
        String gameId = "game-123";

        Game original = mock(Game.class);
        Game updated = mock(Game.class);

        when(gameRepository.findById(gameId))
                .thenReturn(Mono.just(original));

        when(original.hit()).thenReturn(updated);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .expectNext(updated)
                .verifyComplete();

        verify(gameRepository).findById(gameId);
        verify(original).hit();
        verify(gameRepository).save(updated);
        verify(original, never()).stand(any());
    }

    @Test
    void play_stand_updates_game_with_stand() {
        String gameId = "game-456";

        Game original = mock(Game.class);
        Game updated = mock(Game.class);

        when(gameRepository.findById(gameId))
                .thenReturn(Mono.just(original));

        when(original.stand(dealerService)).thenReturn(updated);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.STAND)))
                .expectNext(updated)
                .verifyComplete();

        verify(gameRepository).findById(gameId);
        verify(original).stand(dealerService);
        verify(gameRepository).save(updated);
        verify(original, never()).hit();
    }


    @Test
    void play_when_game_not_found_returns_empty() {
        String gameId = "missing";

        when(gameRepository.findById(gameId))
                .thenReturn(Mono.empty());

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .verifyComplete();

        verify(gameRepository).findById(gameId);
        verify(gameRepository, never()).save(any());
    }
}
