package cat.itacademy.blackjack.game.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = List.copyOf(cards);
    }

    public Hand add(Card card) {
        List<Card> newCards = new ArrayList<>(cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    public int score() {
        int total = cards.stream()
                .mapToInt(c -> c.rank().value())
                .sum();

        long aces = cards.stream()
                .filter(c -> c.rank() == Rank.ACE)
                .count();

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean isBust() {
        return score() > 21;
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }

}
