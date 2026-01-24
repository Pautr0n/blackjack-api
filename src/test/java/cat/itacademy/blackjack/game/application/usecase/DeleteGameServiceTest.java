package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DeleteGameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private DeleteGameService deleteGameService;

    @Test
    void deleteById_completes_even_if_game_not_exist(){

        String gameId = "game-123";

        when(gameRepository.deleteById(gameId)).thenReturn(Mono.empty());

        StepVerifier.create(deleteGameService.deleteById(gameId))
                .verifyComplete();

        verify(gameRepository).deleteById(gameId);

    }

}