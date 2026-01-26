package cat.itacademy.blackjack.game.infrastructure.out.deck;

import cat.itacademy.blackjack.game.domain.model.Card;
import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;

import java.util.Collections;
import java.util.List;

public class StandardDeckFactory implements DeckFactory {

    @Override
    public Deck create() {
        List<Card> cards = Deck.standard52Cards();
        Collections.shuffle(cards);
        return new Deck(cards);
    }

}
