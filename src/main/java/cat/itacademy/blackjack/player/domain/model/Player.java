package cat.itacademy.blackjack.player.domain.model;

import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerScoreException;

public class Player {

    private final PlayerId id;
    private final String name;
    private final int score;

    private Player(PlayerId id, String name, int score) {
        validateId(id);
        validateName(name);
        validateScore(score);


        this.id = id;
        this.name = normalizeName(name);
        this.score = score;
    }

    public static Player create(String name) {
        return new Player(PlayerId.newId(), name, 0);
    }

    public static Player restore(PlayerId playerid, String name, int score){
        return new Player(playerid, name, score);
    }


    public Player rename(String newName) {
        return new Player(id, newName, score);
    }

    public Player addScore(int points) {
        if (points < 0 ) throw new InvalidPlayerScoreException("Cannot add negative points");
        return new Player(id, name, score + points);
    }

    private void validateId(PlayerId id){
        if (id==null) throw new IllegalArgumentException("PlayerId cannot be null");
    }

    private void validateName(String name){
        if (name == null) {
            throw new InvalidPlayerNameException("Player name cannot be null");
        }

        String normalized = normalizeName(name);

        if (normalized.isEmpty()) {
            throw new InvalidPlayerNameException("Player name cannot be empty");
        }

        if (normalized.length() < 3) {
            throw new InvalidPlayerNameException("Player name must have at least 3 characters");
        }

    }

    private void validateScore(int score) {
        if (score < 0) {
            throw new InvalidPlayerScoreException("Player score cannot be negative");
        }
    }

    private String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }


    public PlayerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int score() {
        return score;
    }

}
