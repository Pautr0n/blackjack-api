package cat.itacademy.blackjack.game.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HandTest {

    @Test
    void empty_hand_has_score_zero() {
        Hand hand = Hand.empty();

        assertThat(hand.score()).isEqualTo(0);
    }

    @Test
    void hand_with_number_cards_sums_values() {
        Hand hand = Hand.empty()
                .addCard(new Card(Rank.FIVE, Suit.HEARTS))
                .addCard(new Card(Rank.SEVEN, Suit.CLUBS));

        assertThat(hand.score()).isEqualTo(12);
    }

    @Test
    void ace_counts_as_eleven_when_it_does_not_bust() {
        Hand hand = Hand.empty()
                .addCard(new Card(Rank.ACE, Suit.SPADES))
                .addCard(new Card(Rank.SIX, Suit.DIAMONDS));

        assertThat(hand.score()).isEqualTo(17);
    }

    @Test
    void ace_counts_as_one_when_eleven_would_bust() {
        Hand hand = Hand.empty()
                .addCard(new Card(Rank.ACE, Suit.SPADES))
                .addCard(new Card(Rank.NINE, Suit.DIAMONDS))
                .addCard(new Card(Rank.THREE, Suit.CLUBS));

        assertThat(hand.score()).isEqualTo(13); // 1 + 9 + 3
    }

    @Test
    void blackjack_is_detected_correctly() {
        Hand hand = Hand.empty()
                .addCard(new Card(Rank.ACE, Suit.SPADES))
                .addCard(new Card(Rank.KING, Suit.HEARTS));

        assertThat(hand.isBlackjack()).isTrue();
    }

    @Test
    void bust_is_detected_correctly() {
        Hand hand = Hand.empty()
                .addCard(new Card(Rank.TEN, Suit.SPADES))
                .addCard(new Card(Rank.NINE, Suit.HEARTS))
                .addCard(new Card(Rank.FIVE, Suit.CLUBS));

        assertThat(hand.isBust()).isTrue();
    }

    @Test
    void adding_a_card_returns_a_new_hand_instance() {
        Hand hand1 = Hand.empty();
        Hand hand2 = hand1.addCard(new Card(Rank.TWO, Suit.CLUBS));

        assertThat(hand1).isNotSameAs(hand2);
    }

}