package cat.itacademy.blackjack.game.infrastructure.in.web.controller;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.CreateGameRequest;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.GameResponse;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.PlayRequest;
import cat.itacademy.blackjack.game.infrastructure.in.web.mapper.GameApiMapper;
import cat.itacademy.blackjack.game.infrastructure.in.web.mapper.PlayApiMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Tag(name = "Game", description = "Blackjack game operations")
public class GameController {


    private final CreateGameUseCase createGame;
    private final GetGameUseCase getGame;
    private final PlayMoveUseCase playMove;
    private final DeleteGameUseCase deleteGame;

    @PostMapping("/new")
    @Operation(
            summary = "Create a new Blackjack game",
            description = "Starts a new Blackjack game for the given player. "
                    + "The player receives two cards and the dealer receives one card. "
                    + "The game begins in IN_PROGRESS status.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Game created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid or missing player ID"),
                    @ApiResponse(responseCode = "404", description = "Player not found")
            }
    )
    public Mono<ResponseEntity<GameResponse>> create(@RequestBody CreateGameRequest request) {
        return createGame.create(request.playerId())
                .map(GameApiMapper::fromDomain)
                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get game details",
            description = "Retrieves the current state of the game, including player score, dealer score, and game status.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game found"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public Mono<ResponseEntity<GameResponse>> get(@PathVariable String id) {
        return getGame.getById(id)
                .map(GameApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/play")
    @Operation(
            summary = "Play a move in the game",
            description = "Applies a move (HIT or STAND) to the game. "
                    + "HIT draws a new card for the player. "
                    + "STAND triggers the dealer's turn and determines the final outcome.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Move applied successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid move type"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public Mono<ResponseEntity<GameResponse>> play(@PathVariable String id,
                                                   @RequestBody PlayRequest request) {
        return playMove.play(id, PlayApiMapper.toDomain(request))
                .map(GameApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(
            summary = "Delete a game",
            description = "Deletes the game with the specified ID. "
                    + "If the game does not exist, a 404 error is returned.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return deleteGame.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
