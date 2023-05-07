package own.clean.buswaybill.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stateNumber;

    @ManyToOne()
    @JoinColumn(name = "bus_place_id")
    @JsonBackReference
    private BusPlace busPlace;

    public Bus(Long id, String stateNumber) {
        this.id = id;
        this.stateNumber = stateNumber;
    }
}
