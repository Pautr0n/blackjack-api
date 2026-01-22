package cat.itacademy.blackjack.game.domain.service;

import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.model.Hand;

public class DealerService {

    public Hand playDealerTurn(Hand dealerHand, Deck deck) {
        Hand hand = dealerHand;

        while (hand.score() < 17) {
            hand = hand.addCard(deck.draw());
        }

        return hand;
    }

}
