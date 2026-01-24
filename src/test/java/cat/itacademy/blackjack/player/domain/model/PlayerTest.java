package cat.itacademy.blackjack.player.domain.model;

import cat.itacademy.blackjack.player.domain.model.exception.InvalidPlayerNameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlayerTest {

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

}