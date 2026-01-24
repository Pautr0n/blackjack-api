package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRankingServiceTest {

    @Mock
    PlayerQueryRepository playerQueryRepository;

    @InjectMocks
    GetRankingService getRankingService;

    @Test
    void shouldReturnRanking() {
        PlayerRankingEntry p1 = new PlayerRankingEntry("1", "Pau", 100, 1);
        PlayerRankingEntry p2 = new PlayerRankingEntry("2", "Anna", 80, 2);

        when(playerQueryRepository.getRanking()).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(getRankingService.getRanking())
                .assertNext(entry -> {
                    assertThat(entry.id()).isEqualTo("1");
                    assertThat(entry.name()).isEqualTo("Pau");
                    assertThat(entry.score()).isEqualTo(100);
                    assertThat(entry.position()).isEqualTo(1);
                })
                .assertNext(entry -> {
                    assertThat(entry.id()).isEqualTo("2");
                    assertThat(entry.name()).isEqualTo("Anna");
                    assertThat(entry.score()).isEqualTo(80);
                    assertThat(entry.position()).isEqualTo(2);
                })
                .verifyComplete();

        verify(playerQueryRepository).getRanking();
    }


}