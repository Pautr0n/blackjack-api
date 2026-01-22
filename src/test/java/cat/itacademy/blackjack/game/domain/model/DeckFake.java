package cat.itacademy.blackjack.game.domain.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
/*
* Fake Deck for testing purposes.
* */
public class DeckFake extends Deck{

    private final Deque<Card> fixedCards;

    public DeckFake(List<Card> cards) {
        super(List.of());
        this.fixedCards = new ArrayDeque<>(cards);
    }

    @Override
    public Card draw() {
        return fixedCards.pop();
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(fixedCards);
    }

}
