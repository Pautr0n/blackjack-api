package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
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
class FindAllPlayersServiceTest {

    @Mock
    PlayerQueryRepository queryRepository;

    @InjectMocks
    FindAllPlayersService findAllPlayersService;

    @Test
    void findAll_should_return_flux_with_all_players() {
        PlayerSummary p1 = new PlayerSummary("1", "Pau", 100);
        PlayerSummary p2 = new PlayerSummary("2", "Anna", 80);
        PlayerSummary p3 = new PlayerSummary("3","Laura",90);

        when(queryRepository.findAll())
                .thenReturn(Flux.just(p1, p2, p3));

        StepVerifier.create(findAllPlayersService.findAll())
                .assertNext(s -> assertThat(s.name()).isEqualTo("Pau"))
                .assertNext(s -> assertThat(s.name()).isEqualTo("Anna"))
                .assertNext(s->assertThat(s.name()).isEqualTo("Laura"))
                .verifyComplete();

        verify(queryRepository).findAll();
    }

    @Test
    void findAll_should_return_empty_flux_when_no_players(){
        when(queryRepository.findAll())
                .thenReturn(Flux.empty());

        StepVerifier.create(findAllPlayersService.findAll())
                .verifyComplete();

        verify(queryRepository).findAll();
    }


}