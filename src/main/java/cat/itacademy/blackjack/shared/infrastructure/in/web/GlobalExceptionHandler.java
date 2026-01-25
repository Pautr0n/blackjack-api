package cat.itacademy.blackjack.shared.infrastructure.in.web;

import cat.itacademy.blackjack.game.domain.model.exception.GameNotFoundException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerScoreException;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleGameNotFound(GameNotFoundException ex) {
        return Mono.just(new ErrorResponse(
                "GAME_NOT_FOUND",
                ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidPlayerNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidPlayerName(InvalidPlayerNameException ex) {
        return Mono.just(new ErrorResponse(
                "INVALID_PLAYER_NAME",
                ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidPlayerScoreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidPlayerScore(InvalidPlayerScoreException ex) {
        return Mono.just(new ErrorResponse(
                "INVALID_PLAYER_SCORE",
                ex.getMessage()
        ));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handlePlayerNotFound(PlayerNotFoundException ex) {
        return Mono.just(new ErrorResponse(
                "PLAYER_NOT_FOUND",
                ex.getMessage()
        ));
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleRuntime(RuntimeException ex) {
        return Mono.just(new ErrorResponse(
                "BAD_REQUEST",
                ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleGeneric(Exception ex) {
        return Mono.just(new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected error occurred"
        ));
    }

}
