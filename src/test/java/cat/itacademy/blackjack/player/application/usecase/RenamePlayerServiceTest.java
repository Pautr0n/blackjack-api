package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RenamePlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    RenamePlayerService renamePlayerService;

    @Test
    void rename_returns_renamed_player_when_successful() {
        Player original = Player.create("Pau");
        PlayerId id = original.id();

        Player renamed = original.rename("Carlos");

        when(playerRepository.findById(id)).thenReturn(Mono.just(original));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(renamed));

        StepVerifier.create(renamePlayerService.rename(id.value(), "Carlos"))
                .assertNext(response -> {
                    assertThat(response.id()).isEqualTo(id.value());
                    assertThat(response.name()).isEqualTo("Carlos");
                    assertThat(response.score()).isZero();
                })
                .verifyComplete();

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());

        Player saved = captor.getValue();
        assertThat(saved.name()).isEqualTo("Carlos");
    }

    @Test
    void rename_returns_error_when_player_does_not_exist() {
        PlayerId id = PlayerId.newId();

        when(playerRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(renamePlayerService.rename(id.value(), "Carlos"))
                .expectErrorMatches(ex -> ex.getMessage().equals("Player not found"))
                .verify();

        verify(playerRepository, never()).save(any());
    }

    @Test
    void rename_should_propagate_InvalidNameException_when_invalid_name_is_provided() {
        Player original = Player.create("Pau");
        PlayerId id = original.id();

        when(playerRepository.findById(id)).thenReturn(Mono.just(original));

        StepVerifier.create(renamePlayerService.rename(id.value(), "ab"))
                .expectError(InvalidPlayerNameException.class)
                .verify();

        verify(playerRepository, never()).save(any());
    }

}