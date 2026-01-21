package cat.itacademy.blackjack.game.domain.service;

import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.model.Hand;

public class DealerService {

    public Hand playDealerTurn(Hand dealerHand, Deck deck) {
        Hand current = dealerHand;

        while (current.score() < 17) {
            current = current.add(deck.draw());
        }

        return current;
    }

}
