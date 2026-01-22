package cat.itacademy.blackjack.game.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Hand (List<Card> cards){


    public Hand{
        cards = List.copyOf(cards);
    }

    public static Hand empty() {
        return new Hand(List.of());
    }

    public Hand addCard(Card card) {
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

    public boolean isBlackjack() {
        return cards.size() == 2 && score() == 21;
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }

}
