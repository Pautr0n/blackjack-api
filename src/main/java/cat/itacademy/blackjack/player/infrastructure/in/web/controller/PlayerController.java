package cat.itacademy.blackjack.player.infrastructure.in.web.controller;


import cat.itacademy.blackjack.player.domain.port.in.*;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.CreatePlayerRequest;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.PlayerResponse;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.RenamePlayerRequest;
import cat.itacademy.blackjack.player.infrastructure.in.web.mapper.PlayerApiMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
@Tag(name = "Player", description = "Player operations")
public class PlayerController {
    private final CreatePlayerUseCase createPlayer;
    private final RenamePlayerUseCase renamePlayer;
    private final GetPlayerUseCase getPlayer;
    private final FindAllPlayersUseCase findAllPlayers;
    private final SearchPlayerByNameUseCase searchPlayers;
    private final GetRankingUseCase getRanking;
    private final DeletePlayerUseCase deletePlayer;

    @PostMapping
    @Operation(
            summary = "Create a new player",
            description = "Creates a new player with an initial score of 0",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Player created"),
                    @ApiResponse(responseCode = "400", description = "Invalid player name")
            }
    )
    public Mono<ResponseEntity<PlayerResponse>> create(@RequestBody CreatePlayerRequest request) {
        return createPlayer.create(request.name())
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Rename a player",
            description = "Rename an existing player",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Player created"),
                    @ApiResponse(responseCode = "400", description = "Invalid player name"),
                    @ApiResponse(responseCode = "404", description = "Player not found")
            })
    public Mono<ResponseEntity<PlayerResponse>> rename(
            @PathVariable String id,
            @RequestBody RenamePlayerRequest request
    ) {
        return renamePlayer.rename(id, request.newName())
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a player by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Player found"),
                    @ApiResponse(responseCode = "404", description = "Player not found")
            }
    )
    public Mono<ResponseEntity<PlayerResponse>> get(@PathVariable String id) {
        return getPlayer.getById(id)
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    @Operation(
            summary = "Get all players",
            description = "Returns a list of all registered players with their current score.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of players returned successfully")
            }
    )
    public Flux<PlayerResponse> findAll() {
        return findAllPlayers.findAll()
                .map(PlayerApiMapper::fromDomain);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search players by name",
            description = "Returns all players whose name contains the given search term (case-insensitive).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matching players returned successfully"),
                    @ApiResponse(responseCode = "400", description = "Missing or invalid search parameter")
            }
    )
    public Flux<PlayerResponse> search(@RequestParam String name) {
        return searchPlayers.searchByName(name)
                .map(PlayerApiMapper::fromDomain);
    }

    @GetMapping("/ranking")
    @Operation(
            summary = "Get ranking of players",
            description = "Returns the ranking of players ordered by score in descending order. "
                    + "Each entry includes the player's position in the ranking.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ranking returned successfully")
            }
    )
    public Flux<PlayerResponse> ranking() {
        return getRanking.getRanking()
                .map(PlayerApiMapper::fromRanking);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a player",
            description = "Deletes the player with the specified ID. "
                    + "If the player does not exist, a 404 error is returned.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Player deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Player not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return deletePlayer.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
