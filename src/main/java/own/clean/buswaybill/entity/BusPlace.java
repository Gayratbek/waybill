package own.clean.buswaybill.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BusPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busPlace")
    @JsonManagedReference
    private List<Driver> drivers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busPlace")
    @JsonManagedReference
    private List<Bus> buses;



}
