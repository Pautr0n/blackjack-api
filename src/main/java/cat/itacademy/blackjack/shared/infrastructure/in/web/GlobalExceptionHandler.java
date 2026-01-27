package cat.itacademy.blackjack.shared.infrastructure.in.web;

import cat.itacademy.blackjack.game.domain.model.exception.GameNotFoundException;
import cat.itacademy.blackjack.game.domain.model.exception.IllegalGameStateException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerScoreException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerTotalGamesException;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

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

    @ExceptionHandler(InvalidPlayerTotalGamesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidPlayerTotalGames(InvalidPlayerScoreException ex) {
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


    @ExceptionHandler(IllegalGameStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleIllegalGameState(IllegalGameStateException ex) {
        return Mono.just(new ErrorResponse(
                "ILLEGAL_GAME_STATE",
                ex.getMessage()
        ));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return Mono.just(new ErrorResponse(
                "VALIDATION_ERROR",
                message
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return Mono.just(new ErrorResponse(
                "VALIDATION_ERROR",
                message
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
