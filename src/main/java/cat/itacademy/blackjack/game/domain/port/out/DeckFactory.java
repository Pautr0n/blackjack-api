package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.game.domain.model.Deck;

public interface DeckFactory {
    Deck create();
}
