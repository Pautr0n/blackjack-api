package cat.itacademy.blackjack.game.domain.model;

import java.util.*;

public class Deck {

    private final Deque<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = new ArrayDeque<>(cards);
    }

    public Card draw() {
        //Include to GlobalHandlerException
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot draw from an empty deck");
        }

        return cards.pop();
    }

    public static List<Card> standard52Cards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        return cards;
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

}
