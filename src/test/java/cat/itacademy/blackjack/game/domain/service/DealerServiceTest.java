package cat.itacademy.blackjack.game.domain.service;

import cat.itacademy.blackjack.game.domain.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DealerServiceTest {

    private DealerService dealerService = new DealerService();

    private Deck deckForDealerToReach17() {

        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.THREE, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.TWO, Suit.CLUBS)
        ));
    }

    private Deck deckWhereDealerBusts() {

        return new DeckFake(List.of(
                new Card(Rank.NINE, Suit.HEARTS),
                new Card(Rank.THREE, Suit.CLUBS),
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.TEN, Suit.CLUBS)
        ));
    }

    @Test
    void dealer_hits_until_reaching_17_or_more() {
        Deck deck = deckForDealerToReach17();

        Hand dealerHand = Hand.empty()
                .addCard(deck.draw());

        Hand finalHand = dealerService.playDealerTurn(dealerHand, deck);

        assertThat(finalHand.score()).isGreaterThanOrEqualTo(17);
        assertThat(finalHand.cards()).hasSize(3);

        Deck deck2 = deckWhereDealerBusts();
        Hand dealerHand2 = Hand.empty().addCard(deck2.draw());

        Hand finalHand2 = dealerService.playDealerTurn(dealerHand2,deck2);
        assertThat(finalHand2.score()).isGreaterThanOrEqualTo(17);
        assertThat(finalHand2.cards()).hasSize(4);

    }

    @Test
    void dealer_stops_when_reaching_17() {
        Deck deck = deckForDealerToReach17();

        Hand dealerHand = Hand.empty()
                .addCard(deck.draw()); // 10

        Hand finalHand = dealerService.playDealerTurn(dealerHand, deck);

        assertThat(finalHand.score()).isEqualTo(17);
    }

    @Test
    void dealer_can_bust_if_cards_exceed_21() {
        Deck deck = deckWhereDealerBusts();

        Hand dealerHand = Hand.empty()
                .addCard(deck.draw());

        Hand finalHand = dealerService.playDealerTurn(dealerHand, deck);

        assertThat(finalHand.score()).isGreaterThan(21);
        assertThat(finalHand.isBust()).isTrue();
    }

    @Test
    void dealer_uses_the_same_mutable_deck() {
        Deck deck = deckForDealerToReach17();

        Hand dealerHand = Hand.empty()
                .addCard(deck.draw());

        int before = deck.getCards().size();

        dealerService.playDealerTurn(dealerHand, deck);

        int after = deck.getCards().size();

        assertThat(after).isLessThan(before);
    }

}