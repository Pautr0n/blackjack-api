package cat.itacademy.blackjack.game.infrastructure.in.web.mapper;

import cat.itacademy.blackjack.game.domain.port.in.PlayCommand;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.PlayRequest;

public class PlayApiMapper {

    public static PlayCommand toDomain(PlayRequest request) {
        return new PlayCommand(
                PlayCommand.Move.valueOf(request.moveType().toUpperCase())
        );
    }

}
