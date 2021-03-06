package kg.brigada.telegrambot.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bludo {
    @Id
    @GeneratedValue
    Long id;

    String name;

    Double price;

    @ManyToOne
    BludaCategory bludaCategory;
}
