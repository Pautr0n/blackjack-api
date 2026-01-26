package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerScoreException;
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
class AddScoreServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    AddScoreService addScoreService;

    @Test
    void addScore_should_return_new_player_response_when_successful() {
        Player original = Player.create("Pau");
        PlayerId id = original.id();

        Player updated = original.addScore(10);

        when(playerRepository.findById(id)).thenReturn(Mono.just(original));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(updated));

        StepVerifier.create(addScoreService.addScore(id.value(), 10))
                .assertNext(response -> {
                    assertThat(response.domainId()).isEqualTo(id.value());
                    assertThat(response.name()).isEqualTo("Pau");
                    assertThat(response.score()).isEqualTo(10);
                })
                .verifyComplete();

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());

        Player saved = captor.getValue();
        assertThat(saved.score()).isEqualTo(10);
    }

    @Test
    void addScore_should_return_exception_when_player_does_not_exist() {
        PlayerId id = PlayerId.newId();

        when(playerRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(addScoreService.addScore(id.value(), 10))
                .expectErrorMatches(ex -> ex.getMessage().equals("Player not found"))
                .verify();

        verify(playerRepository, never()).save(any());
    }

    @Test
    void _should_propagate_InvalidPlayerScoreException_when_invalid_score_provided() {
        Player original = Player.create("Pau");
        PlayerId id = original.id();

        when(playerRepository.findById(id)).thenReturn(Mono.just(original));

        Mono<PlayerResponse> result = addScoreService.addScore(id.value(), -5);

        StepVerifier.create(result)
                .expectError(InvalidPlayerScoreException.class)
                .verify();

        verify(playerRepository, never()).save(any());
    }

}