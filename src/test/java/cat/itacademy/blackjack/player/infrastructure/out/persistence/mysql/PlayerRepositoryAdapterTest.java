package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayerRepositoryAdapterTest {

    @Mock
    private SpringDataPlayerRepository springRepo;

    @InjectMocks
    private PlayerRepositoryAdapter adapter;


    @Test
    void save_should_return_player_when_saved_successfully() {
        Player player = Player.create("Pau");
        PlayerEntity entity = PlayerMapper.toEntity(player);

        when(springRepo.save(any(PlayerEntity.class))).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.save(player))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(player.id());
                    assertThat(saved.name()).isEqualTo("Pau");
                    assertThat(saved.score()).isEqualTo(0);
                })
                .verifyComplete();

        verify(springRepo).save(any(PlayerEntity.class));
    }

    @Test
    void findById_should_return_player_when_found() {
        Player player = Player.create("Pau");
        PlayerEntity entity = PlayerMapper.toEntity(player);

        when(springRepo.findById(player.id().value())).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.findById(player.id()))
                .assertNext(found -> {
                    assertThat(found.id()).isEqualTo(player.id());
                    assertThat(found.name()).isEqualTo("Pau");
                })
                .verifyComplete();

        verify(springRepo).findById(player.id().value());
    }

    @Test
    void findById_should_return_empty_when_player_does_not_exist(){

        when(springRepo.findById("1234")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findById(new PlayerId("1234")))
                .verifyComplete();

        verify(springRepo).findById("1234");
    }

    @Test
    void deleteById_should_return_empty_always() {
        PlayerId id = PlayerId.newId();

        when(springRepo.deleteById(id.value())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(id))
                .verifyComplete();

        verify(springRepo).deleteById(id.value());
    }


}