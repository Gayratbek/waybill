package own.clean.buswaybill.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeName;
    private String description;

    public Route(String routeName, String description) {
        this.routeName = routeName;
        this.description = description;
    }
}
