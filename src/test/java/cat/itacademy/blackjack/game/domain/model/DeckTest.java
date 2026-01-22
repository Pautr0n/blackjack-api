package cat.itacademy.blackjack.game.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;


class DeckTest {

    @Test
    void standard_deck_has_52_cards() {
        Deck deck = Deck.standard52Cards();

        assertThat(deck.getCards()).hasSize(52);
    }

    @Test
    void standard_deck_has_52_unique_cards() {
        Deck deck = Deck.standard52Cards();

        assertThat(deck.getCards()).doesNotHaveDuplicates();
    }

    @Test
    void drawing_a_card_reduces_deck_size_by_one() {
        Deck deck = Deck.standard52Cards();

        assertThat(deck.getCards()).hasSize(52);

        Card first = deck.draw();
        assertThat(first).isNotNull();
        assertThat(deck.getCards()).hasSize(51);

        Card second = deck.draw();
        assertThat(second).isNotNull();
        assertThat(deck.getCards()).hasSize(50);

    }

    @Test
    void drawing_from_empty_deck_throws_exception() {
        Deck deck = new Deck(List.of());

        assertThatThrownBy(deck::draw)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deck_draws_cards_in_fifo_order() {

        List<Card> cards = List.of(
                new Card(Rank.TWO, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.FOUR, Suit.HEARTS)
        );

        Deck deck = new Deck(cards);

        Card c1 = deck.draw();
        Card c2 = deck.draw();
        Card c3 = deck.draw();

        assertThat(c1.rank()).isEqualTo(Rank.TWO);
        assertThat(c2.rank()).isEqualTo(Rank.THREE);
        assertThat(c3.rank()).isEqualTo(Rank.FOUR);
    }

}