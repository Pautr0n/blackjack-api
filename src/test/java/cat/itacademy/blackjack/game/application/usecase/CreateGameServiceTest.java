package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.*;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.PlayerInfo;
import cat.itacademy.blackjack.game.domain.port.out.PlayerLookupPort;
import cat.itacademy.blackjack.player.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGameServiceTest {

    @Mock
    private PlayerLookupPort playerLookupPort;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DeckFactory deckFactory;

    @InjectMocks
    private CreateGameService createGameService;

    @BeforeEach
    void setup() {
        lenient().when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    }


    @Test
    void creates_game_for_existing_player() {
        String playerName = "Pau";
        PlayerInfo existingPlayer = new PlayerInfo("1234","Pau",0);

        when(playerLookupPort.findByName(anyString()))
                .thenReturn(Mono.just(existingPlayer));

        Deck deck = mock(Deck.class);
        when(deckFactory.create()).thenReturn(deck);

        when(deck.draw()).thenReturn(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.FIVE, Suit.SPADES)
        );

        StepVerifier.create(createGameService.create(playerName))
                .assertNext(game -> {
                    assertThat(game.playerId()).isEqualTo(existingPlayer.id());
                    assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
                })
                .verifyComplete();

        verify(playerLookupPort).findByName(anyString());
        verify(deckFactory).create();
        verify(gameRepository).save(any(Game.class));

    }


    @Test
    void uses_deck_from_factory() {
        String playerName = "Pau";
        Player existingPlayer = Player.create(playerName);

        when(playerLookupPort.findByName(eq(playerName)))
                .thenReturn(Mono.just(existingPlayer));

        Deck deck = mock(Deck.class);
        when(deckFactory.create()).thenReturn(deck);

        when(deck.draw()).thenReturn(
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.TWO, Suit.HEARTS),
                new Card(Rank.THREE, Suit.CLUBS)
        );

        StepVerifier.create(createGameService.create(playerName))
                .assertNext(game -> {
                    assertThat(game.getDeck()).isSameAs(deck);
                })
                .verifyComplete();

        verify(deckFactory).create();
    }
}
