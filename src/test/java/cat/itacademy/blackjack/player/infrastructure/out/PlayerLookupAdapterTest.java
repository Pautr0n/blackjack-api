package cat.itacademy.blackjack.player.infrastructure.out;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerLookupAdapterTest {

    @Mock
    private PlayerQueryRepository queryRepository;

    @InjectMocks
    private PlayerLookupAdapter adapter;

    @Test
    void findById_should_return_player_info_when_player_exists() {
        PlayerSummary summary = new PlayerSummary("123", "Pau", 50);

        when(queryRepository.findById("123")).thenReturn(Mono.just(summary));

        StepVerifier.create(adapter.findById("123"))
                .assertNext(info -> {
                    assertThat(info.domainId()).isEqualTo("123");
                    assertThat(info.name()).isEqualTo("Pau");
                    assertThat(info.score()).isEqualTo(50);
                })
                .verifyComplete();

        verify(queryRepository).findById("123");
    }

    @Test
    void findById_should_return_empty_when_player_does_not_exist() {
        when(queryRepository.findById("999")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findById("999"))
                .verifyComplete();

        verify(queryRepository).findById("999");
    }

}