package cat.itacademy.blackjack.player.infrastructure.in.web.mapper;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.PlayerResponse;

public class PlayerApiMapper {

    public static PlayerResponse fromDomain(Player player) {
        return new PlayerResponse(
                player.id().value(),
                player.name(),
                player.score(),
                null
        );
    }

    public static PlayerResponse fromRanking(PlayerRankingEntry entry) {
        return new PlayerResponse(
                entry.id(),
                entry.name(),
                entry.score()
        );
    }

    public static PlayerResponse fromSummary(PlayerSummary summary) {
        return new PlayerResponse(
                summary.id(),
                summary.name(),
                summary.score(),
                null
        );
    }

}
