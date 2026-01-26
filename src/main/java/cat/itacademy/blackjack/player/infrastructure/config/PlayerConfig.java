package cat.itacademy.blackjack.player.infrastructure.config;

import cat.itacademy.blackjack.game.domain.port.out.PlayerLookupPort;
import cat.itacademy.blackjack.player.application.usecase.*;
import cat.itacademy.blackjack.player.domain.port.in.*;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.infrastructure.out.PlayerLookupAdapter;
import cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql.PlayerQueryRepositoryAdapter;
import cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql.PlayerRepositoryAdapter;
import cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql.SpringDataPlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {


    @Bean
    public PlayerQueryRepository playerQueryRepository(SpringDataPlayerRepository springRepo) {
        return new PlayerQueryRepositoryAdapter(springRepo);
    }

    @Bean
    public PlayerLookupPort playerLookupPort(PlayerQueryRepository queryRepository) {
        return new PlayerLookupAdapter(queryRepository);
    }


    @Bean
    public CreatePlayerUseCase createPlayerUseCase(PlayerRepository repo) {
        return new CreatePlayerService(repo);
    }

    @Bean
    public RenamePlayerUseCase renamePlayerUseCase(PlayerRepository repo) {
        return new RenamePlayerService(repo);
    }

    @Bean
    public GetPlayerUseCase getPlayerUseCase(PlayerRepository repo) {
        return new GetPlayerService(repo);
    }

    @Bean
    public FindAllPlayersUseCase findAllPlayersUseCase(PlayerQueryRepository repo) {
        return new FindAllPlayersService(repo);
    }

    @Bean
    public SearchPlayerByNameUseCase searchPlayersUseCase(PlayerQueryRepository repo) {
        return new SearchPlayersByNameService(repo);
    }

    @Bean
    public GetRankingUseCase getRankingUseCase(PlayerQueryRepository repo) {
        return new GetRankingService(repo);
    }

    @Bean
    public DeletePlayerUseCase deletePlayerUseCase(PlayerRepository repo) {
        return new DeletePlayerService(repo);
    }

}
