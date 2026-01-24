package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("players")
public class PlayerEntity {
    @Id
    private String id;

    private String name;

    private int score;

}
