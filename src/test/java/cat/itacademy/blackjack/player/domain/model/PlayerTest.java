package cat.itacademy.blackjack.player.domain.model;

import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerScoreException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlayerTest {
    @Test
    void create_should_normalize_multiple_spaces() {
        Player player = Player.create("  John   Doe  ");

        assertThat(player.name()).isEqualTo("John Doe");
    }


    @Test
    void create_should_throw_when_name_is_null() {
        assertThatThrownBy(() -> Player.create(null))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be null");
    }

    @Test
    void create_should_throw_when_name_is_empty_string() {
        assertThatThrownBy(() -> Player.create(""))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be empty");
    }

    @Test
    void create_should_throw_when_name_is_blank_spaces() {
        assertThatThrownBy(() -> Player.create("   "))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be empty");
    }

    @Test
    void create_should_throw_when_name_is_tab() {
        assertThatThrownBy(() -> Player.create("\t"))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be empty");
    }

    @Test
    void create_should_throw_when_name_is_newline() {
        assertThatThrownBy(() -> Player.create("\n"))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be empty");
    }

    @Test
    void create_should_throw_when_name_has_1_char() {
        assertThatThrownBy(() -> Player.create("a"))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name must have at least 3 characters");
    }

    @Test
    void create_should_throw_when_name_has_2_chars() {
        assertThatThrownBy(() -> Player.create("ab"))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name must have at least 3 characters");
    }

    @Test
    void create_should_throw_when_name_has_2_chars_after_trim() {
        assertThatThrownBy(() -> Player.create("  ab  "))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name must have at least 3 characters");
    }

    @Test
    void create_should_succeed_with_valid_name() {
        Player player = Player.create("Pau");

        assertThat(player.name()).isEqualTo("Pau");
        assertThat(player.score()).isZero();
        assertThat(player.id()).isNotNull();
    }

    @Test
    void create_should_normalize_name_correctly() {
        Player player = Player.create("   Pau   ");

        assertThat(player.name()).isEqualTo("Pau");
    }

    @Test
    void create_should_allow_multiword_names() {
        Player player = Player.create("John Doe");

        assertThat(player.name()).isEqualTo("John Doe");
    }

    @Test
    void rename_should_update_name_when_valid() {
        Player player = Player.create("Pau");

        Player renamed = player.rename("Carlos");

        assertThat(renamed.name()).isEqualTo("Carlos");
        assertThat(renamed.id()).isEqualTo(player.id()); // identidad no cambia
        assertThat(renamed.score()).isEqualTo(player.score());
    }
    @Test
    void rename_should_throw_when_name_is_null() {
        Player player = Player.create("Pau");

        assertThatThrownBy(() -> player.rename(null))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be null");
    }
    @Test
    void rename_should_throw_when_name_is_empty() {
        Player player = Player.create("Pau");

        assertThatThrownBy(() -> player.rename("   "))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name cannot be empty");
    }
    @Test
    void rename_should_throw_when_name_is_too_short() {
        Player player = Player.create("Pau");

        assertThatThrownBy(() -> player.rename("ab"))
                .isInstanceOf(InvalidPlayerNameException.class)
                .hasMessage("Player name must have at least 3 characters");
    }

    @Test
    void addScore_should_increase_score() {
        Player player = Player.create("Pau");

        Player updated = player.addScore(10);

        assertThat(updated.score()).isEqualTo(10);
        assertThat(updated.id()).isEqualTo(player.id());
        assertThat(updated.name()).isEqualTo(player.name());
    }
    @Test
    void addScore_should_throw_when_points_are_negative() {
        Player player = Player.create("Pau");

        assertThatThrownBy(() -> player.addScore(-5))
                .isInstanceOf(InvalidPlayerScoreException.class)
                .hasMessage("Cannot add negative points");
    }
    @Test
    void addScore_should_allow_zero_points() {
        Player player = Player.create("Pau");

        Player updated = player.addScore(0);

        assertThat(updated.score()).isEqualTo(0);
    }
    @Test
    void addScore_should_return_new_instance() {
        Player player = Player.create("Pau");

        Player updated = player.addScore(5);

        assertThat(updated).isNotSameAs(player);
    }

    @Test
    void rename_should_return_new_instance() {
        Player player = Player.create("Pau");

        Player renamed = player.rename("Carlos");

        assertThat(renamed).isNotSameAs(player);
    }


}