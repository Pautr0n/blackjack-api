package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.port.in.PlayerResponse;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    CreatePlayerService createPlayerService;

    @Test
    void create_should_create_and_save_player(){
        String playerName = "Pau";
        Player createdPlayer = Player.create(playerName);

        when(playerRepository.save(any(Player.class)))
                .thenReturn(Mono.just(createdPlayer));

        StepVerifier.create(createPlayerService.create(playerName))
                .assertNext(response ->{
                    assertThat(response.id()).isEqualTo(createdPlayer.id().value());
                    assertThat(response.name()).isEqualTo("Pau");
                    assertThat(response.score()).isEqualTo(0);
                })
                .verifyComplete();

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());

        Player saved = captor.getValue();
        assertThat(saved.name()).isEqualTo("Pau");
        assertThat(saved.score()).isZero();

    }

    @Test
    void create_should_throw_InvalidPlayerNameException_when_name_is_less_than_3_characters() {
        String invalidName = " ad ";

        InvalidPlayerNameException ex = assertThrows(
                InvalidPlayerNameException.class,
                () -> createPlayerService.create(invalidName) // se lanza aquí, antes del Mono
        );

        assertEquals("Player name must have at least 3 characters", ex.getMessage());
        verify(playerRepository, never()).save(any());
    }
}