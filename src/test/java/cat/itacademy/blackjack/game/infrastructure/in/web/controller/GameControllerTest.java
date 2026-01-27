package cat.itacademy.blackjack.game.infrastructure.in.web.controller;

import cat.itacademy.blackjack.game.domain.model.Deck;
import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.model.GameStatus;
import cat.itacademy.blackjack.game.domain.model.exception.GameNotFoundException;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.CreateGameRequest;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.PlayRequest;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = GameController.class)
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    CreateGameUseCase createGame;

    @MockBean
    GetGameUseCase getGame;

    @MockBean
    PlayMoveUseCase playMove;

    @MockBean
    DeleteGameUseCase deleteGame;

    private static PlayerId playerId;

    @BeforeAll
    static void setup(){
        playerId = new PlayerId("player-123");
    }

    @Test
    void create_game_returns_GameResponse() {
        Game domainGame = Game.start(GameId.newId(), playerId, new Deck(Deck.standard52Cards()));

        when(createGame.create(anyString())).thenReturn(Mono.just(domainGame));

        webTestClient.post()
                .uri("/game/new")
                .bodyValue(new CreateGameRequest("player-123"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(domainGame.id().value())
                .jsonPath("$.playerId").isEqualTo("player-123")
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");


    }

    @Test
    void getGame_returns_GameResponse() {
        Game domainGame = Game.start(
                new GameId("g1"),
                playerId,
                new Deck(Deck.standard52Cards())
        );

        when(getGame.getById("g1"))
                .thenReturn(Mono.just(domainGame));

        webTestClient.get()
                .uri("/game/g1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("g1")
                .jsonPath("$.playerId").isEqualTo("player-123");
    }

    @Test
    void getGame_notFound_returns404() {
        when(getGame.getById("missing"))
                .thenReturn(Mono.error(new GameNotFoundException("Game not found")));

        webTestClient.get()
                .uri("/game/missing")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void playMove_hit_returns_UpdatedGame() {
        Game updatedGame = Game.start(
                new GameId("g1"),
                playerId,
                new Deck(Deck.standard52Cards())
        ).hit();

        when(playMove.play(eq("g1"), any()))
                .thenReturn(Mono.just(updatedGame));

        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("HIT"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("g1")
                .jsonPath("$.status").isEqualTo(updatedGame.status().name());
    }

    @Test
    void playMove_stand_returnsUpdatedGame() {
        Game updatedGame = Game.start(
                new GameId("g1"),
                playerId,
                new Deck(Deck.standard52Cards())
        );

        when(playMove.play(eq("g1"), any()))
                .thenReturn(Mono.just(updatedGame));

        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("STAND"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("g1");
    }

    @Test
    void playMove_invalidMove_returns400() {
        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("INVALID"))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void playMove_gameNotFound_returns404() {
        when(playMove.play(eq("g1"), any()))
                .thenReturn(Mono.error(new GameNotFoundException("Game not found")));

        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("HIT"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteGame_returns204() {
        when(deleteGame.deleteById("g1"))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/game/g1/delete")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteGame_notFound_returns404() {
        when(deleteGame.deleteById("g1"))
                .thenReturn(Mono.error(new GameNotFoundException("Game not found")));

        webTestClient.delete()
                .uri("/game/g1/delete")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createGame_invalidPlayer_returns400() {
        when(createGame.create(anyString()))
                .thenReturn(Mono.error(new IllegalArgumentException("Invalid player")));

        webTestClient.post()
                .uri("/game/new")
                .bodyValue(new CreateGameRequest("   "))
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    void createGame_with_blank_playerId_returns400() {
        webTestClient.post()
                .uri("/game/new")
                .bodyValue(new CreateGameRequest("   "))  // Blank playerId
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createGame_with_null_playerId_returns400() {
        webTestClient.post()
                .uri("/game/new")
                .bodyValue("{\"playerId\": null}")  // Null playerId
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void playMove_with_blank_moveType_returns400() {
        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("   "))  // Blank moveType
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void playMove_with_invalid_moveType_returns400() {
        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("INVALID_MOVE"))  // Invalid moveType
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void playMove_with_illegal_game_state_returns400() {
        Game finishedGame = Game.start(
                new GameId("g1"),
                playerId,
                new Deck(Deck.standard52Cards())
        );
        Game finished = Game.restore(
                finishedGame.id(),
                finishedGame.playerId(),
                finishedGame.playerHand(),
                finishedGame.dealerHand(),
                finishedGame.deck(),
                GameStatus.PLAYER_WINS
        );

        when(playMove.play(eq("g1"), any()))
                .thenReturn(Mono.error(
                        new cat.itacademy.blackjack.game.domain.model.exception.IllegalGameStateException(
                                "Cannot hit when game is not in progress"
                        )
                ));

        webTestClient.post()
                .uri("/game/g1/play")
                .bodyValue(new PlayRequest("HIT"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo("ILLEGAL_GAME_STATE");
    }

}