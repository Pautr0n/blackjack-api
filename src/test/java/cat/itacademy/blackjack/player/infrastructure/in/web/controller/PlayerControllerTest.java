package cat.itacademy.blackjack.player.infrastructure.in.web.controller;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.in.*;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.CreatePlayerRequest;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.RenamePlayerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PlayerController.class)
class PlayerControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreatePlayerUseCase createPlayer;

    @MockBean
    private RenamePlayerUseCase renamePlayer;

    @MockBean
    private GetPlayerUseCase getPlayer;

    @MockBean
    private FindAllPlayersUseCase findAllPlayers;

    @MockBean
    private SearchPlayerByNameUseCase searchPlayers;

    @MockBean
    private GetRankingUseCase getRanking;

    @MockBean
    private DeletePlayerUseCase deletePlayer;

    @Test
    void createPlayer_returnsPlayerResponse() {

        Player domainPlayer = Player.restore(
                new PlayerId("123"),
                "Pau",
                0
        );

        when(createPlayer.create(anyString()))
                .thenReturn(Mono.just(domainPlayer));


        webTestClient.post()
                .uri("/player")
                .bodyValue(new CreatePlayerRequest("Pau"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("123")
                .jsonPath("$.name").isEqualTo("Pau")
                .jsonPath("$.score").isEqualTo(0)
                .jsonPath("$.position").doesNotExist();
    }

    @Test
    void getPlayer_returnsPlayerResponse() {
        Player domainPlayer = Player.restore(
                new PlayerId("abc"),
                "John",
                10
        );

        when(getPlayer.getById(anyString()))
                .thenReturn(Mono.just(domainPlayer));

        webTestClient.get()
                .uri("/player/abc")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("abc")
                .jsonPath("$.name").isEqualTo("John")
                .jsonPath("$.score").isEqualTo(10);
    }

    @Test
    void getPlayer_notFound_returns404() {
        when(getPlayer.getById("xyz"))
                .thenReturn(Mono.error(new PlayerNotFoundException("Player not found")));

        webTestClient.get()
                .uri("/player/xyz")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void ranking_returnsListOfPlayers() {
        PlayerRankingEntry entry = new PlayerRankingEntry("1", "Pau", 50, 1);

        when(getRanking.getRanking())
                .thenReturn(Flux.just(entry));

        webTestClient.get()
                .uri("/player/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Pau")
                .jsonPath("$[0].score").isEqualTo(50)
                .jsonPath("$[0].position").isEqualTo(1);
    }

    @Test
    void renamePlayer_returnsPlayerResponse() {
        Player renamed = Player.restore(new PlayerId("123"), "NewName", 0);

        when(renamePlayer.rename(anyString(), anyString()))
                .thenReturn(Mono.just(renamed));

        webTestClient.put()
                .uri("/player/123")
                .bodyValue(new RenamePlayerRequest("NewName"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("123")
                .jsonPath("$.name").isEqualTo("NewName")
                .jsonPath("$.score").isEqualTo(0);
    }
    @Test
    void renamePlayer_notFound_returns404() {
        when(renamePlayer.rename(anyString(), anyString()))
                .thenReturn(Mono.error(new PlayerNotFoundException("Player not found")));

        webTestClient.put()
                .uri("/player/999")
                .bodyValue(new RenamePlayerRequest("NewName"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void findAllPlayers_returnsList() {
        Player p1 = Player.restore(new PlayerId("1"), "Alice", 10);
        Player p2 = Player.restore(new PlayerId("2"), "Bob", 20);

        when(findAllPlayers.findAll())
                .thenReturn(Flux.just(p1, p2));

        webTestClient.get()
                .uri("/player")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Alice")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].name").isEqualTo("Bob");
    }

    @Test
    void findAllPlayers_empty_returnsEmptyList() {
        when(findAllPlayers.findAll())
                .thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/player")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    void searchPlayers_returnsList() {
        Player p = Player.restore(new PlayerId("1"), "Pau", 0);

        when(searchPlayers.searchByName(anyString()))
                .thenReturn(Flux.just(p));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/player/search")
                        .queryParam("name", "Pau")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Pau");
    }

    @Test
    void searchPlayers_empty_returnsEmptyList() {
        when(searchPlayers.searchByName(anyString()))
                .thenReturn(Flux.empty());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/player/search")
                        .queryParam("name", "Nobody")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    void deletePlayer_returns204() {
        when(deletePlayer.deleteById(anyString()))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/player/123")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deletePlayer_notFound_returns404() {
        when(deletePlayer.deleteById(anyString()))
                .thenReturn(Mono.error(new PlayerNotFoundException("Player not found")));

        webTestClient.delete()
                .uri("/player/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createPlayer_invalidName_returns400() {
        when(createPlayer.create(anyString()))
                .thenReturn(Mono.error(new InvalidPlayerNameException("Invalid name")));

        webTestClient.post()
                .uri("/player")
                .bodyValue(new CreatePlayerRequest("  "))
                .exchange()
                .expectStatus().isBadRequest();
    }


}