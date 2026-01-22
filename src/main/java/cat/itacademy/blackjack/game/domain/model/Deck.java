package cat.itacademy.blackjack.game.domain.model;

import java.util.*;

public class Deck {

    private final Deque<Card> cards;

    public Deck(List<Card> cards) {
        List<Card> copy = new ArrayList<>(cards);
        Collections.shuffle(copy);
        this.cards = new ArrayDeque<>(copy);
    }

    public Card draw() {
        //Include to GlobalHandlerException
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot draw from an empty deck");
        }

        return cards.pop();
    }

    public static Deck standard52Cards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        return new Deck(cards);
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

}
