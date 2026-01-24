package cat.itacademy.blackjack.player.infrastructure.out;

import cat.itacademy.blackjack.game.domain.port.out.PlayerInfo;
import cat.itacademy.blackjack.game.domain.port.out.PlayerLookupPort;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import reactor.core.publisher.Mono;

public class PlayerLookupAdapter implements PlayerLookupPort {
    private final PlayerQueryRepository queryRepository;

    public PlayerLookupAdapter(PlayerQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public Mono<PlayerInfo> findById(String playerId) {
        return queryRepository.findById(playerId)
                .map(summary -> new PlayerInfo(
                        summary.id(),
                        summary.name(),
                        summary.score()
                ));
    }
}
